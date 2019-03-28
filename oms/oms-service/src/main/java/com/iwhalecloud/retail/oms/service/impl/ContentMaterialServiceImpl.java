package com.iwhalecloud.retail.oms.service.impl;


import com.iwhalecloud.retail.oms.dto.ContentMaterialDTO;
import com.iwhalecloud.retail.oms.entity.ContentMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.oms.manager.ContentMaterialManager;
import com.iwhalecloud.retail.oms.service.ContentMaterialService;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ContentMaterialServiceImpl implements ContentMaterialService {

    @Autowired
    private ContentMaterialManager contentMaterialManager;


    @Override
    public List<ContentMaterialDTO> queryContentMaterialList(Long contentId) {
        log.info("ContentMaterialServiceImpl queryContentMaterialList contentId={} ",contentId);
        List<ContentMaterialDTO> contentMaterials = new ArrayList<ContentMaterialDTO>();
        try{
            contentMaterials = contentMaterialManager.queryContentMaterialList(contentId);
        }catch (Exception e){
            log.info("ContentMaterialServiceImpl queryContentMaterialList Exception={} ",e);
        }
        return contentMaterials;
    }

}
