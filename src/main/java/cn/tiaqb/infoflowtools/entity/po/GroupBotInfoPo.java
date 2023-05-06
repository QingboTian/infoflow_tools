package cn.tiaqb.infoflowtools.entity.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/25 10:35 AM
 * @since 1.0
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "group_bot_info_table")
public class GroupBotInfoPo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bot_url")
    private String botUrl;

    @Column(name = "bot_aes_key")
    private String botAesKey;

    @Column(name = "bot_token")
    private String botToken;

    @Column(name = "state")
    private Integer state;

    @Column(name = "describe")
    private String describe;
}
