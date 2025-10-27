### Spring MVC
#### 拦截器（Interceptor）
拦截器是一种动态拦截方法调用的机制，在 SpringMVC 中动态拦截控制器方法（Controller 方法）的执行。
##### 全局拦截器
###### 自定义拦截器
```java
@Slf4j  
@Component  
public class GlobalInterceptor implements HandlerInterceptor {  
  
  
    /**  
     * Controller 方法调用前执行  
     *  
     * @param request  request  
     * @param response response  
     * @param handler  handler  
     * @return boolean     */    @Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {  
        return true;  
    }  
  
  
    /**  
     * Controller 方法调用正常返回后执行  
     *  
     * @param request      request  
     * @param response     response  
     * @param handler      handler  
     * @param modelAndView modelAndView  
     */    @Override  
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {  
    }  
  
  
    /**  
     * Controller 方法调用完成后执行，无论是否发生了异常  
     *  
     * @param request  request  
     * @param response response  
     * @param handler  handler  
     * @param ex       若没有异常则为 null  
     */    @Override  
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {  
    }  
}
```
###### 注册自定义拦截器
```Java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private GlobalInterceptor globalInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义拦截器
        registry.addInterceptor(globalInterceptor)
                // 拦截的请求
                .addPathPatterns("/**")
                // 不拦截的请求
                .excludePathPatterns("");
    }
}
```
#### 全局异常和自定义异常
在Spring MVC中，使用`@RestControllerAdvice` 注解创建一个全局异常处理器：GlobalExceptionHandler。在这个类中，可以包含处理各种异常的方法，这些方法使用`@ExceptionHandler(异常类.class)`注解进行标注。
```java
@Slf4j  
@RestControllerAdvice  
public class GlobalExceptionHandler {  
    @ExceptionHandler(Exception.class)  
    public AjaxResult handleException(Exception e) {  
        log.error("异常", e);  
        return AjaxResult.error("异常");  
    }  
}
```
在 SpringBoot 中，使用 自定义异常。
#### 重定向
```java
// 后端指定浏览器重定向到视图
    @GetMapping("/add")
    public ModelAndView addRecordTrack() {
        return new ModelAndView("redirect:/add");
    }

// 后端指定浏览器重定向到网页
 @GetMapping("/add")
    public RedirectView addRecordTrack() {
        return new RedirectView("https://www.baidu.com");
    }
```
### 注解和 AOP
[【SpringBoot】AOP 自定义注解的使用详解_spring boot aop注解-CSDN博客](https://blog.csdn.net/yuxiangdeming/article/details/129159139)
#### 注解
##### @EnableAsync
注解在`SpringBootApplication`类上，表示启用 Spring 的异步方法执行功能。标识此注解后，`@Async`注解生效。
Spring 会在启动时加载一个`AsyncAnnotationBeanPostProcessor`后置处理器，扫描所有拥有`@Async`注解的方法，对这些方法所在的 Bean 生成代理对象，当调用该方法时，Spring 不会直接执行原方法，而是把调用任务交给线程池异步执行。
线程池默认使用`SimpleAsyncTaskExecutor`。
##### @Async
`@Async`是 Spring 提供的异步方法执行注解，注解在方法上。拥有该注解的方法被调用时，该方法的调用交由线程池执行，调用线程立即返回，不等待方法完成。
需要启用`@EnableAsync`。
##### @EventListener(ApplicationReadyEvent.class)
事件监听注解，用于监听 Spring 事件。
`ApplicationReadyEvent` 是 Spring Boot 提供的一个特定事件，表示 Spring 容器启动完成、所有 `ApplicationRunner` 和 `CommandLineRunner` 都执行完毕、应用已经完全就绪，可以接收请求
##### @Component
注解在类上，标识这个类作为 Spring 中的一个组件，会被扫描并注册到Spring 容器中，受 Spring 管理。默认是 单例（singleton），除非显式指定作用域 `@Scope("prototype")`，表示显式声明原型，每次注入都会创建新实例。
##### @Configuration
注解在类上，标记这个类是一个配置类。Spring 会把 `@Configuration` 标记的类本身作为一个 Bean 放到容器中。被这个注解标记的配置类会被 CGLIB 代理，多次调用同一个 `@Bean` 方法，Spring 会保证返回同一个单例。
##### @Bean
注解在方法上，这个方法的返回对象会被 Spring 注册为 Bean。`@Bean` 创建的对象默认是单例（singleton）。Spring 会在容器启动时创建一次 Bean 实例，并在整个应用中复用。每次注入的都是同一个实例。
##### @PostConstruct
注解在方法上，当类的实例创建并且依赖注入完成后，Spring 会自动调用这个方法。它是在 构造函数之后、Bean 可用之前执行的回调方法。常用于初始化资源。
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

### SpEL
Spring 表达式语言（Spring Expression Language，SpEL）是一个功能强大的表达式语言，用于在 Spring 运行时动态查询和操作对象。
[一文吃透 Spring 表达式语言（SpEL），Spring 高级开发者必须掌握！ - 知乎](https://zhuanlan.zhihu.com/p/1917584196890785099)

#### 字面量表达式
SpEL 支持字符串、数字、布尔值和 null 等字面量类型。
```Java
// 创建一个表达式解析器  
ExpressionParser parser = new SpelExpressionParser();  
// 解析表达式  
Expression expression = parser.parseExpression("'字符串'");  
// 获取表达式的值  
String message = expression.getValue(String.class);  
System.out.println(message);
```

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
### ApplicationRunner
`ApplicationRunner` 是 Spring Boot 提供的**接口**，用于在应用启动后立即执行代码。
它提供一个方法：
```java
void run(ApplicationArguments args) throws Exception;
```
这个方法会在 Spring 容器初始化完成后、Spring Boot 启动完成前被调用。可以重写该方法，在这里做一些应用启动后的初始化操作。如：
- 加载配置到内存
- 初始化缓存
- 打印启动日志
- 检查数据库或外部依赖是否可用
#### 示例：启动后输出日志信息
```java
/**  
 * 启动后信息输出日志  
 */
@Slf4j
@Component  
public class StartupLogger implements ApplicationRunner {  
    private final long maxMemory = Runtime.getRuntime().maxMemory() / (1024 * 1024);  
    private final long totalMemory = Runtime.getRuntime().totalMemory() / (1024 * 1024);  
    private final long freeMemory = Runtime.getRuntime().freeMemory() / (1024 * 1024);  
    
    @Resource  
    private Environment environment;  
  
    @Override  
    public void run(ApplicationArguments args) {  
        log.info("============================================================");  
        log.info("                      系统启动信息");  
        log.info("============================================================");  
        log.info("【系统信息】");  
        log.info("  操作系统：         {} {}", System.getProperty("os.name"), System.getProperty("os.version"));  
        log.info("  用户名：           {}", System.getProperty("user.name"));  
        log.info("  用户目录：         {}", System.getProperty("user.home"));  
        log.info("  工作目录：         {}", System.getProperty("user.dir"));  
        log.info("------------------------------------------------------------");  
        log.info("【Java 信息】");  
        log.info("  Java版本：         {}", System.getProperty("java.version"));  
        log.info("  Java路径：         {}", System.getProperty("java.home"));  
        log.info("  命令行参数：        {}", args.getOptionNames());  
        log.info("  运行环境：          {}", Arrays.toString(environment.getActiveProfiles()));  
        log.info("  JVM 最大可用内存：   {} MB", maxMemory);  
        log.info("  JVM 已分配内存：     {} MB", totalMemory);  
        log.info("  JVM 空闲内存：      {} MB", freeMemory);  
        log.info("------------------------------------------------------------");  
        log.info("【服务信息】");  
        log.info("  Spring Boot版本：  {}", SpringBootVersion.getVersion());  
        log.info("  MySQL URL：       {}", environment.getProperty("spring.datasource.url"));  
        log.info("============================================================");  
    }  
}
```
### application.yml
#### 配置文件最大上传大小
```C++
spring:  
  servlet:  
    multipart:  
      max-file-size: 1MB  
      max-request-size: 10MB
```
- `max-file-size`：文件通过 Spring MVC 上传（MultipartFile）时，限制一次请求中单个文件的最大上传大小。默认 `1MB`。
- max-request-size：文件通过 Spring MVC 上传（MultipartFile）时，限制一次请求中所有文件的总上传大小。默认 `10MB`。
#### 配置监听端口和 HTTP/2
Chrome 浏览器允许的到同一个域名的单次最大 TCP 并发连接数一般是 **6 个**。这个限制是基于每个域名的，而不是每个标签页或浏览器的全局限制，即可以同时与多个不同域名建立超过 6 个连接。
`HTTP/1.1` 和 `HTTP/2`：
- `HTTP/1.1` 受限于每个域名 6 个连接。SpringBoot 默认使用 `HTTP/1.1`。
- `HTTP/2`允许**多路复用**。可以在单个 TCP 连接上同时发送多个请求和接收多个响应。即使限制为 6 个连接，使用 `HTTP/2` 的网站也能实现更高的并发性和性能。
##### **私钥**（Private Key）和 **公钥证书**（Certificate / Public Key）
- 私钥：自己保管的、用于解密客户端发送的加密信息，以及签名服务器身份。
- 公钥证书：用于让客户端验证身份。客户端会检查证书是否可信。证书中记录公钥（Public Key）、服务器域名（CN）、颁发机构信息（Issuer）、有效期（Validity）、签名等信息。
##### **自签名证书** 和 **CA 签名证书**
- **自签名证书**（Self-signed）：自行生成的证书，没有第三方机构签名，用于测试环境。浏览器会产生安全警告。
- **CA 签名证书**（Certificate Authority）：由受信任的第三方颁发。浏览器默认信任，适合正式上线的 HTTPS 服务。
##### 密钥库文件生成
使用 JDK 自带 keytool 工具用以生成自签名证书。
```C++
keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650
```
- -genkeypair：生成公钥+私钥对
- -alias tomcat：指定条目别名
- -keyalg RSA：指定生成密钥对时使用的加密算法，此处使用 RSA 非对称加密算法。RSA 表示生成 `公钥 + 私钥对`。
- -keysize 2048：密钥长度，指定为 2048 位。
- -storetype PKCS12：指定密钥库文件类型为：`.p12` 文件
- -keystore keystore.p12：指定输出的文件名为：`keystore.p12`
- -validity 3650：指定证书有效期，单位：天。此处为 10 年。
##### `.p12`导出为`.crt`
使用 JDK 自带 keytool 工具导出。
```C++
# 从 .p12 导出证书
keytool -export -rfc -keystore keystore.p12 -alias tomcat -file keystore.crt
```
##### 配置 SpringBoot yaml 文件
```yaml
server:  
  port: 3721  
  ssl:  
    key-store: classpath:keystore.p12
    key-store-password: 123456
    key-store-type: PKCS12
    key-alias: tomcat
  http2:
    enabled: true
```
- ssl.key-store：指向密钥库文件的存放位置。这里保存了私钥和证书。
	- `classpath:keystore.p12`：`classpath` 表示放在 `resources` 目录下。此处的`keystore.p12` 为密钥库文件，保存私钥和证书。
- ssl.key-store-password：打开密钥库文件的密码。Spring Boot 会在启动时读取密钥库文件，然后用指定的密码（此处是：123456）解密密钥库文件，获取里面的私钥和证书，根据 `ssl.key-alias` 指定的别名，找到对应的条目，然后用获取到的信息建立 HTTPS 连接。
- ssl.key-store-type：密钥库类型。常见`PKCS12`或`JKS`。PKCS12 是现代标准，兼容性好。
- ssl.key-alias：在密钥库中有多个条目时，用这个别名指定使用哪一个密钥和证书。此处条目是：`tomcat`。
	- 一个m密钥库文件里可以存多个`私钥 + 证书`的组合，每个组合叫一个`条目`，每个条目都有一个唯一的`别名（alias）`用以标识。
	- 如果没有指定 `key-alias`，Spring Boot 会尝试使用密钥库文件中的第一个可用条目。
- http2.enabled：指定是否开启 HTTP/2 协议支持。
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
##### 启动时指定配置文件
在 springboot 项目发布为 jar 包后，默认使用项目中 spring.profiles.active 指定的配置，如需切换到其他配置文件，使用 java 启动项目时，通过 `--spring.profiles.active={profile}`指定使用的配置。
##### 外部加载配置文件
Spring Boot 默认支持从以下位置加载配置文件，并会优先使用外部的配置覆盖内部 jar 包中的配置，且配置文件加载优先级从高到底列表如下：
1. 工作目录
2. `./config/`
3. `/config/`
4. `/`
5. jar 包内部
Spring Boot 的配置文件按层次结构进行合并，且外部配置优先级更高。Spring Boot 会将多个配置文件合并成一个 Environment，如果某个配置项在高优先级文件中未定义，则会使用低优先级文件中的配置项。
#### 读取配置项
在 Spring Boot 中，通过注入 `@Value` 注解或使用 `@ConfigurationProperties` 读取 `application.yml` 中的配置项。还可以通过注入 `Environment` 对象读取。
##### @Value(value="${}")
`@Value` 注解标记在成员变量上，用于读取单个配置项。Spring 会在 Bean 实例化之后，把配置文件中的值注入到字段。
```java
@Value(value="${spring.datasource.username}")
private String username;
```
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