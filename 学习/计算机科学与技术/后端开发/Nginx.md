# Nginx 概述
Nginx 是一个开源 Web 服务器和反向代理服务器，可以实现负载均衡、缓存、反向代理、SSL 终止
等功能。
## 安装
### 本机安装
[nginx 下载](https://nginx.org/en/download.html)
### Docker 安装
[[nginx] 基于Docker安装Nginx - 千千寰宇 - 博客园](https://www.cnblogs.com/johnnyzen/p/18081630)
[2024年最新 Docker 安装 Nginx 容器 (完整详细版) 2025年最新_docker 安装最新nginx-CSDN博客](https://blog.csdn.net/qq_33192671/article/details/144433363)
## 目录结构
- conf：存放 Nginx 配置文件的目录
- html：存放静态 html 文件的目录
- logs：存放Nginx日志的目录
## 配置项
在 `nginx.conf` 中配置。
```nginx
server {
    listen 80;
    server_name yourdomain.com;
    
    location / {
        root /usr/share/nginx/html;
        index index.html;
        try_files $uri $uri/ /index.html;
    }

    location / {
        proxy_pass http://localhost:3721/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```
`listen 80`：指定 Nginx 监听 80 端口的请求。
`server_name yourdomain.com`：指定浏览器访问时的域名。
`location /`：匹配所有以`/` 开头的请求。
`proxy_pass http://localhost:3721`：将请求反向代理给监听在 3721 端口的后端服务器。
## 命令
- **启动 Nginx**：`start nginx`
- **重新加载 Nginx 配置，重启 Nginx**：`nginx -s reload`
- **查看 Nginx 状态**：`systemctl status nginx`
- **配置开启自启**：`systemctl enable nginx`

## 日志
查看访问 nginx 的 IP 列表：`awk '{print $1}' /home/nginx/log/access.log | sort | uniq`

前端项目部署 nginx
https://zhuanlan.zhihu.com/p/431796992