package com.geekq.miaosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;


@ImportResource({"classpath:dubbo/applicationContext-dubbo-consumer.xml"})
@ComponentScan(basePackages={"com.geekq.admin.*","com.geekq.miaosha.*"})
@SpringBootApplication
@EnableScheduling
public class GeekQMainApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GeekQMainApplication.class, args);
    }










































}
