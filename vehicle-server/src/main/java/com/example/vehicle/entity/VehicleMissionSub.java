package com.example.vehicle.entity;

import lombok.Data;
import lombok.ToString;

// vehicle-server 模块
@Data
@ToString
public class VehicleMissionSub {
    private Long id;
    private String vin;
    private String status; // 注意：Entity 字段名与 DTO 不同（missionStatus vs status）
    private String destinationStationName;
}