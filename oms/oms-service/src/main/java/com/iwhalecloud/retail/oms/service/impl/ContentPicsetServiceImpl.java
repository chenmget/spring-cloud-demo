package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.oms.dto.ContentPicsetDTO;
import com.iwhalecloud.retail.oms.entity.ContentPicset;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.oms.manager.ContentPicsetManager;
import com.iwhalecloud.retail.oms.service.ContentPicsetService;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ContentPicsetServiceImpl implements ContentPicsetService {

    @Autowired
    private ContentPicsetManager contentPicsetManager;


    @Override
    public List<ContentPicsetDTO> queryContentPicsetList(Long contentId) {
        log.info("ContentPicServiceImpl queryContentPicsetList contentId={} ",contentId);
        List<ContentPicsetDTO> contentPicsets = new ArrayList<ContentPicsetDTO>();
        try {
            contentPicsets = contentPicsetManager.queryContentPicsetList(contentId);
        } catch (Exception e) {
            log.info("ContentPicServiceImpl queryContentPicsetList Exception={} ",e);
        }
        return contentPicsets;
    }
}
