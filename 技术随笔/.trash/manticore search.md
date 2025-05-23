[Manticore Search 中文文档 | Manticore Search 中文文档](https://vergil-lai.gitbook.io/manticoresearch-manual-zh)
[简介 - 《manticore search 中文文档 - 帮助手册 - 教程》 - 极客文档](https://geekdaxue.co/read/manticore-search/introduce)
# 简介
## manticore search 简介
Manticore Search 是一个专为搜索而设计的多存储数据库，具备卓越的全文搜索能力。
Manticore 语法是SQL，支持通过 HTTP 和 MySQL 协议使用 SQL。同时提供了 HTTP JSON 协议，类似于 Elasticsearch 的协议，并允许执行与 Elasticsearch 兼容的插入和替换 JSON 查询。
## 应用场景
- **全文搜索**
- **拼写纠正**
## 安装
### Docker
```java
docker run --name manticore -v $(pwd)/data:/var/lib/manticore -p 9306:9306 -p 9308:9308 -d manticoresearch/manticore
```
MySQL 客户端使用 9306 端口连接到 manticore search，默认无用户名和密码。
# 快速开始
## 依赖
```xml
```
## 创建数据表
```

```