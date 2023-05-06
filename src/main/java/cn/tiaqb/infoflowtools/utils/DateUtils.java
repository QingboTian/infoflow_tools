package cn.tiaqb.infoflowtools.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/21 5:08 PM
 * @since 1.0
 */
public class DateUtils {

    final public static String LONG_WEB_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date parse(String date, String format) throws IllegalArgumentException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
