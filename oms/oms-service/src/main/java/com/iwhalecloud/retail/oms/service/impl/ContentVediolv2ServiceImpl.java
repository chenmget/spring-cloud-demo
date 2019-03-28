package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.oms.dto.ContentVediolv2DTO;
import com.iwhalecloud.retail.oms.entity.ContentVediolv2;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.oms.manager.ContentVediolv2Manager;
import com.iwhalecloud.retail.oms.service.ContentVediolv2Service;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ContentVediolv2ServiceImpl implements ContentVediolv2Service {

    @Autowired
    private ContentVediolv2Manager contentVediolv2Manager;


    @Override
    public List<ContentVediolv2DTO> queryContentVediolTwoList(List<Long> matids) {
        log.info("ContentVediolv2ServiceImpl queryContentVediolTwoList matids={} ", matids);
        List<ContentVediolv2DTO> contentVediolv2List= new ArrayList<ContentVediolv2DTO>();
        try {
            contentVediolv2List = contentVediolv2Manager.queryContentVediolTwoList(matids);
        } catch (Exception e) {
            log.info("ContentVediolv2ServiceImpl queryContentVediolTwoList matids={} ", matids);
        }
        return contentVediolv2List;
    }

    @Override
    public List<ContentVediolv2DTO> queryContentVediolByUpmatid(List<Long> list) {
        log.info("ContentVediolv2ServiceImpl queryContentVediolByUpmatid matids={} ", list);
        List<ContentVediolv2DTO> contentVediolv2List= new ArrayList<ContentVediolv2DTO>();
        try {
            contentVediolv2List = contentVediolv2Manager.queryContentVediolByUpmatid(list);
        } catch (Exception e) {
            log.info("ContentVediolv2ServiceImpl queryContentVediolTwoList matids={} ", list);
        }
        return contentVediolv2List;
    }


}
