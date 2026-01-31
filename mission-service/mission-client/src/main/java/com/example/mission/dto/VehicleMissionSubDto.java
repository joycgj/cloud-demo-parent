package com.example.mission.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class VehicleMissionSubDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String vin;
    private String missionStatus; // 注意：DTO 字段名
    private String destinationStationName;
}