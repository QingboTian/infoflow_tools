package cn.tiaqb.infoflowtools.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

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
        Object[] args = joinPoint.getArgs();
        Object result = joinPoint.proceed();
        try {
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

}
