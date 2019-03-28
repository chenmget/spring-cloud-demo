package com.iwhalecloud.retail.warehouse.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreReq;
import com.iwhalecloud.retail.warehouse.dto.request.markresswap.*;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.QryMktInstInfoByConditionItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.QryStoreMktInstInfoItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.StoreInventoryQuantityItemSwapResp;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.base.QueryMarkResQueryResultsSwapResp;

/**
 * 与营销资源的库存接口
 */
public interface MarketingResStoreService {
    /**
     * 移动串码入库
     *
     * @param req
     * @return
     */
    ResultVO syncTerminal(SyncTerminalSwapReq req);

    /**
     * 固网串码入库
     *
     * @param req
     * @return
     */
    ResultVO ebuyTerminal(EBuyTerminalSwapReq req);

    /**
     * 同步串码实例的变更信息到零售商仓库（目前只是退库）
     *
     * @param req
     * @return
     */
    ResultVO synMktInstStatus(SynMktInstStatusSwapReq req);


    /**
     * 按串码查询零售商仓库终端的实例信息
     *
     * @param req
     * @return
     */
    ResultVO<QueryMarkResQueryResultsSwapResp<QryStoreMktInstInfoItemSwapResp>> qryStoreMktInstInfo(QryStoreMktInstInfoSwapReq req);

    /**
     * 按多种条件查询零售商仓库终端的实例列表
     *
     * @param req
     * @return
     */
    ResultVO<QueryMarkResQueryResultsSwapResp<QryMktInstInfoByConditionItemSwapResp>> qryMktInstInfoByCondition(QryMktInstInfoByConditionSwapReq req);

    /**
     * 按串码查询零售商仓库终端的实例信息
     *
     * @param req
     * @return
     */
    ResultVO<QueryMarkResQueryResultsSwapResp<StoreInventoryQuantityItemSwapResp>> storeInventoryQuantity(StoreInventoryQuantitySwapReq req);

    /**
     * 仓库信息同步接口
     *
     * @param req
     * @return
     */
    ResultVO<Boolean> synMarkResStore(SynMarkResStoreReq req);
}
