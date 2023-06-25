package cn.tiaqb.infoflowtools.utils;

import java.util.UUID;

/**
 * @author tianqingbo_dxm
 * @date 2023/6/25 4:38 PM
 * @since 1.0
 */
public class UuidUtils {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
