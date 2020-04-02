package com.chenm.service;

import com.chenm.service.impl.HelloRemoteHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "spring-cloud-producer",fallback = HelloRemoteHystrix.class )
//@FeignClient(name= "spring-cloud-producer")
public interface HelloRemote {
    @RequestMapping(value = "/api/hello")
    public String hello(@RequestParam(value = "name") String name);
}
