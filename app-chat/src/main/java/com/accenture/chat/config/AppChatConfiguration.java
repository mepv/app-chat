package com.accenture.chat.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@LoadBalancerClient(name = "billing-service")
public class AppChatConfiguration {

    @Bean
    @LoadBalanced
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}
