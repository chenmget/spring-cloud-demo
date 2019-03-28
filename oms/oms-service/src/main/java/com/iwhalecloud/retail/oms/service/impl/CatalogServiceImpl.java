package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.dto.CataLogDTO;
import com.iwhalecloud.retail.oms.entity.Catalog;
import com.iwhalecloud.retail.oms.manager.CatalogManager;
import com.iwhalecloud.retail.oms.service.CatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogManager catalogManager;


    @Override
    public List<CataLogDTO> queryCatalog(Long cataId) {
        log.info("CatalogServiceImpl queryCatalog cataId={} ",cataId);
        List<CataLogDTO> cataLogDTOList = new ArrayList<CataLogDTO>();
        try {
            cataLogDTOList = catalogManager.queryCatalog(cataId);
        }catch (Exception e){
            log.info("CatalogServiceImpl queryCatalog Exception={} ",e);
        }
        return cataLogDTOList;
    }


}
