package com.iwhalecloud.retail.rights.manager;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import com.iwhalecloud.retail.rights.dto.response.CouponSupplyRuleRespDTO;
import com.iwhalecloud.retail.rights.entity.CouponSupplyRule;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.SaveCouponDiscountRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.request.UpdateCouponDiscountRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponDiscountRuleRespDTO;
import com.iwhalecloud.retail.rights.entity.CouponDiscountRule;
import com.iwhalecloud.retail.rights.mapper.CouponDiscountRuleMapper;


@Component
public class CouponDiscountRuleManager{
    @Resource
    private CouponDiscountRuleMapper couponDiscountRuleMapper;
    
    
    /**
	 * 根据权益主键查询权益使用规则
	 * @param dto
	 * @return
	 */
    public Page<CouponDiscountRuleRespDTO> queryCouponDiscountRule(CommonQueryByMktResIdReqDTO dto){
    	Page<CommonQueryByMktResIdReqDTO> page = new Page<CommonQueryByMktResIdReqDTO>(dto.getPageNo(), dto.getPageSize());
    	return couponDiscountRuleMapper.queryCouponDiscountRule(page, dto);
    }
    
    /**
     * 权益使用规则更新
     * @param dto
     * @return
     */
    public Integer updateCouponDiscountRule(UpdateCouponDiscountRuleReqDTO dto){
        dto.setUpdateDate(new Date());
    	return couponDiscountRuleMapper.updateCouponDiscountRule(dto);
    }
    
    /**
     * 权益使用规则新增
     * @param dto
     * @return
     */
    public Integer saveCouponDiscountRule(SaveCouponDiscountRuleReqDTO dto){
    	CouponDiscountRule t = new CouponDiscountRule();
        BeanUtils.copyProperties(dto, t);
        t.setCreateDate(new Date());
        t.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNOBTAIN);
    	return couponDiscountRuleMapper.insert(t);
    }

    /**
     *根据优惠券使用规则
     * @param mktResId
     * @return
     */
    public CouponDiscountRuleRespDTO queryCouponDiscountRuleOne(String mktResId){
        CouponDiscountRuleRespDTO couponDiscountRuleRespDTO = new CouponDiscountRuleRespDTO();
        QueryWrapper<CouponDiscountRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(CouponDiscountRule.FieldNames.mktResId.getTableFieldName(),mktResId);
        CouponDiscountRule couponDiscountRule = couponDiscountRuleMapper.selectOne(queryWrapper);
        BeanUtils.copyProperties(couponDiscountRule,couponDiscountRuleRespDTO);
        return couponDiscountRuleRespDTO;
    }

    /**
     * 删除优惠券使用规则
     * @param mktResId
     * @return
     */
    public int deleteCouponDiscountRule(String mktResId){
        QueryWrapper<CouponDiscountRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CouponDiscountRule.FieldNames.mktResId.getTableFieldName(),mktResId);
        return couponDiscountRuleMapper.delete(queryWrapper);
    }

    /**
     * 根据优惠券id查询抵扣规则
     * @param mktResId
     * @return
     */
    public List<CouponDiscountRule> queryDiscountRule(List<String> mktResId){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.in("MKT_RES_ID",mktResId);
        List<CouponDiscountRule> discountRules = couponDiscountRuleMapper.selectList(queryWrapper);
        return discountRules;
    }

    /**
     * 根据标识Id查询抵扣规则
     * @param mktResId
     * @return
     */
    public CouponDiscountRule queryDiscountRuleById(String mktResId){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("MKT_RES_ID",mktResId);
        CouponDiscountRule discountRule = couponDiscountRuleMapper.selectOne(queryWrapper);
        return discountRule;
    }
}
