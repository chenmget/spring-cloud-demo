package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.common.ResultCodeEnum;
import com.iwhalecloud.retail.warehouse.dto.ExcelResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.*;
import com.iwhalecloud.retail.warehouse.service.AdminResourceInstService;
import com.iwhalecloud.retail.warehouse.service.ResourceReqDetailService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.controller.b2b.order.service.OrderExportUtil;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.InventoryQueryReq;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.ResourceInstAddReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.response.ExcelToNbrAndCteiResp;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.response.ExcelToNbrAndMacResp;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.response.ResInsExcleImportResp;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ExcelToNbrUtils;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;
import java.util.List;

/**
 * @author he.sw
 * @date 2018/12/24
 */
@RestController
@RequestMapping("/api/b2b/adminResourceInst")
@Slf4j
public class AdminResourceInstB2BController {

	@Reference
    private AdminResourceInstService resourceInstService;

    @Value("${fdfs.suffix.allowUpload}")
    private String allowUploadSuffix;

    @Reference
    private ResourceReqDetailService resourceReqDetailService;

    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;

    @Reference
    private AdminResourceInstService adminResourceInstService;
    
    @ApiOperation(value = "管理员串码管理页面", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="getResourceInstList")
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(@RequestBody ResourceInstListPageReq req) {
        if (StringUtils.isEmpty(req.getMktResStoreIds())) {
            ResultVO.error("仓库为空");
        }
        return resourceInstService.getResourceInstList(req);
    }


    @ApiOperation(value = "串码入库", notes = "添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addResourceInst")
    @UserLoginToken
    public ResultVO<ResourceInstAddResp> addResourceInst(@RequestBody @Valid ResourceInstAddReqDTO dto) {
        if (StringUtils.isEmpty(dto.getMerchantId())) {
            return ResultVO.error("商家不能为空");
        }
        if (StringUtils.isEmpty(dto.getTypeId())) {
            return ResultVO.error("产品类型不能为空");
        }
        if (StringUtils.isEmpty(dto.getMktResId())) {
            return ResultVO.error("产品不能为空");
        }
        String userId = UserContext.getUserId();
        ResourceInstAddReq req = new ResourceInstAddReq();
        req.setCreateStaff(userId);
        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        req.setSourceType(ResourceConst.SOURCE_TYPE.MERCHANT.getCode());
        req.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        req.setStorageType(ResourceConst.STORAGETYPE.MANUAL_ENTRY.getCode());
        BeanUtils.copyProperties(dto, req);
        if (StringUtils.isEmpty(req.getMktResInstType())) {
            req.setMktResInstType(ResourceConst.MKTResInstType.TRANSACTION.getCode());
        }
        return resourceInstService.addResourceInst(req);
    }
    
    @ApiOperation(value = "删除串码", notes = "传入串码集合删除")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="delResourceInst")
    @UserLoginToken
    public ResultVO delResourceInst(@RequestBody @Valid ResourceInstsGetByIdListAndStoreIdReq delReq) {

        String userId = UserContext.getUserId();
        AdminResourceInstDelReq req = new AdminResourceInstDelReq();
        req.setUpdateStaff(userId);
        req.setMktResInstIdList(delReq.getMktResInstIdList());
        req.setDestStoreId(delReq.getMktResStoreId());
        req.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
        req.setEventType(ResourceConst.EVENTTYPE.CANCEL.getCode());

        // 只有可用状态的串码才能删除
        List<String> checkStatusCd = Lists.newArrayList(
                ResourceConst.STATUSCD.DELETED.getCode(),
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.SALED.getCode());
        req.setCheckStatusCd(checkStatusCd);
        log.info("AdminResourceInstB2BController.delResourceInst req={}", JSON.toJSONString(req));
        return resourceInstService.updateResourceInstByIds(req);
    }

    @ApiOperation(value = "根据batchId删除串码", notes = "根据batchId删除串码")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="delResourceInstByBatchId")
    @UserLoginToken
    public ResultVO delResourceInstByBatchId(@RequestParam(value="mktResUploadBatch") String mktResUploadBatch) {
        if(StringUtils.isEmpty(mktResUploadBatch)) {
            return ResultVO.error("mktResUploadBatch can not be null");
        }
        String userId = UserContext.getUserId();
        log.info("AdminResourceInstB2BController.delResourceInstByBatchId mktResUploadBatch={}", mktResUploadBatch);
        return resourceInstService.delResourceInstByBatchId(mktResUploadBatch,userId);
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
        List<String> checkStatusCd = Lists.newArrayList(ResourceConst.STATUSCD.DELETED.getCode(),
                ResourceConst.STATUSCD.SALED.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.EXCHANGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode());
        req.setCheckStatusCd(checkStatusCd);
        req.setCheckStatusCd(checkStatusCd);
        log.info("AdminResourceInstB2BController.delResourceInst req={}", JSON.toJSONString(req));
        return adminResourceInstService.resetResourceInst(req);
    }

    @ApiOperation(value = "上传串码文件", notes = "支持xlsx、xls格式")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/uploadExcel",headers = "content-type=multipart/form-data" ,method = RequestMethod.POST)
    public ResultVO uploadExcel(@RequestParam("file") MultipartFile file) {

        String suffix = StringUtils.getFilenameExtension(file.getOriginalFilename());
        //如果不在允许范围内的附件后缀直接抛出错误
        if (allowUploadSuffix.indexOf(suffix) <= -1) {
            return ResultVO.errorEnum(ResultCodeEnum.FORBID_UPLOAD_ERROR);
        }

        ResultVO resultVO = new ResultVO();
        InputStream is = null;
        try {
            is = file.getInputStream();
            List<ResInsExcleImportResp> data = ExcelToNbrUtils.getData(is);
            resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultData(data);
        } catch (Exception e) {
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg(e.getMessage());
            log.error("excel解析失败",e);
        }
        return resultVO;
    }
    
    @ApiOperation(value = "串码补录", notes = "补录操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="inventoryChange")
    @UserLoginToken
    public ResultVO inventoryChange(@RequestBody InventoryQueryReq dto) {
        if (StringUtils.isEmpty(dto.getDeviceId())) {
            return ResultVO.error("串码不能为空");
        }
        
        InventoryChangeResp inventoryChangeResp = new InventoryChangeResp();
        String userName = UserContext.getUser().getUserName();
        String params = "city_code="+dto.getCode();
        
        InventoryChangeReq req = new InventoryChangeReq ();
        req.setDeviceId(dto.getDeviceId());
        req.setUserName(userName);
        req.setCode("ITMS_ADD");
        req.setParams(params);
        //需要调用itms接口补录，需要提供。
        ResultVO inventoryChange = resourceInstService.inventoryChange(req);
        return inventoryChange;
//        inventoryChange.getResultMsg();
//        return ResultVO.success("操作成功");
    }


    @ApiOperation(value = "上传串码文件", notes = "支持xlsx、xls格式")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/getNbrAndMac",headers = "content-type=multipart/form-data" ,method = RequestMethod.POST)
    public ResultVO getNbrAndMac(@RequestParam("file") MultipartFile file) {

        String suffix = StringUtils.getFilenameExtension(file.getOriginalFilename());
        //如果不在允许范围内的附件后缀直接抛出错误
        if (allowUploadSuffix.indexOf(suffix) <= -1) {
            return ResultVO.errorEnum(ResultCodeEnum.FORBID_UPLOAD_ERROR);
        }

        ResultVO resultVO = new ResultVO();
        InputStream is = null;
        try {
            is = file.getInputStream();
            List<ExcelToNbrAndMacResp> data = ExcelToNbrUtils.getNbrAndMac(is);
            resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultData(data);
        } catch (Exception e) {
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg(e.getMessage());
            log.error("excel解析失败",e);
        }
        return resultVO;
    }

    @ApiOperation(value = "上传串码文件", notes = "支持xlsx、xls格式")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/getNbrAndCtei",headers = "content-type=multipart/form-data" ,method = RequestMethod.POST)
    public ResultVO getNbrAndCtei(@RequestParam("file") MultipartFile file) {

        String suffix = StringUtils.getFilenameExtension(file.getOriginalFilename());
        //如果不在允许范围内的附件后缀直接抛出错误
        if (allowUploadSuffix.indexOf(suffix) <= -1) {
            return ResultVO.errorEnum(ResultCodeEnum.FORBID_UPLOAD_ERROR);
        }

        ResultVO resultVO = new ResultVO();
        InputStream is = null;
        try {
            is = file.getInputStream();
            List<ExcelToNbrAndCteiResp> data = ExcelToNbrUtils.getNbrAndCtei(is);
            resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultData(data);
        } catch (Exception e) {
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg(e.getMessage());
            log.error("excel解析失败",e);
        }
        return resultVO;
    }


    @ApiOperation(value = "查询串码申请明细分页列表", notes = "管理员后台审核厂商串码的列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="listResourceRequestDetailPage")
    @UserLoginToken
    public ResultVO<Page<ResourceReqDetailPageResp>> listResourceRequestPage(ResourceReqDetailQueryReq req) {
        req.setUserId(UserContext.getUser().getUserId());
        return resourceReqDetailService.listResourceRequestDetailPage(req);
    }

    @ApiOperation(value = "导出串码明细", notes = "导出串码数据")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "exportNbrDetail")
    @UserLoginToken
    public void exportNbrDetail(@RequestBody ResourceReqDetailQueryReq req, HttpServletResponse response) {
        req.setUserId(UserContext.getUser().getUserId());
        req.setSearchCount(false);
        ResultVO<Page<ResourceReqDetailPageResp>> resultVO = resourceReqDetailService.listResourceRequestDetailPage(req);
        List<ResourceReqDetailPageResp> data = resultVO.getResultData().getRecords();
        log.info("AdminResourceInstB2BController.exportNbrDetail resourceReqDetailService.listResourceRequestDetailPage req={}, resp={}", JSON.toJSONString(req),JSON.toJSONString(data.size()));
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
                OrderExportUtil.getResReqDetail(), "串码明细");
        deliveryGoodsResNberExcel.exportExcel("导出串码明细", workbook, response);
    }

    @ApiOperation(value = "导入审核的串码文件", notes = "支持xlsx、xls格式")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/uploadNbrDetail", headers = "content-type=multipart/form-data", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<String> uploadNbrDetail(@RequestParam("file") MultipartFile file) {

        String suffix = StringUtils.getFilenameExtension(file.getOriginalFilename());
        //如果不在允许范围内的附件后缀直接抛出错误
        if (allowUploadSuffix.indexOf(suffix) <= -1) {
            return ResultVO.errorEnum(ResultCodeEnum.FORBID_UPLOAD_ERROR);
        }
        InputStream is = null;
        try {
            is = file.getInputStream();
            List<ExcelResourceReqDetailDTO> data = ExcelToNbrUtils.getNbrDetailData(is);
            UserDTO userDTO = UserContext.getUser();
            String userId = userDTO.getUserId();
            return resourceInstService.uploadNbrDetail(data, userId);
       }
        catch (Exception e) {
            log.error("excel解析失败", e);
            return ResultVO.error("excel解析失败");
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @ApiOperation(value = "获取串码审核临时记录", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "listResourceUploadTemp")
    public ResultVO<Page<ResourceReqDetailPageResp>> listResourceUploadTemp(ResourceUploadTempListPageReq req) {
        return resourceInstService.listResourceUploadTemp(req);
    }

    @ApiOperation(value = "查询串码审核临时记录的成功失败次数", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "countResourceUploadTemp")
    public ResultVO<ResourceUploadTempCountResp> countResourceUploadTemp(ResourceUploadTempDelReq req) {
        return resourceInstService.countResourceUploadTemp(req);
    }

    @ApiOperation(value = "导出串码明细", notes = "导出串码数据")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "exportResourceUploadTemp")
    public void exportResourceUploadTemp(@RequestBody ResourceUploadTempListPageReq req, HttpServletResponse response) {
        ResultVO<Page<ResourceReqDetailPageResp>> resultVO = resourceInstService.listResourceUploadTemp(req);
        List<ResourceReqDetailPageResp> data = resultVO.getResultData().getRecords();
        log.info("ResourceReqDetailB2BController.nbrDetailExport resourceReqDetailService.listResourceRequestDetailPage req={}, resp={}", JSON.toJSONString(req),JSON.toJSONString(data));
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data, OrderExportUtil.getResourceUploadTemp(), "串码");
        deliveryGoodsResNberExcel.exportExcel("导入失败的串码", workbook, response);
    }

    @ApiOperation(value = "提交导入excel的审批", notes = "提交串码审核的excel")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/submitNbrAudit", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<String> submitNbrAudit(@RequestBody ResourceUploadTempListPageReq req) {
        req.setUpdateStaff(UserContext.getUserId());
        return resourceInstService.submitNbrAudit(req);
    }

    @ApiOperation(value = "批量审核串码", notes = "批量审核串码")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/batchAuditNbr", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<String> batchAuditNbr(@RequestBody ResourceInstCheckReq req) {
        req.setUpdateStaff(UserContext.getUserId());
        req.setUpdateStaffName(UserContext.getUser().getUserName());
        return resourceInstService.batchAuditNbr(req);
    }

    @ApiOperation(value = "验证提交串码审核是否执行完毕", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "validBatchAuditNbr")
    public ResultVO<Boolean> validBatchAuditNbr() {
        return resourceInstService.validBatchAuditNbr();
    }

    @ApiOperation(value = "导入待删除的串码文件", notes = "支持xlsx、xls格式")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/uploadDelResourceInst", headers = "content-type=multipart/form-data", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<ResourceUploadTempCountResp> uploadDelResourceInst(@RequestParam("file") MultipartFile file) {

        String suffix = StringUtils.getFilenameExtension(file.getOriginalFilename());
        //如果不在允许范围内的附件后缀直接抛出错误
        if (allowUploadSuffix.indexOf(suffix) <= -1) {
            return ResultVO.errorEnum(ResultCodeEnum.FORBID_UPLOAD_ERROR);
        }
        try (InputStream is = file.getInputStream()) {
            List<ExcelResourceReqDetailDTO> data = ExcelToNbrUtils.getNbrDetailData(is);
            String userId = UserContext.getUser().getUserId();
            return resourceInstService.uploadDelResourceInst(data, userId);
        } catch (Exception e) {
            log.error("excel解析失败", e);
            return ResultVO.error("excel解析失败");
        }
    }

    @ApiOperation(value = "获取待删除的串码临时记录", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "listDelResourceInstTemp")
    public ResultVO<Page<ResourceInstListPageResp>> listDelResourceInstTemp(ResourceUploadTempListPageReq req) {
        return resourceInstService.listDelResourceInstTemp(req);
    }

    @ApiOperation(value = "导出待删除的串码临时记录", notes = "导出串码数据")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "exportDelResourceInstTemp")
    @UserLoginToken
    public void exportDelResourceInstTemp(@RequestBody ResourceUploadTempListPageReq req, HttpServletResponse response) {
        //导出有异常数据
        req.setResult(ResourceConst.CONSTANT_YES);
        ResultVO<Page<ResourceInstListPageResp>> resultVO = resourceInstService.listDelResourceInstTemp(req);
        List<ResourceInstListPageResp> data = resultVO.getResultData().getRecords();
        log.info("AdminResourceInstB2BController.exportDelResourceInstTemp listDelResourceInstTemp req={}", JSON.toJSONString(req));
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
                OrderExportUtil.getDelResourceInstTemp(), "导入删除串码失败列表");
        deliveryGoodsResNberExcel.exportExcel("导入删除串码失败列表", workbook, response);
    }
}
