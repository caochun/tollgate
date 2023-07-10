# Step 1. 根据官网tutorial下载相关包

[依赖包安装tutorial](https://www.asyncapi.com/docs/tutorials/generate-code)

需要安装npm、nodejs（可以使用nvm），mqtt cli，asyncapi generator

```sh
npm install -g @asyncapi/generator
```

# Step 2. 启动mqtt broker

可以使用docker镜像，也可以使用ditto中的mqtt服务。
```sh
docker run -it -p 31883:1883 eclipse-mosquitto
```

# Step 3. 根据规范编写asyncapi yaml

[Studio Example](https://studio.asyncapi.com/)

channel的message使用到components中的messages，messages中的payload定义会使用到components中的schemas，格式如下：

```yaml
channels:
  pole.getStatus:
    subscribe:
      message: 
        $ref: "#/components/messages/poleGetStatus"
components:
  messages:
    poleGetStatus:
      name: poleGetStatus
      payload:
        $ref: "#/components/schemas/poleGetStatus"
  schemas:
    poleGetStatus:
      type: object
      properties:
        id:
            type: integer
            minimum: 0
            description: Id of the pole.
        status:
            type: integer
            minimum: 0
            maximum: 1
            description: 0 represents pole down, 1 represents pole up.     
```

# Step 4. 使用asyncapi generator生成代码框架（code framework）
## 使用默认模板：
```sh
ag ./asyncapi.yaml -o output-python @asyncapi/python-paho-template
```

## 使用自定义模板：
fork基础模板，然后提交commit修改模板，使用如下命令生成代码框架：
```sh
ag ./asyncapi.yaml https://github.com/Changri-Liuhen/python-paho-template -o output-python
```
或

使用本地的代码模板：
```sh
ag ./asyncapi.yaml ./ -o output-python
```

> 具体的方案、命令行可参考[asyncapi/template-for-generator-templates](https://github.com/asyncapi/template-for-generator-templates)的库，包括生成全新的代码模板。


# Step 5. 按照业务逻辑补充和修改代码框架，并运行
## nodejs:
```sh
npm install

npm start
```
## python:
```sh
python3 main.py
```