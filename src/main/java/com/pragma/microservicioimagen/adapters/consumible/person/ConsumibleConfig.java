package com.pragma.microservicioimagen.adapters.consumible.person;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ConsumibleConfig {

    private static final String URL = "http://servicio-persona/api/v1/client";

    @Bean
    @LoadBalanced
    public WebClient.Builder readPerson() {
        return WebClient.builder().baseUrl(URL);
    }
}
