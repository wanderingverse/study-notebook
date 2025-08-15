# Element Plus 简介
[一个 Vue 3 UI 框架 | Element Plus](https://element-plus.org/zh-CN/)
## 安装和使用
1. 添加依赖
	`npm install element-plus`
2. 在 main.js 中全局引入
```js
import ElementPlus from 'element-plus'  
import 'element-plus/dist/index.css'

const app = createApp(App)  
app.use(ElementPlus)
```
3. 使用 Element Plus 组件：按照官方文档，在 `.vue` 文件中使用不同组件。