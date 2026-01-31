package com.example.vehicle.controller;

import com.example.mission.feign.MissionFeign;
import com.example.vehicle.entity.VehicleMissionSub;
import com.example.vehicle.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private MissionFeign missionFeign; // SDK 提供的 Feign 接口

    @Autowired
    private VehicleService vehicleService;

    /**
     * 测试接口：获取车辆正在运行的任务
     * 访问地址示例：http://localhost:8002/vehicle/missions/VIN123456
     */
    @GetMapping("/missions/{vin}")
    public List<String> getRunningMissions(@PathVariable("vin") String vin) {
        log.info("Vehicle-Server 收到请求，查询 VIN: {}", vin);
        
        // 调用 Feign 接口（如果 mission-server 挂了，这里会返回 FallbackFactory 中的默认值）
        List<String> missions = missionFeign.getMissions(vin);
        
        log.info("查询结果: {}", missions);

        VehicleMissionSub vehicleMissionSub = vehicleService.getVehicleMission(vin);
        log.info("通过 VehicleService 获取的 VehicleMissionSub: {}", vehicleMissionSub);

        return missions;
    }
}