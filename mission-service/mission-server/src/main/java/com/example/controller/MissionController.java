package com.example.controller;

import com.example.mission.dto.VehicleMissionSubDto;
import com.example.mission.feign.MissionFeign;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MissionController implements MissionFeign { // 实现 Client 接口
    @Override
    public List<String> getMissions(String vin) {
        return Arrays.asList("Task-A", "Task-B");
    }

    @Override
    public VehicleMissionSubDto getMissionByVin(String vin) {
        // 模拟数据库查询后返回 DTO
        VehicleMissionSubDto dto = new VehicleMissionSubDto();
        dto.setId(100L);
        dto.setVin(vin);
        dto.setMissionStatus("RUNNING");
        return dto;
    }
}