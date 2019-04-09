package com.iwhalecloud.retail.web.controller.b2b.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.BusinessEntityDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.service.BusinessEntityService;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhong.wenlong
 * @Date 2018/12/27
 *
 * 经营主体
 **/
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/b2b/businessEntity")
public class BusinessEntityB2BController {
    @Reference
    private BusinessEntityService businessEntityService;

    @Reference
    private MerchantRulesService merchantRulesService;

    @ApiOperation(value = "经营主体信息新建接口", notes = "经营主体信息新建接口")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/save")
    public ResultVO<BusinessEntityDTO> saveBusinessEntity(@RequestBody @ApiParam(value = "经营主体信息新建入参", required = true) BusinessEntitySaveReq businessEntitySaveReq){
        log.info("BusinessEntityB2BController.saveBusinessEntity(), 入参businessEntitySaveReq={} ", businessEntitySaveReq);
        ResultVO resp = businessEntityService.saveBusinessEntity(businessEntitySaveReq);
        log.info("BusinessEntityB2BController.saveBusinessEntity(), 出参resp={} ", resp);
        return resp;
    }

    @ApiOperation(value = "经营主体信息更新接口", notes = "厂商信息更新接口。")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="/update")
    public ResultVO<Integer> updateBusinessEntity(
            @RequestBody @ApiParam(value = "经营主体信息更新入参", required = true) BusinessEntityUpdateReq businessEntityUpdateReq
    ) {
        log.info("BusinessEntityB2BController.updateBusinessEntity(), 入参businessEntityUpdateReq={} ", businessEntityUpdateReq);
        ResultVO resp = businessEntityService.updateBusinessEntity(businessEntityUpdateReq);
        log.info("BusinessEntityB2BController.updateBusinessEntity(), 出参resp={} ", resp);
        return resp;
    }

    @ApiOperation(value = "经营主体信息删除接口", notes = "经营主体信息删除接口。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessEntityId", value = "经营主体ID", paramType = "query", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="/delete")
    public ResultVO<Integer> deleteBusinessEntity(@RequestParam(value = "businessEntityId") String businessEntityId) {
        log.info("BusinessEntityB2BController.deleteBusinessEntity(), 入参businessEntityId={} ", businessEntityId);
        if(StringUtils.isEmpty(businessEntityId)){
            return ResultVO.error("经营主体ID不能为空");
        }
        ResultVO resp = businessEntityService.deleteBusinessEntityById(businessEntityId);
        log.info("BusinessEntityB2BController.deleteBusinessEntity(), 出参resp={} ", resp);
        return resp;
    }

    @ApiOperation(value = "查询经营主体详情接口", notes = "根据ID查询经营主体信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessEntityId", value = "经营主体ID", paramType = "query", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/get")
    public ResultVO<BusinessEntityDTO> getBusinessEntity(@RequestParam(value = "businessEntityId") String businessEntityId) {
        log.info("BusinessEntityB2BController.getBusinessEntity(), 入参businessEntityId={} ", businessEntityId);
        if(StringUtils.isEmpty(businessEntityId)){
            return ResultVO.error("经营主体ID不能为空");
        }
        BusinessEntityGetReq req = new BusinessEntityGetReq();
        req.setBusinessEntityId(businessEntityId);
        ResultVO resp = businessEntityService.getBusinessEntity(req);
        log.info("BusinessEntityB2BController.getBusinessEntity(), 出参resp={} ", resp);
        return resp;
    }

    @ApiOperation(value = "经营主体信息分页列表查询接口", notes = "经营主体信息分页列表查询，名称和编码都是模糊查询。")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/page")
    public ResultVO<Page<BusinessEntityDTO>> pageBusinessEntity(@RequestBody @ApiParam(value = "经营主体信息分页入参", required = true) BusinessEntityPageReq businessEntityPageReq){
        log.info("BusinessEntityB2BController.pageBusinessEntity(), 入参businessEntityPageReq={} ", businessEntityPageReq);
        ResultVO resp = businessEntityService.pageBusinessEntity(businessEntityPageReq);
        log.info("BusinessEntityB2BController.pageBusinessEntity(), 出参resp={} ", resp);
        return resp;
    }

    @ApiOperation(value = "经营主体信息列表查询接口", notes = "经营主体信息列表查询，名称和编码都是模糊查询。")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/list")
    public ResultVO listBusinessEntity(@RequestBody @ApiParam(value = "经营主体信息列表查询入参", required = true) BusinessEntityListReq businessEntityListReq){
        log.info("BusinessEntityB2BController.listBusinessEntity(), 入参businessEntityPageReq={} ", businessEntityListReq);
        ResultVO resp = businessEntityService.listBusinessEntity(businessEntityListReq);
        log.info("BusinessEntityB2BController.listBusinessEntity(), 出参resp={} ", resp);
        return resp;
    }

    @ApiOperation(value = "经营主体信息分页列表查询接口", notes = "有权限的经营主体信息分页列表查询，名称和编码都是模糊查询。")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/pageBusinessEntityByRight")
    @UserLoginToken
    public ResultVO pageBusinessEntityByRight(@RequestBody @ApiParam(value = "经营主体信息分页入参", required = true) BusinessEntityPageByRightsReq req){

        Boolean isAdminType = UserContext.isAdminType();
        MerchantRulesCommonReq commonReq = new MerchantRulesCommonReq();
        commonReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType());
        if (UserContext.getUserOtherMsg() != null && UserContext.getUserOtherMsg().getMerchant() != null && !isAdminType) {
            String merchantId = UserContext.getUserOtherMsg().getMerchant().getMerchantId();
            ResultVO<List<String>> permissionVO = merchantRulesService.getRegionAndMerchantPermission(commonReq);
            log.info("BusinessEntityB2BController.pageBusinessEntityByRight merchantRulesService.getRegionAndMerchantPermission, req= {} ", merchantId, JSON.toJSONString(permissionVO));
            if (permissionVO.isSuccess() && !CollectionUtils.isEmpty(permissionVO.getResultData())) {
                req.setMerchantIdList(permissionVO.getResultData());
            }
        }else if(isAdminType && !StringUtils.isEmpty(req.getMerchantId())){
            // 管理员登陆且指定商家
            String merchantId = req.getMerchantId();
            ResultVO<List<String>> permissionVO = merchantRulesService.getRegionAndMerchantPermission(commonReq);
            log.info("BusinessEntityB2BController.pageBusinessEntityByRight merchantRulesService.getRegionAndMerchantPermission, req= {} ", req.getMerchantId(), JSON.toJSONString(permissionVO));
            if (permissionVO.isSuccess() && !CollectionUtils.isEmpty(permissionVO.getResultData())) {
                req.setMerchantIdList(permissionVO.getResultData());
            }
        }

        log.info("BusinessEntityB2BController.pageBusinessEntityByRight(), req= {} ", JSON.toJSONString(req));
        ResultVO<Page<BusinessEntityDTO>> resp = businessEntityService.pageBusinessEntityByRight(req);
        return resp;
    }
}
