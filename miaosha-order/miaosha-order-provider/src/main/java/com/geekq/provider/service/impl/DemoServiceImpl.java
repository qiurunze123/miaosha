package com.geekq.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.geekq.api.service.DemoService;

/**
 * DemoServiceImpl
 * 服务提供类
 * @author geekq
 * @date 2018/6/7
 */
@Service(version = "${demo.service.version}")
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("2321121212312312");
        return "Hello, " + name + " (from Spring Boot)";
    }
}
