package cn.tiaqb.infoflowtools.service.message;

import cn.tiaqb.infoflowtools.entity.Message;
import cn.tiaqb.infoflowtools.entity.UserMessageEntity;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/25 1:12 PM
 * @since 1.0
 */
public interface MessageService {

    void send(Message message);

    void sendAll(Message message);

    void save(UserMessageEntity entity);


}
