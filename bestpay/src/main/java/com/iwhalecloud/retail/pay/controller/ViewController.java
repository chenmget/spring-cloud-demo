package com.iwhalecloud.retail.pay.controller;

import java.util.Map;

import com.iwhalecloud.retail.pay.config.BusConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/view"})
public class ViewController
{

    @Autowired
    private BusConfig busConfig;

    @RequestMapping({"/pay"})
    public String payAllocate(Map<String, Object> map)
    {
        map.put("busConfig", this.busConfig);
        return "pay";
    }

    public BusConfig getBusConfig() {
        return this.busConfig;
    }

    public void setBusConfig(BusConfig busConfig) {
        this.busConfig = busConfig;
    }
}