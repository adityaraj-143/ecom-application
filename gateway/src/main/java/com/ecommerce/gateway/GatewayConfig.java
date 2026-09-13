package com.ecommerce.gateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    @Bean
    public RedisRateLimiter redisRateLimiter () {
        return new RedisRateLimiter(10,15   ,1);
    }

    @Bean
    public KeyResolver hostNameKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r-> r
                        .path("/api/products/**")
                        .filters(f -> f
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(hostNameKeyResolver()))
                                .circuitBreaker(config -> config
                                .setName("ecomService")
                                .setFallbackUri("forward:/fallback/products")))
                        .uri("lb://PRODUCT-SERVICE"))
                .route("user-service", r-> r
                        .path("/api/users/**")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("ecomService")
                                .setFallbackUri("forward:/fallback/users")))
                        .uri("lb://USER-SERVICE"))
                .route("order-service", r-> r
                        .path("/api/orders/**", "/cart/**")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("ecomService")
                                .setFallbackUri("forward:/fallback/orders")))
                        .uri("lb://ORDER-SERVICE"))
                .route("eureka-server", r-> r
                        .path("/eureka/main")
                        .filters(f -> f.rewritePath("/eureka/main", "/"))
                        .uri("http://localhost:8761"))
                .route("eureka-server-static", r-> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();
    }
}
