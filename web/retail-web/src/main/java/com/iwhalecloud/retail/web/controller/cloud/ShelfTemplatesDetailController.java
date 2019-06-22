package com.iwhalecloud.retail.web.controller.cloud;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.service.ShelfTemplatesDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/shelfTemplatesDetail")
public class ShelfTemplatesDetailController {
	
    @Reference
    private ShelfTemplatesDetailService shelfTemplatesDetailService;

    
    
    
    
}