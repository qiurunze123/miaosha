package com.geekq.miaosha.controller;

import com.geekq.miaosha.domain.MiaoShaMessageUser;
import com.geekq.miaosha.rabbitmq.MQSender;
import com.geekq.miaosha.service.MiaoShaMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/message")
public class MiaoShaMessageController {

    @Autowired
    private MiaoShaMessageService messageService ;
    @Autowired
    private MQSender sendMessage;

    @RequestMapping(value="/list", produces="text/html")
    public String list() {

//        List<MiaoShaMessageUser> messageUserList  = messageService.getMessage("");
        sendMessage.sendMessage(null);
        return "";
    }
}
