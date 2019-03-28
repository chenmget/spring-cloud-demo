package com.iwhalecloud.retail.rights.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.UpdateCouponSupplyRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponSupplyRuleRespDTO;
import com.iwhalecloud.retail.rights.entity.CouponSupplyRule;

/**
 * @Class: CouponSupplyRuleMapper
 * @author autoCreate
 */
@Mapper
public interface CouponSupplyRuleMapper extends BaseMapper<CouponSupplyRule>{
	

	/**
     * 权益领取规则查询
     * @param page
     * @param dto
     * @return
     */
    public Page<CouponSupplyRuleRespDTO> queryCouponSupplyRule(Page<CommonQueryByMktResIdReqDTO> page, @Param("req") CommonQueryByMktResIdReqDTO dto);
    
    /**
     * 权益领取规则更新
     * @param dto
     * @return
     */
    public Integer updateCouponSupplyRule(UpdateCouponSupplyRuleReqDTO dto);
    
}