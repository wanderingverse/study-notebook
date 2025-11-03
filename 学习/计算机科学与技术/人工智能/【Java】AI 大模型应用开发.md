# AI 与大模型概述
## AI
AI（Artificial Intelligence，人工智能）指使机器像人类一样思考、学习和解决问题的技术。
## 神经网络
### 神经元和感知机模型
#### 神经元
![](src/神经元.png)
#### 感知机模型
罗森布拉特提出感知机模型，用于模拟神经元。
在感知机模型中，输入类比神经元的树突、权重类比神经元的连接强度、激活函数类比神经元的突触。假设输入和激活函数都不变的情况下，我们可以通过调整权重值，得到不同的输出。
每个神经元上使用的**权重**和**阈值（又称偏置）**，统称为**参数**。单个神经元上的参数数量等于权重 + 1。
![](src/感知机模型.png)
### 神经网络
由多个感知机叠加成多层感知机，构成神经网络。
每一层感知机都可以对输入的信息做整合处理并输出，输出的结果又作为下一层感知机的输入，这样层层传递得到最终的输出。
理论上，只要多层感知机模型足够宽、足够深，就能够解决足够复杂的任务。
![](src/神经网络.png)
## 大模型
通过代码实现的神经网络数学模型，称为模型。
这种具备学习能力。可以自主学习人们事先准备好并交给他的数据。在学习的过程中，它会自动设置神经网络中需要的参数。
通常会把参数规模在`100 B（1000 亿）`以上的模型称为**大模型**。
token 是大模型处理文本的基本单位，不同的分词器计算得出的 token 和 token 数可能不一致。
# 大模型应用开发
## 部署和调用大模型
### 本地部署大模型
1. 安装并启动 [Ollama](https://ollama.com/)。启动后默认监听 11434 端口。
2. 下载 [模型](https://ollama.com/search)。
3. 安装并启动模型到 Ollama：`ollama run 模型名`。启动后会进入交互模式，可与模型进行交互。
4. 停止对话并退出：交互模式下输入：`/bye`
5. Ollama 常用命令
	- `ollama run <模型名>`：运行指定模型（若不存在则自动下载）
	- `ollama list`：查看本地已下载的模型列表
	- `ollama rm <模型名>`：删除本地模型
	- `ollama show <模型名> --modelfile`：查看模型配置
	- `ollama show <模型名> --parameters`：查看运行参数
### 调用大模型
调用文档：[Ollama Blog](https://ollama.com/blog/thinking)
请求方式：POST
请求体：JSON。详见官方文档。
核心请求参数（以`阿里云百炼大模型服务平台`为例）：
- model：指定当前要调用的模型名。
- messages：发送给模型的数据，模型根据发送的数据给出合适的响应。
	- role：本条消息的类型（角色）
		- user：用户发送的消息
		- system：系统消息，可设定模型回复风格等。
		- assistant：模型响应的消息。
	- content：具体消息内容。
- stream：调用方式。是否启用流式调用。默认使用阻塞式调用。
- enable_search：是否启用联网搜索。启用后，模型将联网搜索结果并作为参考信息。默认不开启。
核心响应参数（以`阿里云百炼大模型服务平台`为例）：
- choices：模型生成的内容**数组**，包含一或多条内容。
	- message：本次模型输出的消息内容。
	- finish_reason：本次输出消息的结束原因。
		- stop：输出完毕，自然结束
		- length：生成内容过长
	- index：当前内容在 choices 数组中的索引。
- usage：本次会话过程中使用的 token 信息。
	- prompt_tokens：用户的输入转换成 token 的个数。
	- completion_tokens：模型生成的回复转换成 token 的个数。
	- total_tokens：用户输入和模型生成的总 token 个数。
- created：本次会话被创建时的时间戳。
- model：本次会话使用的模型名称。
## [LangChain4J](https://docs.langchain4j.dev/)
### 集成
#### 非 SpringBoot 集成
1. 添加 Maven 依赖。JDK 17 及以上。
	```xml
	<!-- LangChain4J OpenAI -->
	<dependency>
	    <groupId>dev.langchain4j</groupId>
	    <artifactId>langchain4j-open-ai</artifactId>
	    <version>1.8.0</version>
	</dependency>
	```
2. AI 配置类。其中，`AI_API_KEY` 配置到环境变量。
	```Java
	@Slf4j
	@Configuration
	public class AiConfig {
	    /**
	     * 模型请求 url（阿里云百炼大模型服务平台为例）
	     */
	    @Value("https://dashscope.aliyuncs.com/compatible-mode/v1")
	    private String baseUrl;
	    
	    /**
	     * apiKey
	     */
	    @Value("${AI_API_KEY}")
	    private String apiKey;
	
	    /**
	     * 模型名称
	     */
	    @Value("modelName")
	    private String modelName;
	
	    private OpenAiChatModel openAiChatModel;
	
	    /**
	     * 初始化 AI 模型
	     */
	    @PostConstruct
	    private void initAi() {
	        openAiChatModel = OpenAiChatModel.builder()
	                                         .baseUrl(baseUrl)
	                                         .apiKey(apiKey)
	                                         .modelName(modelName)
	                                         // 输出请求日志
	                                         .logRequests(true)
	                                         // 输出响应日志
	                                         .logResponses(true)
	                                         .build();
	    }
	
	    /**
	     * 向 AI 提问
	     *
	     * @param question question
	     * @return answer
	     */
	    public String chat(String question) {
	        return openAiChatModel.chat(question);
	    }
	}
	```
#### SpringBoot 集成
1. 添加 Maven 依赖。JDK 17 及以上。
	```xml
	<!-- LangChain4J -->  
	<dependency>  
	    <groupId>dev.langchain4j</groupId>  
	    <artifactId>langchain4j-spring-boot-starter</artifactId>  
	    <version>1.8.0-beta15</version>  
	</dependency>  
	<!-- LangChain4J OpenAI -->  
	<dependency>  
	    <groupId>dev.langchain4j</groupId>  
	    <artifactId>langchain4j-open-ai-spring-boot-starter</artifactId>  
	    <version>1.8.0-beta15</version>  
	</dependency>
	```
2. 注入 `OpenAiChatModel` 对象
`langchain4j-open-ai-spring-boot-starter` 会自动向 IOC 容器中注册`OpenAiChatModel` 对象，供需要时注入。
```Java
@SpringBootTest  
public class AiTest {  
    @Resource  
    private OpenAiChatModel openAiChatModel;  
  
    @Test  
    void AiChatTest() {  
        String response = openAiChatModel.chat("Hello World");  
    }  
}
```
### AiServices 工具类
#### 声明接口
```Java
public interface ArtificialIntelligenceService {  
  
    /**
     * 向 AI 提问
     *
     * @param question question
     * @return answer
     */
     String chat(String question);
}
```
#### 创建动态代理
在声明的接口方法上添加注解：`@AiService`，LangChain4j 会扫描所有标记有该注解的接口，然后自动创建这些接口的代理对象并注册到 IOC 容器中。
```Java
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT, chatModel = "openAiChatModel")
public interface OpenAiService {

    /**
     * 向 AI 提问
     *
     * @param question question
     * @return answer
     */
    @SystemMessage("value")
    String chat(String question);
}
```
##### @AiService 注解
标记在接口上。
- wiringMode：指定装配模式。
	- `AiServiceWiringMode.AUTOMATIC`：默认值。自动装配。
	- `AiServiceWiringMode.EXPLICIT`：手动装配。
- chatModel：指定需要使用的模型对象容器名。IOC 容器中 Bean 对象的容器名默认类名首字母小写。
##### @SystemMessage 注解
可标记在方法上。
参数不能为空。
#### 注入声明的接口并使用
```Java
@SpringBootTest
public class AiTest {
    @Resource
    private OpenAiService openAiService;
    
    @Test
    void AiChatTest() {
        String response = openAiService.chat("Hello World");
    }
}
```
### 流式调用
#### 添加依赖
```xml
<!-- SpringBoot Reactive -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>

<!-- LangChain4J Reactor -->
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j-reactor</artifactId>
    <version>1.8.0-beta15</version>
</dependency>
```
#### 配置流式模型对象
## Spring AI
