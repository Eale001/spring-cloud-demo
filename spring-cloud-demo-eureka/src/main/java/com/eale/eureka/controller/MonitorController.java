package com.eale.eureka.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Admin
 * @Date 2020/8/5
 * @Description
 * @Version 1.0
 **/
@RestController
@RequestMapping("/")
public class MonitorController {


    @GetMapping("/monitor")
    public String sayHello(){
        return "hello, this is eureka!";

    }


}
