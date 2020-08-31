package com.eale.consumer.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author Admin
 * @Date 2020/8/18
 * @Description //TODO
 * @Version 1.0
 **/
@FeignClient(name = "spring-cloud-demo-user-provider")
public interface DemoRemoteClient {

    @GetMapping("/provider/getData/{uid}")
    public Map getData(@PathVariable(value="uid") String uid, @RequestParam(value="data") String data);


}
