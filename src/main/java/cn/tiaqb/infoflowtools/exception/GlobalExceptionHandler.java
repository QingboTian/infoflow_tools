package cn.tiaqb.infoflowtools.exception;

import cn.tiaqb.infoflowtools.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/25 2:35 PM
 * @since 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Response<Void> bizException(BizException exception) {
        log.error(exception.getMessage(), exception);
        Response<Void> response = new Response<>();
        response.setCode(exception.getCode());
        response.setMsg(exception.getMessage());
        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response<Void> exception(Exception exception) {
        log.error("发生了一个错误", exception);
        Response<Void> response = new Response<>();
        response.setCode(500);
        response.setMsg("请稍后再试");
        return response;
    }

}
