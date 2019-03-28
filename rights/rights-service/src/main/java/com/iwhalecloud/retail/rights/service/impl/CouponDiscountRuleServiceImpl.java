package com.iwhalecloud.retail.rights.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.common.ResultCodeEnum;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.SaveCouponDiscountRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.request.UpdateCouponDiscountRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponDiscountRuleRespDTO;
import com.iwhalecloud.retail.rights.manager.CouponDiscountRuleManager;
import com.iwhalecloud.retail.rights.service.CouponDiscountRuleService;


@Service
public class CouponDiscountRuleServiceImpl implements CouponDiscountRuleService {

    @Autowired
    private CouponDiscountRuleManager couponDiscountRuleManager;

	@Override
	public Page<CouponDiscountRuleRespDTO> queryCouponDiscountRule(CommonQueryByMktResIdReqDTO dto) {
		return couponDiscountRuleManager.queryCouponDiscountRule(dto);
	}

	@Override
	public ResultVO updateCouponDiscountRule(UpdateCouponDiscountRuleReqDTO dto) {
		ResultVO resultVo = new ResultVO();
		resultVo.setResultMsg("优惠券抵扣规则更新成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		
		Integer updateResult = couponDiscountRuleManager.updateCouponDiscountRule(dto);
		if (updateResult <= 0) {
			resultVo.setResultMsg("优惠券抵扣规则更新失败");
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
		}
		return resultVo;
	}

	@Override
	public ResultVO saveCouponDiscountRule(SaveCouponDiscountRuleReqDTO dto) {
		ResultVO resultVo = new ResultVO();
		resultVo.setResultMsg("优惠券抵扣规则新增成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		
		Integer saveResult = couponDiscountRuleManager.saveCouponDiscountRule(dto);
		if (saveResult <= 0) {
			resultVo.setResultMsg("优惠券抵扣规则新增失败");
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
		}
		return resultVo;
	}



	

    
    
    
    
}