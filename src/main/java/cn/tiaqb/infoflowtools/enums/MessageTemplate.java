package cn.tiaqb.infoflowtools.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author tianqingbo_dxm
 * @date 2023/7/10 8:23 PM
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public enum MessageTemplate {
    /**
     *
     */
    REMIND_ITEM_FOR_LIST("1", "【提醒事项】：%s\n【提醒时间】：%s\n"),
    NOT_PARSE("2", "当前消息无法解析，您可以发送指定内容触发相关功能\n1.您可以发送【提醒列表】关键字并@gogogo机器人，就可以查看您在当前群组的提醒事项列表\n2.您可以发送以下格式内容并@gogogo机器人来创建提醒：\n【消息类型】:remind\n【提醒时间】:2023-07-07 14:00:00\n【提醒内容】:年中绩效自评\n若您的这个提醒也想通知到其他人，也可以在该提醒的开头或者末尾艾特指定同学！\n3.若您还是不理解，详见：https://tianqb.cn/infoflow/robot/doc\n"),
    DOC("3", "您可以发送指定内容触发相关功能\n1.您可以发送【提醒列表】关键字并@gogogo机器人，就可以查看您在当前群组的提醒事项列表\n2.您可以发送以下格式内容并@gogogo机器人来创建提醒：\n【消息类型】:remind\n【提醒时间】:2023-07-07 14:00:00\n【提醒内容】:年中绩效自评\n若您的这个提醒也想通知到其他人，也可以在该提醒的开头或者末尾艾特指定同学！\n3.若您还是不理解，详见：https://tianqb.cn/infoflow/robot/doc\n"),
    ;

    final private String id;
    final private String content;

    public static MessageTemplate getById(String id) {
        if (id == null) {
            return null;
        }
        for (MessageTemplate value : MessageTemplate.values()) {
            if (Objects.equals(value.getId(), id)) {
                return value;
            }
        }
        return null;
    }
}
