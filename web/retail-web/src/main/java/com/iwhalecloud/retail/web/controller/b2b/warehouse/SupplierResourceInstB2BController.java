package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.resp.TransferPermissionGetResp;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceAllocateResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;
import com.iwhalecloud.retail.warehouse.service.SupplierResourceInstService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.ConfirmReciveNbrReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.ResourceInstAddReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.ResourceInstAllocateReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.ResourceInstUpdateReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ExcelToNbrUtils;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ResourceInstColum;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;
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

    @ApiOperation(value = "厂商串码管理页面", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="getResourceInstList")
    public ResultVO<Page<ResourceInstListResp>> getResourceInstList(@RequestBody ResourceInstListReq req) {
        if (StringUtils.isEmpty(req.getMktResStoreIds())) {
            ResultVO.error("仓库为空");
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
    public ResultVO<List<String>> delResourceInst(@RequestBody ResourceInstUpdateReqDTO dto) {
        String userId = UserContext.getUserId();
        ResourceInstUpdateReq req = new ResourceInstUpdateReq();
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

    @ApiOperation(value = "串码还原在库可用", notes = "串码还原在库可用")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="resetResourceInst")
    @UserLoginToken
    public ResultVO<List<String>> resetResourceInst(@RequestBody ResourceInstUpdateReqDTO dto) {
        String userId = UserContext.getUserId();
        ResourceInstUpdateReq req = new ResourceInstUpdateReq();
        BeanUtils.copyProperties(dto, req);
        req.setUpdateStaff(userId);
        List<String> checkStatusCd = new ArrayList<String>(1);
        checkStatusCd.add(ResourceConst.STATUSCD.AVAILABLE.getCode());
        req.setEventType(ResourceConst.EVENTTYPE.RECYCLE.getCode());
        req.setCheckStatusCd(checkStatusCd);
        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        req.setMerchantId(UserContext.getMerchantId());
        return supplierResourceInstService.resetResourceInst(req);
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
        req.setSourceType(ResourceConst.SOURCE_TYPE.SUPPLIER.getCode());
        BeanUtils.copyProperties(dto, req);
        req.setStorageType(ResourceConst.STORAGETYPE.SUPPLIER_INPUT.getCode());
        req.setMktResInstType(ResourceConst.MKTResInstType.NONTRANSACTION.getCode());
        req.setMerchantId(UserContext.getMerchantId());
        return supplierResourceInstService.addResourceInst(req);
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
        log.info("RetailerResourceInstB2BController.getBatch merchantRulesService.getTransferPermission req={}, resp={}", merchantId, JSON.toJSONString(transferPermissionVO));
        if (null == transferPermissionVO || !transferPermissionVO.isSuccess() || null == transferPermissionVO.getResultData()) {
            // 没有权限，直接返回不能查到数据
            return ResultVO.success(resourceAllocateResp);
        }

        TransferPermissionGetResp resp = transferPermissionVO.getResultData();
        List mktResIdList = CollectionUtils.isEmpty(resp.getProductIdList()) ? Lists.newArrayList("-1") : resp.getProductIdList();
        ResultVO<List<ResourceInstListResp>> respListVO = supplierResourceInstService.getBatch(req);
        log.info("RetailerResourceInstB2BController.getBatch retailerResourceInstService.getBatch req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(respListVO));
        List<ResourceInstListResp> respList = respListVO.getResultData();
        // 有机型权限的串码
        List<ResourceInstListResp> resourceInstRespList = respList.stream().filter(s -> mktResIdList.contains(s.getMktResId())).collect(Collectors.toList());
        List<String> resourceInstNbrList = resourceInstRespList.stream().map(ResourceInstListResp::getMktResInstNbr).collect(Collectors.toList());

        // 无机型权限的串码
        List<String> getBatchNbrList = respList.stream().map(ResourceInstListResp::getMktResInstNbr).collect(Collectors.toList());
        getBatchNbrList.removeAll(resourceInstNbrList);

        // 状态不对的串码,传进来的串码去掉有权限的，去掉没权限的
        List<String> nbrs = req.getMktResInstNbrs();
        nbrs.removeAll(resourceInstNbrList);
        nbrs.removeAll(getBatchNbrList);

        resourceAllocateResp.setResourceInstListRespList(resourceInstRespList);
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
    public void nbrExport(@RequestBody ResourceInstListReq req, HttpServletResponse response) {
        if (StringUtils.isEmpty(req.getMktResStoreIds())) {
            ResultVO.error("仓库为空");
        }
        ResultVO result = new ResultVO();

        UserDTO userDTO = UserContext.getUser();
        if (userDTO == null) {
            return;
        }
        ResultVO<Page<ResourceInstListResp>> dataVO = supplierResourceInstService.listResourceInst(req);
        if (!dataVO.isSuccess()) {
            return;
        }
        List<ResourceInstListResp> list = dataVO.getResultData().getRecords();
        log.info("SupplierResourceInstB2BController.nbrExport supplierResourceInstService.listResourceInst req={}, resp={}", JSON.toJSONString(req),JSON.toJSONString(list));

        List<Integer> supplierList = Lists.newArrayList(
                SystemConst.USER_FOUNDER_4,
                SystemConst.USER_FOUNDER_5,
                SystemConst.USER_FOUNDER_1,
                SystemConst.USER_FOUNDER_12,
                SystemConst.USER_FOUNDER_24
        );
        Boolean supplierExcel = supplierList.contains(userDTO.getUserFounder());
        List<ExcelTitleName> excelTitleNames = null;
        if (supplierExcel) {
            excelTitleNames = ResourceInstColum.supplierColumn();
        }else{
            excelTitleNames = ResourceInstColum.retailerColumn();
        }

        try{
            //创建Excel
            Workbook workbook = new HSSFWorkbook();
            String fileName = "串码列表";
            ExcelToNbrUtils.builderOrderExcel(workbook, list, excelTitleNames);

            OutputStream output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            workbook.write(output);
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
        if (confirmRecive.equals(dto.getIsPass())) {
            return supplierResourceInstService.confirmReciveNbr(req);
        }else{
            return supplierResourceInstService.confirmRefuseNbr(req);
        }
    }

    @ApiOperation(value = "串码调拨确认", notes = "串码调拨确认")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="test123")
    @UserLoginToken
    public ResultVO test123(@RequestBody ConfirmReciveNbrReqDTO dto) {
        String userId = UserContext.getUserId();
        ConfirmReciveNbrReq req = new ConfirmReciveNbrReq();
        req.setUpdateStaff(userId);
        String confirmRecive = "0";
        BeanUtils.copyProperties(dto, req);
        if (confirmRecive.equals(dto.getIsPass())) {
            return supplierResourceInstService.confirmReciveNbr(req);
        }else{
            return supplierResourceInstService.confirmRefuseNbr(req);
        }
    }

}
