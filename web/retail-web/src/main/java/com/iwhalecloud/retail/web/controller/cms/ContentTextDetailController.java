package com.iwhalecloud.retail.web.controller.cms;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.oms.service.ContentTextDetailService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/contentTextDetail")
public class ContentTextDetailController {
	
    @Reference
    private ContentTextDetailService contentTextDetailService;

    
    
    
    
}