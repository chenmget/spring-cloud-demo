package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.request.RegionsGetReq;
import com.iwhalecloud.retail.system.dto.request.RegionsListReq;
import com.iwhalecloud.retail.system.dto.response.RegionsGetResp;
import com.iwhalecloud.retail.system.manager.RegionsManager;
import com.iwhalecloud.retail.system.service.RegionsService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author he.sw
 * @Date 2018/12/01
 **/
@Service
public class RegionServiceImpl implements RegionsService {
    @Resource
    private RegionsManager regionsManager;

    @Override
    public ResultVO<List<RegionsGetResp>> listRegions(RegionsListReq req) {
		return ResultVO.success(regionsManager.listRegions(req));
    }

	@Override
	public ResultVO<RegionsGetResp> getRegion(RegionsGetReq req) {
		ResultVO<RegionsGetResp> resultVo = new ResultVO<RegionsGetResp>();
		resultVo.setResultMsg("查询成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		RegionsGetResp region = regionsManager.getRegion(req);
		resultVo.setResultData(region);
		return resultVo;
	}

	@Override
	public ResultVO<RegionsGetResp> getPregionId(String regionId) {
		ResultVO<RegionsGetResp> resultVo = new ResultVO<RegionsGetResp>();
		resultVo.setResultMsg("查询成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        
		RegionsGetResp regions = regionsManager.getPregionId(regionId);
		resultVo.setResultData(regions);
		return resultVo;
	}

	/**
	 * 根据ID集合查询
	 * @param regionIds
	 * @return
	 */
	@Override
	public ResultVO<List<RegionsGetResp>> getRegionList(List<String> regionIds){
		return ResultVO.success(regionsManager.getRegionList(regionIds));
	}
    
}
