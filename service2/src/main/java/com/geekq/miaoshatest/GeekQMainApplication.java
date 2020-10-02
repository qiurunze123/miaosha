package com.geekq.miaoshatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@ComponentScan(value = "com.geekq")
public class GeekQMainApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GeekQMainApplication.class, args);
    }










































}
