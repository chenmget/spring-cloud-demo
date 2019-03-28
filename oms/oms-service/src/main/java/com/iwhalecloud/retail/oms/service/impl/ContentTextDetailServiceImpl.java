package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.oms.dto.ContentTextDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.manager.ContentTextDetailManager;
import com.iwhalecloud.retail.oms.service.ContentTextDetailService;
import java.util.List;


@Service
public class ContentTextDetailServiceImpl implements ContentTextDetailService {

    @Autowired
    private ContentTextDetailManager contentTextDetailManager;


    @Override
    public List<ContentTextDetailDTO> queryContentTextDetail(Long textid) {
        return contentTextDetailManager.queryContentTextDetail(textid);
    }

}