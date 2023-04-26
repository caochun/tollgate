# 人机物融合的云收费架构

## 背景

车道收费软件（MTC）是高速公路收费系统的重要组成。传统MTC实现为桌面应用程序，运行在连接了各种车道可控制设备的工业计算机之上，并部署在车道收费亭内有收费站工作人员进行现场操作。其基本结构如下所示。

![](https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/caochun/tollgate/main/plantuml/mtc.puml)

## 云收费架构

云计算平台技术的发展推动车道收费系统进行技术改造。一方面部分计算/数据密集型业务可以在云端部署运行，发挥云平台资源弹性可伸缩的优势；另一方面借助云计算模式构建面向高速业务的人机物融合应用架构。

传统车道软件实现多层面职责，包括：

1. 设备驱动控制
2. 收费业务过程
3. 用户交互界面

将这些职责进行分离，我们很容易得到以下调整过的架构。

![](https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/caochun/tollgate/main/plantuml/mtc-refactory.puml)

车道业务逻辑在处于核心位置，用户（人）与设备和信息服务在车道业务逻辑协同下完成车道业务。以高速出口的收费业务为例：

![](https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/caochun/tollgate/main/plantuml/mtc-seq.puml)
