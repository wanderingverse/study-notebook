# Servlet 容器
Servlet 容器负责加载、运行和管理 Servlet 的运行环境。
## Servlet
Servlet 是一个遵守 Servlet 规范的 Java 类，用于在服务器端处理 HTTP 请求并生成响应。更底层地讲，它是 Java 语言对 HTTP 请求-响应模型的一个抽象。
Servlet 是一个实现了 Servlet  接口的 Java 类：
```Java
public interface Servlet {
    void init(ServletConfig config);
    void service(ServletRequest req, ServletResponse res);
    void destroy();
}
```
当一个 HTTP 请求到来后：
1. Servlet 容器（如 Tomcat）收到请求
2. 容器找到与 URL 匹配的 Servlet
3. 容器调用该 Servlet 的 `service()` 方法
4. Servlet 通过 `ServletRequest` 获取请求数据
5. Servlet 通过 `ServletResponse` 写出响应
6. 容器将响应返回客户端。
# Tomcat
应用最广泛的 Servlet 容器，也可看作轻量级应用服务器。
[简介 - 手写Tomcat - 廖雪峰的官方网站](https://liaoxuefeng.com/books/jerrymouse/introduction/index.html)
