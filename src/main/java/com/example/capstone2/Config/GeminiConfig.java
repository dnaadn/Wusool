package com.example.capstone2.Config;

import com.google.genai.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Bean
    public Client geminiClient() {
        return Client.builder().apiKey("AQ.Ab8RN6L6Y83Wz6krf8dBBmtWtejTi2pzV81RFXXOTunVyChX1A").build();
    }
}
