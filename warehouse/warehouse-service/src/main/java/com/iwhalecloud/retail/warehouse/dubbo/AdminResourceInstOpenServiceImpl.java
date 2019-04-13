package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.dto.request.AdminResourceInstDelReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstListPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.service.AdminResourceInstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@Slf4j
public class AdminResourceInstOpenServiceImpl implements AdminResourceInstService {

    @Autowired
    private AdminResourceInstService adminResourceInstService;

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    @Override
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq req) {
        log.info("AdminResourceInstOpenServiceImpl.getResourceInstList req={}", JSON.toJSONString(req));
        return adminResourceInstService.getResourceInstList(req);
    }

    @Override
    public ResultVO<ResourceInstAddResp> addResourceInst(ResourceInstAddReq req) {
        log.info("AdminResourceInstOpenServiceImpl.addResourceInst req={}", JSON.toJSONString(req));
        ResultVO<ResourceInstAddResp> resp = adminResourceInstService.addResourceInst(req);
        resouceInstTrackService.asynSaveTrackForAddmin(req, resp);
        log.info("AdminResourceInstOpenServiceImpl.addResourceInst req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(resp));
        return resp;
    }

    @Override
    public ResultVO updateResourceInstByIds(AdminResourceInstDelReq req) {
        log.info("AdminResourceInstOpenServiceImpl.updateResourceInstByIds req={}", JSON.toJSONString(req));
        ResultVO resp = adminResourceInstService.updateResourceInstByIds(req);
        resouceInstTrackService.asynUpdateTrackForAddmin(req, resp);
        return resp;
    }

}
