### Nginx 概述
Nginx 是一个开源 Web 服务器和反向代理服务器，可以实现负载均衡、缓存、反向代理、SSL 终止
等功能。
### 安装
### 常用配置
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
### 常用命令
- 启动 Nginx：`start nginx`
- 重新加载 Nginx 配置：`nginx -s reload`

https://zhuanlan.zhihu.com/p/431796992