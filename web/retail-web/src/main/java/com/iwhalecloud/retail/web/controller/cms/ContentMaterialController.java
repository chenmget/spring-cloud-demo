package com.iwhalecloud.retail.web.controller.cms;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.oms.service.ContentMaterialService;

@RestController
@CrossOrigin
@RequestMapping("/api/contentMaterial")
public class ContentMaterialController {
	
	@Reference
    private ContentMaterialService contentMaterialService;

    
    
    
    
}