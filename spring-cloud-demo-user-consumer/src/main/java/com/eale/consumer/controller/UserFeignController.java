package com.eale.consumer.controller;

import com.eale.consumer.remote.DemoRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author Admin
 * @Date 2020/8/18
 * @Description
 * @Version 1.0
 **/

@RestController
public class UserFeignController {

    @Autowired
    DemoRemoteClient demoRemoteClient;

    @GetMapping("/remote/demo/getData/{uid}")
    public Map basePath(@PathVariable String uid , String data){
        return demoRemoteClient.getData(uid, data);
    }

}
