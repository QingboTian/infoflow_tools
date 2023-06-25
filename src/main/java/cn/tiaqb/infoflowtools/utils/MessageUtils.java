package cn.tiaqb.infoflowtools.utils;

import cn.tiaqb.infoflowtools.constant.Constant;
import cn.tiaqb.infoflowtools.constant.MessageConstant;
import cn.tiaqb.infoflowtools.entity.Message;
import cn.tiaqb.infoflowtools.entity.Remind;
import cn.tiaqb.infoflowtools.entity.UserMessageEntity;
import cn.tiaqb.infoflowtools.enums.MessageTypeEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.slf4j.MDC;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/25 1:15 PM
 * @since 1.0
 */
@Slf4j
public class MessageUtils {

    public static void post(Map<String, Object> content, String url) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, JSONObject.toJSONString(content));
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        log.info("message utils request content = {}", JSONObject.toJSONString(content));

        try {
            Response response = client.newCall(request).execute();
            if (response != null) {
                log.info("message utils response = {}", JSONObject.toJSONString(response));
                response.body().close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String parseTimer(UserMessageEntity entity) {
        String text = parseText(entity);
        Remind remind = parseRemindContent(text);
        return remind.getTimer();
    }

    public static Remind parseRemind(UserMessageEntity entity) {
        String text = parseText(entity);
        return parseRemindContent(text);
    }

    public static Remind parseRemindContent(String content) {
        // 按照回车进行字符串分割
        String[] split = content.split(MessageConstant.ENTER);
        Remind remind = new Remind();
        String separator = MessageConstant.SEPARATOR;

        for (int i = 0; i < split.length; i++) {
            String line = split[i];
            String[] col = line.split(separator);
            if (col.length < 2) {
                continue;
            }

            String key = col[0];
            String value = line.substring(line.indexOf(separator) + 1);
            if (i == 1) {
                if (!Objects.equals("【消息类型】", key)) {
                    throw new RuntimeException();
                }
                if (!Objects.equals(value, MessageTypeEnum.REMIND.getType())) {
                    throw new RuntimeException();
                }
            }
            if (i == 2) {
                if (!Objects.equals("【提醒时间】", key)) {
                    throw new RuntimeException();
                }
                if (!DataUtil.webTimeReg(value)) {
                    throw new RuntimeException();
                }
                remind.setTimer(value);
            }
            if (i == 3) {
                if (!Objects.equals("【提醒内容】", key)) {
                    throw new RuntimeException();
                }
                if (ObjectUtils.isEmpty(value)) {
                    throw new RuntimeException();
                }
                remind.setContent(value);
            }
        }
        return remind;
    }

    public static MessageTypeEnum getMessageType(UserMessageEntity userMessageEntity) {
        try {
            String content = parseText(userMessageEntity);
            String[] split = content.split(MessageConstant.ENTER);
            String type = split[1].substring(split[1].indexOf(MessageConstant.SEPARATOR) + 1);
            return MessageTypeEnum.type(type);
        } catch (Exception ex) {
            log.error("解析消息类型发生错误", ex);
        }
        return null;
    }

    public static Message buildMessage(UserMessageEntity entity, String msg) {
        Message message = new Message();
        message.setRobotUrl(entity.getRobotUrl());
        message.setGroupId(entity.getGroupid());
        message.setUid(entity.getMessage().getHeader().getFromuserid());
        message.setMessage(msg);
        message.setAts(MessageUtils.parseAT(entity));
        // 设置消息所属trace
        message.setTraceId(MDC.get(Constant.INFO_FLOW_TRACE_ID));
        return message;
    }

    public static Message buildMessage(UserMessageEntity entity) {
        Message message = new Message();
        message.setRobotUrl(entity.getRobotUrl());
        message.setGroupId(entity.getGroupid());
        message.setUid(entity.getMessage().getHeader().getFromuserid());
        message.setAts(MessageUtils.parseAT(entity));
        return message;
    }

    public static Message buildMessage(Remind remind) {
        Message message = new Message();
        message.setRobotUrl(remind.getRobotUrl());
        message.setGroupId(remind.getGroupId());
        message.setUid(remind.getUid());
        message.setMessage(remind.getContent());
        message.setAts(remind.getAts());
        return message;
    }

    public static String parseText(UserMessageEntity entity) {
        StringJoiner content = new StringJoiner(MessageConstant.EMPTY);
        List<UserMessageEntity.Body> body = entity.getMessage().getBody();
        for (UserMessageEntity.Body b : body) {
            if (Objects.equals(b.getType(), MessageConstant.TEXT_TYPE)) {
                content.add(b.getContent());
            }
        }
        return content.toString();
    }

    public static List<String> parseAT(UserMessageEntity entity) {
        List<String> ats = new ArrayList<>();
        List<UserMessageEntity.Body> body = entity.getMessage().getBody();
        for (UserMessageEntity.Body b : body) {
            // 将机器人的id过滤掉
            if (Objects.equals(b.getType(), MessageConstant.AT_TYPE) && !Objects.equals(4100073388L, b.getRobotid())) {
                ats.add(b.getUserid());
            }
        }
        return ats;
    }
}
