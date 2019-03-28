package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.ConfigInfoDTO;
import com.iwhalecloud.retail.system.service.ConfigInfoService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.GetProductQuantityByMerchantResp;
import com.iwhalecloud.retail.warehouse.dto.response.InventoryWarningResp;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstStoreManager;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceInstStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ResourceInstStoreServiceImpl implements ResourceInstStoreService {

    @Autowired
    private ResourceInstStoreManager resourceInstStoreManager;

    @Reference
    private ConfigInfoService configInfoService;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Autowired
    private Constant constant;

    @Override
    public ResultVO<Integer> getQuantityByMerchantId(String merchantId) {
        List<String> statusList = new ArrayList<>();
        statusList.add(ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode());
        Integer amount = resourceInstStoreManager.getQuantityByMerchantId(merchantId, statusList);
        log.info("ResourceInstStoreServiceImpl.getQuantityByMerchantId esourceInstStoreManager.getQuantityByMerchantId req.merchantId={}, req.statusList={}, resp={}", merchantId, JSON.toJSONString(statusList), amount);
        return ResultVO.success(amount);
    }

    @Override
    public ResultVO<GetProductQuantityByMerchantResp> getProductQuantityByMerchant(GetProductQuantityByMerchantReq req) {
        List<ProductQuantityItem> itemList = req.getItemList();
        StringBuffer stringBuffer = new StringBuffer("");
        GetProductQuantityByMerchantResp resp = new GetProductQuantityByMerchantResp();
        List<ProductQuantityItem> itemListResp = new ArrayList<>();
        boolean flag = true;
        for (ProductQuantityItem item : itemList) {
            StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
            storeGetStoreIdReq.setMerchantId(req.getMerchantId());
            storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
            String storeId = resouceStoreService.getStoreId(storeGetStoreIdReq);
            if (StringUtils.isBlank(storeId)) {
                return ResultVO.error(constant.getCannotGetStoreMsg());
            }
            String statusCd = ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode();
            ResourceInstStoreDTO dto = resourceInstStoreManager.getStore(req.getMerchantId(), item.getProductId(), ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode(), storeId);
            log.info("ResourceInstStoreServiceImpl.getProductQuantityByMerchant resourceInstStoreManager.getStore req.merchantId={}, req.productId={}, req.statusList={}, resp={}", req.getMerchantId(), item.getProductId(), JSON.toJSONString(statusCd), JSON.toJSONString(dto));
            ProductQuantityItem itemResp = new ProductQuantityItem();
            if (null == dto) {
                stringBuffer.append(item.getProductId() + ":库存不够;");
                flag = false;
                itemResp.setProductId(item.getProductId());
                itemResp.setNum(0L);
            } else {
                Long quantity = dto.getQuantity();
                Long onwayQuantity = dto.getOnwayQuantity();
                //库存数量- 在途数量 - 下单数量 小于0 则表示无库存
                if (Long.compare(quantity - onwayQuantity, item.getNum()) < 0) {
                    stringBuffer.append(item.getProductId() + ":库存不够;");
                    flag = false;
                }
                itemResp.setProductId(item.getProductId());
                itemResp.setNum(quantity - onwayQuantity - item.getNum());
            }
            itemListResp.add(itemResp);
        }
        resp.setItemList(itemListResp);
        resp.setInStock(flag);
        return ResultVO.success(stringBuffer.toString(), resp);
    }

    @Override
    public ResultVO updateStock(UpdateStockReq req) {
        int i = this.resourceInstStoreManager.updateStock(req);
        log.info("ResourceInstStoreServiceImpl.updateStock resourceInstStoreManager.updateStock req={}, resp={}", JSON.toJSONString(req), i);
        if (i > 0) {
            return ResultVO.success();
        } else {
            return ResultVO.error("没有更新成功");
        }
    }

    @Override
    public ResultVO updateResourceInstStore(ResourceInstStoreDTO req) {
        return ResultVO.success(resourceInstStoreManager.updateResourceInstStore(req));
    }

    @Override
    public ResultVO<List<InventoryWarningResp>> queryInventoryWarning(List<InventoryWaringReq> req) {
        log.info("ResourceInstStoreServiceImpl.queryInventoryWarning req={}", JSON.toJSONString(req));
        List<InventoryWarningResp> resp = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(req)) {
            req.forEach(item -> {
                StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
                storeGetStoreIdReq.setMerchantId(item.getMerchantId());
                storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
                String storeId = resouceStoreService.getStoreId(storeGetStoreIdReq);
                Long quantity = 0L;
                Long onWayQuantity = 0L;
                if (!StringUtils.isBlank(storeId)) {
                    ResourceInstStoreDTO dto = resourceInstStoreManager.getStore(item.getMerchantId(), item.getProductId(), ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode(), storeId);
                    quantity = null == dto ? 0L : dto.getQuantity();
                    onWayQuantity = null == dto ? 0L : dto.getOnwayQuantity();
                }

                InventoryWarningResp inventoryWarningResp = new InventoryWarningResp();
                inventoryWarningResp.setProductId(item.getProductId());
                inventoryWarningResp.setMerchantId(item.getMerchantId());

                ConfigInfoDTO configInfo = configInfoService.getConfigInfoById(ResourceConst.INVENTORY_CONFIG_ID);
                Long inventoryNum = Long.valueOf(configInfo.getCfValue());
                //库存总数量 - 在途数量 - 预警数量 小于0 则预警提示
                if (Long.compare(quantity - onWayQuantity, inventoryNum) < 0) {
                    inventoryWarningResp.setInventoryNum(quantity - onWayQuantity);
                    inventoryWarningResp.setIsInventory(true);
                } else {
                    inventoryWarningResp.setIsInventory(false);
                }
                resp.add(inventoryWarningResp);
            });
        }
        log.info("ResourceInstStoreServiceImpl.queryInventoryWarning resp={}", JSON.toJSONString(resp));
        return ResultVO.success(resp);
    }
}