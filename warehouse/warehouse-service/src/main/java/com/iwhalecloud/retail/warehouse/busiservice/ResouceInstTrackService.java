package com.iwhalecloud.retail.warehouse.busiservice;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface ResouceInstTrackService {

    /**
     * 管理员修改
     * @param req
     * @param resp
     */
    void asynUpdateTrackForAddmin(AdminResourceInstDelReq req, ResultVO resp);

    /**
     * 商家员录入
     * @param req
     */
    void asynSaveTrackForMerchant(ResourceInstAddReq req, ResultVO resp, CopyOnWriteArrayList<String> newlist);

    /**
     * 商家员修改
     * @param req
     * @param resp
     */
    void asynUpdateTrackForMerchant(ResourceInstUpdateReq req, ResultVO resp);

    /**
     * 供应商录入
     * @param req
     */
    void asynSaveTrackForSupplier(ResourceInstAddReq req, ResultVO resp);

    /**
     * 供应商修改
     * @param req
     * @param resp
     */
    void asynDeleteTrackForSupplier(AdminResourceInstDelReq req, ResultVO resp);

    /**
     * 退换货场景下，冻结/解冻操作
     * @param req
     * @param resp
     */
    void asynUpdateTrackForSupplier(ResourceInstUpdateReq req, ResultVO resp);

    /**
     * 供应商调拨
     * @param req
     * @param resp
     */
    void asynAllocateTrackForSupplier(SupplierResourceInstAllocateReq req, ResultVO resp);

    /**
     * 供应商发货
     * @param req
     * @param resp
     */
    void asynShipTrackForSupplier(DeliveryResourceInstReq req, ResultVO resp);

    /**
     * 供应商收货
     * @param req
     * @param resp
     */
    void asynAcceptTrackForSupplier(DeliveryResourceInstReq req, ResultVO resp);

    /**
     * 供应商退货发货
     * @param req
     * @param resp
     */
    void asynBackShipTrackForSupplier(DeliveryResourceInstReq req, ResultVO resp);

    /**
     * 供应商退货收货
     * @param req
     * @param resp
     */
    void asynBackAcceptTrackForSupplier(DeliveryResourceInstReq req, ResultVO resp);

    /**
     * 零售商绿色通道导入
     * @return
     */
    void asynGreenChannelForRetail(ResourceInstAddReq req, ResultVO resp);

    /**
     * 零售商删除串码
     * @return
     */
    void asynDeleteInstForRetail(ResourceInstUpdateReq req, ResultVO resp);


    /**
     * 零售商领用串码
     *
     */
    void pickResourceInstForRetail(ResourceInstPickupReq req, ResultVO resp);

    /**
     * 零售商调拨
     * @return
     */
    void allocateResourceInstForRetail(RetailerResourceInstAllocateReq req, ResultVO resp);

    /**
     * 零售商调拨入库
     * @return
     */
    void allocateResourceIntsWarehousingForRetail(ConfirmReciveNbrReq req, ResultVO resp);

    /**
     * 零售商调拨入库取消
     * @return
     */
    void allocateResourceIntsWarehousingCancelForRetail(ConfirmReciveNbrReq req, ResultVO resp);

    /**
     * 通过串码查订单
     * @param nbr
     * @return
     */
    String qryOrderIdByNbr(String nbr);

    /**
     * 通过串码查轨迹
     * @param nbr
     * @param merchantId
     * @return
     */
    ResultVO<ResouceInstTrackDTO> getResourceInstTrackByNbrAndMerchantId(String nbr,String merchantId);

    /**
     * 查询串码轨迹列表
     * @param req
     * @return
     */
    ResultVO<List<ResouceInstTrackDTO>> listResourceInstsTrack(ResourceInstsTrackGetReq req);

    /**
     * 采购发货
     * @param req
     * @param resp
     */
    void asynTradeOutResourceInst(TradeResourceInstReq req, ResultVO resp);

    /**
     * 采购收货
     * @param req
     * @param resp
     */
    void asynTradeInResourceInst(TradeResourceInstReq req, ResultVO resp);

    /**
     * 串码退库
     * @param req
     * @param resp
     */
    void asynResetResourceInst(AdminResourceInstDelReq req, ResultVO resp);
}
