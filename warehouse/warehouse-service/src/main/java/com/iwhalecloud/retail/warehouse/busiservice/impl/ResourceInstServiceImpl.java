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
import com.iwhalecloud.retail.goods2b.dto.resp.ProductPageResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResourceResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.resp.TransferPermissionGetResp;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.promo.dto.req.HistoryPurchaseQueryExistReq;
import com.iwhalecloud.retail.promo.service.HistoryPurchaseService;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceBatchRecService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstLogService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.*;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.request.markresswap.SyncTerminalItemSwapReq;
import com.iwhalecloud.retail.warehouse.dto.request.markresswap.SyncTerminalSwapReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;
import com.iwhalecloud.retail.warehouse.entity.ResourceInst;
import com.iwhalecloud.retail.warehouse.manager.*;
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

    @Override
    public ResultVO<Page<ResourceInstListResp>> getResourceInstList(ResourceInstListReq req) {
        req = setProductIds(req);
        Page<ResourceInstListResp> page = resourceInstManager.getResourceInstList(req);
        log.info("ResourceInstServiceImpl.getResourceInstList resourceInstManager.getResourceInstList req={}", JSON.toJSONString(req));
        List<ResourceInstListResp> list = page.getRecords();
        if (null == list || list.isEmpty()) {
            return ResultVO.success(page);
        }
        // 添加产品信息
        for (ResourceInstListResp resp : list) {
            ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(resp.getMerchantId());
            log.info("ResourceInstServiceImpl.getResourceInstList  merchantService.getMerchantById req={},resp={}", resp.getMerchantId(), JSON.toJSONString(merchantResultVO));
            MerchantDTO merchantDTO = merchantResultVO.getResultData();
            if (null != merchantDTO) {
                resp.setCity(merchantDTO.getCity());
                resp.setBusinessEntityName(merchantDTO.getBusinessEntityName());
            }
            String productId = resp.getMktResId();
            if (StringUtils.isBlank(productId)) {
                continue;
            }
            ProductResourceInstGetReq queryReq = new ProductResourceInstGetReq();
            queryReq.setProductId(productId);
            ResultVO<List<ProductResourceResp>> resultVO = productService.getProductResource(queryReq);
            log.info("ResourceInstServiceImpl.getResourceInstList productService.getProductResource req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(resultVO));
            List<ProductResourceResp> prodList = resultVO.getResultData();
            if (null != prodList && !prodList.isEmpty()) {
                ProductResourceResp prodResp = prodList.get(0);
                BeanUtils.copyProperties(prodResp, resp);
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
    public ResultVO updateResourceInst(ResourceInstUpdateReq req) {
        log.info("ResourceInstServiceImpl.delResourceInst req={},resp={}", JSON.toJSONString(req));
        Map<String, Object> data = assembleData(req);
        Map<String, List<ResourceInstListResp>> insts = (HashMap<String, List<ResourceInstListResp>>)data.get("productNbr");
        List<String> unavailbaleNbrs = (List<String>)data.get("unavailbaleNbrs");
        List<String> availbaleNbrs = (List<String>)data.get("availbaleNbrs");
        if (CollectionUtils.isEmpty(availbaleNbrs)) {
            return ResultVO.success("失败串码",unavailbaleNbrs);
        }

        // step2:源修改状态，即串码源属供应商修改成出库状态
        Integer successNum = 0;
        if (!availbaleNbrs.isEmpty()) {
            ResourceInstUpdateReq updateReq = new ResourceInstUpdateReq();
            BeanUtils.copyProperties(req, updateReq);
            updateReq.setMktResStoreId(req.getDestStoreId());
            updateReq.setMktResInstNbrs(availbaleNbrs);
            successNum = resourceInstManager.updateResourceInst(updateReq);
            log.info("ResourceInstServiceImpl.delResourceInst resourceInstManager.updateResourceInst req={},resp={}", JSON.toJSONString(updateReq), JSON.toJSONString(successNum));
        }

        for (Map.Entry<String, List<ResourceInstListResp>> entry : insts.entrySet()) {
            String productId = entry.getKey();
            List<ResourceInstListResp> dtoList = entry.getValue();
            ResourceInstListResp inst = dtoList.get(0);
            Double salePrice = inst.getSalesPrice() == null ? 0D : inst.getSalesPrice();

            List<ResourceInstListResp> updatedInstList = new ArrayList<>(dtoList.size());
            for (ResourceInstListResp dto : dtoList) {
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
            log.info("ResourceInstServiceImpl.delResourceInst resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(updateResInstStore));
        }

        return ResultVO.success();

    }

    @Override
    public ResultVO<List<String>> updateResourceInstForTransaction(ResourceInstUpdateReq req) {
        log.info("ResourceInstServiceImpl.updateResourceInstForTransaction req={}", JSON.toJSONString(req));
        Map<String, Object> data = assembleData(req);
        Map<String, List<ResourceInstListResp>> insts = (HashMap<String, List<ResourceInstListResp>>)data.get("productNbr");
        List<String> unavailbaleNbrs = (List<String>)data.get("unavailbaleNbrs");
        List<String> availbaleNbrs = (List<String>)data.get("availbaleNbrs");
        if (CollectionUtils.isEmpty(availbaleNbrs)) {
            return ResultVO.success("失败串码",unavailbaleNbrs);
        }

        // step2:源修改状态，即串码源属供应商修改成出库状态
        Integer successNum = 0;
        if (!availbaleNbrs.isEmpty()) {
            ResourceInstUpdateReq updateReq = new ResourceInstUpdateReq();
            BeanUtils.copyProperties(req, updateReq);
            updateReq.setMktResInstNbrs(availbaleNbrs);
            successNum = resourceInstManager.updateResourceInst(updateReq);
            log.info("ResourceInstServiceImpl.updateResourceInstForTransaction resourceInstManager.updateResourceInst req={},resp={}", JSON.toJSONString(updateReq), JSON.toJSONString(successNum));
        }

        for (Map.Entry<String, List<ResourceInstListResp>> entry : insts.entrySet()) {
            List<ResourceInstListResp> dtoList = entry.getValue();
            ResourceInstListResp inst = dtoList.get(0);
            List<ResourceInstListResp> updatedInstList = new ArrayList<>(dtoList.size());
            for (ResourceInstListResp dto : dtoList) {
                ResourceInstDTO resourceInstDTO = resourceInstManager.selectById(dto.getMktResInstId());
                String statusCd = resourceInstDTO.getStatusCd();
                String changeStatusCd = req.getStatusCd();
                // 修改不成功的返回，不加事件
                if (!statusCd.equals(changeStatusCd)) {
                    continue;
                }
                updatedInstList.add(dto);
            }
            // step3 记录事件(根据产品维度)
            resourceInstLogService.updateResourceInstLog(req, updatedInstList);

            // step 4:修改库存(出库)
            ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
            BeanUtils.copyProperties(inst, resourceInstStoreDTO);
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
            log.info("ResourceInstServiceImpl.updateResourceInstForTransaction resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(updateResInstStore));
        }

        return ResultVO.success();
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO<ResourceInstAddResp> addResourceInst(ResourceInstAddReq req) {
        log.info("ResourceInstServiceImpl.addResourceInst req={}", JSON.toJSONString(req));
        ResourceInstAddResp resourceInstAddResp = new ResourceInstAddResp();
        // 要入库的串码
        List<String> putInNbrs =  Lists.newArrayList(req.getMktResInstNbrs());
        // 串码已经存在
        List<String> mktResInstNbrs =  Lists.newArrayList(req.getMktResInstNbrs());
        // 一去重
        List<String> existNbrs = qryEnableInsertNbr(req);
        mktResInstNbrs.removeAll(existNbrs);
        if(CollectionUtils.isEmpty(mktResInstNbrs)){
            return ResultVO.error("全部是重复串码");
        }
        // 二校验：
        // A) 绿色通道不需要校验
        // B) 非厂家新增串码时，只有在厂家存在的串码，才允许新增
        boolean checkExistsInMerchantStore = !ResourceConst.STORAGETYPE.GREEN_CHANNEL.getCode().equals(req.getStorageType()) && !req.getMerchantType().equals(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType());
        if(checkExistsInMerchantStore ){
            ResourceInstsGetReq manufacturerResourceInstsGetReq = new ResourceInstsGetReq();
            manufacturerResourceInstsGetReq.setMktResInstNbrs(mktResInstNbrs);
            manufacturerResourceInstsGetReq.setMktResId(req.getMktResId());
            List<String> manufacturerTypes = new ArrayList<>();
            manufacturerTypes.add(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType());
            manufacturerResourceInstsGetReq.setMerchantTypes(manufacturerTypes);
            List<ResourceInstDTO> manufacturerInst = resourceInstManager.getResourceInsts(manufacturerResourceInstsGetReq);
            log.info("ResourceInstServiceImpl.addResourceInst resourceInstManager.getResourceInsts req={},resp={}", JSON.toJSONString(manufacturerResourceInstsGetReq), JSON.toJSONString(manufacturerInst));
            if (CollectionUtils.isNotEmpty(manufacturerInst)) {
                List<String> manufacturerNbrs = manufacturerInst.stream().map(ResourceInstDTO::getMktResInstNbr).collect(Collectors.toList());
                putInNbrs = putInNbrs.stream().filter(t -> manufacturerNbrs.contains(t)).collect(Collectors.toList());
            }else{
                return ResultVO.error(ResultCodeEnum.ERROR.getCode(), "厂商库该机型串码不存在");
            }
        }
        String batchId = resourceInstManager.getPrimaryKey();
        List<ResourceInst> resourceInsts = new ArrayList<ResourceInst>(putInNbrs.size());
        Date now = new Date();
        for (String mktResInstNbr : putInNbrs) {
            ResourceInst resourceInst = new ResourceInst();
            BeanUtils.copyProperties(req, resourceInst);
            resourceInst.setMktResInstId(resourceInstManager.getPrimaryKey());
            resourceInst.setMktResInstNbr(mktResInstNbr);
            resourceInst.setMktResBatchId(batchId);
            // 目标仓库是串码所属人的仓库
            resourceInst.setMktResStoreId(req.getDestStoreId());
            if (null != req.getCtCode()) {
                resourceInst.setCtCode(req.getCtCode().get(mktResInstNbr));
            }
            resourceInst.setCreateDate(now);
            resourceInst.setCreateTime(now);
            resourceInst.setSourceType(req.getSourceType());
            resourceInst.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
            resourceInsts.add(resourceInst);
        }
        Boolean addResInstCnt = resourceInstManager.saveBatch(resourceInsts);
        log.info("ResourceInstServiceImpl.addResourceInst resourceInstManager.saveBatch req={} resp={}", JSON.toJSONString(resourceInsts), addResInstCnt);
        if (!addResInstCnt) {
            return ResultVO.error("串码入库失败");
        }
        ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
        BeanUtils.copyProperties(req, resourceInstStoreDTO);
        resourceInstStoreDTO.setQuantity(Long.valueOf(putInNbrs.size()));
        resourceInstStoreDTO.setQuantityAddFlag(true);
        resourceInstStoreDTO.setCreateStaff(req.getMerchantId());
        resourceInstStoreDTO.setMktResStoreId(req.getDestStoreId());
        int num = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
        log.info("ResourceInstServiceImpl.addResourceInst resourceInstStoreManager.updateResourceInstStore req={} num={}", JSON.toJSONString(resourceInstStoreDTO), num);
        if (num < 1) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "库存没更新成功");
        }
        resourceInstLogService.addResourceInstLog(req, resourceInsts, batchId);
        resourceInstAddResp.setExistNbrs(existNbrs);
        List<String> putInFailNbrs = Lists.newArrayList(req.getMktResInstNbrs());
        putInFailNbrs.removeAll(putInNbrs);
        resourceInstAddResp.setPutInFailNbrs(putInFailNbrs);
        return ResultVO.success("串码入库完成", resourceInstAddResp);
    }

    /**
     * 查询串码状态，过滤出可以入库的串码
     * @param req
     * @return
     */
    private List<String> qryEnableInsertNbr(ResourceInstAddReq req){
        List<String> existNbrs = new ArrayList<>();
        // 一去重：串码存在且状态可用不再导入
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        List<String> mktResInstNbrs = Lists.newArrayList(req.getMktResInstNbrs());
        resourceInstsGetReq.setMktResInstNbrs(mktResInstNbrs);
        resourceInstsGetReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        List<String> merchantTypes = null;
        if (PartnerConst.MerchantTypeEnum.MANUFACTURER.getType().equals(req.getMerchantType())) {
            // 厂商增加：只校验厂商库
            merchantTypes = Lists.newArrayList(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType());
        }else{
            // 非厂商增加：只校验非厂商库
            merchantTypes = Lists.newArrayList(PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType(),
                    PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType(),
                    PartnerConst.MerchantTypeEnum.PARTNER.getType());
        }
        resourceInstsGetReq.setMerchantTypes(merchantTypes);
        List<ResourceInstDTO> inst = resourceInstManager.getResourceInsts(resourceInstsGetReq);
        log.info("ResourceInstServiceImpl.addResourceInst resourceInstManager.getResourceInsts req={},resp={}", JSON.toJSONString(resourceInstsGetReq), JSON.toJSONString(inst));
        if (CollectionUtils.isNotEmpty(inst)) {
            List<String> instNbrs = inst.stream().map(ResourceInstDTO::getMktResInstNbr).collect(Collectors.toList());
            existNbrs = mktResInstNbrs.stream().filter(t -> instNbrs.contains(t)).collect(Collectors.toList());
        }
        return existNbrs;
    }

    /**
     * 通过品牌等查询出产品id
     * @param req
     * @return
     */
    private ResourceInstListReq setProductIds(ResourceInstListReq req){
        if(StringUtils.isNotBlank(req.getUnitName()) || StringUtils.isNotBlank(req.getBrandId()) || StringUtils.isNotBlank(req.getSn())) {
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
     * @return
     */
    private Map<String, Object> assembleData(ResourceInstUpdateReq req){
        List<String> nbrs = Lists.newArrayList(req.getMktResInstNbrs());
        List<String> checkStatusCd = req.getCheckStatusCd();
        String mktResStoreId = req.getMktResStoreId();
        Map<String, Object> data = new HashMap<String, Object>(8);
        // 去重
        List<String> distinctList = nbrs.stream().distinct().collect(Collectors.toList());
        // 找出串码实列
        ResourceInstBatchReq queryReq = new ResourceInstBatchReq();
        queryReq.setMktResInstNbrs(distinctList);
        queryReq.setMktResStoreId(mktResStoreId);
        List<ResourceInstListResp> insts = resourceInstManager.getBatch(queryReq);
        log.info("ResourceInstServiceImpl.assembleData resourceInstManager.getResourceInst req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(insts));

        // 筛选出状态不正确的串码实列(状态在校验的状态集中的数据)
        List<ResourceInstListResp> unStatusCdinsts = insts.stream().filter(t -> checkStatusCd.contains(t.getStatusCd())).collect(Collectors.toList());
        // 去除状态不正确的串码实列
        insts.removeAll(unStatusCdinsts);
        // 查出来的实列对应的串码
        List<String> useNbrs = insts.stream().map(ResourceInstListResp::getMktResInstNbr).collect(Collectors.toList());
        // 全部串码减去筛选出来的串码为不可用串码
        nbrs.removeAll(useNbrs);
        // 按产品维度组装数据
        Map<String, List<ResourceInstListResp>> map = insts.stream().collect(Collectors.groupingBy(t -> t.getMktResId()));
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
    public ResultVO updateResourceInstByIds(AdminResourceInstDelReq req){
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
                List<String> mktResInstList = new ArrayList<>();
                adminResourceInstDelReq.setMktResStoreId(dto.getMktResStoreId());
                mktResInstList.add(dto.getMktResInstId());
                adminResourceInstDelReq.setMktResInstIds(mktResInstList);
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
    public ResultVO updateResourceInstByIdsForTransaction(AdminResourceInstDelReq req) {
        log.info("ResourceInstServiceImpl.updateResourceInstByIdsForTransaction req={},resp={}", JSON.toJSONString(req));
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
                List<String> mktResInstList = new ArrayList<>();
                mktResInstList.add(dto.getMktResInstId());
                adminResourceInstDelReq.setMktResInstIds(mktResInstList);
                Integer num = resourceInstManager.updateResourceInstByIds(adminResourceInstDelReq);
                log.info("ResourceInstServiceImpl.updateResourceInstByIdsForTransaction resourceInstManager.updateResourceInstByIds req={},resp={}", JSON.toJSONString(adminResourceInstDelReq), JSON.toJSONString(sucessNum));
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
            log.info("ResourceInstServiceImpl.updateResourceInstByIdsForTransaction resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(updateRestInstStore));
        }
        return ResultVO.success("失败串码数据", unavailbaleNbrs);
    }

    /**
     * 无效的串码主键集合,串码实列按产品维度组装成map，再设置到请求参数
     * @return
     */
    private void assembleData(AdminResourceInstDelReq req){
        List<String> mktResInstIds = req.getMktResInstIds();
        List<String> checkStatusCd = req.getCheckStatusCd();
        // 去重
        List<String> distinctList = mktResInstIds.stream().distinct().collect(Collectors.toList());
        // 找出串码实列
        List<ResourceInstDTO> insts = resourceInstManager.selectByIds(distinctList);
        log.info("ResourceInstServiceImpl.assembleData resourceInstManager.selectByIds req={},resp={}", JSON.toJSONString(distinctList), JSON.toJSONString(insts));
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
        req.setMktResInstIds(useMktResInstId);
        req.setUnUse(unStatusCdinstIds);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO resourceInstPutIn(ResourceInstPutInReq req){
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

            List<ResourceInstListResp> updatedInstList = new ArrayList<>(dtoList.size());
            String batchId = resourceInstManager.getPrimaryKey();
            List<ResourceInst> resourceInsts = new ArrayList<ResourceInst>(dtoList.size());
            for (ResourceInstDTO resourceInst : dtoList) {
                resourceInst.setMktResInstId(null);
                ResourceInst t = new ResourceInst();
                BeanUtils.copyProperties(resourceInst, t);
                BeanUtils.copyProperties(req, t);
                t.setMktResBatchId(batchId);
                t.setMktResStoreId(req.getDestStoreId());
                t.setStorageType(req.getStorageType());
                t.setMktResInstType(ResourceConst.MKTResInstType.NONTRANSACTION.getCode());
                t.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
                t.setCreateStaff("1");
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
    public ResultVO<Boolean> updateInstState(ResourceInstUpdateReq req) {
        log.info("ResourceInstServiceImpl.updateInstState req={}", JSON.toJSONString(req));
        String storeSubType = req.getStoreType();
        if(org.apache.commons.lang.StringUtils.isEmpty(storeSubType)){
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
    public ResultVO<List<String>> delResourceInstForMerchant(ResourceInstUpdateReq req){
        log.info("ResourceInstServiceImpl.delResourceInstForMerchant req={}", JSON.toJSONString(req));
        //step 1:串码是否有效
        String mktResStoreId = req.getMktResStoreId();
        String merchantId = req.getMerchantId();
        List<String> checkStatusCd = req.getCheckStatusCd();
        String statusCd = req.getStatusCd();

        // 删除的时候传的是商家Id,没传mktResStoreId
        if (StringUtils.isBlank(mktResStoreId)) {
            String storeSubType = req.getStoreType();
            if(org.apache.commons.lang.StringUtils.isEmpty(storeSubType)){
                storeSubType = ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode();
            }
            ResouceStoreDTO store = resouceStoreManager.getStore(merchantId, storeSubType);
            log.info("ResourceInstServiceImpl.delResourceInstForMerchant resouceStoreManager.getStore merchantId={},resp={}", merchantId, JSON.toJSONString(store));
            if (null == store) {
                return ResultVO.error(constant.getNoStoreMsg());
            }
            req.setMktResStoreId(store.getMktResStoreId());
        }

        Map<String, Object> data = assembleDataForMerchant(req);

        if(data.containsKey("errors")){
            Set<String> errorSet = (Set<String>)data.get("errors");
            StringBuffer errors = new StringBuffer();
            errors.append("串码：");
            for (String s : errorSet) {
                errors.append(s).append(",");
            }
            errors.delete(errors.length()-1,errors.length());
            errors.append("状态不可删除或被其他库使用");
            return ResultVO.error(errors.toString());
        }

        Map<String, List<ResourceInstListResp>> insts = (HashMap<String, List<ResourceInstListResp>>)data.get("productNbr");
        List<String> unavailbaleNbrs = (List<String>)data.get("unavailbaleNbrs");
        List<String> availbaleNbrs = (List<String>)data.get("availbaleNbrs");
        if (CollectionUtils.isEmpty(availbaleNbrs)) {
            return ResultVO.success("失败串码",unavailbaleNbrs);
        }

        // step2:源修改状态，即串码源属供应商修改成出库状态
        Integer successNum = 0;
        if (!availbaleNbrs.isEmpty()) {
            ResourceInstUpdateReq updateReq = new ResourceInstUpdateReq();
            BeanUtils.copyProperties(req, updateReq);
            updateReq.setMktResStoreId(req.getDestStoreId());
            updateReq.setMktResInstNbrs(availbaleNbrs);
            successNum = resourceInstManager.updateResourceInst(updateReq);
            log.info("ResourceInstServiceImpl.delResourceInstForMerchant resourceInstManager.updateResourceInst req={},resp={}", JSON.toJSONString(updateReq), JSON.toJSONString(successNum));
        }

        for (Map.Entry<String, List<ResourceInstListResp>> entry : insts.entrySet()) {
            List<ResourceInstListResp> dtoList = entry.getValue();
            ResourceInstListResp inst = dtoList.get(0);

            List<ResourceInstListResp> updatedInstList = new ArrayList<>(dtoList.size());
            for (ResourceInstListResp dto : dtoList) {
                ResourceInstDTO resourceInstDTO = resourceInstManager.selectById(dto.getMktResInstId());
                String resourceInstDTOStatusCd = resourceInstDTO.getStatusCd();
                String changeStatusCd = req.getStatusCd();
                // 修改不成功的返回，不加事件
                if (!resourceInstDTOStatusCd.equals(changeStatusCd)) {
                    continue;
                }
                updatedInstList.add(dto);
            }
            // step3 记录事件(根据产品维度)
            resourceInstLogService.updateResourceInstLog(req, updatedInstList);
            // step 4:修改库存(出库)
            ResourceInstStoreDTO resourceInstStoreDTO = new ResourceInstStoreDTO();
            BeanUtils.copyProperties(inst, resourceInstStoreDTO);
            resourceInstStoreDTO.setQuantity(Long.valueOf(successNum));
            String reqStatusCd = req.getStatusCd();
            // 出库类型，库存减少
            resourceInstStoreDTO.setQuantityAddFlag(false);
            if (ResourceConst.STATUSCD.AVAILABLE.getCode().equals(reqStatusCd)) {
                // 入库类型，库存增加
                resourceInstStoreDTO.setQuantityAddFlag(true);
            }else if (ResourceConst.STATUSCD.ALLOCATIONING.getCode().equals(reqStatusCd) || ResourceConst.STATUSCD.RESTORAGEING.getCode().equals(reqStatusCd)) {
                // 调拨、退库中类型，库存不变，增加在途库存
                resourceInstStoreDTO.setOnwayQuantityAddFlag(true);
            }else if (ResourceConst.STATUSCD.ALLOCATIONED.getCode().equals(reqStatusCd) || ResourceConst.STATUSCD.RESTORAGED.getCode().equals(reqStatusCd)) {
                // 已调拨、已退库类型，库存减少，减少在途库存
                resourceInstStoreDTO.setQuantityAddFlag(false);
                resourceInstStoreDTO.setOnwayQuantityAddFlag(false);
            }else if (ResourceConst.STATUSCD.SALED.getCode().equals(reqStatusCd) || ResourceConst.STATUSCD.DELETED.getCode().equals(reqStatusCd)) {
                // 已销售、已删除类型，库存减少
                resourceInstStoreDTO.setQuantityAddFlag(false);
            }

            int updateResInstStore = resourceInstStoreManager.updateResourceInstStore(resourceInstStoreDTO);
            log.info("ResourceInstServiceImpl.delResourceInst resourceInstStoreManager.updateResourceInstStore req={},resp={}", JSON.toJSONString(resourceInstStoreDTO), JSON.toJSONString(updateResInstStore));
        }

        return ResultVO.success();

    }

    @Override
    public List<ResourceInstDTO> selectByIds(List<String> idList) {
        return resourceInstManager.selectByIds(idList);
    }

    private Map<String, Object> assembleDataForMerchant(ResourceInstUpdateReq req){
        List<String> nbrs = req.getMktResInstNbrs();
        List<String> checkStatusCd = req.getCheckStatusCd();
        String mktResStoreId = req.getMktResStoreId();
        Map<String, Object> data = new HashMap<String, Object>(8);
        // 去重
        List<String> distinctList = nbrs.stream().distinct().collect(Collectors.toList());
        // 找出串码实列
        ResourceInstBatchReq queryReq = new ResourceInstBatchReq();
        queryReq.setMktResInstNbrs(distinctList);
//        queryReq.setMktResStoreId(mktResStoreId);
        List<ResourceInstListResp> instsTemp = resourceInstManager.getBatch(queryReq);
        log.info("ResourceInstServiceImpl.assembleData resourceInstManager.getResourceInst req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(instsTemp));
        //统计数:串码-个数
        Map<String,Integer> unStoreCountMap = new HashMap<String,Integer>();
        Set<String> error = new HashSet<String>();
        List<ResourceInstListResp> unStatusCdinsts = new ArrayList<ResourceInstListResp>();
        List<ResourceInstListResp> insts = new ArrayList<ResourceInstListResp>();
        for (ResourceInstListResp inst : instsTemp) {
            String key = inst.getMktResInstNbr();
            //状态
            String statusCd = inst.getStatusCd();

            //属于自己的库
            if(mktResStoreId.equals(inst.getMktResStoreId())){
                insts.add(inst);
            }else{
                //其他库的数据
                if(!unStoreCountMap.containsKey(key)){
                    unStoreCountMap.put(key,!ResourceConst.STATUSCD.DELETED.getCode().equals(statusCd)?1:0);
                }else{
                    unStoreCountMap.put(key,unStoreCountMap.get(key)+(!ResourceConst.STATUSCD.DELETED.getCode().equals(statusCd)?1:0));
                }
            }

        }
        //循环需要处理的串码
        for (ResourceInstListResp inst : insts) {
            String key = inst.getMktResInstNbr();
            Integer count =unStoreCountMap.get(key);
            //在其他库被使用过
            if(count!=null&&count.intValue()>0){
                unStatusCdinsts.add(inst);
                error.add(key);
            }else if(!error.contains(key)&&checkStatusCd.contains(inst.getStatusCd())){
                //其他库未被使用，但是状态过滤
                unStatusCdinsts.add(inst);
                error.add(key);
            }
        }
        if(!error.isEmpty()){
            data.put("errors",error);
            return data;
        }
        // 去除状态不正确的串码实列
        insts.removeAll(unStatusCdinsts);
        // 查出来的实列对应的串码
        List<String> useNbrs = insts.stream().map(ResourceInstListResp::getMktResInstNbr).collect(Collectors.toList());
        // 全部串码减去筛选出来的串码为不可用串码
        nbrs.removeAll(useNbrs);
        // 按产品维度组装数据
        Map<String, List<ResourceInstListResp>> map = insts.stream().collect(Collectors.groupingBy(t -> t.getMktResId()));
        // 不可修改的串码
        data.put("unavailbaleNbrs", nbrs);
        // 可修改的串码
        data.put("availbaleNbrs", useNbrs);
        // 按产品分组的串码
        data.put("productNbr", map);
        return data;
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
        StoreGetStoreIdReq storePageReq = new StoreGetStoreIdReq();
        storePageReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        storePageReq.setMerchantId(req.getMerchantId());
        String storeId = resouceStoreService.getStoreId(storePageReq);
        log.info("ResourceInstServiceImpl.syncTerminal resouceStoreService.getStoreId merchantId={},storeId={}", req.getMerchantId(), storeId);
        if (StringUtils.isBlank(storeId)) {
            return ResultVO.error(constant.getNoStoreMsg());
        }
        ProductGetByIdReq productReq = new ProductGetByIdReq();
        productReq.setProductId(mktResId);
        ResultVO<ProductResp> productRespResultVO = productService.getProduct(productReq);
        String sn = "";
        if (productRespResultVO.isSuccess() && productRespResultVO.getResultData() != null) {
            sn = productRespResultVO.getResultData().getSn();
        }
        SyncTerminalSwapReq syncTerminalReq = new SyncTerminalSwapReq();
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
            syncTerminalItemReq.setStoreId(storeId);
            // 绿色通道省级供应商和市级都没有信息
            syncTerminalItemReq.setDirectPrice(String.valueOf(req.getSalesPrice()));
            // 零售商的本地网
            syncTerminalItemReq.setLanId(lanId);
            // 获取产品25位编码
            syncTerminalItemReq.setProductCode(sn);
            // 零售商导入选社采
            syncTerminalItemReq.setPurchaseType(ResourceConst.PURCHASE_TYPE.PURCHASE_TYPE_12.getCode());
            MerchantInfByNbrModel merchantInfByNbrModel = resourceInstService.qryMerchantInfoByNbr(mktResInstNbr);
            syncTerminalItemReq.setProvSupplyId(merchantInfByNbrModel.getProvSupplyId());
            syncTerminalItemReq.setProvSupplyName(merchantInfByNbrModel.getProvSupplyName());
            syncTerminalItemReq.setCitySupplyId(merchantInfByNbrModel.getCitySupplyId());
            syncTerminalItemReq.setCitySupplyName(merchantInfByNbrModel.getCitySupplyName());
            mktResList.add(syncTerminalItemReq);
        }
        syncTerminalReq.setMktResList(mktResList);
        // step2 串码入库
        ResultVO syncTerminalResultVO = marketingResStoreService.syncTerminal(syncTerminalReq);
        log.info("ResourceInstServiceImpl.syncTerminal marketingResStoreService.syncTerminal req={},resp={}", JSON.toJSONString(syncTerminalReq), JSON.toJSONString(syncTerminalResultVO));
        return syncTerminalResultVO;
    }

    @Override
    public ResultVO<List<ResourceInstListResp>> getExportResourceInstList(ResourceInstListReq req) {
        req = setProductIds(req);
        List<ResourceInstListResp> list = resourceInstManager.getExportResourceInstList(req);
        log.info("ResourceInstServiceImpl.getExportResourceInstList resourceInstManager.getExportResourceInstList req={}", JSON.toJSONString(req));
        /**添加产品信息*/
        for (ResourceInstListResp resp : list) {
            ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(resp.getMerchantId());
            log.info("RetailerResourceInstB2BController.nbrExportAll  merchantService.getMerchantById req={},resp={}", resp.getMerchantId(), JSON.toJSONString(merchantResultVO));
            MerchantDTO merchantDTO = merchantResultVO.getResultData();
            if (null != merchantDTO) {
                resp.setCity(merchantDTO.getCity());
                resp.setBusinessEntityName(merchantDTO.getBusinessEntityName());
            }
            String productId = resp.getMktResId();
            if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(productId)) {
                continue;
            }
            ProductResourceInstGetReq queryReq = new ProductResourceInstGetReq();
            queryReq.setProductId(productId);
            ResultVO<List<ProductResourceResp>> resultVO = productService.getProductResource(queryReq);
            log.info("RetailerResourceInstB2BController.nbrExportAll productService.getProductResource req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(resultVO));
            List<ProductResourceResp> prodList = resultVO.getResultData();
            if (null != prodList && !prodList.isEmpty()) {
                ProductResourceResp prodResp = prodList.get(0);
                BeanUtils.copyProperties(prodResp, resp);
            }
        }
        return ResultVO.success(list);
    }

    @Override
    public ResultVO<Boolean> nbrHasActivity(Map<String, String> nbrAndProductId) {
        if (null == nbrAndProductId) {
            return ResultVO.success(false);
        }

        for (Map.Entry<String, String> entry : nbrAndProductId.entrySet()){
            String orderId = resouceInstTrackService.qryOrderIdByNbr(entry.getKey());
            String productId = StringUtils.isBlank(entry.getValue()) ? "" : entry.getValue();
            log.info("ResourceInstServiceImpl.nbrHasActivity resouceInstTrackService.qryOrderIdByNbr req={},resp={}", entry.getKey(), orderId);
            HistoryPurchaseQueryExistReq req = new HistoryPurchaseQueryExistReq();
            req.setOrderId(orderId);
            req.setProductId(productId);
            ResultVO<Boolean> queryHistoryPurchaseQueryIsExistVO = historyPurchaseService.queryHistoryPurchaseQueryIsExist(req);
            log.info("ResourceInstServiceImpl.getExportResourceInstList historyPurchaseService.queryHistoryPurchaseQueryIsExist req={}", JSON.toJSONString(req), JSON.toJSONString(queryHistoryPurchaseQueryIsExistVO));
            if (queryHistoryPurchaseQueryIsExistVO.isSuccess() && queryHistoryPurchaseQueryIsExistVO.getResultData()) {
                return ResultVO.success(true);
            }
        }
        return ResultVO.success(false);
    }

    @Override
    public String getMerchantStoreIdByNbr(String nbr) {
        return resourceInstManager.getMerchantStoreIdByNbr(nbr);
    }


}