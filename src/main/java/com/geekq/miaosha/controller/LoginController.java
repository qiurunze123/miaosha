package com.geekq.miaosha.controller;

import com.geekq.miaosha.common.resultbean.ResultGeekQ;
import com.geekq.miaosha.service.MiaoShaUserService;
import com.geekq.miaosha.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 登录Controller
 */

@Controller
@Api(tags = "LoginController",description = "用户登录")
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MiaoShaUserService userService;

    @ApiOperation("跳转登录页面")
    @RequestMapping(value = "/to_login",method = RequestMethod.GET)
    public String tologin(LoginVo loginVo) {
        logger.info(loginVo.toString());
        return "login";
    }

    @ApiOperation("用户登录")
    @RequestMapping(value = "/do_login",method = RequestMethod.POST)
    @ResponseBody
    public ResultGeekQ<Boolean> dologin(HttpServletResponse response, @Valid LoginVo loginVo) {
        ResultGeekQ<Boolean> result = ResultGeekQ.build();
        logger.info(loginVo.toString());
        userService.login(response, loginVo);
        return result;
    }
}
