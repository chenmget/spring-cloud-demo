package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestItemQueryReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestQueryReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestUpdateReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestQueryResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestResp;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author My
 * @Date 2019/1/10
 **/
@Service
@Slf4j
public class ResourceRequestOpenServiceImpl implements ResourceRequestService {

    @Autowired
    private ResourceRequestService resourceRequestService;

    @Override
    public ResultVO<String> insertResourceRequest(ResourceRequestAddReq req) {
        log.info("ResourceRequestOpenServiceImpl.insertResourceRequest req={}", JSON.toJSONString(req));
        return resourceRequestService.insertResourceRequest(req);
    }

    @Override
    public ResultVO<ResourceRequestResp> queryResourceRequest(ResourceRequestItemQueryReq req) {
        log.info("ResourceRequestOpenServiceImpl.queryResourceRequest req={}", JSON.toJSONString(req));
        return resourceRequestService.queryResourceRequest(req);
    }

    @Override
    public ResultVO<Page<ResourceRequestQueryResp>> listResourceRequest(ResourceRequestQueryReq req){
        log.info("ResourceRequestOpenServiceImpl.listResourceRequest req={}", JSON.toJSONString(req));
        return resourceRequestService.listResourceRequest(req);
    }

    @Override
    public ResultVO<Boolean> updateResourceRequestState(ResourceRequestUpdateReq req) {
        log.info("ResourceRequestOpenServiceImpl.updateResourceRequestState req={}", JSON.toJSONString(req));
        return resourceRequestService.updateResourceRequestState(req);
    }

    @Override
    public ResultVO<ResourceRequestResp> queryResourceRequestDetail(ResourceRequestItemQueryReq req) {
        log.info("ResourceRequestOpenServiceImpl.queryResourceRequestDetail req={}", JSON.toJSONString(req));
        return resourceRequestService.queryResourceRequestDetail(req);
    }

    @Override
    public ResultVO<Boolean> hadDelivery(ResourceRequestUpdateReq req) {
        log.info("ResourceRequestOpenServiceImpl.hadDelivery req={}", JSON.toJSONString(req));
        return resourceRequestService.hadDelivery(req);
    }
}
