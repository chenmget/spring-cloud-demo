package com.iwhalecloud.retail.rights.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.common.ResultCodeEnum;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.SaveCouponSupplyRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.request.UpdateCouponSupplyRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponSupplyRuleRespDTO;
import com.iwhalecloud.retail.rights.manager.CouponSupplyRuleManager;
import com.iwhalecloud.retail.rights.service.CouponSupplyRuleService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class CouponSupplyRuleServiceImpl implements CouponSupplyRuleService {

    @Autowired
    private CouponSupplyRuleManager couponSupplyRuleManager;

	@Override
	public Page<CouponSupplyRuleRespDTO> queryCouponSupplyRule(CommonQueryByMktResIdReqDTO dto) {
		return couponSupplyRuleManager.queryCouponSupplyRule(dto);
	}

	@Override
	public ResultVO updateCouponSupplyRule(UpdateCouponSupplyRuleReqDTO dto) {
		ResultVO resultVo = new ResultVO();
		resultVo.setResultMsg("优惠券领取规则更新成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		
		Integer updateResult = couponSupplyRuleManager.updateCouponSupplyRule(dto);
		if (updateResult <= 0) {
			resultVo.setResultMsg("优惠券领取规则更新失败");
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
		}
		return resultVo;
	}

	@Override
	public ResultVO saveCouponSupplyRule(SaveCouponSupplyRuleReqDTO dto) {
		ResultVO resultVo = new ResultVO();
		resultVo.setResultMsg("优惠券领取规则新增成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		
		Integer saveResult = couponSupplyRuleManager.saveCouponSupplyRule(dto);
		if (saveResult <= 0) {
			resultVo.setResultMsg("优惠券领取规则新增失败");
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
		}
		return resultVo;
	}

	
	

	



	

    
    
    
    
}