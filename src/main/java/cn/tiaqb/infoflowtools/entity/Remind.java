package cn.tiaqb.infoflowtools.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/21 4:26 PM
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class Remind {

    private String timer;
    private String content;
    private String uid;
    private Long groupId;
    private String robotUrl;
    private List<String> ats;


}
