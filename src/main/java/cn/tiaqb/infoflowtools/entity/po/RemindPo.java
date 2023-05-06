package cn.tiaqb.infoflowtools.entity.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/21 4:33 PM
 * @since 1.0
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "remind_table")
public class RemindPo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 创建提醒的用户
     */
    @Column(name = "uid")
    private String uid;

    /**
     * 提醒内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 提醒时间
     * yyyy-MM-dd HH:mm:ss
     */
    @Column(name = "timer")
    private String timer;

    /**
     * 创建时间
     */
    @Column(name = "created")
    private Date created;

    /**
     * 修改时间
     */
    @Column(name = "modified")
    private Date modified;

    /**
     * 状态
     * 1：正常数据
     * -1：失效数据
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 群组Id
     */
    @Column(name = "group_id")
    private Long groupId;

    /**
     * 艾特人群，逗号分隔
     */
    @Column(name = "ats")
    private String ats;
}
