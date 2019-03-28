package com.iwhalecloud.retail.warehouse.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;

import java.util.List;

public interface RetailerResourceInstService {

    /**
     * 绿色通道
     *
     * @param req
     * @return
     */
    ResultVO addResourceInstByGreenChannel(ResourceInstAddReq req);

    /**
     * 删除串码
     *
     * @param req
     * @return
     */
    ResultVO delResourceInst(ResourceInstUpdateReq req);

    /**
     * 根据查询条件串码实列
     *
     * @param req
     * @return
     */
    ResultVO<Page<ResourceInstListResp>> listResourceInst(ResourceInstListReq req);

    /**
     * 领用串码
     *
     * @param req
     * @return
     */
    ResultVO pickResourceInst(ResourceInstPickupReq req);

    /**
     * 退库
     *
     * @param req
     * @return
     */
    ResultVO retreatStorageResourceInst(RetreatStorageReq req);

    /**
     * 调拨批量查询
     *
     * @param req
     * @return
     */
    ResultVO<List<ResourceInstListResp>> getBatch(ResourceInstBatchReq req);

    /**
     * 零售商调拨串码
     *
     * @param req
     * @return
     */
    ResultVO allocateResourceInst(RetailerResourceInstAllocateReq req);

    /**
     * 调拨串码收货确认
     *
     * @param req
     * @return
     */
    ResultVO confirmReciveNbr(ConfirmReciveNbrReq req);

    /**
     * 调拨串码收货取消
     *
     * @param req
     * @return
     */
    public ResultVO confirmRefuseNbr(ConfirmReciveNbrReq req);

    /**
     * 根据条件查询全量的串码实例
     *
     * @param req
     * @return
     */
    ResultVO<List<ResourceInstListResp>> getExportResourceInstList(ResourceInstListReq req);
}