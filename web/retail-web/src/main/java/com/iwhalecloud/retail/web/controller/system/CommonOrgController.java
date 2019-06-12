package com.iwhalecloud.retail.web.controller.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.CommonOrgDTO;
import com.iwhalecloud.retail.system.dto.request.CommonOrgListReq;
import com.iwhalecloud.retail.system.service.CommonOrgService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** 通用组织信息控制器
 * @Author lipeng
 * @Date 2019/06/12
 **/
@RestController
@RequestMapping("/api/commonOrg")
@Slf4j
public class CommonOrgController {

    @Reference
    private CommonOrgService commonOrgService;


    @ApiOperation(value = "查询通用组织信息列表", notes = "不传参数，默认查湖南 通用组织信息第一级 列表")
    @ApiImplicitParam(name="parentOrgId", value = "父级区域ID，为空默认查湖南通用组织信息列表", paramType = "query", required = false, dataType = "String")

    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/listCommonOrg")
    public ResultVO<List<CommonOrgDTO>> listCommonOrg(@RequestParam(required = false, value = "parentOrgId") String parentOrgId){
        CommonOrgListReq req = new CommonOrgListReq();
        if (!StringUtils.isEmpty(parentOrgId)) {
            req.setParentOrgId(parentOrgId);
        }

        return commonOrgService.listCommonOrg(req,false);
    }


    @ApiOperation(value = "查询通用组织信息全部列表", notes = "不传参数，默认查湖南 通用组织信息全部 列表")
    @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    @GetMapping(value = "/listAllCommonOrg")
    public ResultVO<List<CommonOrgDTO>> listAllCommonOrg(){
        CommonOrgListReq req = new CommonOrgListReq();
        return commonOrgService.listCommonOrg(req,true);
    }

}
