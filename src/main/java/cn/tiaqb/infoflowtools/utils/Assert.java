package cn.tiaqb.infoflowtools.utils;

import cn.tiaqb.infoflowtools.exception.BizException;
import org.springframework.util.ObjectUtils;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/25 1:20 PM
 * @since 1.0
 */
public class Assert {
    public static void isEmpty(Object... obj) {
        if (obj == null) {
            throw new BizException(50000, "illegal parameter");
        }
        for (Object o : obj) {
            if (ObjectUtils.isEmpty(o)) {
                throw new BizException(50000, "illegal parameter");
            }
        }
    }
}
