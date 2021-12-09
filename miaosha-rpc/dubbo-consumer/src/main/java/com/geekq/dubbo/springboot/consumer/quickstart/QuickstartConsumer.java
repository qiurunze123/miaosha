package com.geekq.dubbo.springboot.consumer.quickstart;

import com.alibaba.dubbo.config.annotation.Reference;
import com.geekq.dubbo.springboot.ServiceAPI;

//@Component
public class QuickstartConsumer {

    @Reference(interfaceClass = ServiceAPI.class)
    ServiceAPI serviceAPI;

    public void sendMessage(String message) {

        System.out.println(serviceAPI.sendMessage(message));
    }
}
