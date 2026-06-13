技能下载：[OpenClaw Skills Directory | Best ClawHub Plugins (ex-Moltbot)](https://openclawskills.wiki/)
配置文件路径：`.openclaw\openclaw.json`
初始化配置：openclaw onboard
启动网关：openclaw gateway
关闭网关：openclaw gateway stop
启动前端控制台：openclaw dashboard
修改配置：openclaw config
更新：openclaw update
诊断工具：openclaw doctor --fix

彻底卸载：[卸载 OpenClaw：干净、彻底且优雅地 -告别”龙虾残留”终极指南 - 知乎](https://zhuanlan.zhihu.com/p/2014664006330249234)
清空聊天消息：clear
### Agent 工作区
位于：`/.openclaw/workspace`
#### 新建工作区
1. 创建空工作区目录
2. 运行`openclaw setup --workspace 路径`
3. 修改 `openclaw.json` 配置文件，确保 agents.defaults.workspace 指向新路径。
4. 重启 Gateway


#### BOOTSTRAP.md
Agent 的初始化和新手引导程序。当 Agent 第一次启动（或者全新的工作区建立）时，它会读取此文件。文件的内容会指导 Agent 向你提问（例如：你叫什么名字？你希望我有什么性格？你常用的时区是？）
#### IDENTITY.md
身份元数据。定义 Agent 的物理/社交身份。
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