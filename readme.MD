# 部署与调试

**验证步骤**

1. 启动基础设施：确保 Nacos (127.0.0.1:8849) 已经启动。

```
docker run --name nacos \
-e MODE=standalone \
-p 8849:8848 \
nacos/nacos-server:v2.1.0
```

nacos 访问地址：http://localhost:8849/nacos

2. 编译 Client：在 mission-client 目录下执行 mvn clean install，确保本地仓库有了该 SDK 的 jar 包。

3. 启动服务：

   - 启动 mission-server (端口 8001)。
   - 启动 vehicle-server (端口 8002)。

4. 正常测试：
   - 访问 http://localhost:8002/vehicle/missions/VIN123。
   - 预期结果：返回 ["Task-A", "Task-B"]。

5. 异常测试（核心）：

   - 手动停止 mission-server。
   - 再次访问上述地址。
   - 预期结果：页面不会报 404 或 500，而是返回 ["SDK级降级：服务连接失败"]。
   ```Mission服务不可用，VIN: VIN124, 异常: Unexpected end of file from server executing GET http://mission-server/mission/list/VIN124```
   - 查看 vehicle-server 控制台：你会看到 FallbackFactory 打印的日志，详细说明了连接被拒绝（ConnectException）的原因。

```
feign:
  # 这一行在 Hoxton 版本极其重要，不写降级永远不生效！
  sentinel:
    enabled: true
```

必须设置为 true，如果设置为 false，curl http://localhost:8002/vehicle/missions/VIN124 会报如下错误：

```{"timestamp":"2026-01-31T06:00:57.631+0000","status":500,"error":"Internal Server Error","message":"Unexpected end of file from server executing GET http://mission-server/mission/list/VIN124","path":"/vehicle/missions/VIN124"}```

**超时验证步骤**

1. 修改 mission-server 代码，手动在方法里添加 Thread.sleep(3000)。
2. 在 vehicle-server 配置 feign.client.config.default.readTimeout=1000。
3. 调用接口，你会看到虽然 mission-server 还在运行，但因为超时，mission-client 中的 FallbackFactory 被触发并返回了降级数据。

日志打印：Mission服务不可用，VIN: VIN124, 异常: Read timed out executing GET http://mission-server/mission/list/VIN124
curl http://localhost:8002/vehicle/missions/VIN124 
["SDK级降级：服务连接失败"]

# 项目总结与关键点

- 版本兼容性：在 Spring Boot 2.2.8 + Hoxton.SR6 下，Feign 的降级是依赖 Hystrix 或 Sentinel 的。由于你使用了 Spring Cloud Alibaba，所以必须确保配置文件中有 feign.sentinel.enabled=true。
- 代码分离：将 Feign 接口放在 client 包中，虽然增加了维护工作，但在多人协作时，调用方只需要引用 Maven 坐标，非常优雅。
- 异常捕获：使用 FallbackFactory 相比普通的 Fallback，最大的价值在于生产环境下你可以根据 cause 判断是超时还是连接失败，从而快速排查网络故障。