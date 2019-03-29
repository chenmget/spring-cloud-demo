package com.iwhalecloud.retail.rights.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.UpdateCouponDiscountRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponDiscountRuleRespDTO;
import com.iwhalecloud.retail.rights.entity.CouponDiscountRule;

import java.util.List;

/**
 * @Class: CouponDiscountRuleMapper
 * @author autoCreate
 */
@Mapper
public interface CouponDiscountRuleMapper extends BaseMapper<CouponDiscountRule>{

	/**
     * 权益使用规则查询
     * @param page
     * @param dto
     * @return
     */
    public Page<CouponDiscountRuleRespDTO> queryCouponDiscountRule(Page<CommonQueryByMktResIdReqDTO> page, @Param("req") CommonQueryByMktResIdReqDTO dto);
    
    /**
     * 权益使用规则更新
     * @param dto
     * @return
     */
    public Integer updateCouponDiscountRule(UpdateCouponDiscountRuleReqDTO dto);

    /**
     * 删除之前的混用券
     * @param mktResId
     * @return
     */
    Integer deleteMixUseCoupon(@Param("req") String mktResId);

    /**
     * 新增混合券的记录
     * @param mktResIds
     * @param mixUseId
     * @return
     */
    Integer addMixUseCoupon(@Param("mktResIds")List<String> mktResIds, @Param("mixUseId") String mixUseId);

}