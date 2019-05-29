package com.iwhalecloud.retail.warehouse.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsTrackDetailGetReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstTrackDetailListResp;

import java.util.List;

public interface ResouceInstTrackDetailService {

    /**
     * 通过串码查轨迹明细
     * @param req
     * @return
     */
    ResultVO<List<ResourceInstTrackDetailListResp>> getResourceInstTrackDetailByNbr(ResourceInstsTrackDetailGetReq req);
}
