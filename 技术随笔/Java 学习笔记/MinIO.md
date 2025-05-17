## MinIO 概述
MinIO 是一个高性能的对象存储服务，用于存储大规模非结构化数据，如图片、视频、日志文件等，支持单节点或多节点集群部署。
### Docker 部署
1. 拉取最新镜像：`docker pull minio/minio`
2. 创建挂载目录：
	- Minio 配置文件：`mkdir -p /home/minio/config`
	- Minio 存储的上传文件：`mkdir -p /home/minio/data`
3. 创建 Minio 容器并运行：
```java
docker run -p 9000:9000 -p 9090:9090 \
     --name minio \
     -d --restart=always \
     -e "MINIO_ACCESS_KEY=minioadmin" \
     -e "MINIO_SECRET_KEY=minioadmin" \
     -v /home/minio/data:/data \
     -v /home/minio/config:/root/.minio \
     minio/minio server \
     /data --console-address ":9090" -address ":9000"
```
- `9000:9000`：将容器的 9000 端口映射到宿主机的 9000 端口，用于 MinIO API 访问。
- `9090:9090`：将容器的 9090 端口映射到宿主机的 9090 端口，用于 MinIO Web 控制台访问。
- `MINIO_ACCESS_KEY=minioadmin`：设置 Minio 的访问密钥 ID（用户名）为：minioadmin。
- `MINIO_SECRET_KEY=minioadmin`：设置 Minio 的访问密钥（密码）为：minioadmin。
## Buckets
MinIO uses buckets to organize objects. A bucket is similar to a folder or directory in a filesystem, where each bucket can hold an arbitrary number of objects.
MinIO 使用存储桶来组织对象。存储桶类似于文件系统中的文件夹或目录，每个存储桶可以容纳任意数量的对象。
### Create a Bucket
创建一个 Bucket。
#### Bucket Name
指定一个 Bucket 的名字。
##### Bucket Naming Rules
Bucket 名称命名规则。
```
Bucket names must be between 3 (min) and 63 (max) characters long.
Bucket 名称的长度必须在 3（最小）到 63（最大）个字符之间。
```
```
Bucket names can consist only of lowercase letters, numbers, dots (.), and hyphens (-).
Bucket 名称只能由小写字母、数字、点号（.）和连字符（-）组成。
```
```
Bucket names must not contain two adjacent periods, or a period adjacent to a hyphen.
Bucket 名称不得包含两个相邻的句点（.），也不得包含与连字符相邻的句点。
```
```
Bucket names must not be formatted as an IP address (for example, 192.168.5.4).
Bucket 名称不得格式化为 IP 地址（例如，192.168.5.4）。
```
```
Bucket names must not start with the prefix xn--.
Bucket 名称不得以 “xn--” 前缀开头。
```
```
Bucket names must not end with the suffix -s3alias. This suffix is reserved for access point alias names.
Bucket 名称不得以后缀 “-s3alias” 结尾。此后缀是为访问点别名（point alias）保留的。
```
```
Bucket names must be unique within a partition.
Bucket 名称在一个分区内必须是唯一的。
```
## MinIO API
### 添加依赖
```xml
<!-- https://mvnrepository.com/artifact/io.minio/minio -->  
<dependency>  
    <groupId>io.minio</groupId>  
    <artifactId>minio</artifactId>  
    <version>8.5.17</version>  
</dependency>
```
### 创建 MinIO 客户端
```java
// minIO 服务地址  
String url = "http://192.168.100.33:9000";  
// 访问密钥 ID
String accessKey = "minioadmin";  
// 访问密钥  
String secretKey = "minioadmin";  
// 创建 minIO 客户端实例  
MinioClient minioClient = MinioClient.builder()  
                                     .endpoint(url)  
                                     .credentials(accessKey, secretKey)  
                                     .build();
```
### 上传文件
```java
// 待上传文件路径
Path updateFilePath = Paths.get("./updateFile.txt");
// 待上传文件的 bucket 名称
String bucketName = "minio-default-bucket";
// 上传到 minIO 的文件名称
String objectName = "object.txt";
minioClient.putObject(PutObjectArgs.builder()
                                   .bucket(bucketName)
                                   .object(objectName)
                                   .stream(Files.newInputStream(updateFilePath),
                                           Files.size(updateFilePath),
                                           -1)
                                   .build());
log.info("上传成功");
```
#### 注意
如果未启用 minIO 的版本控制功能，当上传到 minIO 一个同 bucket 同文件名的对象时，会**覆盖原有的对象**而不做任何提醒。
### 下载文件
```java
//  bucket 名称  
String bucketName = "minio-default-bucket";  
// 待下载文件名称  
String objectName = "一纸阐（网络全域资产管理系统）V1.1.pptx";  
// 下载指定文件，如果指定的文件不存在，抛出异常  
GetObjectResponse response = minioClient.getObject(  
        GetObjectArgs.builder()  
                     .bucket(bucketName)  
                     .object(objectName)  
                     .build());  
// 写入到本地的文件名称  
String fileName = response.object();  
// 写入到本地文件  
response.transferTo(new FileOutputStream(fileName));  
log.info("下载成功");
```
### Bucket 管理
#### 创建 Bucket
```java
// 待上传文件的 bucket 名称
String bucketName = "minio-default-bucket";
// 创建一个 bucket。如果指定的 bucket 已经存在或无法创建，则抛出异常。
minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
```
#### 获取所有 Bucket
```java
// 列出所有 Bucket
List<Bucket> bucketList = minioClient.listBuckets();  
for (Bucket bucket : bucketList) {  
    log.info("Bucket name: {}", bucket.name());  
}
```
#### 查询指定的 Bucket 是否存在
```java
//  bucket 名
String bucketName = "minio-default-bucket";
// 判断指定的 bucket 是否存在  
boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());  
if (!exists) {  
    log.info("bucketName: {} 不存在", bucketName);  
}
```
#### 删除指定的 bucket
```java
//  bucket 名
String bucketName = "minio-default-bucket";  
// 删除一个 bucket
minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
```
#### 获取桶中的所有文件
```java
//  bucket 名
String bucketName = "minio-default-bucket";  
// 获取 bucket 中的所有文件及其名称  
Iterable<Result<Item>> resultIterable = minioClient.listObjects(  
        ListObjectsArgs.builder()  
                       .bucket(bucketName)  
                       .build());  
for (Result<Item> result : resultIterable) {  
    Item item = result.get();  
    log.info("文件名称:{}", item.objectName());  
}
```
#### 删除桶中的指定文件
```java
//  bucket 名称  
String bucketName = "minio-default-bucket";  
// 待删除的文件名称  
String objectName = "test.txt";  
// 删除 bucket 中的指定文件，如果文件存在，则删除，如果文件不存在，不做任何操作  
minioClient.removeObject(RemoveObjectArgs.builder()  
                                         .bucket(bucketName)  
                                         .object(objectName)  
                                         .build());
```
### 对象管理
#### 检查对象是否存在
#### 获取对象元数据
## 断点续传
MinIO 基于分块上传（Multipart Upload），可将一个大文件拆分为多个小块，每个分块单独存储和上传。以此实现并发、失败重试、断点续传功能。
### 分块上传（Multipart Upload）
#### 初始化分块上传
#### 上传每一分块文件
#### 列出已上传的分块
#### 完成上传
### 下载时断点续传
从 MinIO 下载文件到本地时使用断点续传。
### 上传时断点续传
从本地上传文件到 MinIO 时使用断点续传。
