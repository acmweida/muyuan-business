package com.muyuan.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MuyuanUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuyuanUserApplication.class, args);
    }

}