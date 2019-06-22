package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestItemQueryReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestQueryReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestUpdateReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestQueryResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestResp;
import com.iwhalecloud.retail.warehouse.service.ResourceRequestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author My
 * @Date 2019/1/12
 **/
@RestController
@RequestMapping("/api/b2b/resourceRequest")
@Slf4j
public class ResourceRequestB2BController {

    @Reference
    private ResourceRequestService requestService;

    @ApiOperation(value = "查询申请单分页列表", notes = "查询申请单明细分页列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="listResourceRequest")
    public ResultVO<Page<ResourceRequestQueryResp>> listResourceRequest(@RequestBody ResourceRequestQueryReq req) {
        return requestService.listResourceRequest(req);
    }

    @ApiOperation(value = "查询申请单明详情", notes = "查询申请单明详情")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="queryResourceRequestDetail")
    public ResultVO<ResourceRequestResp> queryResourceRequestDetail(@RequestParam String mktResReqId) {
        if(StringUtils.isEmpty(mktResReqId)){
            return ResultVO.error();
        }
        ResourceRequestItemQueryReq req = new ResourceRequestItemQueryReq();
        req.setMktResReqId(mktResReqId);
        return requestService.queryResourceRequestDetail(req);
    }

    @ApiOperation(value = "查询申请单明详情", notes = "查询申请单明详情")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="hadDelivery")
    public ResultVO<Boolean>  hadDelivery(@RequestParam String mktResReqId) {
        if(StringUtils.isEmpty(mktResReqId)){
            return ResultVO.error();
        }
        ResourceRequestUpdateReq req = new ResourceRequestUpdateReq();
        req.setMktResReqId(mktResReqId);
        return requestService.hadDelivery(req);
    }
}
