package com.eale.hystrix.remote;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Admin
 * @Date 2021/3/16
 * @Description //HelloRemoteFallbackFactory
 * @Version 1.0
 **/
@Component
public class HelloRemoteFallbackFactory implements FallbackFactory<HelloRemote> {
    @Override
    public HelloRemote create(final Throwable throwable) {
        return new HelloRemote() {
            @Override
            public Map hello(String name) {
                System.out.println("这里调用失败了 ，我打印哈日志");
                Map<String,String> map = new HashMap<>();
                map.put("name", name);
                map.put("feign调用", "调用失败");
                map.put("message", "failed");
                return map;
            }
        };
    }
}
