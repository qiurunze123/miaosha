package com.geekq.miaosha.mybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class MessageController {


    @RequestMapping("index")
    public String index(){
        return "message";
    }


}
