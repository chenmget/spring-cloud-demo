package com.iwhalecloud.retail.web.controller.b2b.cgmanage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductsPageReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductPageResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.UpdateCorporationPriceReq;
import com.iwhalecloud.retail.order2b.service.GovernmentPriceManageService;
import com.iwhalecloud.retail.order2b.service.JyPurApplyService;
import com.iwhalecloud.retail.order2b.service.PurApplyService;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.report.dto.response.ReportCodeStatementsResp;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.response.ResInsExcleImportResp;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ExcelToNbrUtils;
import com.iwhalecloud.retail.web.interceptor.UserContext;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liweisong
 * @date 2019-04-16
 * 采购申请管理平台
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/cgmanage/price")
public class GovernmentPriceManageController extends BaseController {

	@Reference
    private ProductService productService;
	
	@Reference
    private MerchantRulesService merchantRulesService;
	
	@Reference
    private PurApplyService purApplyService;
	
	@Value("${fdfs.suffix.allowUpload}")
    private String allowUploadSuffix;
	
	@Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;
	
	@ApiOperation(value = "导出分页查询产品", notes = "导出条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/dcSearchPriceApply")
    @UserLoginToken
    public void dcSearchPriceApply(@RequestBody ProductsPageReq req, HttpServletResponse response) {
		req.setPageNo(1);
        //数据量控制在1万条
        req.setPageSize(60000);
        Integer userFounder = UserContext.getUser().getUserFounder();
        
            // 管理员查看所有
        ResultVO<Page<ProductPageResp>> productPageRespPage = productService.selectPageProductAdminDc(req);

        List<ProductPageResp> data = productPageRespPage.getResultData().getRecords();
        
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("typeName", "产品类型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
//        orderMap.add(new ExcelTitleName("unitTypeName", "产品型号"));
        orderMap.add(new ExcelTitleName("unitType", "产品型号"));
//        orderMap.add(new ExcelTitleName("color", "颜色"));
//        orderMap.add(new ExcelTitleName("memory", "内存版本"));
        orderMap.add(new ExcelTitleName("attrValue2", "颜色"));
        orderMap.add(new ExcelTitleName("attrValue3", "内存版本"));
        orderMap.add(new ExcelTitleName("sn", "营销资源编码"));
        orderMap.add(new ExcelTitleName("cost", "零售价格"));
        orderMap.add(new ExcelTitleName("corporationPrice", "政企价格"));
        orderMap.add(new ExcelTitleName("purchaseType", "采购类型"));
        orderMap.add(new ExcelTitleName("effDate", "产品生效期"));
        orderMap.add(new ExcelTitleName("expDate", "产品失效期"));
        orderMap.add(new ExcelTitleName("manufacturerName", "所属厂家"));
        
      //创建Excel
        Workbook workbook = new HSSFWorkbook();
//      //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
        		orderMap, "政企价格管理");
        deliveryGoodsResNberExcel.exportExcel("政企价格管理",workbook,response);
      
    }
	
	
	@ApiOperation(value = "修改价格操作", notes = "修改价格操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/updatePrice")
    public ResultVO updatePrice(@RequestBody UpdateCorporationPriceReq req) {
		return purApplyService.updatePrice(req);
    }
	
	@ApiOperation(value = "上传串码文件", notes = "支持xlsx、xls格式")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/uploadPriceExcel",headers = "content-type=multipart/form-data" ,method = RequestMethod.POST)
    public ResultVO uploadPriceExcel(@RequestParam("file") MultipartFile file) {

        String suffix = StringUtils.getFilenameExtension(file.getOriginalFilename());
        //如果不在允许范围内的附件后缀直接抛出错误
        if (allowUploadSuffix.indexOf(suffix) <= -1) {
            return ResultVO.errorEnum(ResultCodeEnum.FORBID_UPLOAD_ERROR);
        }

        ResultVO resultVO = new ResultVO();
        InputStream is = null;
        try {
            is = file.getInputStream();
//            List<ResInsExcleImportResp> data = ExcelToNbrUtils.getData(is);
            List<UpdateCorporationPriceReq> data = ExcelToNbrUtils.getPriceData(is);
            resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultData(data);
        } catch (Exception e) {
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg(e.getMessage());
            log.error("excel解析失败",e);
        }
        return resultVO;
    }
	
}
