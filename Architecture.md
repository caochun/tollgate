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

车道业务逻辑在处于核心位置，用户（人）与设备和信息服务在车道业务逻辑协同下完成车道业务。以高速出口的收费业务为例，收费业务逻辑可用状态机建模：

![](https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/caochun/tollgate/main/plantuml/mtc-statemachine.puml)

该业务状态机执行过程中，在各参与方根据状态机状态变化而做出相应业务动作并产生业务事件，驱动状态机不断往前直至完成：

![](https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/caochun/tollgate/main/plantuml/mtc-seq.puml)


当然，作为物理设备的打印机、读卡器、栏杆等和用户并不能直接与车道业务这个信息服务进行交互，所以我们需要在计算机内为每个设备配备一个信息实体作为其与信息服务交互的桥梁，而对于人来说，他/她与信息世界的交互入口就是我们在计算机上实现的桌面软件（UI），所以上面的架构可以再次细化为：

![](https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/caochun/tollgate/main/plantuml/mtc-cpss.puml)

我们将这种架构称为人机物融合架构。这一逻辑架构可采用以下云计算部署架构：

![](https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/caochun/tollgate/main/plantuml/mtc-cloud.puml)


实际生产环境中，该系统需要服务多条车道，每条车道对应部署边缘节点，多条车道多辆车通行过程中，计费等计算密集型服务又可通过多实例水平扩展保证服务质量，形成以下运行部署。

![](https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/caochun/tollgate/main/plantuml/mtc-cloud-lanes.puml)

其与传统桌面应用实现的业务架构相对比存在优势如下：

|桌面应用架构|人机物融合架构|
|---|---|
|业务逻辑固化在桌面应用中 | 业务逻辑独立运行管理，便于修改管理|
|设备直接被桌面应用访问，设备无法从外部进行管理|设备有其封装服务（或数字孪生）所代表，其状态可以自主上传，其接口可以独立访问|
|业务与设备、操作员和其他信息服务间是静态绑定关系，无法更改| 业务与设备、操作员和服务能动态绑定，运行时可实现各种灵活跨车道业务过程|
