package com.chill.mallang.config;

import com.theokanning.openai.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIClientConfig {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    @Bean
    public OpenAiService openAiService(){
        return new OpenAiService(openAiApiKey);
    }
}
