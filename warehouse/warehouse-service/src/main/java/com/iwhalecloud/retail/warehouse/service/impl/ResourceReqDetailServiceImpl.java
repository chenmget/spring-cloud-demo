package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductResourceInstGetReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResourceResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.runable.RunableTask;
import com.iwhalecloud.retail.warehouse.service.ResourceReqDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class ResourceReqDetailServiceImpl implements ResourceReqDetailService {

    @Autowired
    private ResourceReqDetailManager resourceReqDetailManager;

    @Reference
    private ProductService productService;

    @Autowired
    private RunableTask runableTask;

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
}