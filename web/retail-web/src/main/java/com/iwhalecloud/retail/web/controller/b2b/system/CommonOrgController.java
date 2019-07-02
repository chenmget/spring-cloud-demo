package com.iwhalecloud.retail.web.controller.b2b.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.CommonOrgDTO;
import com.iwhalecloud.retail.system.dto.request.CommonOrgListReq;
import com.iwhalecloud.retail.system.dto.request.OrganizationListReq;
import com.iwhalecloud.retail.system.service.CommonOrgService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通用组织信息控制器
 *
 * @Author lipeng
 * @Date 2019/06/12
 **/
@RestController
@RequestMapping("/api/commonOrg")
@Slf4j
@Api(value = "通用组织信息", tags = {"通用组织信息"})
public class CommonOrgController {

    @Reference
    private CommonOrgService commonOrgService;


    @ApiOperation(value = "查询通用组织信息列表", notes = "不传参数，默认查湖南 通用组织信息第一级 列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentOrgId", value = "父级区域ID，为空默认查湖南通用组织信息列表", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "orgLevel", value = "组织的级别", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "parentOrgId", value = "组织所属的本地网", paramType = "query", required = false, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/listCommonOrg")
    public ResultVO<List<CommonOrgDTO>> listCommonOrg(
            @RequestParam(required = false, value = "parentOrgId") String parentOrgId,
            @RequestParam(required = false, value = "orgLevel") String orgLevel,
            @RequestParam(required = false, value = "lanId") String lanId) {
        CommonOrgListReq req = new CommonOrgListReq();
        if (!StringUtils.isEmpty(parentOrgId)) {
            req.setParentOrgId(parentOrgId);
        }
        if (StringUtils.isNotEmpty(orgLevel)) {
            req.setOrgLevel(orgLevel);
        }
        if (StringUtils.isNotEmpty(lanId)) {
            req.setLanId(lanId);
        }

        return commonOrgService.listCommonOrg(req, false);
    }

    @ApiOperation(value = "查询通用组织信息列表", notes = "不传参数，默认查湖南 通用组织信息第一级 列表")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/list")
    public ResultVO<List<CommonOrgDTO>> list(@RequestBody @ApiParam(value = "查询通用组织列表参数", required = true) CommonOrgListReq req) {
        log.info("CommonOrgController.list()  input: req: {}", JSON.toJSONString(req));
        return commonOrgService.listCommonOrg(req, false);
    }

    @ApiOperation(value = "查询通用组织信息全部列表", notes = "不传参数，默认查湖南 通用组织信息全部 列表")
    @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    @GetMapping(value = "/listAllCommonOrg")
    public ResultVO<List<CommonOrgDTO>> listAllCommonOrg() {
        CommonOrgListReq req = new CommonOrgListReq();
        return commonOrgService.listCommonOrg(req, true);
    }

}
