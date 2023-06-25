package cn.tiaqb.infoflowtools.service.remind;

import cn.tiaqb.infoflowtools.entity.Message;
import cn.tiaqb.infoflowtools.entity.UserMessageEntity;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/21 4:20 PM
 * @since 1.0
 */
public interface RemindService {
    /**
     * 消息定时提醒
     *
     * @param message 提醒消息
     * @param timer   提醒时间
     * @return 是否成功
     */
    boolean remind(Message message, String timer);

    /**
     * 消息定时提醒 但是数据不持久化
     *
     * @param message 提醒消息
     * @param timer   提醒时间
     * @return 是否成功
     */
    boolean remindNotPersistence(Message message, String timer);

    /**
     * 消息处理
     *
     * @param entity 消息实体
     */
    void apply(UserMessageEntity entity);

    /**
     * 置指定id的提醒为失效数据
     *
     * @param id 提醒id
     */
    void expire(Long id);
}
