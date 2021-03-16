package com.eale.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author hzh
 * @date 2021-03-16
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProducerNacosDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerNacosDemoApplication.class, args);
	}

}
