package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.dto.ContentTextDTO;
import com.iwhalecloud.retail.oms.manager.ContentTextManager;
import com.iwhalecloud.retail.oms.service.ContentTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ContentTextServiceImpl implements ContentTextService {

    @Autowired
    private ContentTextManager contentTextManager;


    @Override
    public List<ContentTextDTO> queryContentText(Long contentid) {
        log.info("ContentTextServiceImpl queryContentText contentid={} ",contentid);
        List<ContentTextDTO>  contentTexts = new ArrayList<ContentTextDTO>();
        try {
            contentTexts = contentTextManager.queryContentText(contentid);
        } catch (Exception e) {
            log.info("ContentTextServiceImpl queryContentText Exception={} ",e);
        }
        return contentTexts;
    }
}
