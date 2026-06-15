## 安装
下载 [termux](https://github.com/termux/termux-app) 安装到安卓手机。
## 快速开始
1. 更新软件库：pkg update && pkg upgrade
2. 安装 SSH 服务：pkg install openssh
3. 查看 wlan0 网卡地址：ifconfig
	- 如：192.168.43.1
4. 查看用户名：whoami
	- 如：u0_a252
5. 修改连接密码：passwd
	- 如：root
6. 启动 SSH 服务端：sshd
	- 默认端口：8022
	- 停止 SSH 服务：pkill sshd
## 常用命令
- 保持 Termux 后台运行
	- 开启：termux-wake-lock
	- 关闭：termux-wake-unlock
- 访问内部存储空间：termux-setup-storage
	- 此命令将创建一个名为 `storage` 的文件夹（软链接）
	- 手机内部存储位于 `storage/shared` 目录下。
- 查询系统属性服务：getprop
	- 此命令是 Android 系统提供的命令
	- Android版本：getprop ro.build.version.release
	- 手机型号：getprop ro.product.model
	- 双卡状态：getprop gsm.sim.state
## 常用操作
### 获取 Android 信息
#### 获取 SIM 卡槽状态和运营商
```bash
echo "{\"sim1\":{\"state\":\"$(getprop gsm.sim.state | cut -d',' -f1)\",\"operator\":\"$(getprop gsm.operator.alpha0)\"},\"sim2\":{\"state\":\"$(getprop gsm.sim.state | cut -d',' -f2)\",\"operator\":\"$(getprop gsm.operator.alpha1)\"}}"
```
### proot-distro
Termux 官方的脚本管理器，可以方便地在安卓手机上安装多个 Linux 发行版。
#### 安装
- 安装 proot-distro：`pkg install proot-distro`
- 安装 ubuntu 系统：`proot-distro install ubuntu`
#### 常用命令
- 登陆系统：`proot-distro login ubuntu`
- 登出系统：`exit`
#### 常用操作
在 Termux home 目录创建软链接 `ubuntu`，链接到 `ubuntu root` 根目录，以便在 Termux 中操作 ubuntu 内部文件。
`ln -s /data/data/com.termux/files/usr/var/lib/proot-distro/installed-rootfs/ubuntu/root ~/ubuntu`
### termux-api
Termux 配套的安卓插件 + 命令行工具，能够让 Termux 命令行直接调用手机硬件、系统功能等，把安卓系统能力开放给终端脚本使用。需要[下载](https://github.com/termux/termux-api)并安装到安卓手机。
1. 下载并安装到安卓手机，启动 app，按提示进行授权。
2. 安装 termux-api 软件包：pkg install termux-api
#### 功能和命令
##### 电池信息
- 获取电池电量
- 获取充电状态
- 获取温度
- 获取电压
##### 传感器数据
- 获取加速度计数据
- 获取陀螺仪数据
- 获取磁力计数据
- 获取光线传感器数据
##### 系统控制
- 震动（termux-vibrate）：让手机按指定时长震动。
- 手电筒（termux-torch）：控制闪光灯开关。
##### 任务计划
设定在特定条件下（如连接充电器、连接 Wi-Fi 时）执行某功能。
### adb 控制
通过在 Termux 启用 adb 连接到本机，以使用 adb 控制本机，拥有更高控制能力。
1. 安装 adb 客户端：pkg install android-tools
2. adb 连接到本机：adb connect 127.0.0.1:5555
	- 事先需要 adb tcpip 5555
### 代码高亮
在SSH连接中，连接工具本身不具备命令补全和语法高亮功能。所有的命令补全、高亮颜色（即ANSI转义序列）都是由远程服务器上的 Shell（命令解释器）计算好之后，发送给连接工具，连接工具再显示出来的。Termux 默认使用的是 bash，没有彩色语法高亮。需要在 Termux 中安装并使用 fish 或配置好的 zsh。
1. 安装 fish：pkg install fish
2. 将 fish 设置为默认 Shell：chsh -s fish
3. 重新 SSH 连接。
### VS Code 远程开发
在 VS Code 中安装插件：`SSH FS` 或 `Remote - SSH`，连接到远程，后可直接在 VS Code 中操作远程文件。
注意，`Remote - SSH`在 Termux 中无法使用。