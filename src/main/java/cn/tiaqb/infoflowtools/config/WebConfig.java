package cn.tiaqb.infoflowtools.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author tianqingbo_dxm
 * @date 2023/6/25 10:35 AM
 * @since 1.0
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private LogInterceptor logInterceptor;
    @Autowired
    private InterfaceInterceptor interfaceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
        registry.addInterceptor(interfaceInterceptor).addPathPatterns("/infoflow/**");
    }
}
