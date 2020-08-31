package com.eale.hystrix.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author Admin
 * @Date 2020/8/19
 * @Description
 * @Version 1.0
 **/
@FeignClient(name = "spring-cloud-demo-user-provider",fallback = HelloRemoteHystrix.class)
public interface HelloRemote {

    @GetMapping(value = "/provider/hello")
    public Map hello(@RequestParam(value = "name") String name);

}
