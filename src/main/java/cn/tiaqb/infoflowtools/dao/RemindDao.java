package cn.tiaqb.infoflowtools.dao;

import cn.tiaqb.infoflowtools.entity.po.RemindPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/21 4:36 PM
 * @since 1.0
 */
public interface RemindDao extends JpaRepository<RemindPo, Long> {
}
