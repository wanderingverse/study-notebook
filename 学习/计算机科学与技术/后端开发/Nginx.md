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
- **配置文件路径**：`nginx -t`

## 日志
查看访问 nginx 的 IP 列表：`awk '{print $1}' /home/nginx/log/access.log | sort | uniq`

前端项目部署 nginx
https://zhuanlan.zhihu.com/p/431796992
## 单端口映射多服务
如何用nginx在仅向外网暴露一个端口的情况下做多个内网服务的反向代理？
用魔法DNS。互联网上有免费的公益泛解析 DNS 服务，比如 [http://nip.io](https://link.zhihu.com/?target=http%3A//nip.io)或 [http://sslip.io](https://link.zhihu.com/?target=http%3A//sslip.io)。你把 IP 地址写在域名前面，它就自动解析到这个 IP
https://www.zhihu.com/question/640673121/answer/2020137422688662795
## 同类工具
traefik：专为云原生和容器环境设计的自动化智能网关。适用于 docker 内容器的管理，可设置访问密码等。