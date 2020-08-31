package com.eale.hystrix.remote;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Admin
 * @Date 2020/8/19
 * @Description
 * @Version 1.0
 **/

@Component
public class HelloRemoteHystrix implements HelloRemote {

    @Override
    public Map hello(String name) {
        Map<String,String> map = new HashMap<>();
        map.put("name", name);
        map.put("feign调用", "调用失败");
        map.put("message", "failed");

        return map;
    }
}
