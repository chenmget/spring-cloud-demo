package com.iwhalecloud.retail.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.member.dto.request.BindingAddReq;
import com.iwhalecloud.retail.member.dto.request.BindingDeleteReq;
import com.iwhalecloud.retail.member.dto.request.BindingQueryReq;
import com.iwhalecloud.retail.member.dto.request.BindingUpdateReq;
import com.iwhalecloud.retail.member.dto.response.BindingQueryResp;
import com.iwhalecloud.retail.member.manager.BindingManager;
import com.iwhalecloud.retail.member.service.BindingService;


@Service
public class BindingServiceImpl implements BindingService {

    @Autowired
    private BindingManager bindingManager;

	@Override
	public ResultVO<Integer> insertBinding(BindingAddReq req) {
		
		ResultVO<Integer> resultVo = new ResultVO<Integer>();
		resultVo.setResultMsg("添加成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		
		Integer num = bindingManager.insertBinding(req);
		resultVo.setResultData(num);
		return resultVo;
	}

	@Override
	public ResultVO<Integer> deleteBindingCondition(BindingDeleteReq req) {
		
		ResultVO<Integer> resultVo = new ResultVO<Integer>();
		resultVo.setResultMsg("删除成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		
		Integer num = bindingManager.deleteBindingCondition(req);
		resultVo.setResultData(num);
		return resultVo;
	}

	@Override
	public ResultVO<List<BindingQueryResp>> queryeBindingCodition(BindingQueryReq req) {
		
		ResultVO<List<BindingQueryResp>> resultVo = new ResultVO<List<BindingQueryResp>>();
		resultVo.setResultMsg("查询成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		
		List<BindingQueryResp> list = bindingManager.queryeBindingCodition(req);
		resultVo.setResultData(list);
		return resultVo;
	}

	@Override
	public ResultVO<Integer> updateBindingCodition(BindingUpdateReq req) {
		
		ResultVO<Integer> resultVo = new ResultVO<Integer>();
		resultVo.setResultMsg("更新成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		
		Integer num = bindingManager.updateBindingCodition(req);
		resultVo.setResultData(num);
		return resultVo;
	}

	

	
	
    
    
}