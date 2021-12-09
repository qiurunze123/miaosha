package com.geekq.dubbo.springboot.consumer.transaction;

import com.geekq.dubbo.springboot.ServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {

    @Autowired
    private ServiceAPI serviceAPI;

    public void sendMessage(String message) {
//        System.out.println("this is consumer sendMessage message="+message);
//
//        System.out.println(serviceAPI.sendMessage(message));

        // 测试业务
        serviceAPI.saveOrder("001", message, "5");

        serviceAPI.isTrueSeats(message);

        serviceAPI.isNotSold(message);

    }

    public void confirmSendMessage(String message) {
        System.out.println("this is consumer confirmSendMessage message=" + message);
//        System.out.println(serviceAPI.sendMessage(message));
    }

    public void cancelSendMessage(String message) {
        System.out.println("this is consumer cancelSendMessage message=" + message);
//        System.out.println(serviceAPI.sendMessage(message));
    }
}
