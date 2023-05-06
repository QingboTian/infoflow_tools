package cn.tiaqb.infoflowtools.dao;

import cn.tiaqb.infoflowtools.entity.po.MessagePo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/25 3:52 PM
 * @since 1.0
 */
public interface MessageDao extends JpaRepository<MessagePo, Long> {

}