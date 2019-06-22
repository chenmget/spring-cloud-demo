package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.dto.ContentTagDTO;
import com.iwhalecloud.retail.oms.manager.ContentTagManager;
import com.iwhalecloud.retail.oms.service.ContentTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ContentTagServiceImpl implements ContentTagService {

    @Autowired
    private ContentTagManager contentTagManager;


    @Override
    public List<ContentTagDTO> queryContentTag(ContentTagDTO contentTagDTO) {
        log.info("ContentTagServiceImpl queryContentTag contentTagDTO={} ",contentTagDTO);
        List<ContentTagDTO> contentTagList = new ArrayList<ContentTagDTO>();
        try{
            contentTagList = contentTagManager.queryContentTag(contentTagDTO);
        }catch (Exception e){
            log.error("ContentTagServiceImpl queryContentTag  Exception={} ",e);
        }
        return contentTagList;
    }

}
