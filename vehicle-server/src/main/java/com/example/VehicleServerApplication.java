package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Vehicle Server 启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
// 必须：指定 Feign 接口所在的包路径，否则无法注入 MissionFeign
@EnableFeignClients(basePackages = "com.example.feign")
// 关键：由于 FallbackFactory 在 client 模块，必须确保 ComponentScan 能扫描到它
@ComponentScan(basePackages = {"com.example.controller", "com.example.feign"})
public class VehicleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleServerApplication.class, args);
    }
}