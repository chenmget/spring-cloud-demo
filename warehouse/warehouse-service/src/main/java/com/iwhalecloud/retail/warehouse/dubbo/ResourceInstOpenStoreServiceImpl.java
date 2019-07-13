package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.GetProductQuantityByMerchantReq;
import com.iwhalecloud.retail.warehouse.dto.request.InventoryWaringReq;
import com.iwhalecloud.retail.warehouse.dto.request.UpdateStockReq;
import com.iwhalecloud.retail.warehouse.dto.response.GetProductQuantityByMerchantResp;
import com.iwhalecloud.retail.warehouse.dto.response.InventoryWarningResp;
import com.iwhalecloud.retail.warehouse.service.ResourceInstStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
@Slf4j
public class ResourceInstOpenStoreServiceImpl implements ResourceInstStoreService {

    @Autowired
    private ResourceInstStoreService resourceInstStoreService;

    @Override
    public ResultVO<Integer> getQuantityByMerchantId(String merchantId) {
        log.info("ResourceInstOpenStoreServiceImpl.getQuantityByMerchantId req={}", JSON.toJSONString(merchantId));
        return resourceInstStoreService.getQuantityByMerchantId(merchantId);
    }

    @Override
    public ResultVO<GetProductQuantityByMerchantResp> getProductQuantityByMerchant(GetProductQuantityByMerchantReq getProductQuantityByMerchant) {
        log.info("ResourceInstOpenStoreServiceImpl.getProductQuantityByMerchant req={}", JSON.toJSONString(getProductQuantityByMerchant));
        return resourceInstStoreService.getProductQuantityByMerchant(getProductQuantityByMerchant);
    }

    @Override
    public ResultVO updateStock(UpdateStockReq req) {
        log.info("ResourceInstOpenStoreServiceImpl.updateStock req={}", JSON.toJSONString(req));
        return resourceInstStoreService.updateStock(req);
    }

    @Override
    public ResultVO updateResourceInstStore(ResourceInstStoreDTO req){
        return resourceInstStoreService.updateResourceInstStore(req);
    }

    @Override
    public ResultVO<List<InventoryWarningResp>> queryInventoryWarning(List<InventoryWaringReq> req) {
        return resourceInstStoreService.queryInventoryWarning(req);
    }

}