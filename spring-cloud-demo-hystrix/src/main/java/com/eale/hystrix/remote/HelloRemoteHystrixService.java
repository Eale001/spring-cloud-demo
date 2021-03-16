package com.eale.hystrix.remote;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Admin
 * @Date 2021/3/16
 * @Description //HelloRemoteHystrixService
 * @Version 1.0
 **/
public class HelloRemoteHystrixService {

    @HystrixCommand(fallbackMethod = "hello")
    public Map hello(String name) {
        Map<String,String> map = new HashMap<>();
        map.put("name", name);
        map.put("feign调用", "调用失败");
        map.put("message", "failed");

        return map;
    }

}
