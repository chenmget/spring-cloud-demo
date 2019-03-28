package com.iwhalecloud.retail.oms.service.impl;


import com.iwhalecloud.retail.oms.dto.ContentPicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.oms.manager.ContentPicManager;
import com.iwhalecloud.retail.oms.service.ContentPicService;
import java.util.List;
import java.util.ArrayList;
import com.iwhalecloud.retail.oms.entity.ContentPic;


@Service
@Slf4j
public class ContentPicServiceImpl implements ContentPicService {

    @Autowired
    private ContentPicManager contentPicManager;

    @Override
    public List<ContentPicDTO> queryContentPicList(Long contentId) {
        log.info("ContentPicServiceImpl queryContentPicList contentId={} ",contentId);
        List<ContentPicDTO> contentPicSets = new ArrayList<ContentPicDTO>();
        try{
            contentPicSets = contentPicManager.queryContentPicList(contentId);
        }catch (Exception e){
            log.info("ContentPicServiceImpl queryContentPicList Exception={} ",e);
        }
        return contentPicSets;
    }

}
