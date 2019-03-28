package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.oms.dto.ContentOrderpicDTO;
import com.iwhalecloud.retail.oms.entity.ContentOrderpic;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.oms.manager.ContentOrderpicManager;
import com.iwhalecloud.retail.oms.service.ContentOrderpicService;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ContentOrderpicServiceImpl implements ContentOrderpicService {

    @Autowired
    private ContentOrderpicManager contentOrderpicManager;


    @Override
    public List<ContentOrderpicDTO> queryContentOrderPicList(Long contentId) {
        log.info("ContentOrderpicServiceImpl queryContentOrderPicList contentId={} ",contentId);
        List<ContentOrderpicDTO>  contentOrderpicList = new ArrayList<>();
        try {
            contentOrderpicList = contentOrderpicManager.queryContentOrderPicList(contentId);
        }catch (Exception e){
            log.info("ContentOrderpicServiceImpl queryContentOrderPicList Exception={} ",e);
        }
        return contentOrderpicList;
    }

}
