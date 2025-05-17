### 快速入门
#### 在 HTML 标签上绑定变量：`<h1>{{ title }}</h1>`
#### 创建一个响应式引用：
`ref` 创建的变量会变成一个包含 `.value` 属性的对象，`title.value` 就是你访问 `title` 的实际值
```
import {ref} from 'vue'
let title = ref('登录')
```
`let` 是用来声明一个可修改的变量，你可以随时修改它的值。
`const` 用来声明一个常量，一旦赋值后，变量的引用不可变。注意，`const` 并不意味着变量的值不可改变，而是变量本身不能重新赋值（即不能指向不同的对象或基本类型值），但对象的属性可以修改。
#### 发送axios：
```js
<template>  
  <button @click="fetchData">发送请求</button>  
</template>
```
```js
<script setup>
let fetchData = async () => {  
  try {  
    const res = await axios.get('/api/system/login/test')  
  } catch (error) {  
  }
</script>
```
### 常用命令
- 查看 vue 版本：nvm list vue
- 查看 nodejs 版本：node -v
### Vue3 工程启动流程
#### 启动开发服务器 Vite
`npm run dev` 实际上运行的是 Vite 提供的开发命令，默认在 `vite.config.js` 中配置。
Vite 会启动一个本地服务器，如 http://localhost:5173
#### 加载入口 HTML 文件 `index.html`
打开浏览器访问 Vue 项目时，Vite 会将项目根目录下的 `index.html` 作为入口文件读取并返回。
```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>浏览器标题栏显示标题</title>
  </head>
  <body>
    <div id="app"></div>
    <script type="module" src="/src/main.js"></script>
  </body>
</html>
```
其中，`<div id="app"></div>` 是 Vue 的挂载点，Vue 会把组件渲染在这里。
#### 执行入口 JS 文件 `main.js`
`main.js`文件是 Vue 应用的程序入口点，负责初始化并挂载应用。
```js
import { createApp } from 'vue'
import App from './App.vue'

createApp(App).mount('#app')
```
其中，`createApp(App)`指创建一个 Vue 应用实例，根组件是 `App.vue`，`.mount('#app')`指将 Vue 应用挂载到 HTML 中的 `#app` 元素。`#` 是 CSS 选择器语法中 **ID 选择器** 的标识符，`'#app'` 表示选择 HTML 中 id 为 `app` 的元素。
#### 解析并渲染 `App.vue`
`App.vue` 是单文件组件（SFC），Vite 会调用 Vue 插件将其编译成标准 JavaScript 模块，Vue 使用虚拟 DOM 将 `template` 渲染为浏览器可显示的真实 DOM。从 `App.vue` 开始，整个组件树会被构建和渲染。
### vite.config.js 配置
#### 配置 Vite 开发服务器代理
```js
server: {  
    proxy: {  
        '/api': {  
            target: 'http://localhost:3721',  
            changeOrigin: true,  
            rewrite: (path) => path.replace(/^\/api/, ''),  
        },  
    },  
},
```
配置所有以 `/api` 开头的请求都被代理到 `http://localhost:3721` 服务器，并且在转发时会移除 `/api` 前缀。
### Vue Router
安装 Vue Router：npm install vue-router