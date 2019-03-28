package com.iwhalecloud.retail.web.controller.cloud;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.oms.service.DefaultCategoryService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/defaultCategory")
public class DefaultCategoryController {
	
    @Reference
    private DefaultCategoryService defaultCategoryService;

    
    
    
    
}