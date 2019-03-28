package com.iwhalecloud.retail.rights.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.dto.request.ChangeMktResRegionReqDTO;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.response.MktResRegionRespDTO;

/**
 * 权益适用地区
 */
public interface MktResRegionService {

    /**
     * 权益适用地区查询
     * @param dto
     * @return
     */
    public Page<MktResRegionRespDTO> queryMktResRegion(CommonQueryByMktResIdReqDTO dto);
   
    /**
     * 权益适用地区变更
     * @param dto
     * @return
     */
    public ResultVO changeMktResRegion(ChangeMktResRegionReqDTO dto);
    
    
}
