package cn.tiaqb.infoflowtools.service.message;

import cn.tiaqb.infoflowtools.dao.MessageDao;
import cn.tiaqb.infoflowtools.entity.Message;
import cn.tiaqb.infoflowtools.entity.UserMessageEntity;
import cn.tiaqb.infoflowtools.entity.po.MessagePo;
import cn.tiaqb.infoflowtools.utils.Assert;
import cn.tiaqb.infoflowtools.utils.MessageUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/25 1:12 PM
 * @since 1.0
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public void send(Message message) {
        Assert.isEmpty(message);
        Assert.isEmpty(message.getRobotUrl(), message.getGroupId(), message.getUid());
        Map<String, Object> content = buildSendContent(message, false);
        post(content, message.getRobotUrl());
    }

    @Override
    public void sendAll(Message message) {
        Assert.isEmpty(message);
        Assert.isEmpty(message.getRobotUrl(), message.getGroupId());
        Map<String, Object> content = buildSendContent(message, true);
        post(content, message.getRobotUrl());
    }

    @Override
    public MessagePo save(UserMessageEntity entity) {
        MessagePo message = new MessagePo();
        message.setMessageId(entity.getMessage().getHeader().getMsgseqid());
        message.setUid(entity.getMessage().getHeader().getFromuserid());
        message.setGroupId(entity.getGroupid().toString());
        message.setBody(JSONObject.toJSONString(entity.getMessage().getBody()));
        message.setCreated(new Date());
        message.setModified(new Date());
        message.setState(1);
        return messageDao.save(message);
    }

    private void post(Map<String, Object> content, String url) {
        try {
            MessageUtils.post(content, url);
        } catch (Exception ex) {
            log.error("发送消息发生异常", ex);
        }
    }

    private Map<String, Object> buildSendContent(Message message, boolean all) {
        Map<String, Object> result = new HashMap<>(8);

        // 消息体
        Map<String, Object> msg = new HashMap<>(8);

        // 往哪个群发消息
        Map<String, Object> header = new HashMap<>(8);
        header.put("toid", Collections.singletonList(message.getGroupId()));
        msg.put("header", header);

        // 正文
        List<Map<String, Object>> bodyList = new ArrayList<>();
        // 消息内容
        Map<String, Object> body1 = new HashMap<>(8);
        body1.put("content", message.getMessage());
        body1.put("type", "TEXT");
        bodyList.add(body1);
        // 发送给谁
        Map<String, Object> body2 = new HashMap<>(8);
        if (all) {
            body2.put("atall", true);
        } else {
            body2.put("atall", false);
            List<String> atUserIds = new ArrayList<>(Optional.ofNullable(message.getAts()).orElse(new ArrayList<>()));
            body2.put("atuserids", atUserIds);
        }
        body2.put("type", "AT");
        bodyList.add(body2);
        msg.put("body", bodyList);

        result.put("message", msg);
        return result;
    }
}
