package com.eale.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author hzh
 * @date 2021-03-16
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class DemoNacosConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoNacosConsumerApplication.class, args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
