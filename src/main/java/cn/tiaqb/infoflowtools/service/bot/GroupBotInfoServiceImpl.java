package cn.tiaqb.infoflowtools.service.bot;

import cn.tiaqb.infoflowtools.dao.GroupBotInfoDao;
import cn.tiaqb.infoflowtools.entity.po.GroupBotInfoPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/25 10:59 AM
 * @since 1.0
 */
@Service
@Slf4j
public class GroupBotInfoServiceImpl implements GroupBotInfoService {

    @Autowired
    private GroupBotInfoDao groupBotInfoDao;


    @Override
    public GroupBotInfoPo queryOne() {
        GroupBotInfoPo request = new GroupBotInfoPo();
        request.setState(1);
        Example<GroupBotInfoPo> example = Example.of(request);
        List<GroupBotInfoPo> list = groupBotInfoDao.findAll(example);
        if (ObjectUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }
}
