[黑马程序员前端React18入门到实战视频教程，从react+hooks核心基础到企业级项目开发实战（B站评论、极客园项目等）及大厂面试全通关_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1ZB4y1Z7o8/?vd_source=32d7b7aca593de01e7de9c2be4a87152)
# React 简介
React 是由 Meta 公司研发的一个用于构建 Web 和原生交互界面的库。
## 环境搭建
使用 `create-react-app` 搭建开发环境。`create-react-app` 是一个可快速创建 `React` 开发环境的工具，底层由 `Webpack` 构建，封装了配置细节。
1. 执行命令：`npx create-react-app react-name`
	- `npx`：`Node.js` 工具命令，用于查找并执行后续的包命令。
	- `create-react-app`：创建 `React` 项目的核心包。
	- `react-name`：`React` 项目的名称，可以自定义。
2. 运行项目：进入 `react-name` 项目根目录下，执行 `npm start`。
## package.json
项目包管理文件。通过 JSON 键值管理项目包信息。
- dependencies：管理项目导入的所有包及使用版本。
	- react：React 核心包。
	- react-dom：React 核心包。
- scripts：管理项目可执行的命令。
	- start：启动项目的命令。通过 `npm start` 启动项目。
	- build：打包项目的命令。可打包项目。
## 开发目录结构
项目根目录下的 `src` 目录是 `React` 项目的开发目录。
- App.js：
- index.js：