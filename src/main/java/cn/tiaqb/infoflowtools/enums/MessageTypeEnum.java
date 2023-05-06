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
    REMIND("remind");

    private String type;

    public static MessageTypeEnum type(String type) {
        for (MessageTypeEnum value : values()) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return null;
    }

}
