package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;
import com.iwhalecloud.retail.warehouse.service.ResourceReqDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author My
 * @Date 2019/1/10
 **/
@Service
@Slf4j
public class ResourceRequestDetailOpenServiceImpl implements ResourceReqDetailService {

    @Autowired
    private ResourceReqDetailService resourceReqDetailService;

    @Override
    public ResultVO<Page<ResourceReqDetailPageResp>> resourceRequestPage(ResourceReqDetailPageReq req) {
        log.info("ResourceRequestOpenServiceImpl.resourceRequestPage req={}", JSON.toJSONString(req));
        return resourceReqDetailService.resourceRequestPage(req);
    }

    @Override
    public ResultVO<List<ResourceReqDetailPageResp>> resourceRequestList(ResourceReqDetailPageReq req){
        return resourceReqDetailService.resourceRequestList(req);
    }
}
