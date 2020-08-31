package com.eale.hystrix.comtroller;

import com.eale.hystrix.remote.HelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author Admin
 * @Date 2020/8/19
 * @Description
 * @Version 1.0
 **/
@RestController
public class HelloController {

    @Autowired
    HelloRemote helloRemote;

    @GetMapping("/hello")
    public Map hello(@RequestParam String name){
        return helloRemote.hello(name);
    }


}
