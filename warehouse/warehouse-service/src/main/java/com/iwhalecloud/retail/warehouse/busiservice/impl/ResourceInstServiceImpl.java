package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.exception.RetailTipException;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductResourceInstGetReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductsPageReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductForResourceResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductPageResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResourceResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.resp.TransferPermissionGetResp;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.promo.service.HistoryPurchaseService;
import com.iwhalecloud.retail.warehouse.busiservice.*;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.request.markresswap.SyncTerminalItemSwapReq;
import com.iwhalecloud.retail.warehouse.dto.request.markresswap.SyncTerminalSwapReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;
import com.iwhalecloud.retail.warehouse.entity.ResourceInst;
import com.iwhalecloud.retail.warehouse.manager.ResouceEventManager;
import com.iwhalecloud.retail.warehouse.manager.ResouceStoreManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstStoreManager;
import com.iwhalecloud.retail.warehouse.model.MerchantInfByNbrModel;
import com.iwhalecloud.retail.warehouse.service.MarketingResStoreService;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResourceInstServiceImpl implements ResourceInstService {

    @Autowired
    private ResourceInstManager resourceInstManager;

    @Autowired
    private ResouceEventManager resouceEventManager;

    @Autowired
    private ResouceStoreManager storeManager;

    @Autowired
    private ResourceInstStoreManager resourceInstStoreManager;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Autowired
    private ResouceStoreManager resouceStoreManager;

    @Reference
    private ProductService productService;

    @Reference
    private MerchantService merchantService;

    @Autowired
    private ResourceInstService resourceInstService;

    @Reference
    private MerchantRulesService merchantRulesService;

    @Reference
    private MarketingResStoreService marketingResStoreService;

    @Autowired
    private ResourceBatchRecService resourceBatchRecService;

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    @Reference
    private HistoryPurchaseService historyPurchaseService;

    @Autowired
    private Constant constant;

    @Autowired
    private ResourceInstLogService resourceInstLogService;

    @Autowired
    private ResourceInstCheckService resourceInstCheckService;

    @Override
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq req) {
        req = setProductIds(req);
        Page<ResourceInstListPageResp> page = resourceInstManager.getResourceInstList(req);
        log.info("ResourceInstServiceImpl.getResourceInstList resourceInstManager.getResourceInstList req={}", JSON.toJSONString(req));
        List<ResourceInstListPageResp> list = page.getRecords();
        if (null == list || list.isEmpty()) {
            return ResultVO.success(page);
        }

        // 按产品维度组装数据
        Map<String, List<ResourceInstListPageResp>> map = list.stream().collect(Collectors.groupingBy(t -> t.getMktResId()));
        for (Map.Entry<String, List<ResourceInstListPageResp>> entry : map.entrySet()) {
            String mktResId = entry.getKey();
            if (StringUtils.isBlank(mktResId)) {
                continue;
            }
            ProductResourceInstGetReq queryReq = new ProductResourceInstGetReq();
            queryReq.setProductId(mktResId);
            ResultVO<List<ProductResourceResp>> resultVO = productService.getProductResource(queryReq);
            log.info("ResourceInstServiceImpl.getResourceInstList productService.getProductResource req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(resultVO));
            List<ProductResourceResp> prodList = resultVO.getResultData();
            if (CollectionUtils.isEmpty(prodList)) {
                continue;
            }
            ProductResourceResp prodResp = prodList.get(0);
            List<ResourceInstListPageResp> instList =entry.getValue();
            for (ResourceInstListPageResp resp : instList) {
                // 添加产品信息
                BeanUtils.copyProperties(prodResp, resp);
                // 库中存的是串码所属用户的地市，查询展示的是零售商的地市
                if (StringUtils.isNotBlank(resp.getMerchantId())) {
                    ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(resp.getMerchantId());
                    if (merchantResultVO.isSuccess() && null != merchantResultVO.getResultData()) {
                        MerchantDTO merchantDTO = merchantResultVO.getResultData();
                        resp.setRegionName(merchantDTO.getCityName());
                        resp.setLanName(merchantDTO.getLanName());
                        resp.setMerchantName(merchantDTO.getMerchantName());
                        resp.setBusinessEntityName(merchantDTO.getBusinessEntityName());
                    }
                }
            }
        }
        return ResultVO.success(page);
    }

    @Override
    public ResultVO selectProduct(PageProductReq req) {
        String sourceType = req.getSourceType();
        if(StringUtils.isNotEmpty(sourceType) && !ResourceConst.SOURCE_TYPE.MERCHANT.getCode().equals(sourceType)){
            if(StringUtils.isEmpty(req.getMerchantId())){
                return ResultVO.error(constant.getNoMerchantMsg());
            }
            ResultVO<TransferPermissionGetResp> transferPermissionGetRespResultVO = merchantRulesService.getTransferPermission(req.getMerchantId());
            log.info("ResourceInstServiceImpl.selectProduct merchantRulesService.getTransferPermission req={},resp={}", JSON.toJSONString(req.getMerchantId()), JSON.toJSONString(transferPermissionGetRespResultVO));
            if(null != transferPermissionGetRespResultVO && transferPermissionGetRespResultVO.isSuccess() && null != transferPermissionGetRespResultVO.getResultData().getProductIdList() ){
                List<String> productIdList = transferPermissionGetRespResultVO.getResultData().getProductIdList();
                ProductsPageReq productsPageReq = new ProductsPageReq();
                BeanUtils.copyProperties(req, productsPageReq);
                productsPageReq.setProductIdList(productIdList);
                log.info("ResourceInstServiceImpl.selectProduct productService.selectPageProductAdmin req={}", JSON.toJSONString(productsPageReq));
                ResultVO<Page<ProductPageResp>> pageProduct = productService.selectPageProductAdmin(productsPageReq);
                return pageProduct;
            }else{
                return ResultVO.error(constant.getCannotGetTransferPermission());
            }
        }else{
            ProductsPageReq productsPageReq = new ProductsPageReq();
            BeanUtils.copyProperties(req, productsPageReq);
            log.info("ResourceInstServiceImpl.selectProduct productService.selectPageProductAdmin req={}", JSON.toJSONString(productsPageReq));
            ResultVO<Page<ProductPageResp>> pageProduct = productService.selectPageProductAdmin(productsPageReq);
            return pageProduct;
        }
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized ResultVO updateResourceInst(ResourceInstUpdateReq req) {
        log.info("ResourceInstServiceImpl.updateResourceInst req={}", JSON.toJSONString(req));
        Map<String, Object> data = assembleData(req, req.getDestStoreId());
        Map<String, List<ResourceInstListPageResp>> insts = (HashMap<String, List<ResourceInstListPageResp>>)data.get("productNbr");
        List<String> unavailbaleNbrs = (List<String>)data.get("unavailbaleNbrs");
        List<String> availbaleNbrs = (List<String>)data.get("availbaleNbrs");
        if (CollectionUtils.isEmpty(availbaleNbrs)) {
            return ResultVO.error("失败串码" + unavailbaleNbrs);
        }
        // step2:源修改状态，即串码源属供应商修改成出库状态
        Integer successNum = 0;
        if (!availbaleNbrs.isEmpty()) {
            ResourceInstUpdateReq updateReq = new ResourceInstUpdateReq();
            BeanUtils.copyProperties(req, updateReq);
            updateReq.setMktResStoreId(req.getDestStoreId());
            updateReq.setMktResInstNbrs(availbaleNbrs.stream().distinct().collect(Collectors.toList()));
            successNum = resourceInstManager.updateResourceInst(updateReq);
            log.info("ResourceInstServiceImpl.updateResourceInst resourceInstManager.updateResourceInst req={},resp={}", JSON.toJSONString(updateReq), JSON.toJSONString(successNum));
        }
        for (Map.Entry<String, List<ResourceInstListPageResp>> entry : insts.entrySet()) {
            List<ResourceInstListPageResp> dtoList = entry.getValue();
            ResourceInstListPageResp inst = dtoList.get(0);
            List<ResourceInstListPageResp> updatedInstList = new ArrayList<>(dtoList.size());
            for (ResourceInstListPageResp dto : dtoList) {
                ResourceInstDTO resourceInstDTO = resourceInstManager.selectById(dto.getMktResInstId());
                String statusCd = resourceInstDTO.getStatusCd();
                String changeStatusCd = req.getStatusCd();
                // 修改不成功的返回，不加事件
                if (!statusCd.equals(changeStatusCd)) {
                    continue;
                }
                updatedInstList.add(dto);
            }
            // 增加事件
            resourceInstLogService.updateResourceInstLog(req, updatedInstList);
            // step 4:修改库存(出库)
            ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
            BeanUtils.copyProperties(inst, resourceInstStoreDTO);
            resourceInstStoreDTO.setMktResStoreId(req.getDestStoreId());
            resourceInstStoreDTO.setQuantity(Long.valueOf(successNum));
            resourceInstStoreDTO.setMerchantId(req.getMerchantId());
            String statusCd = req.getStatusCd();
            // 出库类型，库存减少
            resourceInstStoreDTO.setQuantityAddFlag(false);
            if (ResourceConst.STATUSCD.AVAILABLE.getCode().equals(statusCd)) {
                // 入库类型，库存增加
                resourceInstStoreDTO.setQuantityAddFlag(true);
            }else if (ResourceConst.STATUSCD.ALLOCATIONING.getCode().equals(statusCd) || ResourceConst.STATUSCD.RESTORAGEING.getCode().equals(statusCd)) {
                // 调拨、退库中类型，库存不变，增加在途库存
                resourceInstStoreDTO.setOnwayQuantityAddFlag(true);
            }else if (ResourceConst.STATUSCD.ALLOCATIONED.getCode().equals(statusCd) || ResourceConst.STATUSCD.RESTORAGED.getCode().equals(statusCd)) {
                // 已调拨、已退库类型，库存减少，减少在途库存
                resourceInstStoreDTO.setQuantityAddFlag(false);
                resourceInstStoreDTO.setOnwayQuantityAddFlag(false);
            }else if (ResourceConst.STATUSCD.SALED.getCode().equals(statusCd) || ResourceConst.STATUSCD.DELETED.getCode().equals(statusCd)) {
                // 已销售、已删除类型，库存减少
                resourceInstStoreDTO.setQuantityAddFlag(false);
            }
            int updateResInstStore = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
            if (updateResInstStore < 1) {
                throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "库存没更新成功");
            }
            log.info("ResourceInstServiceImpl.updateResourceInst resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(updateResInstStore));
        }
        return ResultVO.success();
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized ResultVO<List<String>> updateResourceInstForTransaction(ResourceInstUpdateReq req) {
        log.info("ResourceInstServiceImpl.updateResourceInstForTransaction req={}", JSON.toJSONString(req));
        // step1:源修改状态，即串码源属供应商修改成出库状态
        List<String> mktResInstNbrList = req.getMktResInstNbrs();
        ResourceInstUpdateReq updateReq = new ResourceInstUpdateReq();
        BeanUtils.copyProperties(req, updateReq);
        updateReq.setMktResStoreId(req.getDestStoreId());
        Integer successNum = resourceInstManager.updateResourceInst(updateReq);
        log.info("ResourceInstServiceImpl.updateResourceInstForTransaction resourceInstManager.updateResourceInst req={},resp={}", JSON.toJSONString(updateReq), successNum);
        if (successNum != mktResInstNbrList.size()) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "发货出库失败");
        }
        ResourceInstsGetReq getReq = new ResourceInstsGetReq();
        getReq.setMktResId(req.getMktResId());
        getReq.setMktResInstNbrs(mktResInstNbrList);
        getReq.setMktResStoreId(req.getDestStoreId());
        List<ResourceInstDTO> updatedInstList = resourceInstManager.getResourceInsts(getReq);
        log.info("ResourceInstServiceImpl.updateResourceInstForTransaction resourceInstManager.getResourceInsts req={},resp={}", JSON.toJSONString(getReq), JSON.toJSONString(updatedInstList));
        // step3 记录事件
        resourceInstLogService.supplierDeliveryOutResourceInstLog(req, updatedInstList);
        // step 4:修改库存(出库)
        ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
        BeanUtils.copyProperties(req, resourceInstStoreDTO);
        resourceInstStoreDTO.setMktResStoreId(req.getDestStoreId());
        resourceInstStoreDTO.setQuantity(Long.valueOf(successNum));
        String statusCd = req.getStatusCd();
        // 出库类型，库存减少
        resourceInstStoreDTO.setQuantityAddFlag(false);
        if (ResourceConst.STATUSCD.AVAILABLE.getCode().equals(statusCd)) {
            // 入库类型，库存增加
            resourceInstStoreDTO.setQuantityAddFlag(true);
        }else if (ResourceConst.STATUSCD.ALLOCATIONING.getCode().equals(statusCd) || ResourceConst.STATUSCD.RESTORAGEING.getCode().equals(statusCd)) {
            // 调拨、退库中类型，库存不变，增加在途库存
            resourceInstStoreDTO.setOnwayQuantityAddFlag(true);
        }else if (ResourceConst.STATUSCD.ALLOCATIONED.getCode().equals(statusCd) || ResourceConst.STATUSCD.RESTORAGED.getCode().equals(statusCd)) {
            // 已调拨、已退库类型，库存减少，减少在途库存
            resourceInstStoreDTO.setQuantityAddFlag(false);
            resourceInstStoreDTO.setOnwayQuantityAddFlag(false);
        }else if (ResourceConst.STATUSCD.SALED.getCode().equals(statusCd) || ResourceConst.STATUSCD.DELETED.getCode().equals(statusCd)) {
            // 已销售、已删除类型，库存减少
            resourceInstStoreDTO.setQuantityAddFlag(false);
        }
        int updateResInstStore = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
        log.info("ResourceInstServiceImpl.updateResourceInstForTransaction resourceInstStoreManager.updateResourceInstStore req={} resp={}", JSON.toJSONString(resourceInstStoreDTO), updateResInstStore);
        if (updateResInstStore < 1) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "库存没更新成功");
        }
        log.info("ResourceInstServiceImpl.updateResourceInstForTransaction resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(updateResInstStore));
        return ResultVO.success();
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized Boolean addResourceInstByMerchant(ResourceInstAddReq req, CopyOnWriteArrayList<String> mktResInstNbrs) {
        log.info("ResourceInstServiceImpl.addResourceInst req={}, mktResInstNbrs={}", JSON.toJSONString(req), JSON.toJSONString(mktResInstNbrs));
        String batchId = resourceInstManager.getPrimaryKey();
        List<ResourceInst> resourceInsts = new ArrayList<ResourceInst>(mktResInstNbrs.size());
        Date now = new Date();
        for (String mktResInstNbr : mktResInstNbrs) {
            ResourceInst resourceInst = new ResourceInst();
            BeanUtils.copyProperties(req, resourceInst);
            resourceInst.setMktResInstId(resourceInstManager.getPrimaryKey());
            resourceInst.setMktResInstNbr(mktResInstNbr);
            resourceInst.setMktResBatchId(batchId);
            // 目标仓库是串码所属人的仓库
            resourceInst.setMktResStoreId(req.getDestStoreId());
            if (null != req.getCtCodeMap()) {
                resourceInst.setCtCode(req.getCtCodeMap().get(mktResInstNbr));
            }
            if (null != req.getSnCodeMap()) {
                resourceInst.setSnCode(req.getSnCodeMap().get(mktResInstNbr));
            }
            if (null != req.getMacCodeMap()) {
                resourceInst.setMacCode(req.getMacCodeMap().get(mktResInstNbr));
            }
            resourceInst.setCreateDate(now);
            resourceInst.setMerchantId(null);
            resourceInst.setMerchantName(null);
            resourceInst.setMerchantCode(null);
            resourceInst.setSourceType(req.getSourceType());
            resourceInst.setStatusDate(now);
            resourceInst.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
            resourceInsts.add(resourceInst);
        }
        Boolean addResInstCnt = resourceInstManager.saveBatch(resourceInsts);
        log.info("ResourceInstServiceImpl.addResourceInst resourceInstManager.saveBatch req={} resp={}", JSON.toJSONString(resourceInsts), addResInstCnt);
        if (!addResInstCnt) {
            return false;
        }
        ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
        BeanUtils.copyProperties(req, resourceInstStoreDTO);
        resourceInstStoreDTO.setMktResId(req.getMktResId());
        resourceInstStoreDTO.setMktResStoreId(req.getDestStoreId());
        resourceInstStoreDTO.setQuantity(Long.valueOf(mktResInstNbrs.size()));
        resourceInstStoreDTO.setQuantityAddFlag(true);
        resourceInstStoreDTO.setCreateStaff(req.getMerchantId());
        resourceInstStoreDTO.setMktResStoreId(req.getDestStoreId());
        int num = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
        log.info("ResourceInstServiceImpl.addResourceInst resourceInstStoreManager.updateResourceInstStore req={} num={}", JSON.toJSONString(resourceInstStoreDTO), num);
        if (num < 1) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "库存没更新成功");
        }
        resourceInstLogService.addResourceInstLog(req, resourceInsts, batchId);
        return addResInstCnt;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized Boolean addResourceInst(ResourceInstAddReq req) {
        log.info("ResourceInstServiceImpl.addResourceInst req={}", JSON.toJSONString(req));
        String batchId = resourceInstManager.getPrimaryKey();
        List<String> nbrList = req.getMktResInstNbrs();
        List<ResourceInst> resourceInsts = new ArrayList<ResourceInst>(nbrList.size());
        Date now = new Date();
        for (String mktResInstNbr : nbrList) {
            ResourceInst resourceInst = new ResourceInst();
            BeanUtils.copyProperties(req, resourceInst);
            resourceInst.setMktResInstId(resourceInstManager.getPrimaryKey());
            resourceInst.setMktResBatchId(batchId);
            // 目标仓库是串码所属人的仓库
            resourceInst.setMktResStoreId(req.getDestStoreId());
            if (StringUtils.isEmpty(req.getMktResInstType())) {
                resourceInst.setMktResInstType(ResourceConst.MKTResInstType.TRANSACTION.getCode());
            }
            resourceInst.setCreateDate(now);
            resourceInst.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
            resourceInst.setMktResInstNbr(mktResInstNbr);
            resourceInst.setMerchantId(null);
            resourceInst.setStatusDate(now);
            resourceInsts.add(resourceInst);
        }
        Boolean addResInstCnt = resourceInstManager.saveBatch(resourceInsts);
        log.info("ResourceInstServiceImpl.addResourceInst resourceInstManager.saveBatch req={} resp={}", JSON.toJSONString(resourceInsts), addResInstCnt);
        if (!addResInstCnt) {
            return false;
        }
        ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
        BeanUtils.copyProperties(req, resourceInstStoreDTO);
        resourceInstStoreDTO.setMktResStoreId(req.getDestStoreId());
        resourceInstStoreDTO.setQuantity(Long.valueOf(nbrList.size()));
        resourceInstStoreDTO.setQuantityAddFlag(true);
        resourceInstStoreDTO.setCreateStaff(req.getMerchantId());
        resourceInstStoreDTO.setMktResStoreId(req.getDestStoreId());
        int num = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
        log.info("ResourceInstServiceImpl.addResourceInst resourceInstStoreManager.updateResourceInstStore req={} num={}", JSON.toJSONString(resourceInstStoreDTO), num);
        if (num < 1) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "库存没更新成功");
        }
        resourceInstLogService.addResourceInstLog(req, resourceInsts, batchId);
        return addResInstCnt;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized ResultVO addResourceInstForTransaction(ResourceInstAddReq req) {
        log.info("ResourceInstServiceImpl.addResourceInstForTransaction req={}", JSON.toJSONString(req));
        String batchId = resourceInstManager.getPrimaryKey();
        List<String> mktResInstNbrList = req.getMktResInstNbrs();
        List<ResourceInst> resourceInsts = new ArrayList<ResourceInst>(mktResInstNbrList.size());
        Date now = new Date();
        for (String mktResInstNbr : mktResInstNbrList) {
            ResourceInst resourceInst = new ResourceInst();
            BeanUtils.copyProperties(req, resourceInst);
            resourceInst.setMktResInstId(resourceInstManager.getPrimaryKey());
            resourceInst.setMktResInstNbr(mktResInstNbr);
            resourceInst.setMktResBatchId(batchId);
            // 目标仓库是串码所属人的仓库
            resourceInst.setMktResStoreId(req.getDestStoreId());
            if (null != req.getCtCodeMap()) {
                resourceInst.setCtCode(req.getCtCodeMap().get(mktResInstNbr));
            }
            if (null != req.getSnCodeMap()) {
                resourceInst.setSnCode(req.getSnCodeMap().get(mktResInstNbr));
            }
            if (null != req.getMacCodeMap()) {
                resourceInst.setMacCode(req.getMacCodeMap().get(mktResInstNbr));
            }
            resourceInst.setCreateDate(now);
            resourceInst.setStatusDate(now);
            resourceInst.setSourceType(req.getSourceType());
            resourceInst.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
            resourceInst.setMerchantId(null);
            resourceInst.setMerchantName(null);
            resourceInst.setMerchantCode(null);
            resourceInsts.add(resourceInst);
        }
        Boolean addResInstCnt = resourceInstManager.saveBatch(resourceInsts);
        log.info("ResourceInstServiceImpl.addResourceInstForTransaction resourceInstManager.saveBatch req={} resp={}", JSON.toJSONString(resourceInsts), addResInstCnt);
        if (!addResInstCnt) {
            return ResultVO.error("串码入库失败");
        }
        ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
        BeanUtils.copyProperties(req, resourceInstStoreDTO);
        resourceInstStoreDTO.setMktResStoreId(req.getDestStoreId());
        resourceInstStoreDTO.setQuantity(Long.valueOf(mktResInstNbrList.size()));
        resourceInstStoreDTO.setQuantityAddFlag(true);
        resourceInstStoreDTO.setCreateStaff(req.getMerchantId());
        resourceInstStoreDTO.setMktResStoreId(req.getDestStoreId());
        int num = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
        log.info("ResourceInstServiceImpl.addResourceInstForTransaction resourceInstStoreManager.updateResourceInstStore req={} num={}", JSON.toJSONString(resourceInstStoreDTO), num);
        if (num < 1) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "库存没更新成功");
        }
        resourceInstLogService.addResourceInstLog(req, resourceInsts, batchId);
        return ResultVO.success();
    }




    /**
     * 通过品牌等查询出产品id
     * @param req
     * @return
     */
    private ResourceInstListPageReq setProductIds(ResourceInstListPageReq req){
        if(StringUtils.isNotBlank(req.getProductName()) || StringUtils.isNotBlank(req.getBrandId()) || StringUtils.isNotBlank(req.getSn())) {
            ProductResourceInstGetReq queryReq = new ProductResourceInstGetReq();
            BeanUtils.copyProperties(req, queryReq);
            ResultVO<List<ProductResourceResp>> resultVO = productService.getProductResource(queryReq);
            log.info("ResourceInstServiceImpl.setProductIds productService.getProductResource req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(resultVO));
            List<ProductResourceResp> list = resultVO.getResultData();
            if(CollectionUtils.isNotEmpty(list)) {
                req.setMktResIds(list.stream().map(ProductResourceResp::getProductId).collect(Collectors.toList()));
            }else{
                // 没有弄一个不存在的值
                req.setMktResIds(Lists.newArrayList(""));
            }
        }
        return req;
    }

    /**
     * 无效的串码主键集合,串码实列按产品维度组装成map，再设置到请求参数
     * @param req
     * @param mktResStoreId 删除更新的是目标仓库的串码;发货更新的是源仓库的串码
     * @return
     */
    private Map<String, Object> assembleData(ResourceInstUpdateReq req, String mktResStoreId){
        List<String> nbrs = Lists.newArrayList(req.getMktResInstNbrs());
        List<String> checkStatusCd = req.getCheckStatusCd();
        Map<String, Object> data = new HashMap<String, Object>(8);
        // 去重
        List<String> distinctList = nbrs.stream().distinct().collect(Collectors.toList());
        // 找出串码实列
        ResourceInstBatchReq queryReq = new ResourceInstBatchReq();
        queryReq.setMktResInstNbrs(distinctList);
        queryReq.setMktResStoreId(mktResStoreId);
        List<ResourceInstListPageResp> insts = resourceInstManager.getBatch(queryReq);
        log.info("ResourceInstServiceImpl.assembleData resourceInstManager.getResourceInst req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(insts));

        // 筛选出状态不正确的串码实列(状态在校验的状态集中的数据)
        List<ResourceInstListPageResp> unStatusCdinsts = insts.stream().filter(t -> checkStatusCd.contains(t.getStatusCd())).collect(Collectors.toList());
        // 去除状态不正确的串码实列
        insts.removeAll(unStatusCdinsts);
        // 查出来的实列对应的串码
        List<String> useNbrs = insts.stream().map(ResourceInstListPageResp::getMktResInstNbr).collect(Collectors.toList());
        // 全部串码减去筛选出来的串码为不可用串码
        nbrs.removeAll(useNbrs);
        // 按产品维度组装数据
        Map<String, List<ResourceInstListPageResp>> map = insts.stream().collect(Collectors.groupingBy(t -> t.getMktResId()));
        // 不可修改的串码
        data.put("unavailbaleNbrs", nbrs);
        // 可修改的串码
        data.put("availbaleNbrs", useNbrs);
        // 按产品分组的串码
        data.put("productNbr", map);
        return data;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized ResultVO updateResourceInstByIds(AdminResourceInstDelReq req){
        log.info("ResourceInstServiceImpl.updateResourceInstByIds req={}", JSON.toJSONString(req));
        // 组装数据
        assembleData(req);
        Map<String, List<ResourceInstDTO>> insts = req.getInsts();
        List<String> unavailbaleNbrs = req.getUnUse();
        Integer sucessNum = 0;
        for (Map.Entry<String, List<ResourceInstDTO>> entry : insts.entrySet()) {
            List<ResourceInstDTO> dtoList = entry.getValue();
            ResourceInstDTO inst = dtoList.get(0);
            // 管理员删除串码，直接查出串码实列原来的仓库id;调拨、订单销售、退换货（返销） 是源仓库、目标仓库都有的; 事件明细的分片字段是仓库id;事件的分片字段是事件类型;手动删除的事件中源仓库是自己
            List<ResourceInstDTO> updatedInstList = new ArrayList<>(dtoList.size());
            for (ResourceInstDTO dto : dtoList) {
                //step 3:修改状态
                AdminResourceInstDelReq adminResourceInstDelReq = new AdminResourceInstDelReq();
                BeanUtils.copyProperties(req, adminResourceInstDelReq);
                adminResourceInstDelReq.setMktResStoreId(dto.getMktResStoreId());
                adminResourceInstDelReq.setMktResInstIdList(Lists.newArrayList(dto.getMktResInstId()));
                Integer num = resourceInstManager.updateResourceInstByIds(adminResourceInstDelReq);
                log.info("ResourceInstServiceImpl.updateResourceInstByIds resourceInstManager.updateResourceInstByIds req={},resp={}", JSON.toJSONString(adminResourceInstDelReq), JSON.toJSONString(sucessNum));
                if(num < 1){
                    unavailbaleNbrs.add(dto.getMktResInstId());
                    continue;
                }
                updatedInstList.add(dto);
                sucessNum += 1;
            }
            //增加事件
            resourceInstLogService.updateResourceInstByIdLog(req, updatedInstList);
            // step 4:修改库存
            ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
            BeanUtils.copyProperties(inst, resourceInstStoreDTO);
            resourceInstStoreDTO.setQuantity(Long.valueOf(sucessNum));
            String statusCd = req.getStatusCd();
            if (ResourceConst.STATUSCD.AVAILABLE.getCode().equals(statusCd)) {
                // 入库类型，库存增加
                resourceInstStoreDTO.setQuantityAddFlag(true);
            }else if (ResourceConst.STATUSCD.ALLOCATIONING.getCode().equals(statusCd) || ResourceConst.STATUSCD.RESTORAGEING.getCode().equals(statusCd)) {
                // 调拨、退库中类型，库存不变，增加在途库存
                resourceInstStoreDTO.setOnwayQuantityAddFlag(true);
            }else if (ResourceConst.STATUSCD.ALLOCATIONED.getCode().equals(statusCd) || ResourceConst.STATUSCD.RESTORAGED.getCode().equals(statusCd)) {
                // 已调拨、已退库类型，库存减少，减少在途库存
                resourceInstStoreDTO.setQuantityAddFlag(false);
                resourceInstStoreDTO.setOnwayQuantityAddFlag(false);
            }else if (ResourceConst.STATUSCD.SALED.getCode().equals(statusCd) || ResourceConst.STATUSCD.DELETED.getCode().equals(statusCd)) {
                // 已销售、已删除类型，库存减少
                resourceInstStoreDTO.setQuantityAddFlag(false);
            }
            int updateRestInstStore = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
            log.info("ResourceInstServiceImpl.updateResourceInstByIds resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(updateRestInstStore));
        }

        return ResultVO.success("失败串码数据", unavailbaleNbrs);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized ResultVO updateResourceInstByIdsForTransaction(AdminResourceInstDelReq req) {
        log.info("ResourceInstServiceImpl.updateResourceInstByIdsForTransaction req={}", JSON.toJSONString(req));
        // 组装数据
        assembleData(req);
        Map<String, List<ResourceInstDTO>> insts = req.getInsts();
        List<String> unavailbaleNbrs = req.getUnUse();
        Integer sucessNum = 0;
        for (Map.Entry<String, List<ResourceInstDTO>> entry : insts.entrySet()) {
            List<ResourceInstDTO> dtoList = entry.getValue();
            ResourceInstDTO inst = dtoList.get(0);
            // 管理员删除串码，直接查出串码实列原来的仓库id;调拨、订单销售、退换货（返销） 是源仓库、目标仓库都有的; 事件明细的分片字段是仓库id;事件的分片字段是事件类型;手动删除的事件中源仓库是自己
            List<ResourceInstDTO> updatedInstList = new ArrayList<>(dtoList.size());
            for (ResourceInstDTO dto : dtoList) {
                //step 3:修改状态
                AdminResourceInstDelReq adminResourceInstDelReq = new AdminResourceInstDelReq();
                adminResourceInstDelReq.setStatusCd(req.getStatusCd());
                adminResourceInstDelReq.setUpdateStaff(req.getMerchantId());
                adminResourceInstDelReq.setMktResInstIdList(Lists.newArrayList(dto.getMktResInstId()));
                adminResourceInstDelReq.setMktResStoreId(req.getDestStoreId());
                Integer num = resourceInstManager.updateResourceInstByIds(adminResourceInstDelReq);
                log.info("ResourceInstServiceImpl.updateResourceInstByIdsForTransaction resourceInstManager.updateResourceInstByIds req={},resp={}", JSON.toJSONString(adminResourceInstDelReq), num);
                if(num < 1){
                    throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "串码更新失败");
                }
                updatedInstList.add(dto);
                sucessNum += 1;
            }
            //增加事件
            resourceInstLogService.updateResourceInstByIdLog(req, updatedInstList);
            // step 4:修改库存
            ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
            BeanUtils.copyProperties(inst, resourceInstStoreDTO);
            resourceInstStoreDTO.setMerchantId(req.getMerchantId());
            resourceInstStoreDTO.setQuantity(Long.valueOf(sucessNum));
            String statusCd = req.getStatusCd();
            if (ResourceConst.STATUSCD.AVAILABLE.getCode().equals(statusCd)) {
                // 入库类型，库存增加
                resourceInstStoreDTO.setQuantityAddFlag(true);
            }else if (ResourceConst.STATUSCD.ALLOCATIONING.getCode().equals(statusCd) || ResourceConst.STATUSCD.RESTORAGEING.getCode().equals(statusCd)) {
                // 调拨、退库中类型，库存不变，增加在途库存
                resourceInstStoreDTO.setOnwayQuantityAddFlag(true);
            }else if (ResourceConst.STATUSCD.ALLOCATIONED.getCode().equals(statusCd) || ResourceConst.STATUSCD.RESTORAGED.getCode().equals(statusCd)) {
                // 已调拨、已退库类型，库存减少，减少在途库存
                resourceInstStoreDTO.setQuantityAddFlag(false);
                resourceInstStoreDTO.setOnwayQuantityAddFlag(false);
            }else if (ResourceConst.STATUSCD.SALED.getCode().equals(statusCd) || ResourceConst.STATUSCD.DELETED.getCode().equals(statusCd)) {
                // 已销售、已删除类型，库存减少
                resourceInstStoreDTO.setQuantityAddFlag(false);
            }
            int updateRestInstStore = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
            log.info("ResourceInstServiceImpl.updateResourceInstByIdsForTransaction resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(updateRestInstStore));
        }
        return ResultVO.success("失败串码数据", unavailbaleNbrs);
    }

    /**
     * 无效的串码主键集合,串码实列按产品维度组装成map，再设置到请求参数
     * @return
     */
    private void assembleData(AdminResourceInstDelReq req){
        List<String> mktResInstIds = req.getMktResInstIdList();
        List<String> checkStatusCd = req.getCheckStatusCd();
        // 去重
        List<String> distinctList = mktResInstIds.stream().distinct().collect(Collectors.toList());
        // 找出串码实列
        ResourceInstsGetByIdListAndStoreIdReq selectReq = new ResourceInstsGetByIdListAndStoreIdReq();
        selectReq.setMktResInstIdList(distinctList);
        selectReq.setMktResStoreId(req.getDestStoreId());
        List<ResourceInstDTO> insts = resourceInstManager.selectByIds(selectReq);
        log.info("ResourceInstServiceImpl.assembleData resourceInstManager.selectByIds req={},resp={}", JSON.toJSONString(selectReq), JSON.toJSONString(insts));
        // 筛选出状态不正确的串码实列(状态在校验的状态集中的数据)
        List<ResourceInstDTO> unStatusCdinsts = insts.stream().filter(t -> checkStatusCd.contains(t.getStatusCd())).collect(Collectors.toList());
        // 去除状态不正确的串码实列
        insts.removeAll(unStatusCdinsts);
        // 查出来的实列对应的id
        List<String> useMktResInstId = insts.stream().map(ResourceInstDTO::getMktResInstId).collect(Collectors.toList());
        // 状态不正确的串码实列id
        List<String> unStatusCdinstIds = unStatusCdinsts.stream().map(ResourceInstDTO::getMktResInstId).collect(Collectors.toList());
        // 按产品维度组装数据
        Map<String, List<ResourceInstDTO>> map = insts.stream().collect(Collectors.groupingBy(t -> t.getMktResId()));
        req.setInsts(map);
        req.setMktResInstIdList(useMktResInstId);
        req.setUnUse(unStatusCdinstIds);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public synchronized ResultVO resourceInstPutIn(ResourceInstPutInReq req){
        String merchantId = req.getMerchantId();
        ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(merchantId);
        log.info("ResourceInstServiceImpl.resourceInstPutIn merchantService.getMerchantById req={},resp={}", JSON.toJSONString(merchantId), JSON.toJSONString(merchantResultVO));
        MerchantDTO merchantDTO = merchantResultVO.getResultData();
        if (null == merchantDTO) {
            return ResultVO.error("商家获取失败");
        }
        // 厂商和零售商新增不会传仓库ID，调用接口查询
        ResouceStoreDTO store = resouceStoreManager.getStore(merchantId, ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        log.info("ResourceInstServiceImpl.resourceInstPutIn resouceStoreManager.getStore req={},resp={}", JSON.toJSONString(merchantId), JSON.toJSONString(store));
        if (null == store) {
            return ResultVO.error(constant.getNoStoreMsg());
        }
        req.setMktResStoreId(store.getMktResStoreId());
        req.setLanId(merchantDTO.getLanId());
        req.setMerchantName(merchantDTO.getMerchantName());
        req.setMerchantType(merchantDTO.getMerchantType());
        req.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        Long allocateNum = 0L;
        Map<String, List<ResourceInstDTO>> insts = req.getInsts();
        for (Map.Entry<String, List<ResourceInstDTO>> entry : insts.entrySet()) {
            List<ResourceInstDTO> dtoList = entry.getValue();
            ResourceInstDTO inst = dtoList.get(0);

            String batchId = resourceInstManager.getPrimaryKey();
            List<ResourceInst> resourceInsts = new ArrayList<ResourceInst>(dtoList.size());
            for (ResourceInstDTO resourceInst : dtoList) {
                resourceInst.setMktResInstId(null);
                ResourceInst t = new ResourceInst();
                BeanUtils.copyProperties(resourceInst, t);
                BeanUtils.copyProperties(req, t);
                Date now = new Date();
                t.setMktResBatchId(batchId);
                t.setMktResStoreId(req.getDestStoreId());
                t.setStorageType(req.getStorageType());
                t.setMktResInstType(ResourceConst.MKTResInstType.TRANSACTION.getCode());
                t.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
                t.setCreateStaff("1");
                t.setStatusDate(now);
                t.setCreateDate(now);
                resourceInsts.add(t);
                allocateNum ++;
            }
            Boolean saveBatch = resourceInstManager.saveBatch(resourceInsts);
            log.info("ResourceInstServiceImpl.resourceInstPutIn resourceInstManager.addResourceInst req={}, resp={}", JSON.toJSONString(resourceInsts), saveBatch);
            if (!saveBatch) {
                return ResultVO.error("串码入库失败");
            }
            // step3:修改库存(入库库)
            ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
            BeanUtils.copyProperties(inst, resourceInstStoreDTO);
            resourceInstStoreDTO.setQuantity(Long.valueOf(dtoList.size()));
            resourceInstStoreDTO.setQuantityAddFlag(true);
            resourceInstStoreDTO.setMktResStoreId(req.getDestStoreId());
            Integer updateRestInstStore = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
            log.info("ResourceInstServiceImpl.resourceInstPutIn resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(updateRestInstStore));
            if (updateRestInstStore < 1) {
                throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "库存没更新成功");
            }
            // step4 增加事件
            ResourceInstAddReq resourceInstAddReq = new ResourceInstAddReq();
            BeanUtils.copyProperties(req, resourceInstAddReq);
            resourceInstLogService.addResourceInstLog(resourceInstAddReq, resourceInsts, batchId);
        }
        return ResultVO.success(allocateNum);
    }

    @Override
    public synchronized ResultVO<Boolean> updateInstState(ResourceInstUpdateReq req) {
        log.info("ResourceInstServiceImpl.updateInstState req={}", JSON.toJSONString(req));
        String storeSubType = req.getStoreType();
        if(StringUtils.isEmpty(storeSubType)){
            storeSubType = ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode();
        }
        ResouceStoreDTO storeDTO = storeManager.getStore(req.getMerchantId(), storeSubType);
        if(null == storeDTO){
            log.info("ResourceInstServiceImpl.updateInstState storeManager.getStore ResouceStore is null. storeType={}", JSON.toJSONString(storeSubType));
            return ResultVO.error(constant.getNoStoreMsg());
        }
        req.setMktResStoreId(storeDTO.getMktResStoreId());
        int i = resourceInstManager.batchUpdateInstState(req);
        log.info("ResourceInstServiceImpl.updateInstState resourceInstManager.batchUpdateInstState req={} resp={}", JSON.toJSONString(req), JSON.toJSONString(i));

        // step 2:修改库存(出库)
        ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
        BeanUtils.copyProperties(req, resourceInstStoreDTO);
        Integer changeQuantity = req.getMktResInstNbrs().size();
        String statusCd = req.getStatusCd();
        // 解冻
        if (ResourceConst.STATUSCD.AVAILABLE.getCode().equals(statusCd)) {
            resourceInstStoreDTO.setQuantityAddFlag(true);
            resourceInstStoreDTO.setOnwayQuantityAddFlag(false);
        }else{
            resourceInstStoreDTO.setQuantityAddFlag(false);
            resourceInstStoreDTO.setOnwayQuantityAddFlag(true);
        }
        resourceInstStoreDTO.setQuantity(Long.valueOf(changeQuantity));
        resourceInstStoreDTO.setLanId(storeDTO.getLanId());
        resourceInstStoreDTO.setRegionId(storeDTO.getRegionId());
        int updateRestInstStore = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
        log.info("ResourceInstServiceImpl.updateInstState resourceInstStoreManager.updateResourceInstStore req={} resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(updateRestInstStore));

        return ResultVO.success(true);
    }

    @Override
    public List<ResourceInstDTO> selectByIds(ResourceInstsGetByIdListAndStoreIdReq req) {
        return resourceInstManager.selectByIds(req);
    }

    /**
     * 根据串码查省包和地包信息
     * 省包 -> 地包 实例信息至多1条，直接取
     * 地包 -> 地包 实例信息至少1条，按时间降序取第一条
     * @param nbr
     * @return
     */
    @Override
    public MerchantInfByNbrModel qryMerchantInfoByNbr(String nbr) {
        MerchantInfByNbrModel model = new MerchantInfByNbrModel();
        List<ResourceInstDTO> resourceInstDTOList = resourceInstManager.listInstsByNbr(nbr);
        for (ResourceInstDTO dto : resourceInstDTOList) {
            if (PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType().equals(dto.getMerchantType())) {
                model.setCitySupplyId(dto.getMerchantCode());
                model.setCitySupplyName(dto.getMerchantName());
                break;
            }
        }
        for (ResourceInstDTO dto : resourceInstDTOList) {
            if (PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType().equals(dto.getMerchantType())) {
                model.setProvSupplyId(dto.getMerchantCode());
                model.setProvSupplyName(dto.getMerchantName());
                break;
            }
        }
        return model;
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO syncTerminal(ResourceInstAddReq req){
        String merchantId =req.getMerchantId();
        String mktResId = req.getMktResId();
        ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(merchantId);
        log.info("ResourceInstServiceImpl.syncTerminal merchantService.getMerchantById req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantDTOResultVO));
        if (!merchantDTOResultVO.isSuccess() || null == merchantDTOResultVO.getResultData()) {
            return ResultVO.error(constant.getNoMerchantMsg());
        }
        String lanId = merchantDTOResultVO.getResultData().getLanId();
        ProductGetByIdReq productReq = new ProductGetByIdReq();
        productReq.setProductId(mktResId);
        ResultVO<ProductForResourceResp> productRespResultVO = productService.getProductForResource(productReq);
        log.info("ResourceInstServiceImpl.syncTerminal productService.getProductForResource req={},resp={}", JSON.toJSONString(productReq), JSON.toJSONString(productRespResultVO));
        String sn = "";
        if (productRespResultVO.isSuccess() && productRespResultVO.getResultData() != null) {
            sn = productRespResultVO.getResultData().getSn();
        }
        List<SyncTerminalItemSwapReq> mktResList = Lists.newArrayList();
        Integer addNum = req.getMktResInstNbrs().size();
        for (int i = 0; i < addNum; i++) {
            String mktResInstNbr = req.getMktResInstNbrs().get(i);
            SyncTerminalItemSwapReq syncTerminalItemReq = new SyncTerminalItemSwapReq();
            // 商家信息
            MerchantInfByNbrModel model = this.qryMerchantInfoByNbr(mktResInstNbr);
            log.info("ResourceInstServiceImpl.syncTerminal qryMerchantInfoByNbr req={},resp={}", mktResInstNbr, JSON.toJSONString(model));
            BeanUtils.copyProperties(model, syncTerminalItemReq);
            syncTerminalItemReq.setBarCode(mktResInstNbr);
            syncTerminalItemReq.setStoreId(req.getMktResStoreId());
            syncTerminalItemReq.setDirectPrice(String.valueOf(req.getSalesPrice()));
            syncTerminalItemReq.setLanId(lanId);
            syncTerminalItemReq.setProductCode(sn);
            syncTerminalItemReq.setPurchaseType(ResourceConst.PURCHASE_TYPE.PURCHASE_TYPE_12.getCode());
            MerchantInfByNbrModel merchantInfByNbrModel = resourceInstService.qryMerchantInfoByNbr(mktResInstNbr);
            syncTerminalItemReq.setProvSupplyId(merchantInfByNbrModel.getProvSupplyId());
            syncTerminalItemReq.setProvSupplyName(merchantInfByNbrModel.getProvSupplyName());
            syncTerminalItemReq.setCitySupplyId(merchantInfByNbrModel.getCitySupplyId());
            syncTerminalItemReq.setCitySupplyName(merchantInfByNbrModel.getCitySupplyName());
            mktResList.add(syncTerminalItemReq);
        }
        SyncTerminalSwapReq syncTerminalReq = new SyncTerminalSwapReq();
        syncTerminalReq.setMktResList(mktResList);
        ResultVO syncTerminalResultVO = marketingResStoreService.syncTerminal(syncTerminalReq);
        log.info("ResourceInstServiceImpl.syncTerminal marketingResStoreService.syncTerminal req={},resp={}", JSON.toJSONString(syncTerminalReq), JSON.toJSONString(syncTerminalResultVO));
        if (!syncTerminalResultVO.isSuccess()) {
            return ResultVO.error(constant.getZopInterfaceError());
        }
        return syncTerminalResultVO;

    }

    @Override
    public ResultVO<List<ResourceInstListResp>> listResourceInst(ResourceInstListReq req) {
        return ResultVO.success(resourceInstManager.listResourceInst(req));
    }

    @Override
    public String getPrimaryKey(){
        return resourceInstManager.getPrimaryKey();
    }

    @Override
    public synchronized List<ResourceInstListPageResp> getResourceInstListManual(ResourceInstListPageReq req) {
        req = setProductIds(req);
        List<ResourceInstListPageResp> list = resourceInstManager.getResourceInstListManual(req);
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        // 按产品维度组装数据
        Map<String, List<ResourceInstListPageResp>> map = list.stream().collect(Collectors.groupingBy(t -> t.getMktResId()));
        for (Map.Entry<String, List<ResourceInstListPageResp>> entry : map.entrySet()) {
            String mktResId = entry.getKey();
            if (StringUtils.isBlank(mktResId)) {
                continue;
            }
            ProductResourceInstGetReq queryReq = new ProductResourceInstGetReq();
            queryReq.setProductId(mktResId);
            ResultVO<List<ProductResourceResp>> resultVO = productService.getProductResource(queryReq);
            List<ProductResourceResp> prodList = resultVO.getResultData();
            if (CollectionUtils.isEmpty(prodList)) {
                continue;
            }
            ProductResourceResp prodResp = prodList.get(0);
            List<ResourceInstListPageResp> instList =entry.getValue();
            for (ResourceInstListPageResp resp : instList) {
                // 添加产品信息
                BeanUtils.copyProperties(prodResp, resp);
                // 库中存的是串码所属用户的地市，查询展示的是零售商的地市
                if (StringUtils.isNotBlank(resp.getMerchantId())) {
                    ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(resp.getMerchantId());
                    if (merchantResultVO.isSuccess() && null != merchantResultVO.getResultData()) {
                        MerchantDTO merchantDTO = merchantResultVO.getResultData();
                        resp.setRegionName(merchantDTO.getCityName());
                        resp.setLanName(merchantDTO.getLanName());
                        resp.setMerchantName(merchantDTO.getMerchantName());
                        resp.setBusinessEntityName(merchantDTO.getBusinessEntityName());
                    }
                }
            }
        }
        return list;
    }

    public String selectMktResInstType(ResourceStoreIdResnbr req) {
    	return resourceInstManager.selectMktResInstType(req);
    }
}