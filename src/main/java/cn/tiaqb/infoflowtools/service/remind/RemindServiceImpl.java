package cn.tiaqb.infoflowtools.service.remind;

import cn.tiaqb.infoflowtools.constant.MessageConstant;
import cn.tiaqb.infoflowtools.dao.RemindDao;
import cn.tiaqb.infoflowtools.entity.Message;
import cn.tiaqb.infoflowtools.entity.Remind;
import cn.tiaqb.infoflowtools.entity.UserMessageEntity;
import cn.tiaqb.infoflowtools.entity.po.RemindPo;
import cn.tiaqb.infoflowtools.enums.MessageTypeEnum;
import cn.tiaqb.infoflowtools.service.message.MessageService;
import cn.tiaqb.infoflowtools.service.template.TemplateService;
import cn.tiaqb.infoflowtools.utils.DateUtils;
import cn.tiaqb.infoflowtools.utils.MessageUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/21 4:20 PM
 * @since 1.0
 */
@Service
@Slf4j
public class RemindServiceImpl implements RemindService {

    @Value("${remind.group.url}")
    private String url;

    @Autowired
    private RemindDao remindDao;
    @Autowired
    private MessageService messageService;
    @Autowired
    private TemplateService templateService;

    final private static Map<MessageTypeEnum, Consumer<UserMessageEntity>> PROCESSOR = new HashMap<>(8);

    @PostConstruct
    public void init() {
        Consumer<UserMessageEntity> processor1 = userMessageEntity -> {
            // 给用户回复消息 说明消息我已经接收到了
            Message message = MessageUtils.buildMessage(userMessageEntity, MessageConstant.PROCESSING);
            messageService.send(message);
            // 入库
            messageService.save(userMessageEntity);

            // 将用户的文本信息解析出来
            Remind remind = MessageUtils.parseRemind(userMessageEntity);
            // 设置提醒内容
            message.setMessage(remind.getContent());
            // 设置定时通知
            remind(message, remind.getTimer());
        };
        PROCESSOR.put(MessageTypeEnum.REMIND, processor1);

        Consumer<UserMessageEntity> processor2 = userMessageEntity -> {
            // 给用户回复消息 说明消息我已经接收到了
            Message message = MessageUtils.buildMessage(userMessageEntity, MessageConstant.PROCESSING);
            messageService.send(message);
            // 入库消息
            messageService.save(userMessageEntity);

            // 查询指定群组&用户提醒列表
            List<RemindPo> list = searchRemindListByUserAndGroup(message.getUid(), message.getGroupId());
            if (ObjectUtils.isEmpty(list)) {
                message.setMessage("您当前暂无提醒列表，可以通过相关文档使用提醒功能");
                messageService.send(message);
                return;
            }

            // 查询消息模版
            String template = templateService.queryTemplateById("00001");
            // 遍历提醒列表 发送消息
            list.forEach(remind -> {
                String content = remind.getContent();
                String timer = remind.getTimer();
                String msg = String.format(template, content, timer);
                message.setMessage(msg);
                messageService.send(message);
            });
        };
        PROCESSOR.put(MessageTypeEnum.SEARCH_REMIND_LIST, processor2);
    }

    /**
     * 定时提醒
     *
     * @param message message
     * @return
     */
    @Override
    public boolean remind(Message message, String timer) {
        if (message == null) {
            return false;
        }

        // 持久化数据
        Date now = new Date();
        RemindPo remindPo = new RemindPo();
        remindPo.setUid(message.getUid());
        remindPo.setContent(message.getMessage());
        remindPo.setTimer(timer);
        remindPo.setCreated(now);
        remindPo.setModified(now);
        remindPo.setState(1);
        remindPo.setGroupId(message.getGroupId());
        if (!ObjectUtils.isEmpty(message.getAts())) {
            remindPo.setAts(String.join(MessageConstant.SEPARATOR_2, message.getAts()));
        }
        RemindPo save = remindDao.save(remindPo);
        if (save == null || save.getId() == null) {
            String msg = String.format("保存消息失败, remind = %s", JSONObject.toJSONString(remindPo));
            log.error(msg);
            return false;
        }

        message.setMessageId(save.getId());

        // 执行时间
        Date parse = DateUtils.parse(timer, DateUtils.LONG_WEB_FORMAT);
        Date timerNow = new Date();
        if (parse.before(timerNow)) {
            log.info("提醒时间设置不合理，提醒时间在当前时间之前, remind = {}, 当前时间 = {}", JSONObject.toJSONString(remindPo), timerNow);
            message.setMessage(MessageConstant.SET_TIME_ERROR);
            messageService.send(message);
            expire(message.getMessageId());
            return false;
        }

        // 创建timer
        Timer t = new Timer();
        t.schedule(new RemindTask(message, messageService, this), parse);
        return true;
    }

    @Override
    public void expire(Long id) {
        try {
            RemindPo one = remindDao.findOne(id);
            one.setState(-1);
            remindDao.save(one);
        } catch (Exception ex) {
            log.error("invalid data occur an error", ex);
        }
    }

    @Override
    public boolean remindNotPersistence(Message message, String timer) {
        if (message == null || ObjectUtils.isEmpty(timer)) {
            return false;
        }

        // 执行时间
        Date parse = DateUtils.parse(timer, DateUtils.LONG_WEB_FORMAT);
        Date timerNow = new Date();
        if (parse.before(timerNow)) {
            log.info("提醒时间设置不合理，提醒时间在当前时间之前, message = {}, 当前时间 = {}", JSONObject.toJSONString(message), timerNow);
            return false;
        }

        // 创建timer
        Timer t = new Timer();
        t.schedule(new RemindTask(message, messageService, this), parse);
        return true;
    }

    @Override
    public void apply(UserMessageEntity entity) {
        try {
            MessageTypeEnum type = MessageUtils.getMessageType(entity);
            if (type == null) {
                applyError(entity);
                return;
            }
            log.info("receive message success, message type = {}", JSONObject.toJSONString(type));
            PROCESSOR.getOrDefault(type, (e) -> {}).accept(entity);
        } catch (Exception ex) {
            log.error("消息解析异常", ex);
            applyError(entity);
        }
    }

    private void applyError(UserMessageEntity entity) {
        String msg = templateService.queryTemplateById("00003");
        Message message = MessageUtils.buildMessage(entity, msg);
        messageService.send(message);
    }

    private List<RemindPo> searchRemindListByUserAndGroup(String userId, Long groupId) {
        RemindPo request = new RemindPo();
        request.setState(1);
        request.setGroupId(groupId);
        request.setUid(userId);
        log.info("searchRemindListByUserAndGroup request = {}", JSONObject.toJSONString(request));
        Example<RemindPo> example = Example.of(request);
        List<RemindPo> list = remindDao.findAll(example);
        log.info("searchRemindListByUserAndGroup response = {}", JSONObject.toJSONString(list));
        return list;
    }
}
