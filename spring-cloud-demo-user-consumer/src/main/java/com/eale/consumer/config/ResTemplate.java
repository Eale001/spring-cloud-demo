package com.eale.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author Admin
 * @Date 2020/8/7
 * @Description
 * @Version 1.0
 **/
@Configuration
public class ResTemplate {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
