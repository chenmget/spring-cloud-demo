package com.iwhalecloud.retail.rights.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.dto.request.ChangeCouponApplyObjectReqDTO;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponByProductAndActivityIdReq;
import com.iwhalecloud.retail.rights.dto.response.CouponApplyObjectRespDTO;
import com.iwhalecloud.retail.rights.entity.CouponApplyObject;
import com.iwhalecloud.retail.rights.manager.CouponApplyObjectManager;
import com.iwhalecloud.retail.rights.service.CouponApplyObjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class CouponApplyObjectServiceImpl implements CouponApplyObjectService {

    @Autowired
    private CouponApplyObjectManager couponApplyObjectManager;

	@Override
	public Page<CouponApplyObjectRespDTO> queryCouponApplyObject(CommonQueryByMktResIdReqDTO dto) {
		return couponApplyObjectManager.queryCouponAppyObject(dto);
	}

	@Override
	public ResultVO changeCouponApplyObject(ChangeCouponApplyObjectReqDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultVO<List<CouponApplyObjectRespDTO>> queryCouponApplyObjectByCondition(QueryCouponByProductAndActivityIdReq req) {
		List<CouponApplyObject> couponApplyObjects = couponApplyObjectManager.queryCouponApplyObjectByCondition(req);
		List<CouponApplyObjectRespDTO> respDTOList = Lists.newArrayList();
		if(couponApplyObjects != null && couponApplyObjects.size() > 0){
			couponApplyObjects.forEach(item -> {
				CouponApplyObjectRespDTO dto = new CouponApplyObjectRespDTO();
				BeanUtils.copyProperties(item, dto);
				respDTOList.add(dto);
			});
		}
		return ResultVO.success(respDTOList);
	}


}