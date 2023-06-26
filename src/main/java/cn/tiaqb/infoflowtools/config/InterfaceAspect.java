package cn.tiaqb.infoflowtools.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tianqingbo_dxm
 * @date 2023/6/25 11:11 AM
 * @since 1.0
 */
@Aspect
@Slf4j
@Configuration
public class InterfaceAspect {
    @Pointcut("execution (* cn.tiaqb.infoflowtools.controller..*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        try {
            List<Object> args = getRequestArgs(joinPoint.getArgs());
            InterfaceContext.InterfaceModel interfaceModel = InterfaceContext.get();
            String router = interfaceModel.getRouter();
            String method = interfaceModel.getMethod();
            String remoteAddress = interfaceModel.getRemoteAddress();
            String localAddress = interfaceModel.getLocalAddress();
            log.info("interface log ----- router = {}, method = {}, remoteAddress = {}, localAddress = {}, request = {}, response = {}", router, method, remoteAddress, localAddress, JSONObject.toJSONString(args), JSONObject.toJSONString(result));
        } catch (Exception ex) {
            log.error("interface log -----  occur an error", ex);
        }
        return result;
    }

    private List<Object> getRequestArgs(Object[] args) {
        if (args == null) {
            return null;
        }
        List<Object> req = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
                continue;
            }
            req.add(arg);
        }
        return req;
    }

}
