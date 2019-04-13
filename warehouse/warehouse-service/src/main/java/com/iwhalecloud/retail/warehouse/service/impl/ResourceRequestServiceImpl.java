package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductsPageReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductPageResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreObjRelDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestQueryResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestResp;
import com.iwhalecloud.retail.warehouse.entity.ResouceStore;
import com.iwhalecloud.retail.warehouse.entity.ResourceRequest;
import com.iwhalecloud.retail.warehouse.manager.*;
import com.iwhalecloud.retail.warehouse.mapper.ResouceStoreMapper;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author My
 * @Date 2019/1/10
 **/
@Slf4j
@Service
public class ResourceRequestServiceImpl implements ResourceRequestService {

    @Autowired
    private ResourceRequestManager requestManager;

    @Autowired
    private ResourceReqItemManager itemManager;

    @Autowired
    private ResourceReqDetailManager detailManager;

    @Autowired
    private ResouceStoreManager storeManager;

    @Reference
    private MerchantService merchantService;

    @Reference
    private ProductService productService;

    @Resource
    private ResouceStoreMapper resouceStoreMapper;

    @Resource
    private ResouceStoreObjRelManager resouceStoreObjRelManager;

    @Override
    //todo 事务先去掉，影响主流程 200_539
//    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public ResultVO<String> insertResourceRequest(ResourceRequestAddReq req) {
        if(CollectionUtils.isEmpty(req.getInstList())){
            return ResultVO.error("instList is null");
        }
        if(StringUtils.isEmpty(req.getChngType())){
            return ResultVO.error("chngType is null");
        }
        String mktResReqId = requestManager.insertResourceRequest(req);
        log.info("ResourceRequestServiceImpl.insertResourceRequest requestManager.insertResourceRequest req={}, resp={}",JSON.toJSONString(req), JSON.toJSONString(mktResReqId));
        List<ResourceRequestAddReq.ResourceRequestInst> instDTOList = req.getInstList();
        List<String> existStr = Lists.newLinkedList();
        Map<String,List<ResourceRequestAddReq.ResourceRequestInst>> map = instDTOList.stream().collect(Collectors.groupingBy(ResourceRequestAddReq.ResourceRequestInst::getMktResId));
        for(Map.Entry<String,List<ResourceRequestAddReq.ResourceRequestInst>> entry:map.entrySet()){
            //新增营销资源申请单项
            ResourceReqItemAddReq itemAddReq = new ResourceReqItemAddReq();
            BeanUtils.copyProperties(req,itemAddReq);
            String size = String.valueOf(entry.getValue().size());
            itemAddReq.setQuantity(Long.parseLong(size));
            itemAddReq.setMktResReqId(mktResReqId);
            itemAddReq.setMktResId(entry.getKey());
            itemAddReq.setRemark("库存管理");
            String itemId = itemManager.insertResourceReqItem(itemAddReq);
            log.info("ResourceRequestServiceImpl.insertResourceRequest itemManager.insertResourceReqItem req={}, resp={}",JSON.toJSONString(itemAddReq), JSON.toJSONString(itemId));
            //营销资源申请单明细
            for(ResourceRequestAddReq.ResourceRequestInst instDTO:entry.getValue()){
                ResourceReqDetailAddReq detailReq = new ResourceReqDetailAddReq();
                detailReq.setMktResInstId(instDTO.getMktResInstId());
                detailReq.setMktResReqItemId(itemId);
                detailReq.setMktResInstNbr(instDTO.getMktResInstNbr());
                detailReq.setQuantity(Long.valueOf(ResourceConst.CONSTANT_YES));
                detailReq.setCreateStaff(req.getCreateStaff());
                detailReq.setChngType(req.getChngType());
                detailReq.setUnit("个");
                detailReq.setRemark("库存管理");
                detailReq.setIsInspection(instDTO.getIsInspection());
                int insertResReqDetailCnt = detailManager.insertResourceReqDetail(detailReq);
                log.info("ResourceRequestServiceImpl.insertResourceRequest detailManager.insertResourceReqDetail req={}, resp={}",JSON.toJSONString(detailReq), JSON.toJSONString(insertResReqDetailCnt));
            }
        }
        if(StringUtils.isEmpty(mktResReqId)){
            return ResultVO.error("insert fail");
        }
        return ResultVO.success(mktResReqId);
    }
    @Override
    public ResultVO<ResourceRequestResp> queryResourceRequest(ResourceRequestItemQueryReq req) {
        ResourceRequest request = requestManager.queryResourceRequest(req);
        ResourceRequestResp resp = new ResourceRequestResp();
        BeanUtils.copyProperties(request,resp);
        return ResultVO.success(resp);
    }

    /**
     *  调入的 目标仓库是自己；
     *  调出的 源仓库是自己；
     * @param req
     * @return
     */
    @Override
    public ResultVO<Page<ResourceRequestQueryResp>> listResourceRequest(ResourceRequestQueryReq req) {
        log.info("ResourceRequestServiceImpl.listResourceRequest req={} ",JSON.toJSONString(req));

        if (StringUtils.isEmpty(req.getMerchantId())) {
            return ResultVO.error("商家ID为空");
        }

        if (StringUtils.isEmpty(req.getQryType())) {
            return ResultVO.error("查询类型为空");
        }

        ResouceStoreDTO resouceStoreDTO = storeManager.getStore(req.getMerchantId(), ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        log.info("ResourceRequestServiceImpl.listResourceRequest storeManager.getStore resp={} ",JSON.toJSONString(resouceStoreDTO));
        if (null == resouceStoreDTO) {
            return ResultVO.error("商家对应的仓库不存在");
        }

        if (req.getQryType().equals(ResourceConst.REQUEST_QRY_TYPE.REQUEST_QRY_TYPE_IN.getCode())){
            req.setDestStoreId(resouceStoreDTO.getMktResStoreId());
        }

        if (req.getQryType().equals(ResourceConst.REQUEST_QRY_TYPE.REQUEST_QRY_TYPE_OUT.getCode())){
            req.setMktResStoreId(resouceStoreDTO.getMktResStoreId());
        }
        Page<ResourceRequestQueryResp> respPage = new Page<>();
        if (StringUtils.isNotEmpty(req.getCatId())) {
            ProductsPageReq productsPageReq = new ProductsPageReq();
            productsPageReq.setCatId(req.getCatId());
            productsPageReq.setPageSize(100000);
            ResultVO<Page<ProductPageResp>> productPage = productService.selectPageProductAdmin(productsPageReq);
            if (productPage.isSuccess() && !CollectionUtils.isEmpty(productPage.getResultData().getRecords())){
                List<String> productList = productPage.getResultData().getRecords().stream().map(ProductPageResp::getProductId).collect(Collectors.toList());
                req.setProductList(productList);
                log.info("ResourceRequestServiceImpl.listResourceRequest productService.selectPageProductAdmin req={}, resp={}", JSON.toJSONString(productsPageReq), JSON.toJSONString(productPage.getResultData().getSize()));
            }else{
                log.info("ResourceRequestServiceImpl.listResourceRequest productService.selectPageProductAdmin result is null. req={}", JSON.toJSONString(productsPageReq));
                return ResultVO.success(new Page<ResourceRequestQueryResp>());
            }
        }
        Page<ResourceRequestQueryResp> page = requestManager.pageResourceRequest(req);
        log.info("ResourceRequestServiceImpl.listResourceRequest requestManager.pageResourceRequest req={}",JSON.toJSONString(req));
        if (null == page && CollectionUtils.isEmpty(page.getRecords())) {
            return ResultVO.success(respPage);
        }
        for (ResourceRequestQueryResp resourceRequestQueryResp : page.getRecords()) {
            String mktResId = resourceRequestQueryResp.getMktResId();
            ProductsPageReq productsPageReq = new ProductsPageReq();
            productsPageReq.setProductIdList(Lists.newArrayList(mktResId));

            ResultVO<Page<ProductPageResp>> productPage = productService.selectPageProductAdmin(productsPageReq);
            if (productPage.isSuccess() && !CollectionUtils.isEmpty(productPage.getResultData().getRecords())){
                resourceRequestQueryResp.setTypeId(productPage.getResultData().getRecords().get(0).getCatId());
                resourceRequestQueryResp.setTypeName(productPage.getResultData().getRecords().get(0).getCatName());
            }

            if (req.getQryType().equals(ResourceConst.REQUEST_QRY_TYPE.REQUEST_QRY_TYPE_IN.getCode())){
                resourceRequestQueryResp.setDestStoreName(resouceStoreDTO.getMktResStoreName());

                if (StringUtils.isNotEmpty(resourceRequestQueryResp.getMktResStoreId())) {
                    QueryWrapper wrapper = new QueryWrapper();
                    wrapper.eq(ResouceStore.FieldNames.mktResStoreId.getTableFieldName(), resourceRequestQueryResp.getMktResStoreId());
                    ResouceStore mktResStore = resouceStoreMapper.selectOne(wrapper);
                    if (mktResStore != null) {
                        resourceRequestQueryResp.setMktResStoreName(mktResStore.getMktResStoreName());
                    }
                }
            }

            if (req.getQryType().equals(ResourceConst.REQUEST_QRY_TYPE.REQUEST_QRY_TYPE_OUT.getCode())){
                if (StringUtils.isNotEmpty(resourceRequestQueryResp.getDestStoreId())){
                    QueryWrapper wrapper = new QueryWrapper();
                    wrapper.eq(ResouceStore.FieldNames.mktResStoreId.getTableFieldName(), resourceRequestQueryResp.getDestStoreId());
                    ResouceStore destStore = resouceStoreMapper.selectOne(wrapper);
                    if (destStore != null){
                        resourceRequestQueryResp.setDestStoreName(destStore.getMktResStoreName());
                    }
                }

                resourceRequestQueryResp.setMktResStoreName(resouceStoreDTO.getMktResStoreName());
            }

        }
        return ResultVO.success(page);
    }

    @Override
    public ResultVO<Boolean> updateResourceRequestState(ResourceRequestUpdateReq req) {
        if(StringUtils.isEmpty(req.getMktResReqId())){
            return ResultVO.error("mktResReqId is must not be null");
        }
        if(StringUtils.isEmpty(req.getStatusCd())){
            return ResultVO.error("statusCd is must not be null");
        }
        int num = requestManager.updateResourceRequestState(req);
        log.info("ResourceRequestServiceImpl.updateResourceRequestState requestManager.updateResourceRequestState req={}, resp={}",JSON.toJSONString(req), JSON.toJSONString(num));
        if(num < 1){
            return ResultVO.error();
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO<ResourceRequestResp> queryResourceRequestDetail(ResourceRequestItemQueryReq req) {
        ResourceRequest request =  requestManager.queryResourceRequest(req);
        ResourceRequestResp resp = new ResourceRequestResp();
        if(null == request){
            return ResultVO.success(resp);
        }
        resp.setStatusCd(request.getStatusCd());
        resp.setOriginName(getStoreName(request.getMktResStoreId()));
        resp.setTargetName(getStoreName(request.getDestStoreId()));
        resp.setReqCode(request.getReqCode());
        resp.setCreateDate(request.getCreateDate());
        resp.setMktResReqId(request.getMktResReqId());
        resp.setCreateStaff(request.getCreateStaff());
        resp.setMerchantId(request.getExtend1());

        ResourceReqDetailQueryReq detailQueryReq = new ResourceReqDetailQueryReq();
        detailQueryReq.setMktResReqId(req.getMktResReqId());
        List<ResourceReqDetailDTO> listDetail = detailManager.listDetail(detailQueryReq);
        resp.setResourceReqDetails(listDetail);
        Integer quantity = CollectionUtils.isEmpty(listDetail) ? 0 : listDetail.size();
        resp.setQuantity(quantity);

        // 源标商家类型
        ResouceStoreObjRelDTO resouceStoreObjRelDTO = resouceStoreObjRelManager.getMerchantByStore(request.getMktResStoreId());
        if(null != resouceStoreObjRelDTO){
            ResultVO<MerchantDTO> storeResultVO = merchantService.getMerchantById(resouceStoreObjRelDTO.getObjId());
            MerchantDTO merchantDTO = storeResultVO.getResultData();
            if(null != merchantDTO) {
                resp.setMerchantName(merchantDTO.getMerchantName());
                resp.setMerchantNameType(PartnerConst.MerchantTypeEnum.getNameByType(merchantDTO.getMerchantType()));
            }else{
                log.warn("ResourceRequestServiceImpl.queryResourceRequestDetail merchantService.getMerchantById req={},resp={}", resouceStoreObjRelDTO.getObjId(), JSON.toJSONString(storeResultVO));
            }
        }else{
            log.warn("ResourceInstServiceImpl.deliveryInResourceInst merchantService.getMerchantById req={},resp={}", request.getMktResStoreId(), JSON.toJSONString(resouceStoreObjRelDTO));
        }
        // 目标商家类型
        ResouceStoreObjRelDTO destObje = resouceStoreObjRelManager.getMerchantByStore(request.getDestStoreId());
        if(null != destObje){
            ResultVO<MerchantDTO> destStoreResultVO = merchantService.getMerchantById(destObje.getObjId());
            MerchantDTO destMerchantDTO = destStoreResultVO.getResultData();
            if(null != destMerchantDTO) {
                resp.setDestMerchantNameType(PartnerConst.MerchantTypeEnum.getNameByType(destMerchantDTO.getMerchantType()));
            }else{
                log.warn("ResourceRequestServiceImpl.queryResourceRequestDetail merchantService.getMerchantById req={},resp={}", resouceStoreObjRelDTO.getObjId(), JSON.toJSONString(destStoreResultVO));
            }
        }else{
            log.warn("ResourceInstServiceImpl.deliveryInResourceInst merchantService.getMerchantById req={},resp={}", request.getDestStoreId(), JSON.toJSONString(destObje));
        }
        return ResultVO.success(resp);
    }

    @Override
    public ResultVO<Boolean> hadDelivery(ResourceRequestUpdateReq req) {
        if (StringUtils.isEmpty(req.getMktResReqId())){
            return ResultVO.error("mktResReqId is must not be null");
        }
        req.setStatusCd(ResourceConst.MKTRESSTATE.DONE.getCode());
        int num = requestManager.updateResourceRequestState(req);
        log.info("ResourceRequestServiceImpl.updateResourceRequestState requestManager.updateResourceRequestState req={}, resp={}",JSON.toJSONString(req), JSON.toJSONString(num));
        if(num < 1){
            return ResultVO.error();
        }
        // 申请单ID->明细
        //根据申请单表保存的源仓库和申请单明细找到对应的串码
        ResourceReqDetailQueryReq detailQueryReq = new ResourceReqDetailQueryReq();
        detailQueryReq.setMktResReqId(req.getMktResReqId());
        List<ResourceReqDetailDTO> reqDetailDTOS = detailManager.listDetail(detailQueryReq);
        List<String> mktResInstNbrs = reqDetailDTOS.stream().map(ResourceReqDetailDTO::getMktResInstNbr).collect(Collectors.toList());
        ResourceReqDetailDTO detailDTO = reqDetailDTOS.get(0);
        //Todo 需要做退库操作
        return ResultVO.success(true);
    }

    /**
     * 获取仓库名称
     * @param instStoreId
     * @return
     */
    private String getStoreName(String instStoreId){
        ResouceStore store = storeManager.getResouceStore(instStoreId);
        if(null == store){
            return null;
        }
        return store.getMktResStoreName();
    }

    /**
     * 通过申请单Id获取串码数量
     * @param requestId
     * @return
     */
    private Integer getStoreQuantity(String requestId){
        return itemManager.getItemQuantity(requestId);
    }

    /**
     * 获取产品的名字
     * @param requestId
     * @return
     */
    private String getProductCatName(String requestId){
        List<String> strCatNameList = itemManager.getProductCatName(requestId);
        StringBuilder b = new StringBuilder();
        for(String str:strCatNameList){
            b.append(str);
        }
        return b.toString();
    }
}
