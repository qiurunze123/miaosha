package com.consumer.demo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.api.dubbo.service.DemoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Reference
    private DemoService demoService;


    @RequestMapping("/sayHello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return demoService.hello(name);
    }
}
