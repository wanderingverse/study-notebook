#### **一、基础操作指令**

| 指令                  | 功能               | 示例                    |
| ------------------- | ---------------- | --------------------- |
| `ollama run <模型名>`  | 运行指定模型（自动下载若不存在） | `ollama run llama3`   |
| `ollama list`       | 查看本地已下载的模型列表     | `ollama list`         |
| `ollama pull <模型名>` | 手动下载模型           | `ollama pull mistral` |
| `ollama rm <模型名>`   | 删除本地模型           | `ollama rm llama2`    |
| `ollama help`       | 查看帮助文档           | `ollama help`         |

#### **二、模型交互指令**

##### **1. 直接对话**

```bash
ollama run llama3 "用中文写一首关于秋天的诗"
```

##### **2. 进入交互模式**

```bash
ollama run llama3
# 进入后输入内容，按 Ctrl+D 或输入 `/bye` 退出
```

##### **3. 从文件输入**

```bash
ollama run llama3 --file input.txt
```

##### **4. 流式输出控制**

| 参数           | 功能         | 示例                             |
| -------------- | ------------ | -------------------------------- |
| `--verbose`    | 显示详细日志 | `ollama run llama3 --verbose`    |
| `--nowordwrap` | 禁用自动换行 | `ollama run llama3 --nowordwrap` |

#### **三、模型管理**

##### **1. 自定义模型配置（Modelfile）**

创建 `Modelfile` 文件：

```bash
FROM llama3  # 基础模型
PARAMETER temperature 0.7  # 控制随机性（0-1）
PARAMETER num_ctx 4096     # 上下文长度
SYSTEM """ 你是一个严谨的学术助手，回答需引用论文来源。"""                # 系统提示词
```

构建自定义模型：

```bash
ollama create my-llama3 -f Modelfile
ollama run my-llama3
```

##### **2. 查看模型信息**

```bash
ollama show <模型名> --modelfile  # 查看模型配置
ollama show <模型名> --parameters # 查看运行参数
```

#### **四、高级功能**

##### **1. API 调用**

启动 API 服务

```bash
ollama serve
```

通过 HTTP 调用

```bash
curl http://localhost:11434/api/generate -d '{
  "model": "llama3",
  "prompt": "你好",
  "stream": false
}'
```

##### **2. GPU 加速配置**

```bash
# 指定显存分配比例（50%）
ollama run llama3 --num-gpu 50
```