package com.geekq.dubbo.springboot.privoder;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class PrivoderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrivoderApplication.class, args);

    }
}
