package com.iwhalecloud.retail.rights.manager;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.SaveCouponEffExpRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.request.UpdateCouponEffExpRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponEffExpRuleRespDTO;
import com.iwhalecloud.retail.rights.entity.CouponEffExpRule;
import com.iwhalecloud.retail.rights.mapper.CouponEffExpRuleMapper;


@Component
public class CouponEffExpRuleManager{
    @Resource
    private CouponEffExpRuleMapper couponEffExpRuleMapper;
    
    
    /**
	 * 根据权益主键查询有效期规则
	 * @param dto
	 * @return
	 */
    public Page<CouponEffExpRuleRespDTO> queryCouponEffExpRule(CommonQueryByMktResIdReqDTO dto){
    	Page<CommonQueryByMktResIdReqDTO> page = new Page<CommonQueryByMktResIdReqDTO>(dto.getPageNo(), dto.getPageSize());
    	return couponEffExpRuleMapper.queryCouponEffExpRule(page, dto);
    }
    
    /**
     * 权益生失效规则更新
     * @param dto
     * @return
     */
    public Integer updateCouponEffExpRule(UpdateCouponEffExpRuleReqDTO dto){
        dto.setUpdateDate(new Date());
    	return couponEffExpRuleMapper.updateCouponEffExpRule(dto);
    }
    
    /**
     * 权益生失效规则新增
     * @param dto
     * @return
     */
    public Integer saveCouponEffExpRule(SaveCouponEffExpRuleReqDTO dto){
    	CouponEffExpRule t = new CouponEffExpRule();
    	BeanUtils.copyProperties(dto, t);
    	t.setCreateDate(new Date());
    	t.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNOBTAIN);
    	return couponEffExpRuleMapper.insert(t);
    }

    /**
     *根据优惠券查询生失效规则
     * @param mktResId
     * @return
     */
    public CouponEffExpRuleRespDTO queryCouponEffExpRuleOne(String mktResId){
        CouponEffExpRuleRespDTO couponEffExpRuleRespDTO = new CouponEffExpRuleRespDTO();
        QueryWrapper<CouponEffExpRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(CouponEffExpRule.FieldNames.mktResId.getTableFieldName(),mktResId);
        CouponEffExpRule couponEffExpRule = couponEffExpRuleMapper.selectOne(queryWrapper);
        BeanUtils.copyProperties(couponEffExpRule,couponEffExpRuleRespDTO);
        return couponEffExpRuleRespDTO;
    }

    public int deleteCouponEffExpRule(String mktResId){
        QueryWrapper<CouponEffExpRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CouponEffExpRule.FieldNames.mktResId.getTableFieldName(),mktResId);
        return couponEffExpRuleMapper.delete(queryWrapper);
    }

    /**
     * 根据优惠券实例id查询权益生失效规则
     * @param mktResId
     * @return
     */
    public List<CouponEffExpRule> quertCouponEffExpRule(List<String> mktResId) {
        QueryWrapper<CouponEffExpRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("MKT_RES_ID",mktResId);
        return couponEffExpRuleMapper.selectList(queryWrapper);
    }

}
