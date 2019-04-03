package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.dto.resp.TransferPermissionGetResp;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceAllocateResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;
import com.iwhalecloud.retail.warehouse.service.RetailerResourceInstService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.*;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ExcelToNbrUtils;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ResourceInstColum;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author he.sw
 * @date 2018/12/24
 * @desc 零售商串码管理
 */
@RestController
@RequestMapping("/api/b2b/retailerMerchantResourceInst")
@Slf4j
public class RetailerResourceInstB2BController {

	@Reference
    private RetailerResourceInstService retailerResourceInstService;
	@Reference
    private MerchantRulesService merchantRulesService;

    @Reference
    private MerchantService merchantService;

    @Reference
    private ProductService productService;

    @ApiOperation(value = "零售商串码管理页面", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="listResourceInst")
    public ResultVO<Page<ResourceInstListResp>> listResourceInst(@RequestBody ResourceInstListReq req) {
        if (org.springframework.util.StringUtils.isEmpty(req.getMktResStoreIds())) {
            ResultVO.error("仓库为空");
        }
	    return retailerResourceInstService.listResourceInst(req);
    }

    @ApiOperation(value = "绿色通道串码导入", notes = "绿色通道串码导入")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addResourceInstByGreenChannel")
    @UserLoginToken
    public ResultVO<List<String>> addResourceInstByGreenChannel(@RequestBody ResourceInstAddReqDTO dto) {
        String userId = UserContext.getUserId();
        ResourceInstAddReq req = new ResourceInstAddReq();
        BeanUtils.copyProperties(dto, req);
        req.setCreateStaff(userId);
        req.setApplyUserName(UserContext.getUser().getUserName());
        req.setSourceType(ResourceConst.SOURCE_TYPE.RETAILER.getCode());
        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        return retailerResourceInstService.addResourceInstByGreenChannel(req);
    }

    @ApiOperation(value = "领用串码", notes = "领用串码")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="pickResourceInst")
    @UserLoginToken
    public ResultVO<Integer> pickResourceInst(@RequestBody ResourceInstPickupReqDTO dto) {
        String userId = UserContext.getUserId();
        ResourceInstPickupReq req = new ResourceInstPickupReq();
        req.setUpdateStaff(userId);
        BeanUtils.copyProperties(dto, req);
        return retailerResourceInstService.pickResourceInst(req);
    }

    @ApiOperation(value = "删除串码", notes = "传入串码集合删除")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="delResourceInst")
    @UserLoginToken
    public ResultVO<Integer> delResourceInst(@RequestBody ResourceInstUpdateReqDTO dto) {
        String userId = UserContext.getUserId();
        ResourceInstUpdateReq req = new ResourceInstUpdateReq();
        BeanUtils.copyProperties(dto, req);
        req.setUpdateStaff(userId);
        List<String> checkStatusCd = new ArrayList<String>(1);
        checkStatusCd.add(ResourceConst.STATUSCD.DELETED.getCode());
        req.setCheckStatusCd(checkStatusCd);
        req.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
        req.setMerchantId(UserContext.getMerchantId());
        req.setEventType(ResourceConst.EVENTTYPE.CANCEL.getCode());
        return retailerResourceInstService.delResourceInst(req);
    }

    @ApiOperation(value = "调拨串码查询", notes = "查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="getBatch")
    @UserLoginToken
    public ResultVO getBatch(@RequestBody ResourceInstBatchReq req) {
        if (org.springframework.util.StringUtils.isEmpty(req.getMktResStoreId())) {
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
        ResultVO<List<ResourceInstListResp>> respListVO = retailerResourceInstService.getBatch(req);
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

    @ApiOperation(value = "调拨串码", notes = "调拨串码")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @UserLoginToken
    @PostMapping(value="allocateResourceInst")
    public ResultVO allocateResourceInst(@RequestBody ResourceInstAllocateReqDTO dto) {
        String userId = UserContext.getUserId();
        if (CollectionUtils.isEmpty(dto.getMktResInstNbrs()) || dto.getMktResInstNbrs().size() > 5) {
            return ResultVO.error("调拨数目不对");
        }
        RetailerResourceInstAllocateReq req = new RetailerResourceInstAllocateReq();
        BeanUtils.copyProperties(dto, req);
        req.setCreateStaff(userId);
        log.info("RetailerResourceInstB2BController.allocateResourceInst req={}", JSON.toJSONString(req));
        return retailerResourceInstService.allocateResourceInst(req);
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
            return retailerResourceInstService.confirmReciveNbr(req);
        }else{
            return retailerResourceInstService.confirmRefuseNbr(req);
        }
    }

    @ApiOperation(value = "导出", notes = "导出串码数据")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="nbrExport")
    @UserLoginToken
    public void nbrExport(@RequestBody ResourceInstListReq req, HttpServletResponse response) {
        ResultVO<Page<ResourceInstListResp>> dataVO = retailerResourceInstService.listResourceInst(req);
        if (!dataVO.isSuccess() || dataVO.getResultData() == null) {
            return;
        }
        List<ResourceInstListResp> list = dataVO.getResultData().getRecords();
        log.info("RetailerResourceInstB2BController.nbrExport retailerResourceInstService.listResourceInst req={}, resp={}", JSON.toJSONString(req),JSON.toJSONString(list));
        List<ExcelTitleName> excelTitleNames = ResourceInstColum.retailerColumn();
        OutputStream output = null;
        try{
            //创建Excel
            Workbook workbook = new HSSFWorkbook();
            String fileName = "串码列表";
            ExcelToNbrUtils.builderOrderExcel(workbook, list, excelTitleNames, true);
            output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            workbook.write(output);
        }catch (Exception e){
            log.error("串码导出失败",e);
        } finally {
            try {
                if (null != output) {
                    output.close();
                }
            } catch (Exception e) {
                log.error("error:", e);
            }
        }
    }

    @ApiOperation(value = "导出全量的数据", notes = "导出串码数据")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="nbrExportAll")
    @UserLoginToken
    public void nbrExportAll(@RequestBody ResourceInstListReq req, HttpServletResponse response) {
        ResultVO<List<ResourceInstListResp>> dataVO = retailerResourceInstService.getExportResourceInstList(req);
        if (!dataVO.isSuccess() || dataVO.getResultData() == null) {
            return;
        }
        List<ResourceInstListResp> list = dataVO.getResultData();
        log.info("RetailerResourceInstB2BController.nbrExportAll retailerResourceInstService.getExportResourceInstList req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(list));
        List<ExcelTitleName> excelTitleNames = ResourceInstColum.retailerColumn();
        OutputStream output = null;
        try {
            Workbook workbook = new HSSFWorkbook();
            String fileName = "串码列表";
            ExcelToNbrUtils.builderOrderExcel(workbook, list, excelTitleNames, true);
            output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            workbook.write(output);
        } catch (Exception e) {
            log.error("串码导出失败", e);
        } finally {
            try {
                if (null != output){
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @ApiOperation(value = "绿色通道录入串码机型权限校验", notes = "有权限返回true,无返回false")
    @ApiImplicitParam(name = "产品id", value = "mktResId", paramType = "query", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="greenChannelValid")
    @UserLoginToken
    public ResultVO greenChannelValid(@RequestParam String mktResId) {
        Boolean noMerchant = UserContext.getUserOtherMsg() == null || UserContext.getUserOtherMsg().getMerchant() == null;
        if (!UserContext.isUserLogin() || noMerchant) {
            // 没有登陆，返回false
            return ResultVO.success(false);
        }

        String  merchantId = UserContext.getUserOtherMsg().getMerchant().getMerchantId();
        ResultVO<List<String>> transferPermissionVO = merchantRulesService.getGreenChannelPermission(merchantId);
        log.info("RetailerResourceInstB2BController.greenChannelValid merchantRulesService.getGreenChannelPermission req={}, resp={}", merchantId, JSON.toJSONString(transferPermissionVO));
        if (null == transferPermissionVO || !transferPermissionVO.isSuccess() || CollectionUtils.isEmpty(transferPermissionVO.getResultData())) {
            // 没有权限，返回false
            return ResultVO.success(false);
        }

        List<String> productIdList = transferPermissionVO.getResultData();
        if (productIdList.contains(mktResId)) {
            return ResultVO.success(true);
        }
        return ResultVO.success(false);
    }
}
