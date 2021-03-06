package com.eale.nacos.controller;

import com.eale.nacos.remote.NacosRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author Admin
 * @Date 2021/3/16
 * @Description //ConsumerController
 * @Version 1.0
 **/
@RestController
public class ConsumerController {


    private final RestTemplate restTemplate;

    @Autowired
    private NacosRemoteClient remoteClient;

    @Autowired
    public ConsumerController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

    @GetMapping(value = "/echo/{str}")
    public String echo(@PathVariable String str) {
        return restTemplate.getForObject("http://producer-nacos/echo/" + str, String.class);
    }

    @GetMapping(value = "/echoRemote/{str}")
    public String echoRemote(@PathVariable String str) {
        return remoteClient.echo(str);
    }


}
