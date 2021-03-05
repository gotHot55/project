package com.lcl.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/3014:44
 */
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("route_path", r -> r.path("/del/*")
                        .uri("http://localhost:8501/del/*"))
                .build();

    }
}
