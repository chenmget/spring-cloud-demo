package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstItmsLimitSaveReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstItmsLimitUpdateReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstItmsLimitResp;
import com.iwhalecloud.retail.warehouse.service.ResourceInstItmsLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
@Service
public class ResourceInstItmsLimitOpenServiceImpl implements ResourceInstItmsLimitService {

    @Autowired
    @Qualifier("resourceInstItmsLimitService")
    private ResourceInstItmsLimitService resourceInstItmsLimitService;

    @Override
    public ResultVO<Integer> saveResourceInstItmsLimit(ResourceInstItmsLimitSaveReq req) {
        log.info("ResourceInstItmsLimitOpenServiceImpl.saveResourceInstItmsLimit req={} ", JSON.toJSONString(req));
        return resourceInstItmsLimitService.saveResourceInstItmsLimit(req);
    }

    @Override
    public ResultVO<ResourceInstItmsLimitResp> getResourceInstItmsLimit(String lanId) {
        log.info("ResourceInstItmsLimitOpenServiceImpl.getResourceInstItmsLimit lanId={} ", lanId);
        return resourceInstItmsLimitService.getResourceInstItmsLimit(lanId);
    }

    @Override
    public ResultVO<Integer> updateResourceInstItmsLimit(ResourceInstItmsLimitUpdateReq req) {
        log.info("ResourceInstItmsLimitOpenServiceImpl.updateResourceInstItmsLimit req={}", JSON.toJSONString(req));
        return resourceInstItmsLimitService.updateResourceInstItmsLimit(req);
    }
}