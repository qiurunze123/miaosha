package com.geekq.admin.utils;


import com.geekq.admin.entity.Logininfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class UserContext {

    public static final String LOGIN_IN_SESSION = "logininfo";
    public static final String VERIFYCODE_IN_SESSION = "VERIFYCODE_IN_SESSION";
    private static ThreadLocal<Logininfo> userHolder = new ThreadLocal<Logininfo>();
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
/*    @Autowired
    private RedisService redisService;*/

    private static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
    }

    public static void removeUser() {

        userHolder.remove();
    }

  /*  public static void putLogininfo(Logininfo user) {

        userHolder.set(user);
    }
    public static Logininfo getCurrent() {

        return userHolder.get();
    }*/

    public static void putLogininfo(Logininfo logininfo) {
        HttpServletRequest a = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        HttpSession b = a.getSession();
        b.setAttribute(LOGIN_IN_SESSION, logininfo);
    }

    public static Logininfo getCurrent() {
        return (Logininfo) getRequest().getSession().getAttribute(
                LOGIN_IN_SESSION);
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

//	public static void putVerifyCode(VerifyCode code) {
//		getRequest().getSession().setAttribute(VERIFYCODE_IN_SESSION, code);
//	}
//
//	public static VerifyCode getVerifyCode() {
//		return (VerifyCode) getRequest().getSession().getAttribute(
//				VERIFYCODE_IN_SESSION);
//	}
}
