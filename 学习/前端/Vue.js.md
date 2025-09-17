# [Vue.js](https://cn.vuejs.org/guide/introduction) 概述
## 简介
Vue.js 是一套构建用户界面的渐进式框架。Vue 的目标是通过尽可能简单的 API 实现响应的数据绑定和组合的视图组件。
渐进式框架‌是指那些允许开发者根据项目需求逐步引入和使用其功能的框架，而不需要一次性全部使用框架的特性。
常见的渐进式框架‌有：Vue、React、Angular 等。
## 初始化项目
1. 安装 Node.js。npm 随 Node 安装。
2. 查看 nodejs 版本：node -v
3. 使用 npm 初始化 vue 项目：`npm create vue@latest`。还可通过 `vue ui` 命令打开 vue 提供的一个图形化界面，可视化创建和管理项目。
	- Vue 官方脚手架从 Vue 3 开始，默认选用 **Vite** 作为构建工具。以前 Vue CLI 基于 webpack。
	- 查看 vue 版本：nvm list vue。
4. 安装依赖：在 vue 项目工作目录下执行 `npm install`。
	- 安装指定依赖：`npm install 依赖名`
	- 卸载指定依赖：`npm uninstall 依赖名`
5. 启动开发服务器 Vite：在 vue 项目工作目录下执行 `npm run dev`。实际上运行的是 Vite 提供的开发命令，默认在 `vite.config.js` 中配置。Vite 会启动一个本地服务器，如 http://localhost:5173
## 项目结构
### 目录结构
- node_modules：项目依赖的第三方库
- public：静态资源文件夹，如图片、字体等。
	- favicon.ico：网站图标。
- src：项目源代码开发目录
	- api：封装的请求 API
		- requestApi.js：封装的请求 API
	- utils：
		- request.js：封装 axios
	- assets：静态资源（如图片、字体、样式等）
		- main.css：全局样式
	- types：类型声明文件
	- components：可复用的 Vue 组件。Vue 组件是应用的基本构建单位。使用单文件组件（`.vue` 文件）定义。
	- views：页面级组件
	- router：路由配置目录。需要 `vue-router` 依赖。
		- index.js：路由配置文件。
	- **App.vue**：根组件文件
	- **main.js**：项目入口文件
	- router.js：路由配置
- index.html：入口 HTML 文件
- package.json：项目配置和依赖管理
- package-lock.json：依赖的精确版本锁定文件，只读。
### 核心文件
#### 入口 HTML 文件：`index.html`
打开浏览器访问 Vue 项目时，Vite 会将项目根目录下的 `index.html` 作为入口文件读取并返回。
```html
<html lang="en">  
    <head>  
        <meta charset="UTF-8">  
        <meta name="viewport" content="width=device-width, initial-scale=1">  
        <title>Vue App</title>  
    </head>  
    <body>  
        <div id="app"></div>
        <script type="module" src="/src/main.js"></script>
    </body>  
</html>
```
其中，`<div id="app"></div>` 是 Vue 的挂载点，Vue 会把组件渲染在这里。
`<script type="module" src="/src/main.js"></script>`加载 `main.js`。
#### 项目入口文件：`main.js`
main.js 位于 ./src 目录下，是 Vue 应用的程序入口文件。它负责创建 Vue 应用实例，并将根组件（App.vue）挂载到 index.html 中的 div#app 中。
```js
import { createApp } from 'vue'
import App from './App.vue'

createApp(App).mount('#app')
```
其中，`createApp(App)`指创建一个 Vue 应用实例，根组件是 `App.vue`，`.mount('#app')`指将 Vue 应用挂载到 HTML 中的 `#app` 元素。`#` 是 CSS 选择器语法中 **ID 选择器** 的标识符，`'#app'`表示选择 HTML 中 id 为 `app` 的元素。
#### 根组件：App.vue
App.vue 是单文件组件（SFC），Vite 会调用 Vue 插件将其编译成标准 JavaScript 模块，Vue 使用虚拟 DOM 将 `template` 渲染为浏览器可显示的真实 DOM。从 `App.vue` 开始，整个组件树会被构建和渲染。
## Vue 组件基本结构
Vue 组件是 Vue 应用的核心构建单元。一个组件包含三个部分。
```js
<script setup>  
</script>  
  
<template>  
</template>  
  
<style scoped>  
</style>
```
### 模板（`<template>`）
使用 HTML 和 Vue 模板语法定义组件的 UI。
### 脚本（`<script>`）
定义组件的逻辑，包括数据、方法、生命周期等。使用 setup 属性简化 `export default`过程，使得开发者不用显式 `export default`，而组件内定义的变量、函数、响应式状态可以自动暴露给模板 `<template>` 使用。
### 样式（`<style>`）
定义组件的样式。使用 `scoped` 属性将样式限制在仅当前组件内生效。
## 打包项目
在 vue 项目工作目录下执行：`npm run build`。
执行完成后，会在 Vue 项目下生成一个 dist 目录，该目录包含 index.html 文件及 static 目录，static 目录包含了静态文件 js、css 以及图片目录 images（如果有图片的话）。
# Vue.js 开发
## 变量
### 创建响应式引用
#### ref
为单个值创建响应式引用，可以是原始类型或对象。
##### 创建
```js
import {ref} from 'vue'
const title = ref('登录')
```
`let` 是用来声明一个可修改的变量，你可以随时修改它的值。
`const` 用来声明一个常量，一旦赋值后，变量的引用不可变。注意，`const` 并不意味着变量的值不可改变，而是变量本身不能重新赋值（即不能指向不同的对象或基本类型值），但对象的属性可以修改。
##### 访问或修改
```js
title.value
```
##### 模板绑定
模板中使用时，不需要 `.value`，Vue 会自动解包。
```vue
<p>{{ count }}</p>
```
#### reactive
为整个对象或数组创建响应式引用。
##### 创建
```js
import {reactive} from 'vue';

const value = reactive({  
    id: route.params.id,  
    title: "",  
    summary: "",  
    content: "",  
});
```
##### 使用
直接使用。
```js
value.id
```
#### 绑定变量
`<h1>{{ title }}</h1>`
## 事件
#### 绑定事件
`<标签 @事件="函数"></标签>`

## Vue 生命周期方法
```js
<script setup>
onMounted(() => {
	// 当组件完成挂载（DOM 已渲染）后触发
})
</script>
```
## 接口调用
### 封装 axios
在 `src/utils/request.js` 中封装 axios 。
```js
import axios from "axios"  
  
const service = axios.create({  
    baseURL: "/api", timeout: 5000  
})  
  
// 请求拦截器  
service.interceptors.request.use(config => {  
    return config  
})  
  
// 响应拦截器  
service.interceptors.response.use(response => response.data, error => {  
    console.error("API Error:", error)  
    return Promise.reject(error)  
})  
  
export default service
```
### 封装 API
在 `src/utils/request.js` 中封装 API。可按业务模块划分多个 `.js` 文件。此处创建一个`requestApi.js`作为示例。
```js

import request from "@/utils/request"  
  
  
/**  
 * 示例：获取博客文章列表  
 * @param params  
 * @returns {*}  
 */  
export function fetchBlogPostList(params) {  
    return request({  
        url: "/blog/post/list", method: "get", params  
    })  
}
```
### 发送请求
```js
<template>  
  <button @click="fetchData">发送请求</button>  
</template>
```
```js
<script setup>  
import {fetchBlogPostList} from "@/api/post.js";  
  
const handleClick = async () => {  
    try {
        const res = await fetchBlogPostList();  
        console.log(res);  
    } catch (error) {  
        console.log(error);  
    }  
}  
</script>  
  
<template>  
    <el-button @click="handleClick">按钮</el-button>  
</template>  
  
<style scoped>  
</style>
```
## Vue Router
Vue 路由允许通过不同的 URL 访问不同的内容以实现多视图的单页 Web 应用（single page web application，SPA）。需要添加 Vue Router 依赖：`npm install vue-router`。
### 配置路由
在 `./router/index.js` 文件内配置路由。
```js
import {createRouter, createWebHistory} from "vue-router";  
import Login from "@/views/Login.vue";  
  
const routes = [  
    {  
        path: '/',  
        name: 'Login',  
        component: Login  
    },  
]  
  
const router = createRouter({  
    history: createWebHistory(), routes  
})  
  
export default router
```
### 挂载路由
在 `./src/main.js` 文件内挂载路由。
```js
import {createApp} from 'vue'  
import App from './App.vue'  
import router from "@/router/index.js";  
  
const app = createApp(App)  
app.use(router)  
app.mount('#app')
```
### 渲染路由
在 `./src/App.vue` 文件内渲染匹配到的路由：`<router-view></router-view>`。
```js
<script setup>  
</script>  
  
<template>  
  <router-view></router-view>  
</template>  
  
<style scoped>  
</style>
```
### 路由跳转
```js
{  
    path: '/blog-post/:参数',  
    name: 'BlogPost',  
    component: BlogPost  
}
```
```js
<script setup>

// 单页面跳转
const router = useRouter()
router.push({name: "BlogPost", params: {参数}})

// 新标签页打开
const url = router.resolve({name: "BlogPost", params: {id}})  
window.open(url.href, '_blank')

</script>
```
```js
<script setup>

// 获取路由参数
const route = useRoute();
const 参数 = route.params.参数;
</script>
```
## 全局状态管理 Pinia
提供跨页面状态共享的依赖，可以在多组件之间传递数据。需要添加依赖：`npm install pinia`
### 挂载
在 `./src/main.js` 文件内挂载 Pinia
```js
import { createApp } from 'vue'
import App from './App.vue'
import { createPinia } from 'pinia'

const app = createApp(App)
const pinia = createPinia()
app.use(pinia)
app.mount('#app')
```
### 创建 Store
在 `src/stores` 下创建 `*.js` 文件管理全局状态。自定义名称用 `XXX` 代替。
```js
import {defineStore} from "pinia";  
  
export const useXXXStore = defineStore("XXX", {  
    state: () => ({  
        id: "",  
    }),  
    actions: {  
        // 更新方法  
        set(val) {  
            this.id = val  
        },  
        // 清空方法  
        clear() {  
            this.id = ""  
        }  
    }  
})
```
### 使用 Store
```js
<script setup>

const store = useXXXStore()
// 调用：如 store.set(val)
store.set("123")
// 获取：store 中管理的变量值
console.log("获取到：",store.id)
</script>
```
## 事件总线 mitt
mitt 是一个轻量事件库，能够自定义事件总线，使用发布/订阅模式。事件触发只调用订阅回调。需要添加依赖：`npm install mitt`
### 创建全局事件
在`src/events`下创建文件 `Emitter.js`，保证全局只有一个事件总线实例。
```js
import mitt from 'mitt'

const emitter = mitt()

export default emitter
```
在`src/events`下创建文件 `EventListener.js`，封装事件监听和释放方法。
```
import emitter from "@/events/Emitter.js";  
import {onMounted, onUnmounted} from "vue";  
  
/**  
 * 通用事件监听方法  
 * @param eventName 事件名  
 * @param callback 回调方法  
 */  
export function useEventListener(eventName, callback) {  
    onMounted(() => emitter.on(eventName, callback))  
    onUnmounted(() => emitter.off(eventName, callback))  
}
```
### 触发事件
```vue
<script setup>
import emitter from '@/Emitter.js'

// 触发事件
emitter.emit('事件名',参数)
</script>
```
### 监听事件
#### 封装监听和释放
```vue
<script setup>
import emitter from '@/Emitter.js'

const doSomething = (参数) => {
	// 事件发生，执行自定义方法
}

// 监听事件
useEventListener('事件名', doSomething)
</script>
```
#### 手动监听和释放
```vue
<script setup>
import emitter from '@/Emitter.js'

const doSomething = () => {
	// 事件发生，执行自定义方法
}

// 组件加载后开启监听
onMounted(() => {
  emitter.on('事件名', doSomething)
})

// 组件卸载时取消监听
onUnmounted(() => {
  emitter.off('事件名', doSomething)
})
</script>
```
## markdown 渲染
[imzbf/md-editor-v3](https://github.com/imzbf/md-editor-v3)

## 日期解析
### dayjs
#### 安装
```C++
npm install dayjs
```
#### 解析 ISO 字符串
```js
import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
import 'dayjs/locale/zh-cn';

// 解析 ISO 字符串
const isoString = "2025-08-27T17:55:22";
const date = dayjs(isoString);

// 格式化
console.log(date.format('YYYY-MM-DD HH:mm:ss')); // 2025-08-27 17:55:22
console.log(date.format('YYYY年MM月DD日 HH:mm')); // 2025年08月27日 17:55

// 获取年月日
console.log(date.year());  // 2025
console.log(date.month() + 1); // 8（month 从 0 开始）
console.log(date.date());  // 27

// 相对时间
dayjs.extend(relativeTime);
dayjs.locale('zh-cn');
console.log(dayjs(isoString).fromNow()); // 输出 "刚刚" / "2天前" 等
```
## 动效
[canvas-confetti](https://github.com/catdad/canvas-confetti)
[Canvas Confetti 五彩纸屑特效 JS 插件canvas-confetti 是一个 canvas 五彩纸屑特 - 掘金](https://juejin.cn/post/7150201876066074661)

## vite.config.js 配置
### 配置自定义端口
```js
server: {  
    port: 5173,
},
```
默认端口为：5173。
### 配置 Vite 开发服务器代理
```js
server: {  
    proxy: {  
        '/api': {  
            target: 'http://localhost:3721',  
            changeOrigin: true,  
            rewrite: (path) => path.replace(/^\/api/, ''),
            secure: false,
        },  
    },  
},
```
配置所有以 `/api` 开头的请求都被代理到 `http://localhost:3721` 服务器，并且在转发时会移除 `/api` 前缀。
secure: false 表示允许自签名证书。
