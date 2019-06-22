package com.iwhalecloud.retail.rights.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.common.ResultCodeEnum;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.SaveCouponEffExpRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.request.UpdateCouponEffExpRuleReqDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponEffExpRuleRespDTO;
import com.iwhalecloud.retail.rights.manager.CouponEffExpRuleManager;
import com.iwhalecloud.retail.rights.service.CouponEffExpRuleService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class CouponEffExpRuleServiceImpl implements CouponEffExpRuleService {

    @Autowired
    private CouponEffExpRuleManager couponEffExpRuleManager;

	@Override
	public Page<CouponEffExpRuleRespDTO> queryCouponEffExpRule(CommonQueryByMktResIdReqDTO dto) {
		return couponEffExpRuleManager.queryCouponEffExpRule(dto);
	}

	@Override
	public ResultVO updateCouponEffExpRule(UpdateCouponEffExpRuleReqDTO dto) {
		ResultVO resultVo = new ResultVO();
		resultVo.setResultMsg("优惠券生失效规则更新成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		
		Integer updateResult = couponEffExpRuleManager.updateCouponEffExpRule(dto);
		if (updateResult <= 0) {
			resultVo.setResultMsg("优惠券生失效规则更新失败");
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
		}
		return resultVo;
	}

	@Override
	public ResultVO saveCouponEffExpRule(SaveCouponEffExpRuleReqDTO dto) {
		ResultVO resultVo = new ResultVO();
		resultVo.setResultMsg("优惠券生失效规则新增成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		
		Integer saveResult = couponEffExpRuleManager.saveCouponEffExpRule(dto);
		if (saveResult <= 0) {
			resultVo.setResultMsg("优惠券生失效规则新增失败");
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
		}
		return resultVo;
	}

	



	

    
    
    
    
}