package cn.tiaqb.infoflowtools.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author tianqingbo_dxm
 * @date 2023/6/25 10:24 Handlersince 1.0
 */
@Configuration
public class LogInterceptor implements HandlerInterceptor {

    private final static String INFO_FLOW_TRACE_ID = "INFO_FLOW_TRACE_ID";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        String traceId = UUID.randomUUID().toString().replace("-", "");
        MDC.put(INFO_FLOW_TRACE_ID, traceId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }
}
