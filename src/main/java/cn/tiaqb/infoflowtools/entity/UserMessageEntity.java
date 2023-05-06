package cn.tiaqb.infoflowtools.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/25 2:42 PM
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class UserMessageEntity {
    private String eventtype;
    private Long agentid;
    private Long groupid;
    private String corpid;
    private Long fromid;
    private String opencode;
    private Long time;
    private Message message;
    private String robotUrl;

    @Getter
    @Setter
    @ToString
    public static class Message {

        private Header header;

        private List<Body> body;
    }

    @Getter
    @Setter
    @ToString
    public static class Header {
        private String fromuserid;
        private Long toid;
        private String totype;
        private String msgtype;
        private Long clientmsgid;
        private Long messageid;
        private String msgseqid;
        private Object at;
        private String compatible;
        private String offlinenotify;
        private String extra;
    }

    @Getter
    @Setter
    @ToString
    public static class Body {
        private String type;
        private String commandname;
        private String content;
        private Long robotid;
        private String name;
        private String label;
        private String downloadurl;
        private String userid;
    }
}
