package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;

import java.util.List;

/**
 * 本地网数据 服务
 */
public interface CommonRegionService{

    /**
     * 获取本地区域 列表
     * @param req
     * @return
     */
    ResultVO<List<CommonRegionDTO>> listCommonRegion(CommonRegionListReq req);

    /**
     * 获取 湖南本地网 列表
     * @return
     */
    ResultVO<List<CommonRegionDTO>> listLan();

    /**
     * 跟regionId获取本地区域
     * @param regionId
     * @return
     */
    ResultVO<CommonRegionDTO> getCommonRegionById(String regionId);

}