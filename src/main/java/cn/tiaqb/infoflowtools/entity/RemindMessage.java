package cn.tiaqb.infoflowtools.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/25 4:13 PM
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class RemindMessage {
    private String type;
    private String timer;
    private String content;

}
