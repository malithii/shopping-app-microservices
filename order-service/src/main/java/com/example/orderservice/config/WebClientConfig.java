package com.example.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    //WebClient class is from spring-webflux dependency
    public WebClient webClient(){
        return WebClient.builder().build();
    }
}
