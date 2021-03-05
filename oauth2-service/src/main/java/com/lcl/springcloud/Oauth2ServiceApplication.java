package com.lcl.springcloud;

import com.lcl.springcloud.interceptor.FeignClientInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Oauth2ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServiceApplication.class, args);
    }

    @Bean
    public FeignClientInterceptor feignClientInterceptor() {
        return new FeignClientInterceptor();
    }
}
