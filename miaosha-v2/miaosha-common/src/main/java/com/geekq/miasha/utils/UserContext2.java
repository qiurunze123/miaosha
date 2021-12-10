package com.geekq.miasha.utils;

import com.geekq.miasha.entity.Logininfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 邱润泽
 * <p>
 * thread local 底层实现方法 和 UserContext 类似 本质上都是 每个线程工作都在自己的实例线程上拷贝
 */
public class UserContext2 {

    public static final String LOGIN_IN_SESSION = "logininfo";

    private static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
    }

    public static void putLogininfo(Logininfo logininfo) {
        getRequest().getSession().setAttribute(LOGIN_IN_SESSION, logininfo);
    }

    public static Logininfo getCurrent() {
        return (Logininfo) getRequest().getSession().getAttribute(
                LOGIN_IN_SESSION);
    }

}
