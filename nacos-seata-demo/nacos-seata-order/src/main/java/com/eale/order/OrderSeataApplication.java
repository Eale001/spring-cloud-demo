package com.eale.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author Admin
 * @Date 2021/3/22
 * @Description //TODO
 * @Version 1.0
 **/
@SpringBootApplication
@MapperScan({"io.seata.samples.integration.order.mapper"})
@EnableDiscoveryClient
public class OrderSeataApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderSeataApplication.class,args);
    }
}
