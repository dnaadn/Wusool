package com.example.capstone2.Config;

import com.google.genai.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Bean
    public Client geminiClient() {
        return Client.builder().apiKey("AQ.Ab8RN6JhmURiYM4T56I8nQyl7-C7QtQ1i8-7WBNXS8XUziMNuA").build();
    }
}
