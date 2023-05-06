package cn.tiaqb.infoflowtools.utils;

import org.springframework.util.ObjectUtils;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/21 4:30 PM
 * @since 1.0
 */
public class DataUtil {

    final static private String TIMER_REG = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)) ([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";

    public static boolean check(Object ...obj) {
        for (Object o : obj) {
            if (ObjectUtils.isEmpty(o)) {
                return true;
            }
        }
        return false;
    }

    public static boolean webTimeReg(String time) {
        if (time == null) {
            return false;
        }
        return time.matches(TIMER_REG);
    }

}
