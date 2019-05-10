package com.geekq.miaosha.config;

import com.geekq.miaosha.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebConfig  extends WebMvcConfigurerAdapter {

    @Autowired
    UserArgumentResolver resolver;

    @Autowired
    private LoginInterceptor interceptor;

    final String[] notLoginInterceptPaths = {"/do_login/**"};
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(notLoginInterceptPaths);
//        registry.addInterceptor(interceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(resolver);
    }
}
