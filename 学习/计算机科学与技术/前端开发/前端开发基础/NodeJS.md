初始化项目（使用默认配置）：npm init -y

## PM2
PM2（Process Manager）是一个 Node.js 应用进程管理器，核心定位是让 Node 服务在服务器上稳定、可控、可运维地长期运行。
### 安装
全局安装：
- npm install -g pm2
- pm2 -v
### 常用命令
- pm2 start npm --name app -- run start：启动进程（npm run start）
- pm2 list：查看所有进程
- pm2 restart app：重启
- pm2 stop app：停止
- pm2 delete app：删除
- pm2 logs app：查看日志