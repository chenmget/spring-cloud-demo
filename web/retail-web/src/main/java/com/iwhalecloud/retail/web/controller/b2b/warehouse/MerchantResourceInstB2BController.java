package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ProductDTO;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.service.MerchantResourceInstService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.ResourceInstAddReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.ResourceInstUpdateReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.response.ResInsExcleImportResp;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author he.sw
 * @date 2018/12/24
 */
@RestController
@RequestMapping("/api/b2b/merchantResourceInst")
@Slf4j
public class MerchantResourceInstB2BController {

    @Reference
    private MerchantResourceInstService resourceInstService;

    @Value("${fdfs.suffix.allowUpload}")
    private String allowUploadSuffix;

    @ApiOperation(value = "厂商串码管理页面", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="getResourceInstList")
    @UserLoginToken
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(@RequestBody ResourceInstListPageReq req) {
        if (StringUtils.isEmpty(req.getMktResStoreIds()) && null == req.getMktResInstNbr()) {
            return ResultVO.error("仓库和串码不能同时为空");
        }
        return resourceInstService.getResourceInstList(req);
    }

    @ApiOperation(value = "删除串码", notes = "传入串码集合删除")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="delResourceInst")
    @UserLoginToken
    public ResultVO delResourceInst(@RequestBody ResourceInstUpdateReqDTO dto) {

        String userId = UserContext.getUserId();
        ResourceInstUpdateReq req = new ResourceInstUpdateReq();
        BeanUtils.copyProperties(dto, req);
        req.setUpdateStaff(userId);
        req.setMerchantId(UserContext.getMerchantId());
        req.setEventType(ResourceConst.EVENTTYPE.CANCEL.getCode());
        req.setCheckStatusCd(Lists.newArrayList(
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONED.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.SALED.getCode()));
        req.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
        return resourceInstService.delResourceInst(req);
    }

    @ApiOperation(value = "串码入库", notes = "添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addResourceInst")
    @UserLoginToken
    public ResultVO<ResourceInstAddResp> addResourceInst(@RequestBody ResourceInstAddReqDTO dto) {
        String userId = UserContext.getUserId();
        ResourceInstAddReq req = new ResourceInstAddReq();
        req.setCreateStaff(userId);
        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        req.setSourceType(ResourceConst.SOURCE_TYPE.MERCHANT.getCode());
        req.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        req.setStorageType(ResourceConst.STORAGETYPE.VENDOR_INPUT.getCode());
        BeanUtils.copyProperties(dto, req);
        req.setMerchantId(UserContext.getMerchantId());
        return resourceInstService.addResourceInst(req);

    }

    @ApiOperation(value = "查询产品", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="selectProduct")
    public ResultVO<Page<ProductDTO>> selectProduct(  @RequestParam(value = "merchantId", required = false) String merchantId,
                                                      @RequestParam(value = "merchantType", required = false) String merchantType,
                                                      @RequestParam(value = "sourceType", required = false) String sourceType,
                                                      @RequestParam(value = "catId", required = false) String catId,
                                                      @RequestParam(value = "unitName", required = false) String unitName,
                                                      @RequestParam(value = "sn", required = false) String sn,
                                                      @RequestParam(value = "unitType", required = false) String unitType) {
        PageProductReq req = new PageProductReq();
        req.setMerchantId(merchantId);
        req.setMerchantType(merchantType);
        req.setSourceType(sourceType);
        req.setCatId(catId);
        req.setUnitName(unitName);
        req.setSn(sn);
        req.setUnitType(unitType);
        return resourceInstService.selectProduct(req);
    }
    @ApiOperation(value = "多串码查询附件上传", notes = "支持xlsx、xls格式")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/uploadNbrsExcel", headers = "content-type=multipart/form-data" ,method = RequestMethod.POST)
    public ResultVO uploadExcel(@RequestParam("file") MultipartFile file) {
        String suffix = StringUtils.getFilenameExtension(file.getOriginalFilename());
        //如果不在允许范围内的附件后缀直接抛出错误
        if (allowUploadSuffix.indexOf(suffix) <= -1) {
            return ResultVO.errorEnum(com.iwhalecloud.retail.oms.common.ResultCodeEnum.FORBID_UPLOAD_ERROR);
        }
        ResultVO resultVO = new ResultVO();
        InputStream is = null;
        try {
            is = file.getInputStream();
            List<ResInsExcleImportResp> data = ExcelToNbrUtils.getData(is);
            resultVO.setResultCode(com.iwhalecloud.retail.oms.common.ResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultData(data);
        } catch (Exception e) {
            resultVO.setResultCode(com.iwhalecloud.retail.oms.common.ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg(e.getMessage());
            log.error("excel解析失败",e);
        }
        return resultVO;
    }

    @ApiOperation(value = "导出", notes = "导出串码数据")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="nbrExport")
    @UserLoginToken
    public void nbrExport(@RequestBody ResourceInstListPageReq req, HttpServletResponse response) {
        ResultVO<Page<ResourceInstListPageResp>> dataVO = resourceInstService.getResourceInstList(req);
        if (!dataVO.isSuccess()) {
            return;
        }
        List<ResourceInstListPageResp> list = dataVO.getResultData().getRecords();
        log.info("SupplierResourceInstB2BController.nbrExport supplierResourceInstService.listResourceInst req={}, resp={}", JSON.toJSONString(req),JSON.toJSONString(list));
        List<ExcelTitleName> excelTitleNames = ResourceInstColum.merchantColumn();
        try{
            //创建Excel
            Workbook workbook = new HSSFWorkbook();
            String fileName = "串码列表";
            ExcelToNbrUtils.builderOrderExcel(workbook, list, excelTitleNames, false);

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
        return resourceInstService.validNbr(req);
    }

    @ApiOperation(value = "校验串码查询", notes = "查询操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="listResourceUploadTemp")
    public ResultVO<Page<ResourceUploadTempListResp>> listResourceUploadTemp(@RequestBody ResourceUploadTempListPageReq req) {
        return resourceInstService.listResourceUploadTemp(req);
    }

    @ApiOperation(value = "删除串码", notes = "传入串码集合删除")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="exceutorDelNbr")
    public ResultVO exceutorDelNbr(@RequestBody ResourceUploadTempDelReq req) {
        return resourceInstService.exceutorDelNbr(req);
    }

    @ApiOperation(value = "多线程查询串码", notes = "导出录入失败串码")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="exceutorQueryTempNbr")
    public ResultVO exceutorQueryTempNbr(@RequestBody ResourceUploadTempDelReq req) {
        List<ResourceUploadTempListResp> pageResultVO = resourceInstService.exceutorQueryTempNbr(req);
        return ResultVO.success(pageResultVO);
    }
}
