package com.geekq.miaosha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource(value = {"classpath:consumer.xml"})
@SpringBootApplication
@MapperScan("com.geekq.miaosha.mapper")
public class GeekQMainApplication {

    public static void main(String[] args) throws Exception {
//        RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
//        Registry registry = registryFactory.getRegistry(URL.valueOf("zookeeper://localhost:2181"));
//        registry.register(URL.valueOf("override://0.0.0.0/com.geekq.api.service.GoodsService?category=configurators&dynamic=false&application=dubbo-consumer2.0&mock=fail:return+444"));
        SpringApplication.run(GeekQMainApplication.class, args);
    }


}
