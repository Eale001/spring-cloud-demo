package com.eale.consumer.controller;

import com.eale.provider.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author Admin
 * @Date 2020/8/7
 * @Description
 * @Version 1.0
 **/
@RestController
@RequestMapping("/consumer")
public class UserConsumerController {

    //多个方法调用只需改一处就ok
    public static  final String URL_PREFIX = "http://localhost:8001";

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/user/{id}")
    public User getUser(@PathVariable("id")Long id){
        //调用远程服务 http请求
        String url = URL_PREFIX+"/provider/user/"+id;
        return restTemplate.getForObject(url,User.class );
    }

}
