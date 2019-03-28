package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;
import com.iwhalecloud.retail.warehouse.service.MarketingResStoreService;
import com.iwhalecloud.retail.warehouse.service.RetailerResourceInstService;
import com.iwhalecloud.retail.warehouse.util.ProfileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * @author he.sw
 * @date 2019/3/8 15:07
 */
@Service(timeout = 500000)
@Slf4j
public class RetailerResourceInstOpenServiceImpl implements RetailerResourceInstService {

    @Autowired
    @Qualifier("retailerResourceInstServiceImpl")
    private RetailerResourceInstService retailerResourceInstService;

    @Autowired
    @Qualifier("retailerResourceInstMarketServiceImpl")
    private RetailerResourceInstService retailerResourceInstMarketService;

    @Reference
    private MarketingResStoreService marketingResStoreService;

    @Autowired
    private ProfileUtil profileUtil;

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    @Override
    public ResultVO addResourceInstByGreenChannel(ResourceInstAddReq req) {
        log.info("RetailerResourceInstOpenServiceImpl.addResourceInstByGreenChannel req={}", JSON.toJSONString(req));
        if (profileUtil.isLocal()) {
            ResultVO resp = retailerResourceInstMarketService.addResourceInstByGreenChannel(req);
            resouceInstTrackService.asynGreenChannelForRetail(req, resp);
            return resp;
        } else {
            ResultVO resp = retailerResourceInstService.addResourceInstByGreenChannel(req);
            resouceInstTrackService.asynGreenChannelForRetail(req, resp);
            return resp;
        }
    }

    @Override
    public ResultVO delResourceInst(ResourceInstUpdateReq req) {
        log.info("RetailerResourceInstOpenServiceImpl.delResourceInst req={}", JSON.toJSONString(req));
        if (profileUtil.isLocal()) {
            ResultVO resp = retailerResourceInstMarketService.delResourceInst(req);
            resouceInstTrackService.asynDeleteInstForRetail(req, resp);
            return resp;
        } else {
            ResultVO resp = retailerResourceInstService.delResourceInst(req);
            resouceInstTrackService.asynDeleteInstForRetail(req, resp);
            return resp;
        }
    }

    @Override
    public ResultVO<Page<ResourceInstListResp>> listResourceInst(ResourceInstListReq req) {
        log.info("RetailerResourceInstOpenServiceImpl.listResourceInst req={}", JSON.toJSONString(req));
        if (profileUtil.isLocal()) {
            return retailerResourceInstMarketService.listResourceInst(req);
        } else {
            return retailerResourceInstService.listResourceInst(req);
        }
    }

    @Override
    public ResultVO pickResourceInst(ResourceInstPickupReq req) {
        log.info("RetailerResourceInstOpenServiceImpl.pickResourceInst req={}", JSON.toJSONString(req));
        if (profileUtil.isLocal()) {
            ResultVO resp = retailerResourceInstMarketService.pickResourceInst(req);
            resouceInstTrackService.pickResourceInstForRetail(req, resp);
            return resp;
        } else {
            ResultVO resp = retailerResourceInstService.pickResourceInst(req);
            resouceInstTrackService.pickResourceInstForRetail(req, resp);
            return resp;
        }
    }

    @Override
    public ResultVO retreatStorageResourceInst(RetreatStorageReq req) {
        log.info("RetailerResourceInstOpenServiceImpl.retreatStorageResourceInst req={}", JSON.toJSONString(req));
        return null;
    }

    @Override
    public ResultVO<List<ResourceInstListResp>> getBatch(ResourceInstBatchReq req) {
        log.info("RetailerResourceInstOpenServiceImpl.getBatch req={}", JSON.toJSONString(req));
        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        if (profileUtil.isLocal()) {
            return retailerResourceInstMarketService.getBatch(req);
        } else {
            return retailerResourceInstService.getBatch(req);
        }
    }

    @Override
    public ResultVO allocateResourceInst(RetailerResourceInstAllocateReq req) {
        log.info("RetailerResourceInstOpenServiceImpl.allocateResourceInst req={}", JSON.toJSONString(req));
        if (profileUtil.isLocal()) {
            ResultVO resp = retailerResourceInstMarketService.allocateResourceInst(req);
            resouceInstTrackService.allocateResourceInstForRetail(req, resp);
            return resp;
        } else {
            ResultVO resp = retailerResourceInstService.allocateResourceInst(req);
            resouceInstTrackService.allocateResourceInstForRetail(req, resp);
            return resp;
        }
    }

    @Override
    public ResultVO confirmReciveNbr(ConfirmReciveNbrReq req) {
        log.info("RetailerResourceInstOpenServiceImpl.confirmReciveNbr req={}", JSON.toJSONString(req));
        if (profileUtil.isLocal()) {
            ResultVO resp = retailerResourceInstMarketService.confirmReciveNbr(req);
            resouceInstTrackService.allocateResourceIntsWarehousingForRetail(req, resp);
            return resp;
        } else {
            ResultVO resp = retailerResourceInstService.confirmReciveNbr(req);
            resouceInstTrackService.allocateResourceIntsWarehousingForRetail(req, resp);
            return resp;
        }
    }

    @Override
    public ResultVO confirmRefuseNbr(ConfirmReciveNbrReq req) {
        log.info("RetailerResourceInstOpenServiceImpl.confirmRefuseNbr req={}", JSON.toJSONString(req));
        if (profileUtil.isLocal()) {
            return retailerResourceInstMarketService.confirmRefuseNbr(req);
        } else {
            ResultVO resp = retailerResourceInstService.confirmRefuseNbr(req);
            resouceInstTrackService.allocateResourceIntsWarehousingCancelForRetail(req, resp);
            return resp;
        }
    }

    @Override
    public ResultVO<List<ResourceInstListResp>> getExportResourceInstList(ResourceInstListReq req) {
        return retailerResourceInstService.getExportResourceInstList(req);
    }
}