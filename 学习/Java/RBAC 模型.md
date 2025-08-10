### RBAC 概述
RBAC（Role-Based Access Control）：基于角色（Role）的访问控制。是一种广泛应用于计算机系统和网络安全领域的访问控制模型。通过将权限分配给角色，再将角色分配给用户，实现对系统资源的访问控制。即一个用户拥有若干角色，每一个角色拥有若干权限。构成：用户-角色-权限的授权模型。
通过RBAC模型，可以实现灵活且易于管理的访问控制策略。即通过分配和调整角色，来管理用户的权限。这种角色层次结构可以帮助简化权限管理，并确保用户只有所需的权限。
#### 核心概念
RBAC 认为权限授权的过程实际上是 Who、What、How 三元组之间的关系，即：Who 对 What 进行 How 的操作。
- Who：权限的拥有者或主体。如：用户（User）、角色（Role）。
- What：某个操作（operation）或对象（object）。
- How：具体的权限（Privilege）。
##### 角色（Role）
指在系统中具有一组相关权限的抽象概念，代表了用户在特定上下文中的**身份或职能**，如管理员、普通用户等。
##### 权限（Permission）
指对系统资源进行操作的许可，如读取、写入、修改等。权限可以被分配给角色。
##### 用户（User）
指系统的实际使用者，每个用户可以被分配一个或多个角色。
##### 分配（Assignment）
指将**角色**与**用户**关联起来，并赋予用户相应的权限。
- 用户-角色映射：关联用户和角色之间的映射。
- 角色-权限映射：关联角色和权限之间的映射。
#### 安全原则
RBAC 支持三个重要的安全原则：最小权限原则、责任分离原则和数据抽象原则。
##### 最小权限原则
RBAC 可以将角色配置成其完成任务所需的最小权限集合。
##### 责任分离原则
通过调用相互独立互斥的角色来共同完成敏感的任务，例如要求一个计账员和财务管理员共同参与统一过账操作。
##### 数据抽象原则
可以通过权限的抽象来体现，例如财务操作用借款、存款等抽象权限，而不是使用典型的读、写、执行权限。


#### 优缺点
##### 优点

##### 缺点


### SpringBoot 实现
#### 数据库设计
##### ER 图
##### 用户表 user

|   字段含义   |     字段名     |      数据类型       |
| :------: | :---------: | :-------------: |
| 用户表主键 id |     id      | bigint unsigned |
|   用户名    |  username   |   varchar(16)   |
|    密码    |  password   |   varchar(64)   |
|   创建时间   | create_time |    datetime     |
|   更新时间   | update_time |    datetime     |
##### 角色表 role
|   字段含义   |     字段名     |      数据类型       |
| :------: | :---------: | :-------------: |
| 角色表主键 id |     id      | bigint unsigned |
|   角色名称   |  role_name  |   varchar(16)   |
|   角色描述   | description |   varchar(64)   |
|   创建时间   | create_time |    datetime     |
|   更新时间   | update_time |    datetime     |
##### 权限表 permission
|   字段含义   |       字段名       |      数据类型       |
| :------: | :-------------: | :-------------: |
| 权限表主键 id |       id        | bigint unsigned |
|   权限名称   | permission_name |   varchar(16)   |
|   权限描述   |   description   |   varchar(64)   |
|   创建时间   |   create_time   |    datetime     |
|   更新时间   |   update_time   |    datetime     |
##### 用户-角色关系表 user_role
关联用户表和角色表，实现多对多关系。

|     字段含义      |     字段名     |      数据类型       |
| :-----------: | :---------: | :-------------: |
| 用户-角色关系表主键 id |     id      | bigint unsigned |
|   用户表主键 id    |   user_id   | bigint unsigned |
|   角色表主键 id    |   role_id   | bigint unsigned |
|     创建时间      | create_time |    datetime     |
|     更新时间      | update_time |    datetime     |

##### 角色-权限关系表 role_permission
关联角色表和权限表，实现多对多关系。

|     字段含义      |      字段名      |      数据类型       |
| :-----------: | :-----------: | :-------------: |
| 角色-权限关系表主键 id |      id       | bigint unsigned |
|   角色表主键 id    |    role_id    | bigint unsigned |
|   权限表主键 id    | permission_id | bigint unsigned |
|     创建时间      |  create_time  |    datetime     |
|     更新时间      |  update_time  |    datetime     |

[参考资料1](https://cloud.tencent.com/developer/article/1802329)
[参考资料2](https://blog.csdn.net/m0_62006803/article/details/133962328)
[参考资料](https://cloud.tencent.com/developer/article/1175271)
