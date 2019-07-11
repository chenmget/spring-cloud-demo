package com.iwhalecloud.retail.warehouse.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailPageReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailQueryReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;
import com.iwhalecloud.retail.warehouse.service.ResourceReqDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author My
 * @Date 2019/1/10
 **/
@Service(parameters={"addActSup.timeout","30000"})
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

    @Override
    public ResultVO<Page<ResourceReqDetailPageResp>> listResourceRequestDetailPage(ResourceReqDetailQueryReq req) {
        return resourceReqDetailService.listResourceRequestDetailPage(req);
    }

    @Override
    public ResultVO<Page<ResourceReqDetailPageResp>> listMerchantResourceRequestDetailPage(ResourceReqDetailQueryReq req) {
        return resourceReqDetailService.listMerchantResourceRequestDetailPage(req);
    }

    @Override
    public List<String> getUserHandleFormId(String userId) {
        return resourceReqDetailService.getUserHandleFormId(userId);
    }

    @Override
    public ResultVO<List<String>> getProcessingNbrList(List<String> nbrList) {
        ResultVO<List<String>> resp = resourceReqDetailService.getProcessingNbrList(nbrList);
        log.info("ResourceRequestOpenServiceImpl.resourceRequestPage req={}, resp={}", JSON.toJSONString(nbrList), JSON.toJSONString(resp));
        return resp;
    }

}
