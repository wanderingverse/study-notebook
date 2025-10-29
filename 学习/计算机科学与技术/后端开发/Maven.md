[黑马程序员Maven全套教程，maven项目管理从基础到高级，Java项目开发必会管理工具maven_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1Ah411S7ZE/?vd_source=32d7b7aca593de01e7de9c2be4a87152)
### Maven
#### 查找依赖
https://mvnrepository.com/
#### 定义依赖
`<scope>作用域</scope>`：指定依赖的作用范围。
- compile：默认作用域。指示依赖在**编译**、**测试**、**运行**、**打包**时都需要。
- provided：指示依赖在**编译**、**测试**时需要，但运行和打包时不需要。
- runtime：指示依赖在**测试**、**运行**、**打包**时需要，但编译时不需要。
- test：指示依赖在**测试**时需要，但编译、运行、打包时不需要。