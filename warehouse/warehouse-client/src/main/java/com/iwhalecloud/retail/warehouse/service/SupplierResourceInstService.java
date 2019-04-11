package com.iwhalecloud.retail.warehouse.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;

import java.util.List;

public interface SupplierResourceInstService {

    /**
     * 添加串码
     *
     * @param req
     * @return
     */
    ResultVO addResourceInst(ResourceInstAddReq req);

    /**
     * 删除串码
     *
     * @param req
     * @return
     */
    ResultVO delResourceInst(ResourceInstUpdateReq req);

    /**
     * 还原串码
     *
     * @param req
     * @return
     */
    ResultVO resetResourceInst(ResourceInstUpdateReq req);

    /**
     * 获取列表
     *
     * @param req
     * @return
     */
    ResultVO<Page<ResourceInstListResp>> getResourceInstList(ResourceInstListReq req);

    /**
     * 调拨串码
     *
     * @param req
     * @return
     */
    ResultVO allocateResourceInst(SupplierResourceInstAllocateReq req);


    /**
     * 订单发货 判断串码有效性
     *
     * @param req
     * @return
     */
    ResultVO validResourceInst(ValidResourceInstReq req);

    /**
     * 订单发货 串码出库
     *
     * @param req
     * @return
     */
    ResultVO deliveryOutResourceInst(DeliveryResourceInstReq req);

    /**
     * 订单收货确认 串码入库
     *
     * @param req
     * @return
     */
    ResultVO deliveryInResourceInst(DeliveryResourceInstReq req);

    /**
     * 订单退货 串码出库
     *
     * @param req
     * @return
     */
    ResultVO backDeliveryOutResourceInst(DeliveryResourceInstReq req);

    /**
     * 订单退货 串码入库
     *
     * @param req
     * @return
     */
    ResultVO backDeliveryInResourceInst(DeliveryResourceInstReq req);

    /**
     * 调拨批量查询
     *
     * @param req
     * @return
     */
    ResultVO<List<ResourceInstListResp>> getBatch(ResourceInstBatchReq req);

    /**
     * 冻结/解冻
     *
     * 1、地级供货商在申请退货退款后，卖家审核后，串码状态建议变成“退货中”批注[P2]:作为问题修改
     * 2、卖家在收到货，确认退款，退货流程走完后，串码状态依然是“已销售”，建议变成“在库可用”
     *
     * 1、买家向卖家申请换货，卖家已经审核，串码状态还是“已销售”未发生变化，建议是“换货中”批注[P5]:作为问题修改
     * 2、换货流程走完后，状态还是“已销售”未发生变化，建议变成“在库可用”
     *
     * @param req
     * @return
     */
    ResultVO<Boolean> updateInstState(ResourceInstUpdateReq req);

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
}