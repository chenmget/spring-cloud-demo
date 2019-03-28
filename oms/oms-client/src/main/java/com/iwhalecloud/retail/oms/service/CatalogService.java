package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.oms.dto.CataLogDTO;
import java.util.List;

public interface CatalogService {
    /**
     * 查询目录
     * @param cataId
     * @return
     */
    List<CataLogDTO> queryCatalog(Long cataId);
}