[Spring Boot 集成 Redis：从入门到实战的详细学习指南 - 软件职业规划 - 博客园](https://www.cnblogs.com/java-note/p/18792309)

# Redis 概述
## 概述
Redis（Remote Dictionary Server，远程字典服务）是一个开源的高性能键值存储数据库，支持多种数据结构，包括字符串（strings）、哈希（hashes）、列表（lists）、集合（sets）、有序集合（sorted sets）以及范围查询、位图、超日志和地理空间索引等。
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
# Redis 命令
- 查看 redis 信息：`info`
- 选择数据库：`select 逻辑数据库编号`
- 删除当前数据库中所有键值对
	- `flushdb`：同步执行清空操作，可能阻塞服务器导致无响应。
	- `flushdb async`：异步执行清空操作，不阻塞主线程。
- 获取距离 `key` 上一次被访问（读取或写入）所经过的秒数：`OBJECT IDLETIME key`
- 获取 `key` 剩余存活时间：`TTL key
