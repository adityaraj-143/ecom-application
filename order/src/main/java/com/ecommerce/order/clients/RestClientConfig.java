package com.ecommerce.order.clients;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Primary
    @Bean
    RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    // Load-balanced builder — only used where explicitly qualified.
    @LoadBalanced
    @Bean
    @Qualifier("loadBalanced")
    RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

}
