package com.iwhalecloud.retail.web.controller.cms;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.service.ContentPicService;

@RestController
@CrossOrigin
@RequestMapping("/api/contentPic")
public class ContentPicController {
	
	@Reference
    private ContentPicService contentPicService;

    
    
    
    
}