package com.iwhalecloud.retail.rights.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponApplyObjectRespDTO;
import com.iwhalecloud.retail.rights.entity.CouponApplyObject;

/**
 * @Class: CouponApplyObjectMapper
 * @author autoCreate
 */
@Mapper
public interface CouponApplyObjectMapper extends BaseMapper<CouponApplyObject>{

	
	/**
     * 权益适用对象查询
     * @param page
     * @param dto
     * @return
     */
    public Page<CouponApplyObjectRespDTO> queryCouponAppyObject(Page<CommonQueryByMktResIdReqDTO> page, @Param("req") CommonQueryByMktResIdReqDTO dto);
}