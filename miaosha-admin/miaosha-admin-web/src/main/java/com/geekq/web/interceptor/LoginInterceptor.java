package com.geekq.web.interceptor;

import com.geekq.admin.entity.Logininfo;
import com.geekq.admin.service.RedisCacheStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisCacheStorageService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            RequiredLogin rl = hm.getMethodAnnotation(RequiredLogin.class);
            System.out.println(request.getParameter("username"));
            String username = request.getParameter("username");
            if (rl != null) {
                Logininfo current = redisService.get("Login" + username);
                if (current == null) {
                    response.sendRedirect("/login.html");
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

}
