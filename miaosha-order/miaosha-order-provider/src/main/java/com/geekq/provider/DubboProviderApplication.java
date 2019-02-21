package com.geekq.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * DubboProviderApplication
 * 服务提供启动类
 * @author geekq
 * @date 2018/6/7
 */
@ImportResource(value={"classpath:provider.xml"})
@SpringBootApplication
@MapperScan("com.geekq.provider.mapper")
public class DubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
    }
}
