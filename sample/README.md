### Flowable 官方模块
| starter          | 描述                                                                                                                              |
|:-----------------|:--------------------------------------------------------------------------------------------------------------------------------|
| flowable-modeler | 让具有建模权限的用户可以创建流程模型、表单、选择表与应用定义。                                                                                                 |
| flowable-idm     | 身份管理应用。为所有Flowable UI应用提供单点登录认证功能，并且为拥有IDM管理员权限的用户提供了管理用户、组与权限的功能                                                               |
| flowable-task    | 运行时任务应用。提供了启动流程实例、编辑任务表单、完成任务，以及查询流程实例与任务的功能。                                                                                   |
| flowable-admin   | 管理应用。让具有管理员权限的用户可以查询BPMN、DMN、Form及Content引擎，并提供了许多选项用于修改流程实例、任务、作业等。管理应用通过REST API连接至引擎， 并与Flowable Task应用及Flowable REST应用一同部署。 |
| flowable-rest    | Flowable页面包含的常用REST API                                                                                                         |

### Flowable 官方资料
#### [集成 SpringBoot 示例](https://github.com/flowable/flowable-examples/tree/master/spring-boot-example)
Example project for using the Flowable starters with Spring boot.
It automatically deploys all process and cases from the `processes` and `cases` folders respectively.
Exposes the REST endpoints for the 6 engines of Flowable:
* `process-api` for the Process Engine
* `cmmn-api` for the CMMN Engine
* `dmn-api` for the DMN Engine
* `idm-api` for the IDM Engine
* `form-api` for the Form Engine
* `content-api` for the Content Engine

#### [Flowable Open Source REST API Documentation](https://documentation.flowable.com/latest/develop/core-swagger/)
BPMN(/process-api) REST API User Guide: [https://www.flowable.com/open-source/docs/bpmn/ch15-REST](https://www.flowable.com/open-source/docs/bpmn/ch15-REST)
```bash
# 访问示例：
http://localhost:8080/app-api/<action>
http://localhost:8080/process-api/<action>
http://localhost:8080/cmmn-api/<action>
http://localhost:8080/dmn-api/<action>
http://localhost:8080/idm-api/<action>
http://localhost:8080/form-api/<action>
http://localhost:8080/content-api/<action>
http://localhost:8080/event-registry-api/<action>
http://localhost:8080/external-job-api/<action>

# e.g. process-api:
http://localhost:8080/process-api/identity/users
# 其中 “admin” 为Flowable数据库中的 UserId
http://localhost:8080/process-api/identity/users/admin
# 流程定义
http://localhost:8080/process-api/repository/process-definitions
# 更多 rest api 参考上面的 REST API User Guide

# e.g. event-registry:
http://localhost:8080/event-registry-api/event-registry-repository/channel-definitions
```


