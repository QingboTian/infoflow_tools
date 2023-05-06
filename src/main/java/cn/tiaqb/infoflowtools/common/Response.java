package cn.tiaqb.infoflowtools.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/21 4:21 PM
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class Response<T> {

    private int code;

    private String msg;

    private T data;

    public static <T> Response<T> ok(T data) {
        Response<T> response = new Response<>();
        response.code = 200;
        response.msg = "success";
        response.data = data;
        return response;
    }

    public static <T> Response<T> ok() {
        Response<T> response = new Response<>();
        response.code = 200;
        response.msg = "success";
        return response;
    }

    public static <T> Response<T> checkError() {
        Response<T> response = new Response<>();
        response.code = 50000;
        response.msg = "illegal parameter";
        return response;
    }

}
