package com.example.vehicle.mapper;
import com.example.mission.dto.VehicleMissionSubDto;
import com.example.vehicle.entity.VehicleMissionSub;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // 让该 Mapper 成为 Spring Bean
public interface MissionConverter {

    // source 是 DTO 的字段名，target 是 Entity 的字段名
    @Mapping(source = "missionStatus", target = "status")
    VehicleMissionSub dtoToEntity(VehicleMissionSubDto dto);

    // 逆向转换（如果需要）
    @Mapping(source = "status", target = "missionStatus")
    VehicleMissionSubDto entityToDto(VehicleMissionSub entity);
}