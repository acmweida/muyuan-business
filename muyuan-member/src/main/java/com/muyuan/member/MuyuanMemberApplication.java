package com.muyuan.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.muyuan"})
public class MuyuanMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuyuanMemberApplication.class, args);
    }

}
