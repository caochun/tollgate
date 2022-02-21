# Tollgate

本项目为事件驱动的微服务系统设计演示之用。系统包括若干独立服务（SpringBoot应用）相互协同完成车辆的收费放行业务。

具体请见[架构说明](docs/architecture.md)

### 运行

```shell
mvn clean install

# 启动MQ
./start-server.sh

# 启动车道服务
cd tolling-service
mvn spring-boot:run

# 启动识别服务
cd ../recognizing-service
mvn spring-boot:run
```

