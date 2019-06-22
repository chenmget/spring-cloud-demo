package com.iwhalecloud.retail.goods.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationDeleteReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationGetReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationUpdateReq;
import com.iwhalecloud.retail.goods.dto.resp.ProdSpecificationGetResp;
import com.iwhalecloud.retail.goods.entity.ProdSpecification;
import com.iwhalecloud.retail.goods.manager.ProdSpecValuesManager;
import com.iwhalecloud.retail.goods.manager.ProdSpecificationManager;
import com.iwhalecloud.retail.goods.service.dubbo.ProdSpecificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("prodSpecificationService")
@Service
public class ProdSpecificationServiceImpl implements ProdSpecificationService {

    @Autowired
    private ProdSpecificationManager prodSpecificationManager;
    @Autowired
    private ProdSpecValuesManager prodSpecValuesManager;

	@Override
	public ResultVO<ProdSpecificationGetResp> getSpec(ProdSpecificationGetReq req) {
		ResultVO<ProdSpecificationGetResp> resultVo = new ResultVO<>();
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		resultVo.setResultMsg("没有数据");
		
		if (StringUtils.isNotBlank(req.getSpecId())) {
			List<ProdSpecification> specList = prodSpecificationManager.selectSpecBySpecId(req.getSpecId());
			if (null != specList && !specList.isEmpty()) {
				ProdSpecificationGetResp dto = new ProdSpecificationGetResp();
				BeanUtils.copyProperties(specList.get(0), dto);
				resultVo.setResultMsg("查询成功");
				resultVo.setResultData(dto);
			}
			
		}
		return resultVo;
	}

	@Override
	public ResultVO<List<ProdSpecificationGetResp>> listSpec() {
		ResultVO<List<ProdSpecificationGetResp>> resultVo = new ResultVO<>();
		resultVo.setResultMsg("没有数据");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		
		List<ProdSpecificationGetResp> listSpec = prodSpecificationManager.listSpec();
		if (null != listSpec && !listSpec.isEmpty()) {
			resultVo.setResultMsg("查询成功");
			resultVo.setResultData(listSpec);
		}
		return resultVo;
	}

	@Override
	@Transactional
	public ResultVO<Integer> addSpec(ProdSpecificationAddReq req) {
		ResultVO<Integer> resultVo = new ResultVO<>();
		resultVo.setResultMsg("新增成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		Integer num = prodSpecificationManager.addSpec(req);
		if (num <= 0) {
			resultVo.setResultMsg("新增失败");
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
		}
		resultVo.setResultData(num);
		return resultVo;
	}

	@Override
	@Transactional
	public ResultVO<Integer> updateSpec(ProdSpecificationUpdateReq req) {
		ResultVO<Integer> resultVo = new ResultVO<>();
		resultVo.setResultMsg("更新成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		Integer num = prodSpecificationManager.updateSpec(req);
		if (num <= 0) {
			resultVo.setResultMsg("更新失败");
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
		}
		resultVo.setResultData(num);
		return resultVo;
	}

	@Override
	@Transactional
	public ResultVO<Integer> deleteSpec(ProdSpecificationDeleteReq req) {
		ResultVO<Integer> resultVo = new ResultVO<>();
		resultVo.setResultMsg("删除成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		Integer num = prodSpecificationManager.deleteSpec(req);
		prodSpecValuesManager.deleteSpecValue(req);
		if (num <= 0) {
			resultVo.setResultMsg("删除失败");
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
		}
		resultVo.setResultData(num);
		return resultVo;
	}

    
}