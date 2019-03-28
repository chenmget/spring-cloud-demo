package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.request.RegionsGetReq;
import com.iwhalecloud.retail.system.dto.request.RegionsListReq;
import com.iwhalecloud.retail.system.dto.response.RegionsGetResp;

import java.util.List;

/**
 * 普通省市县 数据服务
 * @Author he.sw
 * @Date 2018/12/01
 **/
public interface RegionsService {
    /**
     *查询省、市、区列表
     * @param req
     * @return
     */
	ResultVO<List<RegionsGetResp>> listRegions(RegionsListReq req);

    /**
     * 根据Id获得所属区域
     * @param req
     * @return
     */
	ResultVO<RegionsGetResp> getRegion(RegionsGetReq req);

    /**
     * 查询父级ID
     * @param regionId
     * @return
     */
	ResultVO<RegionsGetResp> getPregionId(String regionId);
    /**
     * 根据ID集合查询
     * @param regionIds
     * @return
     */
	ResultVO<List<RegionsGetResp>> getRegionList(List<String> regionIds);

}
