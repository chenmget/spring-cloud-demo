package com.iwhalecloud.retail.order2b.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyDeliveryResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyDeliveryReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyExtReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReceivingReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.entity.PurApplyItemDetail;

import java.util.List;

public interface PurchaseApplyService {

    /**
     * 采购单发货
     *
     * @param req
     * @return
     */
    ResultVO delivery(PurApplyDeliveryReq req);

    /**
     * 采购单确认收货
     *
     * @param req
     * @return
     */
    ResultVO receiving(PurApplyReceivingReq req);

    /**
     * 修改采购申请单状态
     *
     * @param req
     * @return
     */
    ResultVO updatePurApplyStatus(PurApplyReq req);

    /**
     * 新增采购申请单扩展信息
     * @param req
     * @return
     */
    ResultVO addPurApplyExtInfo(PurApplyExtReq req);

    /**
     * 更新采购申请单扩展信息
     * @param req
     * @return
     */
    ResultVO updatePurApplyExtInfo(PurApplyExtReq req);

    /**
     * 获取发货信息
     * @param req
     * @return
     */
    ResultVO<Page<PurApplyDeliveryResp>> getDeliveryInfoByApplyID(PurApplyReq req);

   public  Integer updatePurApplyItemDetailStatusCd(List<String> list);

    List<PurApplyItemDetail>  getDeliveryListByApplyID(String applyId);




}
