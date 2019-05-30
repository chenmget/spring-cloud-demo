package com.iwhalecloud.retail.warehouse.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.GetProductQuantityByMerchantReq;
import com.iwhalecloud.retail.warehouse.dto.request.InventoryWaringReq;
import com.iwhalecloud.retail.warehouse.dto.request.UpdateStockReq;
import com.iwhalecloud.retail.warehouse.dto.response.GetProductQuantityByMerchantResp;
import com.iwhalecloud.retail.warehouse.dto.response.InventoryWarningResp;

import java.util.List;

public interface ResourceInstStoreService{
    /**
     * 根据商家ID获取库存
     *
     * @param merchantId
     * @return
     */
    ResultVO<Integer> getQuantityByMerchantId(String merchantId);

    /**
     * 根据商家ID某商品是否存在库存
     *
     * @param req
     * @return
     */
    ResultVO<GetProductQuantityByMerchantResp> getProductQuantityByMerchant(GetProductQuantityByMerchantReq req);

    /**
     * 下单，修改在途串码库存数量
     *
     * @param req
     * @return
     */
    ResultVO updateStock(UpdateStockReq req);

    /**
     * 更新库存
     *
     * @param req
     * @return
     */
    ResultVO updateResourceInstStore(ResourceInstStoreDTO req);

    /**
     * 产品库存预警查询
     *
     * @param req
     * @return
     */
    ResultVO<List<InventoryWarningResp>> queryInventoryWarning(List<InventoryWaringReq> req);

    /**
     *  串码入库，与ITMS集成
     */
    void syncMktToITMS();
    /**
     * 串码入库，ITMS集成回执
     */
    void syncMktToITMSBack();

}