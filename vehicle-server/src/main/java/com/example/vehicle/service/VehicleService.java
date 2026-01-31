package com.example.vehicle.service;

import com.example.mission.dto.VehicleMissionSubDto;
import com.example.mission.feign.MissionFeign;
import com.example.vehicle.entity.VehicleMissionSub;
import com.example.vehicle.mapper.MissionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// vehicle-server 模块
@Service
public class VehicleService {

    @Autowired
    private MissionFeign missionFeign; // 远程调用客户端

    @Autowired
    private MissionConverter missionConverter; // 转换器

    public VehicleMissionSub getVehicleMission(String vin) {
        // 1. 远程调用获取 DTO
        VehicleMissionSubDto dto = missionFeign.getMissionByVin(vin);
        
        // 2. 判空处理（防止 Fallback 返回 null 或空对象）
        if (dto == null) {
            return null;
        }

        // 3. 使用 MapStruct 转换为本地 Entity
        VehicleMissionSub entity = missionConverter.dtoToEntity(dto);
        
        return entity;
    }
}