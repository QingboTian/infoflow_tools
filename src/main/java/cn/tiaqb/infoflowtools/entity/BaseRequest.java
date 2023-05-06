package cn.tiaqb.infoflowtools.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/24 7:47 PM
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class BaseRequest {
    private String signature;
    private String timestamp;
    private String rn;
    private String echostr;
}
