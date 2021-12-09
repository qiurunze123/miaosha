package com.geekq.web.controller;

import com.geekq.admin.entity.Logininfo;
import com.geekq.admin.service.ILogininfoService;
import com.geekq.admin.service.RedisCacheStorageService;
import com.geekq.common.enums.Constants;
import com.geekq.common.utils.resultbean.ResultGeekQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 邱润泽
 * 登录模块
 */
@Controller
public class LoginController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ILogininfoService iLogininfoService;


    @Autowired
    private RedisCacheStorageService redisCacheStorageService;


    @RequestMapping("/login")
    @ResponseBody
    public ResultGeekQ<Logininfo> dologin(HttpServletResponse response,
                                          HttpServletRequest request,
                                          String username, String password) {
        ResultGeekQ<Logininfo> result = ResultGeekQ.build();


        ResultGeekQ<Logininfo> login = this.iLogininfoService.login(username, password,
                Constants.USERTYPE_NORMAL, request.getRemoteAddr());
        result.setData(login.getData());
        if (!ResultGeekQ.isSuccess(login)) {
            result.withError(login.getCode(), login.getMessage());
        }
        return result;
    }
}
