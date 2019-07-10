package com.iwhalecloud.retail.warehouse.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstItmsLimitSaveReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstItmsLimitUpdateReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstItmsLimitResp;

public interface ResourceInstItmsLimitService {

    /**
     * 添加一个 限额
     * @param req
     * @return
     */
    ResultVO<Integer> saveResourceInstItmsLimit(ResourceInstItmsLimitSaveReq req);

    /**
     * 根据商家ID 获取一个 限额
     * @param lanId
     * @return
     */
    ResultVO<ResourceInstItmsLimitResp> getResourceInstItmsLimit(String lanId);

    /**
     * 更新一个 商家限额
     * @param req
     * @return
     */
    ResultVO<Integer> updateResourceInstItmsLimit(ResourceInstItmsLimitUpdateReq req);


}