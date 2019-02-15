package com.geekq.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DubboProviderApplication
 * 服务提供启动类
 * @author geekq
 * @date 2018/6/7
 */
@EnableDubbo
@SpringBootApplication
@MapperScan("com.geekq.provider.mapper")
public class DubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
    }
}
