package com.geekq.miaosha.controller;

import com.geekq.miaosha.common.SnowflakeIdWorker;
import com.geekq.miaosha.domain.MiaoShaMessageUser;
import com.geekq.miaosha.rabbitmq.MQSender;
import com.geekq.miaosha.service.MiaoShaMessageService;
import com.geekq.miaosha.vo.MiaoShaMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MiaoShaMessageController {

    @Autowired
    private MiaoShaMessageService messageService ;
    @Autowired
    private MQSender sendMessage;

    @RequestMapping(value="/list", produces="text/html")
    public void list() {

        MiaoShaMessageVo vo = new MiaoShaMessageVo();
        vo.setContent("尊敬的用户你好，你已经成功注册！");
        vo.setCreateTime(new Date());
        vo.setMessageId(SnowflakeIdWorker.getOrderId(0,0));
        vo.setSendType(0);
        vo.setStatus(0);
        vo.setUserId(Long.valueOf(22));
         sendMessage.sendRegisterMessage(vo);
    }
}
