package cn.tiaqb.infoflowtools.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/25 4:11 PM
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public enum MessageTypeEnum {
    /**
     *
     */
    REMIND("remind", "用户主动设置提醒事项"),
    SEARCH_REMIND_LIST("search_remind_list", "用户查询提醒事项列表"),
    ;

    final private String type;
    final private String desc;

    public static MessageTypeEnum type(String type) {
        for (MessageTypeEnum value : values()) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return null;
    }

}
