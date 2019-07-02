package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.AttrSpecDTO;
import com.iwhalecloud.retail.goods2b.service.AttrSpecService;
import com.iwhalecloud.retail.partner.dto.resp.TransferPermissionGetResp;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceAllocateResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.ConfirmReciveNbrReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.ResourceInstAddReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.ResourceInstAllocateReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.ResourceInstUpdateByIdReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ExportCSVUtils;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ResourceInstColum;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author My
 * @Date 2019/1/4
 **/
@RestController
@RequestMapping("/api/b2b/supplierResourceInst")
@Slf4j
public class SupplierResourceInstB2BController {

    @Reference
    private SupplierResourceInstService supplierResourceInstService;

    @Reference
    private MerchantRulesService merchantRulesService;

    @Reference
    private AttrSpecService attrSpecService;

    @ApiOperation(value = "供应商串码管理页面", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="getResourceInstList")
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(@RequestBody ResourceInstListPageReq req) {
        if (StringUtils.isEmpty(req.getMktResStoreIds())) {
            return ResultVO.error("仓库为空");
        }
        return supplierResourceInstService.getResourceInstList(req);
    }

    @ApiOperation(value = "删除串码", notes = "传入串码集合删除")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="delResourceInst")
    @UserLoginToken
    public ResultVO<List<String>> delResourceInst(@RequestBody ResourceInstUpdateByIdReqDTO dto) {
        String userId = UserContext.getUserId();
        AdminResourceInstDelReq req = new AdminResourceInstDelReq();
        BeanUtils.copyProperties(dto, req);
        req.setUpdateStaff(userId);
        List<String> checkStatusCd = new ArrayList<String>(1);
        checkStatusCd.add(ResourceConst.STATUSCD.DELETED.getCode());
        req.setCheckStatusCd(checkStatusCd);
        req.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
        req.setEventType(ResourceConst.EVENTTYPE.CANCEL.getCode());
        req.setMerchantId(UserContext.getMerchantId());
        return supplierResourceInstService.delResourceInst(req);
    }


    @ApiOperation(value = "串码入库", notes = "添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addResourceInst")
    @UserLoginToken
    public ResultVO<ResourceInstAddResp> addResourceInst(@RequestBody @Valid ResourceInstAddReqDTO dto) {
        String userId = UserContext.getUserId();
        ResourceInstAddReq req = new ResourceInstAddReq();
        req.setCreateStaff(userId);
        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        req.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        req.setSourceType(ResourceConst.SOURCE_TYPE.MERCHANT.getCode());
        BeanUtils.copyProperties(dto, req);
        req.setStorageType(ResourceConst.STORAGETYPE.SUPPLIER_INPUT.getCode());
        req.setMerchantId(UserContext.getMerchantId());
        log.info("SupplierResourceInstB2BController.addResourceInst req={}", JSON.toJSONString(req));
        return supplierResourceInstService.exceutorAddNbrForSupplier(req);
    }
    @ApiOperation(value = "调拨串码", notes = "调拨串码")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @UserLoginToken
    @PostMapping(value="allocateResourceInst")
    public ResultVO allocateResourceInst(@RequestBody ResourceInstAllocateReqDTO dto) {
        String userId = UserContext.getUserId();
        SupplierResourceInstAllocateReq req = new SupplierResourceInstAllocateReq();
        BeanUtils.copyProperties(dto, req);
        req.setCreateStaff(userId);
        log.info("SupplierResourceInstB2BController.allocateResourceInst req={}", JSON.toJSONString(req));
        return supplierResourceInstService.allocateResourceInst(req);
    }


    @ApiOperation(value = "调拨串码查询列表", notes = "查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="getBatch")
    @UserLoginToken
    public ResultVO getBatch(@RequestBody ResourceInstBatchReq req) {
        if (StringUtils.isEmpty(req.getMktResStoreId())) {
            ResultVO.error("仓库为空");
        }
        ResourceAllocateResp resourceAllocateResp = new ResourceAllocateResp();
        Boolean noMerchant = UserContext.getUserOtherMsg() == null || UserContext.getUserOtherMsg().getMerchant() == null;
        if (!UserContext.isUserLogin() || noMerchant) {
            // 没有登陆，直接返回不能查到数据
            return ResultVO.success(resourceAllocateResp);
        }

        String  merchantId = UserContext.getUserOtherMsg().getMerchant().getMerchantId();
        ResultVO<TransferPermissionGetResp> transferPermissionVO = merchantRulesService.getTransferPermission(merchantId);
        log.info("SupplierResourceInstB2BController.getBatch merchantRulesService.getTransferPermission req={}, resp={}", merchantId, JSON.toJSONString(transferPermissionVO));
        if (null == transferPermissionVO || !transferPermissionVO.isSuccess() || null == transferPermissionVO.getResultData()) {
            // 没有权限，直接返回不能查到数据
            return ResultVO.success(resourceAllocateResp);
        }

        TransferPermissionGetResp resp = transferPermissionVO.getResultData();
        List mktResIdList = CollectionUtils.isEmpty(resp.getProductIdList()) ? Lists.newArrayList("-1") : resp.getProductIdList();
        ResultVO<List<ResourceInstListPageResp>> respListVO = supplierResourceInstService.getBatch(req);
        log.info("SupplierResourceInstB2BController.getBatch retailerResourceInstService.getBatch req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(respListVO));
        List<ResourceInstListPageResp> respList = respListVO.getResultData();
        // 有机型权限的串码
        List<ResourceInstListPageResp> resourceInstRespList = respList.stream().filter(s -> mktResIdList.contains(s.getMktResId())).collect(Collectors.toList());
        List<String> resourceInstNbrList = resourceInstRespList.stream().map(ResourceInstListPageResp::getMktResInstNbr).collect(Collectors.toList());

        // 无机型权限的串码
        List<String> getBatchNbrList = respList.stream().map(ResourceInstListPageResp::getMktResInstNbr).collect(Collectors.toList());
        getBatchNbrList.removeAll(resourceInstNbrList);

        // 状态不对的串码,传进来的串码去掉有权限的，去掉没权限的
        List<String> nbrs = req.getMktResInstNbrs();
        nbrs.removeAll(resourceInstNbrList);
        nbrs.removeAll(getBatchNbrList);

        resourceAllocateResp.setResourceInstListRespListPage(resourceInstRespList);
        resourceAllocateResp.setStatusWrongNbrs(nbrs);
        resourceAllocateResp.setNoRightsNbrs(getBatchNbrList);
        return ResultVO.success(resourceAllocateResp);
    }

    @ApiOperation(value = "导出", notes = "导出串码数据")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="nbrExport")
    @UserLoginToken
    public void nbrExport(@RequestBody ResourceInstListPageReq req, HttpServletResponse response) {
        ResultVO<List<AttrSpecDTO>> attrSpecListVO= attrSpecService.queryAttrSpecList(req.getTypeId());
        log.info("SupplierResourceInstB2BController.nbrExport supplierResourceInstService.queryForExport req={}, num={}", JSON.toJSONString(req), JSON.toJSONString(attrSpecListVO));
        ResultVO<List<ResourceInstListPageResp>> dataVO = supplierResourceInstService.queryForExport(req);
        if (!dataVO.isSuccess() || CollectionUtils.isEmpty(dataVO.getResultData())) {
            return;
        }
        if (!attrSpecListVO.isSuccess() || CollectionUtils.isEmpty(attrSpecListVO.getResultData())) {
            return;
        }
        List<ResourceInstListPageResp> list = dataVO.getResultData();
        log.info("SupplierResourceInstB2BController.nbrExport supplierResourceInstService.queryForExport req={}, num={}", JSON.toJSONString(req), list.size());
        List<ExcelTitleName> excelTitleNames = ResourceInstColum.supplierColumn();
        try{
            OutputStream output = response.getOutputStream();
            String fileName = "串码列表";
            ExportCSVUtils.doExport(output, list, excelTitleNames, attrSpecListVO.getResultData(), false);
            response.setContentType("application/ms-txt.numberformat:@");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "max-age=30");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            output.close();
        }catch (Exception e){
            log.error("串码导出失败",e);
        }
    }

    @ApiOperation(value = "串码调拨确认", notes = "串码调拨确认")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="confirmReciveNbr")
    @UserLoginToken
    public ResultVO confirmReciveNbr(@RequestBody ConfirmReciveNbrReqDTO dto) {
        String userId = UserContext.getUserId();
        ConfirmReciveNbrReq req = new ConfirmReciveNbrReq();
        req.setUpdateStaff(userId);
        String confirmRecive = "0";
        BeanUtils.copyProperties(dto, req);
        req.setMerchantId(UserContext.getMerchantId());
        if (confirmRecive.equals(dto.getIsPass())) {
            return supplierResourceInstService.confirmReciveNbr(req);
        }else{
            return supplierResourceInstService.confirmRefuseNbr(req);
        }
    }

    @ApiOperation(value = "校验串码", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="validNbr")
    @UserLoginToken
    public ResultVO<Page<ResourceUploadTempListResp>> validNbr(@RequestBody ResourceInstValidReq req) {
        req.setMerchantId(UserContext.getMerchantId());
        req.setCreateStaff(UserContext.getUserId());
        req.setMerchantType(UserContext.getUserOtherMsg().getMerchant().getMerchantType());
        return supplierResourceInstService.validNbr(req);
    }

    @ApiOperation(value = "新增串码后查询串码列表", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="getResourceInstListForTask")
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstListForTask(@RequestBody ResourceInstListPageReq req) {
        if (StringUtils.isEmpty(req.getMktResStoreIds())) {
            return ResultVO.error("仓库为空");
        }
        return supplierResourceInstService.getResourceInstListForTask(req);
    }

    @ApiOperation(value = "校验串码查询", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="listResourceUploadTemp")
    public ResultVO<Page<ResourceUploadTempListResp>> listResourceUploadTemp(@RequestBody ResourceUploadTempListPageReq req) {
        return supplierResourceInstService.listResourceUploadTemp(req);
    }

    @ApiOperation(value = "(供应商)串码退库", notes = "串码还原在库可用")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="resetResourceInst")
    @UserLoginToken
    public ResultVO resetResourceInst(@RequestBody AdminResourceInstDelReq req) {
        if(CollectionUtils.isEmpty(req.getMktResInstIdList())) {
            return ResultVO.error("id can not be null");
        }
        if(StringUtils.isEmpty(req.getDestStoreId())) {
            return ResultVO.error("mktResStoreId can not be null");
        }
        String userId = UserContext.getUserId();
        req.setUpdateStaff(userId);
        req.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
        req.setEventType(ResourceConst.EVENTTYPE.BUY_BACK.getCode());
        List<String> checkStatusCd = Lists.newArrayList(ResourceConst.STATUSCD.AVAILABLE.getCode());
        req.setCheckStatusCd(checkStatusCd);
        log.info("SupplierResourceInstB2BController.delResourceInst req={}", JSON.toJSONString(req));
        return supplierResourceInstService.resetResourceInst(req);
    }
}
