package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;
import com.iwhalecloud.retail.warehouse.service.ResourceReqDetailService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author My
 * @Date 2019/04/18
 **/
@RestController
@RequestMapping("/api/b2b/resourceReqDetail")
@Slf4j
public class ResourceReqDetailB2BController {

    @Reference
    private ResourceReqDetailService resourceReqDetailService;

    @ApiOperation(value = "查询申请单详情串码分页列表", notes = "查询申请单详情串码分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mktResReqId", value = "mktResReqId", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "mktResInstNbr", value = "mktResInstNbr", paramType = "query", required = false, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="resourceRequestPage")
    public ResultVO<Page<ResourceReqDetailPageResp>> resourceRequestPage(String mktResReqId, String mktResInstNbr) {
        ResourceReqDetailPageReq req = new ResourceReqDetailPageReq();
        req.setMktResReqId(mktResReqId);
        req.setMktResInstNbr(mktResInstNbr);
        return resourceReqDetailService.resourceRequestPage(req);
    }

}
