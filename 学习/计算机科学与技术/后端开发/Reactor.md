# webflux
## Maven 构建
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```
## `Flux<T>`
`Flux` 是 Reactor 响应式编程中的一部分，用来表示一个异步流，可以不断发送多个数据元素。
### 链式调用
```Java
Flux.interval(...).map(tick -> { ... return T})
```
- `.interval(Duration period)` 指定每隔多长时间发送一次元素。如`.interval(Duration.ofSeconds(1))` 表示每 `1` 秒发送一次。
- `.map(tick -> { ... })`是响应式流的转换操作符。每当 Flux 发送一次数据（`tick`），`map` 就会执行方法体，方法体中把 `tick` 转换成自定义的数据。最后 `return`。
### 响应
客户端接收 Flux 的响应，需要添加请求头：`Accept: text/event-stream`
## WebClient
### WebClient 实例化
#### 创建 WebClient 实例
```java
WebClient client = WebClient.create();
```
#### 指定超时时间并创建 WebClient 实例
默认情况下，HTTP 超时时间为：30 秒。
```java
HttpClient httpClient = HttpClient.create();  
// 设置连接超时时间，单位：ms  
httpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);  
// 设置响应超时时间，单位：ms  
httpClient.responseTimeout(Duration.ofMillis(10000));  
httpClient.doOnConnected(connection -> connection  
        // 设置读取超时时间，单位：ms  
        .addHandlerLast(new ReadTimeoutHandler(10000))  
        // 设置写入超时时间，单位：ms  
        .addHandlerLast(new WriteTimeoutHandler(10000)));  
  
WebClient webClient = WebClient.builder()  
                               .clientConnector(new ReactorClientHttpConnector(httpClient))  
                               .build();
```
### 发起请求
##### 指定请求方法
调用 `method(HttpMethod method)` 指定请求的 HTTP 方法
```java
UriSpec<RequestBodySpec> uriSpec = client.method(HttpMethod.POST);
```
##### 指定请求 URL
```java
RequestBodySpec bodySpec = uriSpec.uri(URI.create("/resource"));
```
##### 指定请求体
```java
RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(Object);
```
##### 定义 Header
```java
ResponseSpec responseSpec = headersSpec.header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
  .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
  .acceptCharset(StandardCharsets.UTF_8)
  .ifNoneMatch("*")
  .ifModifiedSince(ZonedDateTime.now())
  .retrieve();
```
##### 获取响应
```java
Mono<String> response = headersSpec.exchangeToMono(response -> {
  if (response.statusCode().equals(HttpStatus.OK)) {
      return response.bodyToMono(String.class);
  } else if (response.statusCode().is4xxClientError()) {
      return Mono.just("Error response");
  } else {
      return response.createException()
        .flatMap(Mono::error);
  }
});
```