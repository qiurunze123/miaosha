package com.provider.demo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.api.dubbo.service.DemoService;


@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String hello(String name) {
        System.out.println("hello " + name);
        return "hello " + name;
    }
}
