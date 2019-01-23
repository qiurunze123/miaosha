package com.geekq.web.controller;

import com.geekq.common.utils.resultbean.ResultGeekQ;
import com.geekq.common.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author 邱润泽
 * 登录模块
 */
@Controller
public class LoginController  extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/login" )
    @ResponseBody
    public ResultGeekQ<Boolean> dologin(HttpServletResponse response, LoginVo loginVo) {
        ResultGeekQ<Boolean> result = ResultGeekQ.build();
        logger.info(loginVo.toString());
        return result;
    }
}
