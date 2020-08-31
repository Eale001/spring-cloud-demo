package com.eale.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Admin
 * @Date 2020/8/18
 * @Description
 * @Version 1.0
 **/
@RestController
public class HelloController {


    @Value("${neo.hello}")
    private String hello;

    @GetMapping("/hello")
    public String getHello(){
        return this.hello;
    }


}
