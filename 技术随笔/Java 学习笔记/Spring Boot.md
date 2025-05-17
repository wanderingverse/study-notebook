### 注解和 AOP
[【SpringBoot】AOP 自定义注解的使用详解_spring boot aop注解-CSDN博客](https://blog.csdn.net/yuxiangdeming/article/details/129159139)
#### 注解
#### AOP
AOP（Aspect Oriented Programming，面向切面编程）是通过预编译方式和运行期动态代理实现核心业务逻辑之外的横切行为的统一维护的一种技术。AOP 是面向对象编程（OOP）的补充和扩展。利用 AOP 可以对业务逻辑各部分进行隔离，降低模块之间的耦合度，并将那些影响多个类的公共行为封装到一个可重用模块，提高程序的复用性。
AOP 是 Spring 框架中的一个核心内容。在 Spring 中，AOP 代理可以用 JDK 动态代理或者 CGLIB 代理 CglibAopProxy 实现。Spring 中 AOP 代理由 Spring 的 IOC 容器负责生成和管理，其依赖关系也由 IOC 容器负责管理。
 面向切面编程是指对于一个已经封装好的类，可以在编译期间或运行期间对其进行“切割”，即在原有的方法里面添加（织入）一些新的代码，对原有的方法代码进行增强。那些增强部分的代码，称之为切面。
##### 切面（Aspect）
定义的切面类。里面包含切入点（Pointcut）和通知（Advice）的定义。
##### 切入点（PointCut）
要拦截的连接点（要被增强的方法）。指定要对哪些类中的哪些方法进行增强。
##### 连接点（JoinPoint）
被拦截到的点。如被拦截的方法。连接点指定在原方法的某个执行阶段（如方法调用前，方法调用后、发生异常时等）加入增强代码。
##### 通知（Advice）

##### 目标对象（Target Object）

##### AOP 代理对象（AOP Proxy Object）

##### 织入（Weaving）

### starter
SpringBoot 的开箱即用基于一个个的 starter（场景启动器）。SpringBoot 把开发常用的场景抽取成一个个 starter，使得开发者通过引入 SpringBoot 提供的场景启动器，再进行少量的配置就能使用相应的功能。
#### starter 命名规范
SpringBoot 官方提供的 starter 一般以 spring-boot-starter 开头，而第三方 starter 一般以产品名开头。
#### 常用 starter
##### spring-boot-starter-web
spring-boot-starter-web 是 Spring Boot 框架里用于构建 Web 应用程序的核心启动器，它极大地简化了 Spring Web 应用的开发流程，通过自动配置和依赖管理，可以快速构建 RESTful API 服务和传统的基于 MVC 的 Web 应用。它通过一系列**依赖包**，整合了构建 Web 应用所需的各种组件。
###### 依赖包
- spring-boot-starter：Spring Boot 最基础的启动器，包含日志、自动配置等。
	- spring-boot
- spring-web：提供 Web（Servlet）和 REST API 支持。
- spring-webmvc：支持 Spring MVC，如@Controller、@RestController
- spring-boot-starter-tomcat：内嵌 Tomcat，自动启动 Web 服务
###### 配置项
- server.port:8080：指定 Spring Boot 内嵌服务器（如 Tomcat）在本地监听的端口号，使得应用启动后，客户端（浏览器、前端、接口工具）通过该端口访问后端服务。默认端口为：8080。
- spring.application.name: ProjectName：指定当前 Spring Boot 应用的名字。
#### 自定义 starter
[参考](https://blog.csdn.net/m0_62128476/article/details/141948032)
### application.yml
#### 配置 mysql 连接
在 Spring Boot 中，得益于 Spring Boot 提供的 **自动配置（Auto Configuration）** 机制和 **Spring JDBC** 等模块的集成。`application.yml` 能够自动配置数据库连接。
具体来说：Spring Boot 提供了大量的自动配置类，它们会在应用启动时根据配置和类路径中存在的依赖自动进行配置。例如当引入了 MySQL JDBC 驱动 `com.mysql.cj.jdbc.Driver`，Spring Boot 就会利用 `spring-boot-starter-jdbc` 自动加载相关配置，并尝试连接数据库。
```yml
spring:
  datasource:  
    driver-class-name: com.mysql.cj.jdbc.Driver  
    url: jdbc:mysql://127.0.0.1:3306/数据库名
    username: 用户名
    password: 密码
```
#### 配置 application.yml 多环境
##### 创建 application-{profile}.yml
配置文件的命名方式是 Spring Boot 项目中的一种约定俗成的命名方式。规定在 Spring Boot 中，`application.yml` 是默认的主配置文件，而各个 `application-{profile}.yml` 文件则用于按环境区分配置。
```yml
application.yml      #主配置文件
application-dev.yml  #开发环境的配置
application-prod.yml #生产环境的配置
application-test.yml #测试环境的配置
```
##### 指定启用的配置文件
在 application.yml 中，通过配置要使用的配置文件，当选择配置的 `application-{profile}.yml` 文件和 application.yml 文件存在相同的配置时，application.yml 中的相应配置会被覆盖。
```yml
spring:  
  profiles:  
    # 需要使用的配置文件的 profile，如此处选择的配置文件为：application-dev.yml
    # 如果未指定配置文件，即 active 值为空，则默认加载 application.yml
    # 如果找不到指定的配置文件，则跳过。仍默认加载 application.yml
	active: dev
```
#### 读取配置项
在 Spring Boot 中，通过注入 `@Value` 注解或使用 `@ConfigurationProperties` 读取 `application.yml` 中的配置项。还可以通过注入 `Environment` 对象读取。
##### @Value
使用 `@Value` 注解读取单个配置项。
##### 读取 `Environment` 对象
###### 注入  `Environment` 对象
```java
    @Autowired
    private Environment environment;
```
###### environment.getProperty() 获取配置项
```java
String datasourceUsername = environment.getProperty("spring.datasource.username");
String datasourcePassword = environment.getProperty("spring.datasource.password");
```
### SpringBoot 日志系统
#### 概述
- SpringBoot 默认使用 **SLF4J + Logback** 作为实际日志框架。
	- SLF4J：通用日志接口
	- Logback：默认日志实现
- SpringBoot 已具备日志功能，且在启动时自动加载 `logback`。无须引入额外依赖。
- SpringBoot 默认输出日志到控制台（Console）。
- SpringBoot 日志级别默认为 `INFO`。
- 可在 SpringBoot 配置文件（`application.properties` 或 `application.yml`）中进行自定义配置。
#### 日志级别
SpringBoot 支持配置 6 种日志级别。
- `OFF`：完全关闭日志输出，即完全禁用日志记录。
- `ERROR`：输出错误（`error`）级别的日志。
- `WARN`：输出警告（`warn`）及警告以上级别（`error`）的日志。
- `INFO`：输出信息（`info`）及信息以上级别（`warn`、`error`）的日志。
- `DEBUG`：输出调试（`debug`）及调试以上级别的日志。
- `TRACE`：输出详细（`trace`）及详细以上级别的日志。
#### 自定义日志配置
在 SpringBoot 配置文件（`application.properties` 或 `application.yml`）中进行自定义日志配置。
##### 日志级别
`logging.level.root=INFO`：设置全局默认日志级别为 `INFO`。所有日志将默认遵循这个级别，除非另有配置。如果不配置此项，SpringBoot 默认设置全局日志级别为 `INFO`。
`logging.level.com.example.demo=DEBUG`：单独为 `com.example.demo` 包设置日志级别为 `DEBUG`，单独配置的部分将覆盖 `root` 配置（SpringBoot 日志系统会优先使用更具体、更精确的配置，如果存在更具体的配置，具体的配置将替代更宽泛的配置，与配置项的代码顺序无关）。如果同样作用的配置项重复配置多次，即重复配置同一个 key，此时只会生效代码顺序中最后一条（配置文件是 key-value 结构，当 key 重复时会发生覆盖，仅保留最后一项）。
##### 日志输出到文件
`logging.file.name= ./logs/app.log`：指定日志文件路径。
#### 自定义日志格式
`logback-spring.xml` 是用于配置 Spring Boot 应用程序中日志的 Logback 配置文件。
默认情况下，Spring Boot 会自动加载名为 `logback-spring.xml` 的配置文件。
如果将配置文件修改为其他名字比如 `logback.xml`，即如果想使用自定义名称的日志配置文件，需要通过在配置文件中配置 `logging.config` 属性，手动指定配置文件的位置。
##### `logback-spring.xml` 基础配置
##### 日志格式配置
##### 追加写入日志
Spring Boot 默认使用的 `logback`，而在 `logback` 的默认配置中，使用的是 `FileAppender`，`FileAppender` 在每次应用启动时会重新创建日志文件，即清空原有内容，而不是追加写入。
##### 归档日志
##### 日志异步写入
##### 控制台和文件同时输出
##### 不同级别输出到不同文件