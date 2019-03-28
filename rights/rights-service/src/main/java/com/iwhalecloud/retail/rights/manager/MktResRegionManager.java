package com.iwhalecloud.retail.rights.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.response.MktResRegionRespDTO;
import com.iwhalecloud.retail.rights.mapper.MktResRegionMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class MktResRegionManager{
    @Resource
    private MktResRegionMapper mktResRegionMapper;
    
    /**
     * 权益适用地区查询
     * @param dto
     * @return
     */
    public Page<MktResRegionRespDTO> queryMktResRegion(CommonQueryByMktResIdReqDTO dto){
    	Page<CommonQueryByMktResIdReqDTO> page = new Page<CommonQueryByMktResIdReqDTO>(dto.getPageNo(), dto.getPageSize());
    	return mktResRegionMapper.queryMktResRegion(page, dto);
    }
}
