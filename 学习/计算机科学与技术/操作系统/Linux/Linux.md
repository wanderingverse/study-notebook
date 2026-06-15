# CentOS
## 命令
- `su -`：切换用户到默认用户（默认 root）
- `ps -ef | grep 模糊查询内容`
- `kill -9 pid号`
- 查端口信息：`netstat -ntl`、`ss -ntpl`
- 查看系统内存（KB）使用情况：`free -h`
	- total：总物理内存
	- used：已经使用的内存
	- free：剩余（未使用的）内存
	- shared：多进程共享内存
	- buff/cache：读写缓存内存
	- available：应用程序可用物理内存
- 查看系统内存占用前十的进程：`ps aux --sort=-%mem | head -11`
- 查看系统开机自启的服务列表：`systemctl list-unit-files --type=service | grep enabled`
- 查找系统中已安装的文件或命令：`whereis 文件或命令名`
- 查看系统时间和时区信息：`timedatectl`
- 设置系统时间：`date -s "2024-03-03 10:10:10"`
- 同步系统时间到硬件时钟：`hwclock -w`
- 创建目录（如果上级目录不存在，补全上级目录）：`mkdir -p /目录`
- 查看 CentOS 版本信息：`rpm -q centos-release`
- 配置防火墙端口：
	- 开放指定端口：`firewall-cmd --zone=public --add-port=端口号/tcp --permanent`
	- 重新加载防火墙规则：`firewall-cmd --reload`
	- 查看防火墙开放的所有端口：`firewall-cmd --list-ports`
	- 删除防火墙开放的端口：`firewall-cmd --remove-port=端口号/tcp --permanent`
- 查看 docker 状态：`systemctl status docker`
- 查看 docker 版本：
	- `docker --version`
	- `docker -v`
- 启动 Docker 服务：`systemctl start docker`
- 设置 Docker 自启动：`systemctl enable docker`
- 查看 Docker 是否已设置为自启动：`systemctl is-enabled docker`
- 查看 docker 容器的自启动策略：`docker inspect 容器ID --format='{{.HostConfig.RestartPolicy.Name}}'`
	- 容器自启动策略：
		- no：不会自动重启（默认）
		- always：总是自动重启
		- unless-stopped：如果未被手动停止，则自动重启。
		- on-failure：仅在容器非正常退出（退出码非 0）时重启。
- 修改容器的自启动策略：`docker update --restart=自启动策略 容器ID`
- docker 安装mysql8
	- 拉取镜像：`docker pull mysql:8`
	- 创建容器并启动：`docker run -d -p 3306:3306 --restart=always --name mysql -v /home/mysql/log:/var/log/mysql -v /home/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 mysql:8`
	- 远程连接MySQL：`jdbc:mysql://IP地址:3306?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true`
	- docker 连接MySQL：`docker exec -it mysql容器名 mysql -u root -p`
- 进入容器：`docker exec -it mysql容器名 bash`
- 列出当前运行的 docker 容器：`docker ps`
- 查询 docker 容器资源占用情况：`docker stats --no-stream`
	- `--no-stream`：只显示一次，不持续刷新。
- 列出本地存储的所有镜像：`docker images`
- 列出指定容器的详细信息：`docker inspect 容器ID或容器名称`
- 使用指定镜像创建并运行一个新容器：`docker run 镜像名`
	- 如果本地没有指定的镜像，Docker 会自动从 Docker Hub 拉取镜像并启动容器。
	- 常用命令参数：
		- `--rm`：在容器停止后自动删除该容器。相当于容器停止后自动执行：`docker rm 容器名`
		- `--name 容器名`：为容器指定一个名字，这个名字将用于以后对容器的引用。
		- `-d`：后台运行容器（不占用当前终端）。
		- `-e`：用于设置容器内的环境变量。
		- `-v 宿主机（本地机器）目录路径:容器内数据目录路径`：将宿主机（你的本地机器）中的指定文件夹挂载到容器内部的某个数据目录文件夹。使得容器内的数据能够存储在宿主机的指定文件夹中，当容器重启或删除时不会丢失数据。
			- 一般情况下，宿主机（本地机器）目录路径指定在：`/home` 下。
		- `-p 宿主机（你本地机器）上的端口:容器内的端口`：将容器内的端口映射到宿主机的端口，使得可以通过宿主机上的指定端口访问容器内运行的服务。
- 停止运行中的容器：`docker stop 容器名`
- 启动已停止的容器：`docker start 容器名`
- 查看容器日志：`docker logs 容器名`
- 进入容器：`docker exec -it 容器名 bash`
	- 退出 bash：`exit`
- 删除一个容器：`docker rm 容器名`
- 删除一个镜像：`docker rmi 镜像名
# 软件包管理器
## pkg
- 更新软件列表：pkg update
- 升级已安装的所有软件：pkg upgrade
- 安装软件：pkg install <软件名>
	- 默认安装最新稳定版
- 搜索软件：pkg search <关键词>
- 卸载软件：pkg uninstall <软件名>
- 列出已安装的软件：pkg list-installed
# 快捷键
- 清屏：`ctrl+l`，等价于 `clear`
# SSH
## 连接工具
Tabby
[PuTTY](https://putty.org/index.html)