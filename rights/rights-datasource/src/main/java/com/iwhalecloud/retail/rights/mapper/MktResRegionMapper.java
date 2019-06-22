package com.iwhalecloud.retail.rights.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.response.MktResRegionRespDTO;
import com.iwhalecloud.retail.rights.entity.MktResRegion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: MktResRegionMapper
 * @author autoCreate
 */
@Mapper
public interface MktResRegionMapper extends BaseMapper<MktResRegion>{

	
	/**
     * 权益适用地区查询
     * @param dto
     * @return
     */
    public Page<MktResRegionRespDTO> queryMktResRegion(Page<CommonQueryByMktResIdReqDTO> page, @Param("req") CommonQueryByMktResIdReqDTO dto);
}