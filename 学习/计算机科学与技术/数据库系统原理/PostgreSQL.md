# 简介
## 安装
- 使用包管理器安装 PostgreSQL：pkg install postgresql
- 初始化数据库：initdb <数据库路径>
	- initdb：PostgreSQL 自带工具，用于创建空的数据库数据目录、初始化系统表、生成默认配置、设置默认字符集、区域等。
	- 会自动创建一个名为 postgres 的初始空数据库。
	- 如：initdb ./PostgreSQL
- 启动数据库服务器：pg_ctl start -D <数据库路径> -l <日志输出路径>
	- 暴露在 5432 默认端口
	- 默认密码：无。
	- 如：pg_ctl start -D ./PostgreSQL -l ./logs/PostgreSQL.log
- 访问命令行：psql postgres
	- psql：PostgreSQL 的交互式终端工具。
	- postgres：默认数据库名，也是默认用户名。
	- 退出命令行：\q
- 停止数据库服务器：pg_ctl stop -D <数据库路径>
- 重启数据库服务器：pg_ctl restart -D <数据库路径>
### 允许外部访问
修改数据库安装路径下 `./postgresql.conf` 文件。
```C
listen_addresses = 'localhost'
# 改为
listen_addresses = '*'
```
修改数据库安装路径下 `./pg_hba.conf` 文件。配置请求被放行的规则。
```C
...
# 文件最后一行下方，新增一行
host all all 0.0.0.0/0 trust
```
# 命令
- 列出所有用户：\du
	- Attributes：
		- Superuser：超级用户