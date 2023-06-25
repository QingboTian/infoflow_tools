package cn.tiaqb.infoflowtools.config;

import cn.tiaqb.infoflowtools.constant.MessageConstant;
import cn.tiaqb.infoflowtools.dao.RemindDao;
import cn.tiaqb.infoflowtools.entity.Message;
import cn.tiaqb.infoflowtools.entity.po.RemindPo;
import cn.tiaqb.infoflowtools.service.remind.RemindService;
import cn.tiaqb.infoflowtools.utils.DateUtils;
import cn.tiaqb.infoflowtools.utils.UuidUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author tianqingbo_dxm
 * @date 2023/5/5 7:37 PM
 * @since 1.0
 */
@Component
@Slf4j
public class RemindInitialization implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${remind.group.url}")
    private String url;

    @Autowired
    private RemindDao remindDao;
    @Autowired
    private RemindService remindService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("开始初始化事项提醒数据...");
        ApplicationContext parent = event.getApplicationContext().getParent();
        AtomicInteger count = new AtomicInteger();
        // 只有在父容器初始化完成才会触发 其他容器不触发
        if (parent == null) {
            // 查询提醒数据
            RemindPo request = new RemindPo();
            request.setState(1);
            Example<RemindPo> example = Example.of(request);
            List<RemindPo> all = remindDao.findAll(example);
            if (ObjectUtils.isEmpty(all)) {
                return;
            }

            all.forEach(item -> {
                String timer = item.getTimer();
                Date parse = DateUtils.parse(timer, DateUtils.LONG_WEB_FORMAT);
                // 在当前时间之后并且群组id不为空的才会初始化提醒
                if (parse.after(new Date()) && !ObjectUtils.isEmpty(item.getGroupId())) {
                    Message message = new Message();
                    message.setGroupId(item.getGroupId());
                    message.setUid(item.getUid());
                    if (!ObjectUtils.isEmpty(item.getAts())) {
                        message.setAts(new ArrayList<>(Arrays.stream(item.getAts().split(MessageConstant.SEPARATOR_2)).collect(Collectors.toSet())));
                    }
                    message.setMessage(item.getContent());
                    message.setRobotUrl(url);
                    message.setMessageId(item.getId());
                    message.setTraceId(UuidUtils.uuid());
                    boolean ok = remindService.remindNotPersistence(message, timer);
                    if (ok) {
                        count.getAndIncrement();
                        log.info("提醒数据 = {}", JSONObject.toJSONString(item));
                    }
                }
            });
        }
        log.info("结束初始化事项提醒数据，共初始化{}条数据...", count.get());
    }
}
