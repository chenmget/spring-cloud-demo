package com.iwhalecloud.retail.promo.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.req.ActHistoryPurChaseAddReq;
import com.iwhalecloud.retail.promo.dto.req.ActHistoryPurChaseUpReq;
import com.iwhalecloud.retail.promo.dto.req.HistoryPurchaseQueryExistReq;

import java.util.List;

public interface HistoryPurchaseService {
    /**
     * 批量增加
     * @param actHistoryPurChaseAddReqList
     * @return
     */
    ResultVO addActHistroyPurchase(List<ActHistoryPurChaseAddReq> actHistoryPurChaseAddReqList);

    /**
     * 根据orderId和productId查询是否参与补贴
     * @param req orderId、productId
     * @return
     */
    ResultVO<Boolean> queryHistoryPurchaseQueryIsExist(HistoryPurchaseQueryExistReq req);

    /**
     * 更新状态
     * @param req
     * @return
     */
    ResultVO updateHistroyPurchase(ActHistoryPurChaseUpReq req);
}