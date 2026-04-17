[黑马程序员Redis入门到实战教程，深度透析redis底层原理+redis分布式锁+企业解决方案+黑马点评实战项目_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1cr4y1671t/?vd_source=32d7b7aca593de01e7de9c2be4a87152)

[黑马Redis快速入门，一套搞定Redis，常见数据结构及命令，包含jedis应用与优化、springdataRedis应用与优化_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1rV411M7eU/?vd_source=32d7b7aca593de01e7de9c2be4a87152)


# Redis 概述
## 概述
Redis（Remote Dictionary Server，远程字典服务）是一个开源的高性能键值存储数据库，支持多种数据结构以及范围查询、位图、超日志、地理空间索引、向量等。
Redis 的数据存储在内存中，因此具有极高的读写速度，能达到每秒数十万次的读写操作。还支持数据持久化，可通过将内存中的数据定期写入磁盘以防止数据丢失。
Redis 可作为缓存系统存储热点数据，减轻对数据库的访问压力，提高系统的响应速度。可作为分布式锁，在分布式系统中协调多个进程或线程对共享资源的访问，确保同一时间只有一个客户端可以操作共享资源。可作为消息队列，Redis 的发布/订阅功能可以实现简单的消息传递机制，适用于实时消息通知等场景。Redis 还可用于实现排行榜、计数器等功能。
Redis 默认提供了 16 个数据库，数据库索引分别从 0 到 15。在实际开发中，通常不建议使用不同的数据库索引分隔数据，而是通过不同的 Redis 实例或 Redis 集群隔离数据。每个 redis 示例使用默认的 0 索引数据库。
## 安装和配置
### Docker 安装
1. 拉取 redis 最新镜像：`docker pull redis`。此命令等价于：`docker pull redis:latest`
2. 宿主机创建 redis 文件映射目录：推荐放置在 `/home/redis` 目录下。
	1. 创建 `data` 目录，用于存放 `redis` 数据。
	2. 创建 `conf` 目录和 `conf/redis.conf` 文件，用于存放 redis 配置。
3. 启动容器：`docker run -d --name redis -p 6379:6379 -v /home/redis/data:/data -v /home/redis/conf/redis.conf:/usr/local/etc/redis/redis.conf redis redis-server /usr/local/etc/redis/redis.conf`
4. 进入 redis 容器：`docker exec -it redis bash`
	- Redis 命令行客户端：`redis-cli`
- 查看 redis 允许访问的 IP 地址：`docker exec -it redis redis-cli CONFIG GET bind`
- 查看 redis 是否限制外部连接：`docker exec -it redis redis-cli CONFIG GET protected-mode`
- 查看密码：`docker exec -it redis redis-cli CONFIG GET requirepass`
### redis.conf 配置
编辑 `redis.conf` 文件进行配置。
- `redis` 密码：`requirepass 密码`
## 连接客户端
[RedisInsight](https://github.com/redis/RedisInsight)
连接格式：redis://用户名:密码@主机:端口
## SpringBoot 集成
### 添加 Maven 依赖
```xml
<!-- Redis -->  
<dependency>  
    <groupId>org.springframework.boot</groupId>  
    <artifactId>spring-boot-starter-data-redis</artifactId>  
</dependency>
```
### 配置 yaml
```yaml
spring:
  data:
    redis:
      host: 127.0.0.1
      port: 6379
      password:
```
### 配置 RedisTemplate
默认 RedisTemplate 使用 JDK 序列化器 `JdkSerializationRedisSerializer`，会将对象序列化为字节数组存储在 Redis 中的数据是二进制形式，不方便观察和调试，且会增加存储体积。
按需自定义 RedisTemplate。
```Java
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 配置自定义序列化器
        return redisTemplate;
    }
}
```
### 注入 RedisTemplate
```Java
@Resource
private RedisTemplate<String, Object> redisTemplate;
```
# Redis 数据结构
Redis 可以存储键（key）与几种不同数据结构类型值组成的键值字典。
键（key）的数据类型通常是 String。Redis 中键是全局唯一的，一个键对应一个值，但同一个键可以映射到不同的数据类型。
可存储值的数据类型如下。
- 字符串：String
- 列表：List
- 集合：Set
- 有序集合：Zset
- 散列：Hash
# Redis 命令
- 查看 redis 信息：`info`
- 选择数据库：`select 逻辑数据库编号`
- 删除当前数据库中所有键值对
	- `flushdb`：同步执行清空操作，可能阻塞服务器导致无响应。
	- `flushdb async`：异步执行清空操作，不阻塞主线程。
- 获取距离 `key` 上一次被访问（读取或写入）所经过的秒数：`OBJECT IDLETIME key`
- 获取 `key` 剩余存活时间：`TTL key
# RedisTemplate API
## 获取操作数据对象 ValueOperations
```Java
//操作字符串（String）
ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//操作 散列（hash）
redisTemplate.opsForHash();
//操作 列表（list）
redisTemplate.opsForList();
//操作集合（Set）
redisTemplate.opsForSet();
//操作有序集合（Zset）
redisTemplate.opsForZSet();
```
## 操作数据
```Java
// 写入或覆盖写入指定的[key,value]到 Redis，可通过 Duration.ofXxx() 方法指定过期时间 timeout（正整数），timeout 缺省时表示永不过期。
valueOperations.set(K key, V value, Duration timeout);
// 根据指定的 key 获取 value
valueOperations.get(Object key);
// 获取 指定 key 的 value 后删除 key 及 value
valueOperations.getAndDelete(K key);
// 删除指定 key 及 value
redisTemplate.delete(K key);
```
# RediSearch
RediSearch 是一个为 Redis 数据库提供高级索引和搜索功能的模块。支持全文搜索、向量相似度搜索和聚合查询等。
## 安装和配置
### Docker 安装
`redislabs/redisearch` 镜像启动的 Redis 保留原本 Redis 的所有功能，额外自带了 RediSearch 模块。可直接删除原有的 Redis 原版镜像，重新安装带有 RediSearch 模块的 Redis。步骤同 Redis 的 Docker 安装方式。
1. 拉取 `redisearch` 最新镜像：`docker pull redislabs/redisearch`
2. 宿主机创建 redis 文件映射目录：推荐放置在 `/home/redis` 目录下。
	1. 创建 `data` 目录，用于存放 `redis` 数据。如已有数据，考虑删除。否则可能导致版本不兼容。
	2. 创建 `conf` 目录和 `conf/redis.conf` 文件，用于存放 redis 配置。
3. 启动容器：`docker run -d --name redis -p 6379:6379 -v /home/redis/data:/data -v /home/redis/conf/redis.conf:/usr/local/etc/redis/redis.conf redislabs/redisearch redis-server /usr/local/etc/redis/redis.conf`
### redis.conf 配置
编辑 `redis.conf` 文件进行配置，指定加载 redisearch 模块。
```C++
# redisearch 模块
loadmodule /usr/lib/redis/modules/redisearch.so
loadmodule /usr/lib/redis/modules/rejson.so
```
### 检查
1. 进入 redis 容器：`docker exec -it redis bash`
	- Redis 命令行客户端：`redis-cli`
2. 查看当前 Redis 加载的模块：`docker exec -it redis redis-cli MODULE LIST`。
3. 观察是否已加载 `redisearch` 模块：输出结果中包含 `search`。