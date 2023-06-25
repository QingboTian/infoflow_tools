package cn.tiaqb.infoflowtools.config;

import cn.tiaqb.infoflowtools.constant.Constant;
import cn.tiaqb.infoflowtools.utils.UuidUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tianqingbo_dxm
 * @date 2023/6/25 10:24 Handlersince 1.0
 */
@Configuration
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        String traceId = UuidUtils.uuid();
        MDC.put(Constant.INFO_FLOW_TRACE_ID, traceId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        MDC.remove(Constant.INFO_FLOW_TRACE_ID);
    }
}
