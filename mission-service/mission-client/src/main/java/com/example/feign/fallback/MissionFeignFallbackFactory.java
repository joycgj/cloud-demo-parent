package com.example.feign.fallback;

import com.example.feign.MissionFeign;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// 降级逻辑（放在 client 包里，大家共用）
@Slf4j
@Component
public class MissionFeignFallbackFactory implements FallbackFactory<MissionFeign> {
    @Override
    public MissionFeign create(Throwable throwable) {
        return vin -> {
            log.error("Mission服务不可用，VIN: {}, 异常: {}", vin, throwable.getMessage());
            return Arrays.asList("SDK级降级：服务连接失败");
        };
    }
}