package com.example.mission.feign.fallback;

import com.example.mission.dto.VehicleMissionSubDto;
import com.example.mission.feign.MissionFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// 降级逻辑（放在 client 包里，大家共用）
@Slf4j
@Component
public class MissionFeignFallbackFactory implements FallbackFactory<MissionFeign> {
    @Override
    public MissionFeign create(Throwable throwable) {
        return new MissionFeign() {

            @Override
            public List<String> getMissions(String vin) {
                log.error("Mission服务不可用，VIN: {}, 异常: {}", vin, throwable.getMessage());
                return Arrays.asList("SDK级降级：服务连接失败");
            }

            @Override
            public VehicleMissionSubDto getMissionByVin(String vin) {
                log.error("Mission服务不可用，VIN: {}, 异常: {}", vin, throwable.getMessage());
                return null;
            }
        };
    }
}