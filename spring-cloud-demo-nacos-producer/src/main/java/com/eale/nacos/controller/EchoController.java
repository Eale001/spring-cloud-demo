package com.eale.nacos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Admin
 * @Date 2021/3/16
 * @Description //EchoController
 * @Version 1.0
 **/
@RestController
public class EchoController {

    @GetMapping("/echo/{string}")
    public String echo(@PathVariable String string){
        return "Hello Nacos Discovery " + string;
    }



}
