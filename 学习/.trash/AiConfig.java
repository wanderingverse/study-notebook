@Slf4j
@Configuration
public class AiConfig {

    /**
     * 模型请求 url
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
    @Value("qwen-plus")
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