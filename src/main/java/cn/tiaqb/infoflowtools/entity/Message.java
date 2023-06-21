package cn.tiaqb.infoflowtools.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/25 1:12 PM
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class Message {
    /**
     * db primary key
     */
    private Long messageId;
    private Long groupId;
    private String uid;
    private List<String> ats;
    private String message;
    private String robotUrl;
}
