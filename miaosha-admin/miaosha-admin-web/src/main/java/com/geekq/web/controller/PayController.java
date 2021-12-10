package com.geekq.web.controller;

import com.geekq.web.interceptor.RequiredLogin;
import com.geekq.web.service.CulsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 订购商品controller
 */
@Controller
public class PayController {

    @Autowired
    private CulsterService buyService;

    @RequiredLogin
    @RequestMapping("/index")
    public String index() {
        return "login";
    }


}
