package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.dto.request.PageProductReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstListPageReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstUpdateReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.service.MerchantResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

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
}
