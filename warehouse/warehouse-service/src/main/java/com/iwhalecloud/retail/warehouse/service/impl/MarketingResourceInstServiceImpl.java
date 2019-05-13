package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductForResourceResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceBatchRecService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.request.markresswap.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.model.MerchantInfByNbrModel;
import com.iwhalecloud.retail.warehouse.service.MarketingResStoreService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lhr 2019-03-08 14:55:30
 */
@Service("marketingResourceInstService")
@Slf4j
public class MarketingResourceInstServiceImpl implements SupplierResourceInstService {
    @Reference
    private ResouceStoreService resouceStoreService;

    @Reference
    private MarketingResStoreService marketingResStoreService;

    @Autowired
    private ResourceInstService resourceInstService;

    @Reference
    private ProductService productService;

    @Reference
    private MerchantService merchantService;

    @Autowired
    private ResourceBatchRecService resourceBatchRecService;

    @Autowired
    private Constant constant;

    @Override
    public ResultVO addResourceInst(ResourceInstAddReq req) {
        return null;
    }

    @Override
    public ResultVO delResourceInst(ResourceInstUpdateReq req) {
        return null;
    }

    @Override
    public ResultVO resetResourceInst(ResourceInstUpdateReq req) {
        return null;
    }

    @Override
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq dto) {
        return null;
    }

    @Override
    public ResultVO allocateResourceInst(SupplierResourceInstAllocateReq req) {
        return null;
    }

    @Override
    public ResultVO validResourceInst(ValidResourceInstReq req) {
        return null;
    }

    @Override
    public ResultVO deliveryOutResourceInst(DeliveryResourceInstReq req) {
        return null;
    }

    @Override
    public ResultVO deliveryInResourceInst(DeliveryResourceInstReq req) {
        log.info("MarketingResourceInstServiceImpl.deliveryInResourceInst req={}", JSON.toJSONString(req));
        String merchantId = req.getBuyerMerchantId();
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(merchantId);
        List<SyncTerminalItemSwapReq> syncTerminalItemSwapReqs = Lists.newArrayList();
        List<EBuyTerminalItemSwapReq> eBuyTerminalItemReqs = Lists.newArrayList();
        // 获取目标仓库
        StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
        storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storeGetStoreIdReq.setMerchantId(merchantId);
        String destStroeId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("MarketingResourceInstServiceImpl.deliveryInResourceInst resouceStoreService.getStoreId merchantId={},destStroeId={}", merchantId, destStroeId);
        if (StringUtils.isBlank(destStroeId)) {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }
        // 获取源仓库
        storeGetStoreIdReq.setMerchantId(req.getSellerMerchantId());
        String mktResStroeId = resouceStoreService.getStoreId(storeGetStoreIdReq);
        log.info("MarketingResourceInstServiceImpl.deliveryInResourceInst resouceStoreService.getStoreId merchantId={},mktResStroeId={}", merchantId, mktResStroeId);
        if (StringUtils.isBlank(mktResStroeId)) {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }
        List<String> nbrList = new ArrayList<>();
        Map<String, List<String>> mktResIdAndNbrMap = new HashMap<>();
        for (DeliveryResourceInstItem deliveryResourceInstItem : req.getDeliveryResourceInstItemList()) {
            ProductGetByIdReq productReq = new ProductGetByIdReq();
            productReq.setProductId(deliveryResourceInstItem.getProductId());
            ResultVO<ProductForResourceResp> productRespResultVO = productService.getProductForResource(productReq);
            log.info("MarketingResourceInstServiceImpl.deliveryInResourceInst getProductForResource.getProduct req={} resp={}", JSON.toJSONString(productReq), JSON.toJSONString(productRespResultVO));
            String sn = "";
            String isFixedLine = "";
            if (productRespResultVO.isSuccess() && productRespResultVO.getResultData() != null) {
                sn = productRespResultVO.getResultData().getSn();
                isFixedLine = productRespResultVO.getResultData().getIsFixedLine();
            }
            mktResIdAndNbrMap.put(deliveryResourceInstItem.getProductId(), deliveryResourceInstItem.getMktResInstNbrs());
            nbrList.addAll(deliveryResourceInstItem.getMktResInstNbrs());
            for (String nbr : deliveryResourceInstItem.getMktResInstNbrs()) {
                SyncTerminalItemSwapReq syncTerminalItemSwapReq = new SyncTerminalItemSwapReq();
                syncTerminalItemSwapReq.setBarCode(nbr);
                syncTerminalItemSwapReq.setDirectPrice(String.valueOf(deliveryResourceInstItem.getSalesPrice()));
                syncTerminalItemSwapReq.setLanId(merchantResultVO.getResultData().getLanId());
                // 25位编码
                syncTerminalItemSwapReq.setProductCode(sn);
                // 零售商导入选社采
                syncTerminalItemSwapReq.setPurchaseType(ResourceConst.PURCHASE_TYPE.PURCHASE_TYPE_12.getCode());
                syncTerminalItemSwapReq.setStoreId(destStroeId);
                MerchantInfByNbrModel merchantInfByNbrModel = resourceInstService.qryMerchantInfoByNbr(nbr);
                log.info("MarketingResourceInstServiceImpl.deliveryInResourceInst resourceInstService.qryMerchantInfoByNbr req={} resp={}", JSON.toJSONString(nbr), JSON.toJSONString(merchantInfByNbrModel));
                syncTerminalItemSwapReq.setProvSupplyId(merchantInfByNbrModel.getProvSupplyId());
                syncTerminalItemSwapReq.setProvSupplyName(merchantInfByNbrModel.getProvSupplyName());
                syncTerminalItemSwapReq.setCitySupplyId(merchantInfByNbrModel.getCitySupplyId());
                syncTerminalItemSwapReq.setCitySupplyName(merchantInfByNbrModel.getCitySupplyName());
                syncTerminalItemSwapReqs.add(syncTerminalItemSwapReq);
                // 固网终端
                if (ResourceConst.CONSTANT_YES.equals(isFixedLine)) {
                    EBuyTerminalItemSwapReq eBuyTerminalItemSwapReq = new EBuyTerminalItemSwapReq();
                    BeanUtils.copyProperties(syncTerminalItemSwapReq, eBuyTerminalItemSwapReq);
                    eBuyTerminalItemSwapReq.setMktId(sn);
                    eBuyTerminalItemReqs.add(eBuyTerminalItemSwapReq);
                }
            }
        }
        ResultVO syncTerminalResultVO = null;
        ResultVO eBuyTerminalResultVO = null;
        if (CollectionUtils.isNotEmpty(syncTerminalItemSwapReqs)) {
            SyncTerminalSwapReq syncTerminalSwapReq = new SyncTerminalSwapReq();
            syncTerminalSwapReq.setMktResList(syncTerminalItemSwapReqs);
            syncTerminalResultVO = marketingResStoreService.syncTerminal(syncTerminalSwapReq);
            log.info("MarketingResourceInstServiceImpl.deliveryInResourceInst marketingResStoreService.syncTerminal req={}", JSON.toJSONString(syncTerminalSwapReq), JSON.toJSONString(syncTerminalResultVO));
        }
        if (CollectionUtils.isNotEmpty(eBuyTerminalItemReqs)) {
            EBuyTerminalSwapReq eBuyTerminalSwapReq = new EBuyTerminalSwapReq();
            eBuyTerminalSwapReq.setMktResList(eBuyTerminalItemReqs);
            eBuyTerminalResultVO = marketingResStoreService.ebuyTerminal(eBuyTerminalSwapReq);
            log.info("MarketingResourceInstServiceImpl.deliveryInResourceInst marketingResStoreService.ebuyTerminal req={}", JSON.toJSONString(eBuyTerminalSwapReq), JSON.toJSONString(eBuyTerminalResultVO));
        }
        Boolean notSucess = (syncTerminalResultVO != null && !syncTerminalResultVO.isSuccess()) || (eBuyTerminalResultVO != null && !eBuyTerminalResultVO.isSuccess());
        if (!notSucess) {
            // step3 增加事件和批次
            BatchAndEventAddReq batchAndEventAddReq = new BatchAndEventAddReq();
            batchAndEventAddReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
            batchAndEventAddReq.setLanId(merchantResultVO.getResultData().getLanId());
            batchAndEventAddReq.setRegionId(merchantResultVO.getResultData().getCity());
            batchAndEventAddReq.setMktResIdAndNbrMap(mktResIdAndNbrMap);
            batchAndEventAddReq.setMerchantId(merchantResultVO.getResultData().getMerchantId());
            batchAndEventAddReq.setDestStoreId(destStroeId);
            batchAndEventAddReq.setMktResStoreId(mktResStroeId);
            batchAndEventAddReq.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
            batchAndEventAddReq.setMktResInstNbrs(nbrList);
            batchAndEventAddReq.setCreateStaff(merchantResultVO.getResultData().getMerchantId());
            batchAndEventAddReq.setObjId(req.getOrderId());
            batchAndEventAddReq.setObjType(ResourceConst.EVENT_OBJTYPE.ALLOT.getCode());
            resourceBatchRecService.saveEventAndBatch(batchAndEventAddReq);
            log.info("MarketingResourceInstServiceImpl.deliveryInResourceInst resourceBatchRecService.saveEventAndBatch req={},resp={}", JSON.toJSONString(batchAndEventAddReq));
            return ResultVO.success(true);
        } else {
            String errorMsg1 = (syncTerminalResultVO != null && !syncTerminalResultVO.isSuccess()) ? "" : syncTerminalResultVO.getResultMsg();
            String errorMsg2 = (eBuyTerminalResultVO != null && !eBuyTerminalResultVO.isSuccess()) ? "" : eBuyTerminalResultVO.getResultMsg();
            return ResultVO.error(errorMsg1 + errorMsg2);
        }
    }

    @Override
    public ResultVO backDeliveryOutResourceInst(DeliveryResourceInstReq req) {
        log.info("MarketingResourceInstServiceImpl.backDeliveryOutResourceInst req={}", JSON.toJSONString(req));
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(req.getBuyerMerchantId());
        List<DeliveryResourceInstItem> deliveryResourceInstItemList = req.getDeliveryResourceInstItemList();
        List<String> mktResInstNbrs = Lists.newArrayList();
        for (DeliveryResourceInstItem deliveryResourceInstItem : deliveryResourceInstItemList) {
            mktResInstNbrs.addAll(deliveryResourceInstItem.getMktResInstNbrs());
        }
        SynMktInstStatusSwapReq synMktInstStatusSwapReq = new SynMktInstStatusSwapReq();
        synMktInstStatusSwapReq.setLanId(merchantResultVO.getResultData().getLanId());
        synMktInstStatusSwapReq.setBarCode(String.join(",", mktResInstNbrs));
        log.info("MarketingResourceInstServiceImpl.backDeliveryOutResourceInst marketingResStoreService.synMktInstStatus req={}", JSON.toJSONString(synMktInstStatusSwapReq));
        return marketingResStoreService.synMktInstStatus(synMktInstStatusSwapReq);
    }

    @Override
    public ResultVO backDeliveryInResourceInst(DeliveryResourceInstReq req) {
        return null;
    }

    @Override
    public ResultVO<List<ResourceInstListPageResp>> getBatch(ResourceInstBatchReq req) {
        return null;
    }

    @Override
    public ResultVO<Boolean> updateInstState(ResourceInstUpdateReq req) {
        return null;
    }

    @Override
    public ResultVO confirmReciveNbr(ConfirmReciveNbrReq req) {
        return null;
    }

    @Override
    public ResultVO confirmRefuseNbr(ConfirmReciveNbrReq req) {
        return null;
    }
}
