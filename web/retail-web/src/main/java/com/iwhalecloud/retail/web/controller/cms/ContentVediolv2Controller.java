package com.iwhalecloud.retail.web.controller.cms;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.service.ContentVediolv2Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/contentVediolv2")
public class ContentVediolv2Controller {
	
	@Reference
    private ContentVediolv2Service contentVediolv2Service;

    
    
    
    
}