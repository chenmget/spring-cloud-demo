package com.iwhalecloud.retail.web.controller.cms;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.service.ContentTextService;

@RestController
@CrossOrigin
@RequestMapping("/api/contentText")
public class ContentTextController {
	
	@Reference
    private ContentTextService contentTextService;

    
    
    
    
}