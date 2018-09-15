package com.geekq.miaosha.controller;

import com.geekq.miaosha.result.CodeMsg;
import com.geekq.miaosha.result.Result;
import com.geekq.miaosha.service.MiaoShaUserService;
import com.geekq.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class) ;

    @Autowired
    private MiaoShaUserService userService ;


    @RequestMapping("/to_login")
    public String tologin(LoginVo loginVo ){
        log.info(loginVo.toString());
        return "login" ;
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> dologin(HttpServletResponse response ,  @Valid LoginVo loginVo  ){
        log.info(loginVo.toString());
         userService.login(response , loginVo);
         return Result.success(true);
    }

}
