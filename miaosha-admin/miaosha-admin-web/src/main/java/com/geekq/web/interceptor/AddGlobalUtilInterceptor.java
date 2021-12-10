package com.geekq.web.interceptor;

import com.geekq.admin.service.impl.SystemDictionaryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddGlobalUtilInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SystemDictionaryUtil systemDicUtil;

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("_DicUtil", systemDicUtil);
        }
        super.postHandle(request, response, handler, modelAndView);
    }

}
