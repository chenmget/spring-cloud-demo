package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
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
    public ResultVO<List<ResourceInstTrackDetailListResp>> getResourceInstTrackDetailByNbr(String nbr) {
        log.info("ResourceInstOpenTrackDetailServiceImpl.getResourceInstTrackDetailByNbr nbr={}", nbr);
        return resouceInstTrackDetailService.getResourceInstTrackDetailByNbr(nbr);
    }
}