package com.example.mission.feign;

import com.example.mission.dto.VehicleMissionSubDto;
import com.example.mission.feign.fallback.MissionFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// 接口定义
@FeignClient(name = "mission-server", fallbackFactory = MissionFeignFallbackFactory.class)
public interface MissionFeign {
    @GetMapping("/mission/list/{vin}")
    List<String> getMissions(@PathVariable("vin") String vin);

    @GetMapping("/mission/detail/{vin}")
    VehicleMissionSubDto getMissionByVin(@PathVariable("vin") String vin);
}