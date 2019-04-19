package com.iwhalecloud.retail.web.controller.report;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.response.ProductListAllResp;
import com.iwhalecloud.retail.report.dto.response.ReportDeSaleDaoResq;
import com.iwhalecloud.retail.report.service.ReportService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.controller.b2b.order.service.OrderExportUtil;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ExcelToNbrUtils;
import com.iwhalecloud.retail.web.interceptor.UserContext;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;


/**
 * @author zwl
 * @date 2018-11-09
 * 报表
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/reportData")
public class ReportDataController extends BaseController {

    @Reference
    private ReportService reportService;
    
    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;

	@ApiOperation(value = "查询报表", notes = "查询报表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/getReportDeSaleList")
    public ResultVO<Page<ReportDeSaleDaoResq>> getReportDeSaleList(@RequestBody ReportDeSaleDaoReq req) {
		String userType=req.getUserType();
		if(userType!=null&&!userType.equals("")&&userType.equals("4")){//供应商
			String MerchantCode=UserContext.getUser().getRelCode();
			req.setMerchantCode(MerchantCode);
		}
		if(userType!=null&&!userType.equals("")&&userType.equals("2")){//地市管理员
			String lanId=UserContext.getUser().getRegionId();
			req.setLanId(lanId);
		}
		if(!"1".equals(userType) && !"2".equals(userType) && !"4".equals(userType)){
			req.setLanId("999");
		}
		String lanId = req.getLanId();
		if("430100".equals(lanId)){
			req.setLanId("731");
		}else if("430200".equals(lanId)){
			req.setLanId("733");
		}else if("430300".equals(lanId)){
			req.setLanId("732");
		}else if("430400".equals(lanId)){
			req.setLanId("734");
		}else if("430500".equals(lanId)){
			req.setLanId("739");
		}else if("430600".equals(lanId)){
			req.setLanId("730");
		}else if("430700".equals(lanId)){
			req.setLanId("736");
		}else if("430800".equals(lanId)){
			req.setLanId("744");
		}else if("430900".equals(lanId)){
			req.setLanId("737");
		}else if("431000".equals(lanId)){
			req.setLanId("735");
		}else if("431300".equals(lanId)){//娄底
			req.setLanId("738");
		}else if("433100".equals(lanId)){//湘西土家族苗族自治州
			req.setLanId("743");
		}
        return reportService.getReportDeSaleList(req);
    }
	
	@ApiOperation(value = "根据品牌查机型", notes = "根据品牌查机型")
	@ApiImplicitParam(name = "brandId", value = "brandId", paramType = "query", required = false, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
	@GetMapping(value="/listProducts")
    public ResultVO<List<ProductListAllResp>> listPorducts(@RequestParam String brandId) {
		if(brandId==null){
			brandId="";
		}
        return reportService.listProductAll(brandId);

    }
	
	  /**
     * 导出订单
     */
    @ApiOperation(value = "导出", notes = "导出串码数据")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/reportDeSaleExport")
    public ResultVO reportDeSaleExport(@RequestBody ReportDeSaleDaoReq req) {
    	String userType=req.getUserType();
		if(userType!=null&&!userType.equals("")&&userType.equals("4")){//供应商
			String MerchantCode=UserContext.getUser().getRelCode();
			req.setMerchantCode(MerchantCode);
		}
		if(userType!=null&&!userType.equals("")&&userType.equals("2")){//地市管理员
			String lanId=UserContext.getUser().getRegionId();
			req.setLanId(lanId);
		}
		if(!"1".equals(userType) && !"2".equals(userType) && !"4".equals(userType)){
			req.setLanId("999");
		}
		String lanId = req.getLanId();
		if("430100".equals(lanId)){
			req.setLanId("731");
		}else if("430200".equals(lanId)){
			req.setLanId("733");
		}else if("430300".equals(lanId)){
			req.setLanId("732");
		}else if("430400".equals(lanId)){
			req.setLanId("734");
		}else if("430500".equals(lanId)){
			req.setLanId("739");
		}else if("430600".equals(lanId)){
			req.setLanId("730");
		}else if("430700".equals(lanId)){
			req.setLanId("736");
		}else if("430800".equals(lanId)){
			req.setLanId("744");
		}else if("430900".equals(lanId)){
			req.setLanId("737");
		}else if("431000".equals(lanId)){
			req.setLanId("735");
		}else if("431300".equals(lanId)){//娄底
			req.setLanId("738");
		}else if("433100".equals(lanId)){//湘西土家族苗族自治州
			req.setLanId("743");
		}
        ResultVO result = new ResultVO();
        ResultVO<List<ReportDeSaleDaoResq>> resultVO = reportService.reportDeSaleExport(req);
        if (!resultVO.isSuccess()) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultData("失败：" + resultVO.getResultMsg());
            return result;
        }
        List<ReportDeSaleDaoResq> data = resultVO.getResultData();
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("supplierName", "地包商名称"));
        orderMap.add(new ExcelTitleName("supplierCode", "地包商编码"));
        orderMap.add(new ExcelTitleName("productName", "机型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("priceLevel", "机型档位"));
        orderMap.add(new ExcelTitleName("totalInnum", "入库总量"));
        orderMap.add(new ExcelTitleName("totalOutNum", "出库总量"));
        orderMap.add(new ExcelTitleName("sumPurchase", "交易进货量"));
        orderMap.add(new ExcelTitleName("amoutPurcchase", "进货金额"));
        orderMap.add(new ExcelTitleName("sumMANUAL", "手工入库量"));
        orderMap.add(new ExcelTitleName("sumTransIn", "调拨入库量"));
        orderMap.add(new ExcelTitleName("sumSellNum", "总销售量"));
        orderMap.add(new ExcelTitleName("sumSellAmout", "总销售额"));
        orderMap.add(new ExcelTitleName("sumTransOut", "调拨出库量"));
        orderMap.add(new ExcelTitleName("sumReturn", "退库量"));
        orderMap.add(new ExcelTitleName("daySale", "近7天的发货出库量/7天"));
        orderMap.add(new ExcelTitleName("sumStockDay", "库存总量"));
        orderMap.add(new ExcelTitleName("sumStockAmoutDay", "库存金额"));
        orderMap.add(new ExcelTitleName("sevenDayLv", "库存周转率"));
        orderMap.add(new ExcelTitleName("redStatus", "库存预警"));
        

        InputStream is = null;
//        try {
//            ExcelToNbrUtils.builderOrderExcel(data, orderMap, "串码列表");
//            resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
//            resultVO.setResultMsg("导出成功");
//        } catch (Exception e) {
//            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
//            resultVO.setResultMsg(e.getMessage());
//            log.error("excel导出失败",e);
//        }
//        return resultVO;
        //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
        		orderMap, "串码");
        return deliveryGoodsResNberExcel.uploadExcel(workbook);
    }

	
	
}
