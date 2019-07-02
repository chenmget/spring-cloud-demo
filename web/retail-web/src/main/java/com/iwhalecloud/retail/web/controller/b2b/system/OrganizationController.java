package com.iwhalecloud.retail.web.controller.b2b.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.request.OrganizationListReq;
import com.iwhalecloud.retail.system.dto.response.OrganizationListResp;
import com.iwhalecloud.retail.system.service.OrganizationService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wenlong.zhong
 * @date 2019/6/27
 */
@RestController
@RequestMapping("/api/organization")
@Slf4j
@Api(value = "全量组织相关接口", tags = {"全量组织相关接口"})
public class OrganizationController {

    @Reference
    OrganizationService organizationService;

    @ApiOperation(value = "查询全量组织列表接口", notes = "查询全量组织列表接口")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/list")
    public ResultVO<List<OrganizationListResp>> list(@RequestBody @ApiParam(value = "查询全量组织列表参数", required = true) OrganizationListReq req) {
        log.info("OrganizationController.list()  input: req: {}", JSON.toJSONString(req));
        return organizationService.listOrganization(req);
    }
}
