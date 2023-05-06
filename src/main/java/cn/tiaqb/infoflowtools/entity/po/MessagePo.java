package cn.tiaqb.infoflowtools.entity.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/25 3:52 PM
 * @since 1.0
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "message_table")
public class MessagePo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "message_id")
    private String messageId;

    @Column(name = "uid")
    private String uid;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "body")
    private String body;

    @Column(name = "created")
    private Date created;

    @Column(name = "modified")
    private Date modified;

    @Column(name = "state")
    private Integer state;
}
