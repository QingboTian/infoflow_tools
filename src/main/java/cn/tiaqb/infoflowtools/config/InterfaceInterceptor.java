package cn.tiaqb.infoflowtools.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tianqingbo_dxm
 * @date 2023/6/25 11:33 AM
 * @since 1.0
 */
@Configuration
public class InterfaceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        String requestURI = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();
        String remoteAddr = httpServletRequest.getRemoteAddr();
        String localAddr = httpServletRequest.getLocalAddr();
        InterfaceContext.InterfaceModel interfaceModel = new InterfaceContext.InterfaceModel();
        interfaceModel.setRouter(requestURI);
        interfaceModel.setMethod(method);
        interfaceModel.setRemoteAddress(remoteAddr);
        interfaceModel.setLocalAddress(localAddr);
        InterfaceContext.put(interfaceModel);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        InterfaceContext.remove();
    }
}
