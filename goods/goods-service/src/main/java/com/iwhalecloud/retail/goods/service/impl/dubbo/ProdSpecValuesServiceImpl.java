package com.iwhalecloud.retail.goods.service.impl.dubbo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecValuesAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecValuesGetReq;
import com.iwhalecloud.retail.goods.dto.resp.ProdSpecValuesGetResp;
import com.iwhalecloud.retail.goods.manager.ProdSpecValuesManager;
import com.iwhalecloud.retail.goods.service.dubbo.ProdSpecValuesService;

@Component("prodSpecValuesService")
@Service
public class ProdSpecValuesServiceImpl implements ProdSpecValuesService {

    @Autowired
    private ProdSpecValuesManager prodSpecValuesManager;

	@Override
	public ResultVO<ProdSpecValuesGetResp> getSpecValues(
			ProdSpecValuesGetReq req) {
		ResultVO<ProdSpecValuesGetResp> resultVo = new ResultVO<>();
		resultVo.setResultMsg("没有数据");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		if (StringUtils.isNotBlank(req.getSpecValueId())) {
			ProdSpecValuesGetResp specValue = prodSpecValuesManager.getSpecValues(req.getSpecValueId());
			resultVo.setResultData(specValue);
			resultVo.setResultMsg("查询成功");
		}
		return resultVo;
	}

	@Override
	public ResultVO<List<ProdSpecValuesGetResp>> listSpecValues(ProdSpecValuesGetReq req) {
		
		ResultVO<List<ProdSpecValuesGetResp>> resultVo = new ResultVO<>();
		resultVo.setResultMsg("查询成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		if (StringUtils.isNotBlank(req.getSpecId())) {
			List<ProdSpecValuesGetResp> specValues = prodSpecValuesManager.querySpecValueBySpecId(req.getSpecId());
			resultVo.setResultData(specValues);
		}
		return resultVo;
	}

	@Override
	public ResultVO<Integer> addSpecValues(ProdSpecValuesAddReq req) {
		ResultVO<Integer> resultVo = new ResultVO<>();
		resultVo.setResultMsg("添加成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		
		Integer num = prodSpecValuesManager.addSpecValue(req);
		if (num <= 0) {
			resultVo.setResultMsg("添加失败");
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
		}
		return resultVo;
	}

    
}