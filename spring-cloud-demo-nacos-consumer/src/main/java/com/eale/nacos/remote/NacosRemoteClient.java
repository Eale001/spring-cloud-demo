package com.eale.nacos.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Admin
 * @Date 2021/3/22
 * @Description //NacosRemoteClient
 * @Version 1.0
 **/
@FeignClient(name = "producer-nacos")
public interface NacosRemoteClient {


    @GetMapping("/echo/{string}")
    String echo(@PathVariable String string);

}
