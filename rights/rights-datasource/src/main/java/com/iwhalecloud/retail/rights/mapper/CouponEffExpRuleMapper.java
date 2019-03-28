package com.iwhalecloud.retail.rights.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.UpdateCouponEffExpRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponEffExpRuleRespDTO;
import com.iwhalecloud.retail.rights.entity.CouponEffExpRule;

/**
 * @Class: CouponEffExpRuleMapper
 * @author autoCreate
 */
@Mapper
public interface CouponEffExpRuleMapper extends BaseMapper<CouponEffExpRule>{
	

	/**
     * 权益生失效规则查询
     * @param page
     * @param dto
     * @return
     */
    public Page<CouponEffExpRuleRespDTO> queryCouponEffExpRule(Page<CommonQueryByMktResIdReqDTO> page, @Param("req") CommonQueryByMktResIdReqDTO dto);
    
    /**
     * 权益生失效规则更新
     * @param dto
     * @return
     */
    public Integer updateCouponEffExpRule(UpdateCouponEffExpRuleReqDTO dto);
    
}