package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.service.MerchantResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@Slf4j
public class MerchantResourceInstOpenServiceImpl implements MerchantResourceInstService {

    @Autowired
    private MerchantResourceInstService merchantResourceInstService;

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    @Override
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq req) {
        log.info("MerchantResourceInstOpenServiceImpl.getResourceInstList req={}", JSON.toJSONString(req));
        return merchantResourceInstService.getResourceInstList(req);
    }

    @Override
    public ResultVO delResourceInst(ResourceInstUpdateReq req) {
        log.info("MerchantResourceInstOpenServiceImpl.delResourceInst req={}", JSON.toJSONString(req));
        ResultVO resp = merchantResourceInstService.delResourceInst(req);
        resouceInstTrackService.asynUpdateTrackForMerchant(req, resp);
        return resp;
    }

    @Override
    public ResultVO<ResourceInstAddResp> addResourceInst(ResourceInstAddReq req) {
        log.info("MerchantResourceInstOpenServiceImpl.addResourceInst req={}", JSON.toJSONString(req));
        ResultVO<ResourceInstAddResp> resp = merchantResourceInstService.addResourceInst(req);
        log.info("MerchantResourceInstOpenServiceImpl.addResourceInst req={} resp={}", JSON.toJSONString(req), JSON.toJSONString(resp));
        resouceInstTrackService.asynSaveTrackForMerchant(req, resp);
        return resp;
    }

    @Override
    public ResultVO selectProduct(PageProductReq req) {
        log.info("MerchantResourceInstOpenServiceImpl.selectProduct req={}", JSON.toJSONString(req));
        return merchantResourceInstService.selectProduct(req);
    }

    @Override
    public ResultVO validNbr(ResourceInstValidReq req){
        return merchantResourceInstService.validNbr(req);
    }

    @Override
    public ResultVO<Page<ResourceUploadTempListResp>> listResourceUploadTemp(ResourceUploadTempListPageReq req){
        return merchantResourceInstService.listResourceUploadTemp(req);
    }

    @Override
    public ResultVO exceutorDelNbr(ResourceUploadTempDelReq req){
        return merchantResourceInstService.exceutorDelNbr(req);
    }

    @Override
    public List<ResourceUploadTempListResp> exceutorQueryTempNbr(ResourceUploadTempDelReq req){
        return merchantResourceInstService.exceutorQueryTempNbr(req);
    }

    @Override
    public ResultVO addResourceInstByAdmin(ResourceInstAddReq req){
        ResultVO<ResourceInstAddResp> resp = merchantResourceInstService.addResourceInstByAdmin(req);
        log.info("MerchantResourceInstOpenServiceImpl.addResourceInstByAdmin req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(req));
        resouceInstTrackService.asynSaveTrackForMerchant(req, resp);
        return resp;
    }
}
