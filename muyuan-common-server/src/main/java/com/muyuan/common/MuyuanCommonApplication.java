package com.muyuan.common;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo(scanBasePackages = "com.muyuan.common")
public class MuyuanCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuyuanCommonApplication.class, args);
    }

}
