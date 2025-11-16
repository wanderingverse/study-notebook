[黑马程序员前端React18入门到实战视频教程，从react+hooks核心基础到企业级项目开发实战（B站评论、极客园项目等）及大厂面试全通关_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1ZB4y1Z7o8/?vd_source=32d7b7aca593de01e7de9c2be4a87152)


学习位置：
[Day1-08.React中的事件绑定_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1ZB4y1Z7o8?vd_source=32d7b7aca593de01e7de9c2be4a87152&spm_id_from=333.788.videopod.episodes&p=9)
# React 简介
React 是由 Meta 公司研发的一个用于构建 Web 和原生交互界面的库。
## [环境搭建](https://zh-hans.react.dev/learn/creating-a-react-app)
使用 `create-react-app` 搭建开发环境。`create-react-app` 是一个可快速创建 `React` 开发环境的工具，底层由 `Webpack` 构建，封装了配置细节。
1. 执行命令：`npx create-react-app react-name`
	- `npx`：`Node.js` 工具命令，用于查找并执行后续的包命令。
	- `create-react-app`：创建 `React` 项目的核心包。
	- `react-name`：`React` 项目的名称，可以自定义。
2. 运行项目：进入 `react-name` 项目根目录下，执行 `npm start`。
## 目录结构
项目根目录下的 `package.json` 文件是 `React` 项目的包管理文件。
项目根目录下的 `src` 目录是 `React` 项目的开发目录。
项目根目录下的 `public` 目录是 `React` 项目的资源存放目录。
### package.json
项目包管理文件。通过 JSON 键值管理项目包信息。
- dependencies：管理项目导入的所有包及使用版本。
	- react：React 核心包。
	- react-dom：React 核心包。
- scripts：管理项目可执行的命令。
	- start：启动项目的命令。通过 `npm start` 启动项目。
	- build：打包项目的命令。可打包项目。
### src
#### App.js
React 项目的根组件。
```js
function App() {
    return (
        <div className="App">
            React App
        </div>
    );
}
export default App;
```
#### index.js
React 项目的入口文件。加载 React 核心包 `React`、`ReactDOM` 和项目根组件 `App.js`。然后将根组件`App.js` 渲染到 `id` 为 `root` 的 `DOM` 节点上。
`id` 为 `root` 的 `DOM` 节点位于 `public/index.html`文件。
```js
import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<App/>);
```
### public
#### index.html
```html
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <link href="%PUBLIC_URL%/favicon.ico" rel="icon"/>
        <meta content="width=device-width, initial-scale=1" name="viewport"/>
        <meta content="#000000" name="theme-color"/>
        <meta content="Web site created using create-react-app" name="description"/>
        <link href="%PUBLIC_URL%/logo192.png" rel="apple-touch-icon"/>
        <link href="%PUBLIC_URL%/manifest.json" rel="manifest"/>
        <title>React App</title>
    </head>
    <body>
        <noscript>You need to enable JavaScript to run this app.</noscript>
        <div id="root"></div>
    </body>
</html>
```
## JSX
### JSX 简介
`JSX` 是 `JavaScript` 和 `XMl(HTML)` 的缩写，表示在 `JS` 代码中编写 `HTML` 模版结构。它是 `React` 中构建 `UI` 的方式。
`JSX` 并不是标准的 `JS` 语法，它是 `JS` 的语法扩展，浏览器本身不能识别，需要通过解析工具 [Babel](https://babeljs.io/) 解析成标准 `JS` 之后才能被浏览器执行。
### JSX 语法
#### JS 表达式
在 JSX 中，使用大括号 `{}` 标识 JS 表达式。
#### 条件渲染
在 JSX 中，可通过逻辑与（`&&`）运算符、三元表达式（`?:`）实现简单的条件渲染。复杂条件通过 JS 函数定义后渲染。
#### 事件绑定
`on + 事件名 = {事件触发时回调函数名}`。