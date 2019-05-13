package com.iwhalecloud.retail.web.controller.report;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.iwhalecloud.retail.web.controller.partner.utils.ExcelToMerchantListUtils;
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
	@UserLoginToken
    public ResultVO<Page<ReportDeSaleDaoResq>> getReportDeSaleList(@RequestBody ReportDeSaleDaoReq req) {
		String userType=req.getUserType();
		//String userType = UserContext.getUser().getUserFounder()+"";
		if(userType!=null&&!userType.equals("")&&userType.equals("3")){
			String MerchantCode=UserContext.getUser().getRelCode();
			req.setMerchantCode(MerchantCode);
		}
		if(userType!=null&&!userType.equals("")&&userType.equals("2")){
			String lanId=UserContext.getUser().getRegionId();
			req.setLanId(lanId);
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
    @UserLoginToken
    public void reportDeSaleExport(@RequestBody ReportDeSaleDaoReq req, HttpServletResponse response) {
		String userType=req.getUserType();
    	////1超级管理员 2普通管理员 3零售商(门店、店中商) 4省包供应商 5地包供应商 6 代理商店员 7经营主体 8厂商 \n12 终端公司管理人员 24 省公司市场部管理人员',
    	//String userType = UserContext.getUser().getUserFounder()+"";
		if(userType!=null&&!userType.equals("")&&userType.equals("3")){
			String MerchantCode=UserContext.getUser().getRelCode();
			req.setMerchantCode(MerchantCode);
		}
		if(userType!=null&&!userType.equals("")&&userType.equals("2")){
			String lanId=UserContext.getUser().getRegionId();
			req.setLanId(lanId);
		}
		
        ResultVO<List<ReportDeSaleDaoResq>> resultVO = reportService.reportDeSaleExport(req);
        
        ResultVO result = new ResultVO();
        if (!resultVO.isSuccess()) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultMsg(resultVO.getResultMsg());
            deliveryGoodsResNberExcel.outputResponse(response,resultVO);
            return;
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
        orderMap.add(new ExcelTitleName("typeId", "产品类型"));
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
        
      //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
        		orderMap, "地包进销存明细报表");
        deliveryGoodsResNberExcel.exportExcel("地包进销存明细报表",workbook,response);
        
//        return deliveryGoodsResNberExcel.uploadExcel(workbook);
//        OutputStream output = null ;
//        try{
//            //创建Excel
//            String fileName = "地包进销存明细报表";
////            ExcelToNbrUtils.builderOrderExcel(workbook, data, orderMap, false);
//            ExcelToMerchantListUtils.builderOrderExcel(workbook, data, orderMap);
//            output = response.getOutputStream();
//            response.reset();
//            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
//            response.setContentType("application/msexcel;charset=UTF-8");
//            response.setCharacterEncoding("UTF-8");
//            workbook.write(output);
////            output.close();
//        }catch (Exception e){
//            log.error("地包进销存明细报表导出失败",e);
//        } finally {
//            if (null != output){
//                try {
//                    output.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
       
    }
	
}
