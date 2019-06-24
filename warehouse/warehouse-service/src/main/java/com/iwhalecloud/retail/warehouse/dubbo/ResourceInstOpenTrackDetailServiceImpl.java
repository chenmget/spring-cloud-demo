package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsTrackDetailGetReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstTrackDetailListResp;
import com.iwhalecloud.retail.warehouse.service.ResouceInstTrackDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
@Slf4j
public class ResourceInstOpenTrackDetailServiceImpl implements ResouceInstTrackDetailService {

    @Autowired
    private ResouceInstTrackDetailService resouceInstTrackDetailService;

    @Override
    public ResultVO<List<ResourceInstTrackDetailListResp>> getResourceInstTrackDetailByNbr(ResourceInstsTrackDetailGetReq req) {
        log.info("ResourceInstOpenTrackDetailServiceImpl.getResourceInstTrackDetailByNbr req={}", JSON.toJSONString(req));
        return resouceInstTrackDetailService.getResourceInstTrackDetailByNbr(req);
    }
}