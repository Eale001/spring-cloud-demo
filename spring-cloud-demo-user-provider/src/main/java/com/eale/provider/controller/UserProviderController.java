package com.eale.provider.controller;

import com.eale.provider.entity.User;
import com.eale.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Admin
 * @Date 2020/8/7
 * @Description
 * @Version 1.0
 **/
@RestController
@RequestMapping("/provider")
public class UserProviderController {


    @Autowired
    UserService userService;

    @RequestMapping("/user/{id}")
    public User getUser(@PathParam("id")Integer id){
        return userService.getUser(id);
    }


    @GetMapping("/getData/{uid}")
    @ResponseBody
    public Map<String,String> getData(@PathVariable String uid, String data){
        System.out.println("#spring-cloud-study-demo#");
        System.out.println("uid->"+uid+",data->"+data);
        Map<String,String> map=new HashMap<String,String>();
        map.put(uid,data);
        map.put("feign","远程调用微服务");
        map.put("demo","本地微服务");
        return map;
    }

    @GetMapping("/hello")
    @ResponseBody
    public Map<String,String> hello(@RequestParam(value = "name") String name){
        Map<String,String> map=new HashMap<String,String>();
        map.put("name",name);
        map.put("feign","远程调用微服务");
        map.put("demo","本地微服务");
        return map;
    }


}
