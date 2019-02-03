package com.geekq.miaosha.controller;

import com.geekq.miaosha.service.impl.LoginInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 邱润泽
 */
@Controller
@RequestMapping("/login")
public class LoginInfoController {

    @Autowired
    private LoginInfoServiceImpl loginInfoService;

    @RequestMapping("/login")
    public String login(){
        return "hello";
    }
}
