package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductRebateReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductResourceInstGetReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResourceResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceBatchRecService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ExcelResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailPageDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempCountResp;
import com.iwhalecloud.retail.warehouse.entity.ResourceRequest;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceRequestManager;
import com.iwhalecloud.retail.warehouse.runable.RunableTask;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceReqDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ResourceReqDetailServiceImpl implements ResourceReqDetailService {

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;

    @Reference
    private ProductService productService;

    @Autowired
    private RunableTask runableTask;

    @Reference
    private MerchantService merchantService;

    @Autowired
    private ResourceRequestManager resourceRequestManager;

    @Reference
    private ResouceStoreService resouceStoreService;





    @Override
    public ResultVO<Page<ResourceReqDetailPageResp>> resourceRequestPage(ResourceReqDetailPageReq req){
        Page<ResourceReqDetailPageResp> respPage = resourceReqDetailManager.resourceRequestPage(req);
        if (null == respPage || CollectionUtils.isEmpty(respPage.getRecords())) {
            return ResultVO.success(respPage);
        }
        List<ResourceReqDetailPageResp> list = respPage.getRecords();
        ProductResourceInstGetReq productReq = new ProductResourceInstGetReq();
        productReq.setProductId(list.get(0).getMktResId());
        ResultVO<List<ProductResourceResp>> productRespVO = productService.getProductResource(productReq);
        log.info("ResourceReqDetailServiceImpl.resourceRequestPage productService.getProductResource req={}, resp={}", JSON.toJSONString(productReq), JSON.toJSONString(productRespVO));
        if (!productRespVO.isSuccess() || CollectionUtils.isEmpty(productRespVO.getResultData())) {
            return ResultVO.success(respPage);
        }
        // 这个接口供厂商新增串码审核页展示用，新增的串码选择的是同一个产品，他们的产品信息都一样
        ProductResourceResp productResourceResp = productRespVO.getResultData().get(0);
        for (ResourceReqDetailPageResp dto : list) {
            BeanUtils.copyProperties(productResourceResp, dto);
        }
        return ResultVO.success(respPage);
    }


    @Override
    public ResultVO<List<ResourceReqDetailPageResp>> resourceRequestList(ResourceReqDetailPageReq req){
        List<ResourceReqDetailPageResp> list = runableTask.exceutorQueryReqDetail(req);

        if (CollectionUtils.isEmpty(list)) {
            return ResultVO.success(list);
        }
        ProductResourceInstGetReq productReq = new ProductResourceInstGetReq();
        productReq.setProductId(list.get(0).getMktResId());
        ResultVO<List<ProductResourceResp>> productRespVO = productService.getProductResource(productReq);
        log.info("ResourceReqDetailServiceImpl.resourceRequestList productService.getProductResource req={}, resp={}", JSON.toJSONString(productReq), JSON.toJSONString(productRespVO));
        if (!productRespVO.isSuccess() || CollectionUtils.isEmpty(productRespVO.getResultData())) {
            return ResultVO.success(list);
        }
        // 这个接口供厂商新增串码审核页展示用，新增的串码选择的是同一个产品，他们的产品信息都一样
        ProductResourceResp productResourceResp = productRespVO.getResultData().get(0);
        for (ResourceReqDetailPageResp dto : list) {
            BeanUtils.copyProperties(productResourceResp, dto);
        }
        return ResultVO.success(list);
    }

    @Override
    public ResultVO<Page<ResourceReqDetailPageResp>> listResourceRequestDetailPage(ResourceReqDetailQueryReq req) {
        //根据搜索条件组装参数
        packageResourceReqDetailQueryReq(req);
        Page<ResourceReqDetailPageDTO> respPage = resourceReqDetailManager.listResourceRequestPage(req);
        List<ResourceReqDetailPageDTO> list = respPage.getRecords();
        Page<ResourceReqDetailPageResp> result=new Page<ResourceReqDetailPageResp>();
        BeanUtils.copyProperties(respPage, result);
        if (null == list || list.isEmpty()) {
            return ResultVO.success(result);
        }
        //根据mktResId分组，减少调用服务次数，组装品牌名，产品名，商家名
        Map<String, List<ResourceReqDetailPageDTO>> map = list.stream().collect(Collectors.groupingBy(t -> t.getMktResId()));
        //商家id与商家名集合，如果id相同，直接从集合中拿
        Map<String,String> merchantMap=new HashMap<>();
        for (Map.Entry<String, List<ResourceReqDetailPageDTO>> entry : map.entrySet()) {
            String mktResId = entry.getKey();
            if (StringUtils.isBlank(mktResId)) {
                continue;
            }
            ProductResourceInstGetReq queryReq = new ProductResourceInstGetReq();
            queryReq.setProductId(mktResId);
            ResultVO<List<ProductResourceResp>> resultVO = productService.getProductResource(queryReq);
            log.info("ResourceReqDetailServiceImpl.listResourceRequestPage.getProductResource req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(resultVO));
            List<ProductResourceResp> prodList = resultVO.getResultData();
            if (CollectionUtils.isEmpty(prodList)) {
                continue;
            }
            ProductResourceResp prodResp = prodList.get(0);
            List<ResourceReqDetailPageDTO> instList =entry.getValue();
            for (ResourceReqDetailPageDTO resp : instList) {
                //填充审核状态
                resp.setStatusCdName(ResourceConst.DetailStatusCd.getNameByCode(resp.getStatusCd()));
                // 添加产品信息
                BeanUtils.copyProperties(prodResp, resp);
                // 添加厂商信息
                String merchantId=resp.getMerchantId();
                if (StringUtils.isNotBlank(merchantId)) {
                    if(StringUtils.isNotBlank(merchantMap.get(merchantId))){
                        resp.setMerchantName(merchantMap.get(merchantId));
                        continue;
                    }
                    //通过厂家id获取厂家信息
                    ResultVO<MerchantDTO> merchantResultVO = merchantService.getMerchantById(merchantId);
                    log.info("ResourceReqDetailServiceImpl.listResourceRequestPage.getMerchantById req={},resp={}", JSON.toJSONString(queryReq), JSON.toJSONString(merchantResultVO));
                    if (merchantResultVO.isSuccess() && null != merchantResultVO.getResultData()) {
                        MerchantDTO merchantDTO = merchantResultVO.getResultData();
                        resp.setMerchantName(merchantDTO.getMerchantName());
                        merchantMap.put(merchantId, merchantDTO.getMerchantName());
                    }
                }

            }
        }
        List<ResourceReqDetailPageResp> resultList = getResourceReqDetailList(list);
        result.setRecords(resultList);
        return ResultVO.success(result);
    }



    private List<ResourceReqDetailPageResp> getResourceReqDetailList(List<ResourceReqDetailPageDTO> list) {
        List<ResourceReqDetailPageResp> resultList=new ArrayList<>();
        for(ResourceReqDetailPageDTO dto: list){
            ResourceReqDetailPageResp resp=new ResourceReqDetailPageResp();
            BeanUtils.copyProperties(dto,resp);
            resultList.add(resp);
        }
        return resultList;
    }

    /**
     * 组装请求参数
     * @param req
     */
    private void packageResourceReqDetailQueryReq(ResourceReqDetailQueryReq req) {
        //如果商家名不为空，取回商家id
        if(req.getMerchantName()!=null){
            MerchantListReq merchantGetReq=new MerchantListReq();
            merchantGetReq.setMerchantName(req.getMerchantName());
            merchantGetReq.setMerchantType(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType());//默认厂商
            ResultVO<List<MerchantDTO>> merchantResult = merchantService.listMerchant(merchantGetReq);
            if(merchantResult.isSuccess()&&merchantResult.getResultData()!=null){
                List<MerchantDTO> merchantList=merchantResult.getResultData();
                List<String> merchantIds=merchantList.stream().distinct().map(MerchantDTO::getMerchantId).collect(Collectors.toList());
                req.setMerchantId(merchantIds);
            }
        }
        //如果产品名称或品牌名称不为空，取回产品id
        if(req.getProductName()!=null||req.getBrandId()!=null||req.getTypeId()!=null){
            ProductRebateReq productRebateReq=new ProductRebateReq();
            productRebateReq.setProductName(req.getProductName());
            productRebateReq.setBrandId(req.getBrandId());
            productRebateReq.setTypeId(req.getTypeId());
            ResultVO<List<ProductResp>> prodctResult=productService.getProductForRebate(productRebateReq);
            if(prodctResult.isSuccess()&&prodctResult.getResultData()!=null){
                List<ProductResp> productList=prodctResult.getResultData();
                List<String> productIds=productList.stream().distinct().map(ProductResp::getProductId).collect(Collectors.toList());
                req.setProductId(productIds);
            }
        }
    }
}