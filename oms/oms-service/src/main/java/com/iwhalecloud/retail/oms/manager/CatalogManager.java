package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.CataLogDTO;
import com.iwhalecloud.retail.oms.entity.Catalog;
import com.iwhalecloud.retail.oms.mapper.CatalogMapper;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;


@Component
public class CatalogManager {
    @Resource
    private CatalogMapper catalogMapper;

    public List<CataLogDTO> queryCatalog(Long cataId){
        return catalogMapper.queryCatalog(cataId);
    }


}
