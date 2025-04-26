package com.cube.user.clients.asaas.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsaasClientConfig {

    @Value("${asaas.api-key}")
    private String apiKey;

    @Bean
    public RequestInterceptor asaasRequestInterceptor() {
        return requestTemplate ->
                requestTemplate.header("access_token", apiKey);
    }

}
