package com.lcl.springcloud.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/315:52
 */
@Configuration
public class SentinelConfig {
    @Bean
//    @SentinelRestTemplate
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
