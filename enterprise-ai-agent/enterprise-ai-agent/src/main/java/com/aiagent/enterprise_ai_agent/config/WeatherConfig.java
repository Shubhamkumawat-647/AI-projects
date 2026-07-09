package com.aiagent.enterprise_ai_agent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestClient;

@Configuration
public class WeatherConfig {

    @Bean
    RestClient restClient(){

        return RestClient.create();

    }

}