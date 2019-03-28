package com.iwhalecloud.retail.rights.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.SaveCouponSupplyRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.request.UpdateCouponSupplyRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponSupplyRuleRespDTO;
import com.iwhalecloud.retail.rights.entity.CouponSupplyRule;
import com.iwhalecloud.retail.rights.mapper.CouponSupplyRuleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


@Component
public class CouponSupplyRuleManager{
    @Resource
    private CouponSupplyRuleMapper couponSupplyRuleMapper;
    
    
    /**
	 * 权益领取规则查询
	 * @param dto
	 * @return
	 */
    public Page<CouponSupplyRuleRespDTO> queryCouponSupplyRule(CommonQueryByMktResIdReqDTO dto){
    	Page<CommonQueryByMktResIdReqDTO> page = new Page<CommonQueryByMktResIdReqDTO>(dto.getPageNo(), dto.getPageSize());
    	return couponSupplyRuleMapper.queryCouponSupplyRule(page, dto);
    }
    
    /**
     * 权益领取规则更新
     * @param dto
     * @return
     */
    public Integer updateCouponSupplyRule(UpdateCouponSupplyRuleReqDTO dto){
        dto.setUpdateDate(new Date());
    	return couponSupplyRuleMapper.updateCouponSupplyRule(dto);
    }
    
    /**
     * 权益领取规则新增
     * @param dto
     * @return
     */
    public Integer saveCouponSupplyRule(SaveCouponSupplyRuleReqDTO dto){
    	CouponSupplyRule t = new CouponSupplyRule();
    	BeanUtils.copyProperties(dto, t);
    	t.setCreateDate(new Date());
    	t.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNOBTAIN);
    	return couponSupplyRuleMapper.insert(t);
    }

   /**
     *根据优惠券查询领取规则
     * @param mktResId
     * @return
     */
    public CouponSupplyRuleRespDTO queryCouponSupplyRuleOne(String mktResId){
        CouponSupplyRuleRespDTO couponSupplyRuleRespDTO = new CouponSupplyRuleRespDTO();
        QueryWrapper<CouponSupplyRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CouponSupplyRule.FieldNames.mktResId.getTableFieldName(),mktResId);
        CouponSupplyRule couponSupplyRule = couponSupplyRuleMapper.selectOne(queryWrapper);
        BeanUtils.copyProperties(couponSupplyRule,couponSupplyRuleRespDTO);
        return couponSupplyRuleRespDTO;
    }

    /**
     * 删除优惠券查询领取规则
     * @param mktResId
     * @return
     */
    public int deleteCouponSupplyRule(String mktResId){
        QueryWrapper<CouponSupplyRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CouponSupplyRule.FieldNames.mktResId.getTableFieldName(),mktResId);
        return couponSupplyRuleMapper.delete(queryWrapper);
    }
}
