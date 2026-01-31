package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Mission Server 启动类
 * 负责将自身注册到 Nacos 注册中心
 */
@SpringBootApplication(scanBasePackages = "com.example")
@EnableDiscoveryClient // 关键注解：开启服务发现与注册功能
public class MissionServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MissionServerApplication.class, args);
    }
}