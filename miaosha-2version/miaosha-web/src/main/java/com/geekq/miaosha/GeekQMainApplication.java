package com.geekq.miaosha;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
@MapperScan("com.geekq.miaosha.mapper")
public class GeekQMainApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GeekQMainApplication.class, args);
    }










































}
