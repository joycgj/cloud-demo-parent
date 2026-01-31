package com.example.controller;

import com.example.feign.MissionFeign;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MissionController implements MissionFeign { // 实现 Client 接口
    @Override
    public List<String> getMissions(String vin) {
        return Arrays.asList("Task-A", "Task-B");
    }
}