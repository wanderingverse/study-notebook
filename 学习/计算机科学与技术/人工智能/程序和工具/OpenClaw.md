技能下载：[OpenClaw Skills Directory | Best ClawHub Plugins (ex-Moltbot)](https://openclawskills.wiki/)
配置文件路径：`.openclaw\openclaw.json`，修改后需重启网关。
初始化配置：openclaw onboard
启动网关：openclaw gateway
关闭网关：openclaw gateway stop
重启网关：openclaw gateway restart
启动前端控制台：openclaw dashboard
修改配置：openclaw config、openclaw configure
更新：openclaw update
诊断工具：openclaw doctor --fix

查看安装的插件：openclaw plugins list

彻底卸载：[卸载 OpenClaw：干净、彻底且优雅地 -告别”龙虾残留”终极指南 - 知乎](https://zhuanlan.zhihu.com/p/2014664006330249234)
清空聊天消息：clear
### 会话
#### Main session
默认，所有 DM 的默认路由。持续存在，可重置但不可删除。
/reset：原地重置当前 session，清除上下文。不创建新 session ID，只是清空当前对话。
默认每天凌晨 4:00（Gateway 本地时间）自动重置 main session。
#### Group session
群聊消息，按群隔离，每个群独立。

### Agent 工作区
位于：`/.openclaw/workspace`
#### 新建工作区
1. 创建空工作区目录
2. 运行`openclaw setup --workspace 路径`
3. 查看 `openclaw.json` 配置文件，确保 agents.defaults.workspace 指向新路径。
4. 重启 Gateway
#### BOOTSTRAP.md
Agent 的初始化和新手引导程序。当 Agent 第一次启动（或者全新的工作区建立）时，它会读取此文件。文件的内容会指导 Agent 向你提问（例如：你叫什么名字？你希望我有什么性格？你常用的时区是？）
#### IDENTITY.md
身份元数据。定义 Agent 的物理/社交身份、头像等。头像通过
#### SOUL.md
定义性格与边界，决定 Agent 的人设、语气、偏好和不能触碰的底线。
#### AGENTS.md
定义运行规则与标准作业程序 SOP，告诉 Agent 每次会话开始时应该做什么。这是它在逻辑层面的最强约束。
示例：
1. 在执行任何操作前，读取 `SOUL.md` 确认你的角色。
2. 读取 `USER.md` 确认用户的习惯。
3. 读取 `MEMORY.md` 了解长期记忆，并读取 `memory/` 下近两天的记录了解最近的上下文。
#### USER.md
用户背景信息，记录关于你（用户）的背景，比如你所用的开发环境、常用的技术栈或日常作息时间，让 Agent 更好地理解你。
#### TOOLS.md
本地环境与设备备忘录，是 Agent 了解你本地软硬件环境的小抄/速查表。Skills 文件夹里的工作流是可以共享、开源的，但你的设备信息是隐私的。TOOLS.md 用来存放你个人的本地配置，比如你局域网内 SSH 服务器的别名和 IP，这样，即使工作流代码在多台设备间共享，Agent 也能通过 TOOLS.md 读取属于当前这台电脑的特有环境参数。
#### HEARTBEAT.md
定期任务表。管理 OpenClaw 的定时任务。
### 记忆维护
OpenClaw 采用两层记忆架构，全部使用 Markdown 文件实现。
#### MEMORY.md
长期记忆。存放一些不需要频繁变动但需要长期记住的决定、事实和设置。它只在私聊（DM）会话中被自动加载（群聊时为了隐私默认不加载）。
##### memory 目录
如 `/.openclaw/workspace/memory/YYYY-MM-DD.md`。Agent 自动将每日的谈话摘要、任务进展和零散观察写入对应日期的文件中，在后续会话启动时，它会自动加载今天和昨天的日记文件以恢复近期语境。
##### 记忆整理（Dreaming / 整合机制）
OpenClaw 拥有后台 Dreaming（做梦整理）机制（可在配置中开启），它会在闲时将 memory/ 日记中的临时零散内容提炼，自动总结并“晋升”写入到长期的 MEMORY.md 中，防止 context 随着时间而变得臃肿。
### 工作流
在 OpenClaw 中，特定任务的工作流被称为 Skills。
- 项目/工作区局部 Skills：~/.openclaw/workspace/skills
- 全局通用 Skills：~/.openclaw/skills/
#### 自定义工作流
- 在 skills 目录下建一个新文件夹，如`skills/my-workflow/`
- 在此文件夹下创建一个 SKILL.md 文件
- 在 SKILL.md 的顶部编写 YAML 格式的 Frontmatter（元数据），以便OpenClaw 解析何时触发该技能，并在下方用 Markdown 详细写明工作流的控制步骤。
- 重启 OpenClaw。
### 外部链接
#### discord 机器人
1. 去[Discord 开发者门户](https://discord.com/developers/home)创建机器人，加入到 discord 服务器。
2. 执行 openclaw onboard 命令，按引导填写。
3. 重启网关。
4. 给机器人发一条私信消息，按照私信机器人返回的授权命令，在本机电脑执行。如：`openclaw pairing approve discord xxxxxxxx`
5. 授权指令后，openclaw 会记录授权信息和用户ID，并将授权用户标记为所有者。
#### 微信
接入个人微信后，只能被动应答，接入企业后可双向交互。
- 执行 openclaw onboard 命令，按引导，安装微信插件。
- 执行：`openclaw channels login --channel openclaw-weixin`拉取授权二维码。
- 重启网关。
#### ClawNode
[bighamx/openclaw-android-node-apk](https://github.com/bighamx/openclaw-android-node-apk)
ClawNode 它安装在手机上后，会将智能手机转换为一个受控的 AI 硬件节点，并利用 WebSocket 协议连接到运行的 OpenClaw 网关（Gateway），方便 OpenClaw 操作手机。
该项目位于 OpenClaw 仓库，支持安卓、IOS
手机安装 ClawNode 后，按提示操作以连接到网关。如执行：openclaw qr，获取连接二维码。
注意：需修改网关监听模式，由仅监听回环地址，修改为监听局域网。
方式一：修改 `.openclaw\openclaw.json`
```json
  "gateway": {
    "mode": "local",
    "auth": {
      "mode": "token",
      "token": "xxxxxxxxxxxxxxxxxxxxxxxxxx"
    },
    "port": 18789,
    // bind 值修改为：lan
    "bind": "loopback",
```
方式二：执行命令：`openclaw config set gateway.bind "lan"`
手机节点成功连接到网关后，再次修改`.openclaw\openclaw.json`，找到如下位置，清空原有的 denyCommands 列表，新增 allowCommands 列表，复制denyCommands列表中的所有内容，填充到 allowCommands 列表，以开放高危指令。示例：
```json
  "gateway": {
    "nodes": {
      "allowCommands": [
        "camera.snap",
        "camera.clip",
        "camera.list",
        "screen.record",
        "screen.snapshot",
        "canvas.present",
        "canvas.eval",
        "canvas.hide",
        "canvas.navigate",
        "canvas.snapshot",
        "location.get",
        "system.notify",
        "device.info",
        "device.status",
        "device.health",
        "device.permissions",
        "notifications.list",
        "notifications.actions",
        "photos.latest",
        "contacts.search",
        "contacts.add",
        "calendar.events",
        "calendar.add",
        "reminders.add",
        "motion.activity",
        "motion.pedometer",
        "sms.send",
        "sms.search",
        "callLog.search"
      ],
      "denyCommands":[]
    }
  },
```
放行网关指令。
然后使用 openclaw nodes status 命令查看连接情况，观察有无功能输出。若没有，执行openclaw nodes pending，查询到请求ID，然后执行：openclaw devices approve 查询到的ID。直到成功授权，有功能输出。
### 自动化
#### 浏览器自动化
openclaw 可自动化控制内置浏览器。文档：[浏览器（由 OpenClaw 管理）](https://docs.openclaw.ai/zh-CN/tools/browser)
默认情况下创建一个独立隔离的浏览器实例进行操作。
这个浏览器有独立的浏览器配置文件、历史记录和 Cookie 等文件会被永久保存在 `~/.openclaw/browser/openclaw/user-data` 目录下，不会主动重置。
重置 openclaw 浏览器：`openclaw browser --browser-profile openclaw reset-profile`
1. cmd 执行：`openclaw config set tools.alsoAllow "[\"browser\"]"`
2. 修改 `openclaw.json`：
	- "browser" 项新增到根级目录（与 "session"、"tools" 同级）
		```json
		"browser": {
		    "enabled": true,
		    "defaultProfile": "openclaw",
		    "headless": false,
		    "ssrfPolicy": {
		      "dangerouslyAllowPrivateNetwork": true,
		      "hostnameAllowlist": ["*"]
		    }
		  },
		```
	-  检查"fetch"项，确保为以下内容：
		```json
		"fetch": {
		        "enabled": true
		```
3. 重启网关。
#### 直接操作浏览器
若需要连接到已有的浏览器实例，以不隔离的方式直接操作浏览器，需在待控制浏览器中开放远程调试功能，让 OpenClaw 借助底层的 MCP 协议直接附到正在运行的 Chrome 或 Edge 上。