package com.muyuan.manager.goods;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableDiscoveryClient
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableDubbo()
public class ManagerGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerGoodsApplication.class, args);
    }

}
