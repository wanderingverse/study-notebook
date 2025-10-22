## Spring WebClient 概述
#### Maven 构建
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

## WebClient 快速入门
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
#### 发起请求
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