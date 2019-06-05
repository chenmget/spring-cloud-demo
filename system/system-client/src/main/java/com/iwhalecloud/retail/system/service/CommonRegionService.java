package com.iwhalecloud.retail.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.dto.request.CommonRegionPageReq;

import java.util.List;

/**
 * @author zhongwenlong
 * 本地网数据 服务
 */
public interface CommonRegionService {

    /**
     * 获取本地区域 列表
     * @param req
     * @return
     */
    ResultVO<List<CommonRegionDTO>> listCommonRegion(CommonRegionListReq req);

    /**
     * 分页 获取本地区域 列表
     * @param req
     * @return
     */
    ResultVO<Page<CommonRegionDTO>> pageCommonRegion(CommonRegionPageReq req);

    /**
     * 获取 湖南本地网 列表
     *
     * @return
     */
    ResultVO<List<CommonRegionDTO>> listLan();

    /**
     * 跟regionId获取本地区域
     *
     * @param regionId
     * @return
     */
    ResultVO<CommonRegionDTO> getCommonRegionById(String regionId);

}