package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.oms.dto.ContentVideosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.oms.manager.ContentVideosManager;
import com.iwhalecloud.retail.oms.service.ContentVideosService;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ContentVideosServiceImpl implements ContentVideosService {

    @Autowired
    private ContentVideosManager contentVideosManager;

    @Override
    public List<ContentVideosDTO> queryContentVideoDefalutList(List<Long> contentIds){
        log.info("ContentVideosServiceImpl queryContentVideoDefalutList contentIds={} ", contentIds);
        List<ContentVideosDTO> contentVideosDefaultContents = new ArrayList<ContentVideosDTO>();
        try {
            contentVideosDefaultContents = contentVideosManager.queryContentVideoDefalutList(contentIds);
        } catch (Exception e) {
            log.info("ContentVideosServiceImpl queryContentVideoDefalutList Exception={} ", e);
        }
        return contentVideosDefaultContents;
    }

}
