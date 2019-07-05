package com.iwhalecloud.retail.web.controller.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.response.ProductListAllResp;
import com.iwhalecloud.retail.report.dto.response.ReportDeSaleDaoResq;
import com.iwhalecloud.retail.report.service.ReportService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author zwl
 * @date 2018-11-09
 * 地包进销存明细报表
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
		log.info("****************ReportOrderController getReportDeSaleList()  ************start param={}",JSON.toJSONString(req));
		//最大跨度查询三个月createTimeStart   createTimeEnd
		String itemDateStart = req.getItemDateStart();
		String itemDateEnd = req.getItemDateEnd();
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();//日期格式，精确到日  
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -3);
		Date date3 = cal.getTime();
		SimpleDateFormat format3= new SimpleDateFormat("yyyy-MM-dd");
		if(itemDateStart==null && itemDateEnd==null){
			itemDateStart = format3.format(date3);
			itemDateEnd = df.format(date);
			req.setItemDateStart(itemDateStart);
			req.setItemDateEnd(itemDateEnd);
		}
		int userType = UserContext.getUser().getUserFounder();
		List<String> list = new ArrayList<String>();
		if(userType == SystemConst.USER_FOUNDER_1  || userType == SystemConst.USER_FOUNDER_2) {//超级管理员  省管理员
		} else if (userType == SystemConst.USER_FOUNDER_9) {//地市管理员
			list.add(UserContext.getUser().getLanId());
			req.setLanIdList(list);
		} else if (userType == SystemConst.USER_FOUNDER_4 || userType == SystemConst.USER_FOUNDER_5) {//省供应商，地市供应商
			req.setSupplierId(UserContext.getUser().getRelCode());
		}
        return reportService.getReportDeSaleList(req);
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
    	//最大跨度查询三个月createTimeStart   createTimeEnd
    	req.setPageNo(1);
		req.setPageSize(50000);
		String itemDateStart = req.getItemDateStart();
		String itemDateEnd = req.getItemDateEnd();
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();//日期格式，精确到日  
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -3);
		Date date3 = cal.getTime();
		SimpleDateFormat format3= new SimpleDateFormat("yyyy-MM-dd");
		if(itemDateStart==null && itemDateEnd==null){
			itemDateStart = format3.format(date3);
			itemDateEnd = df.format(date);
			req.setItemDateStart(itemDateStart);
			req.setItemDateEnd(itemDateEnd);
		}
		int userType = UserContext.getUser().getUserFounder();
		List<String> list = new ArrayList<String>();
		if(userType == SystemConst.USER_FOUNDER_1  || userType == SystemConst.USER_FOUNDER_2) {//超级管理员  省管理员
		} else if (userType == SystemConst.USER_FOUNDER_9) {//地市管理员
			list.add(UserContext.getUser().getLanId());
			req.setLanIdList(list);
		} else if (userType == SystemConst.USER_FOUNDER_4 || userType == SystemConst.USER_FOUNDER_5) {//省供应商，地市供应商
			req.setSupplierId(UserContext.getUser().getRelCode());
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
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("productBaseName", "产品型号"));
        orderMap.add(new ExcelTitleName("typeName", "产品类型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("priceLevel", "机型档位"));
        orderMap.add(new ExcelTitleName("totalRu", "入库总量"));
        orderMap.add(new ExcelTitleName("totalChu", "出库总量"));
        orderMap.add(new ExcelTitleName("stockNum", "库存总量"));
        orderMap.add(new ExcelTitleName("purchaseNum", "交易进货量"));
        orderMap.add(new ExcelTitleName("purchaseAmount", "进货金额"));
        orderMap.add(new ExcelTitleName("manualNum", "手工入库量"));
        orderMap.add(new ExcelTitleName("transInNum", "调拨入库量"));
        orderMap.add(new ExcelTitleName("sellNum", "总销售量"));
        orderMap.add(new ExcelTitleName("sellAmount", "总销售额"));
        orderMap.add(new ExcelTitleName("transOutNum", "调拨出库量"));
        orderMap.add(new ExcelTitleName("returnNum", "退库量"));
        orderMap.add(new ExcelTitleName("weekAvgSellNum", "近7天的发货出库量/7天"));
        orderMap.add(new ExcelTitleName("stockAmount", "库存金额"));
        orderMap.add(new ExcelTitleName("turnoverRate", "库存周转率"));
        orderMap.add(new ExcelTitleName("stockWarning", "库存预警"));
        
      //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
        		orderMap, "地包进销存明细报表");
        deliveryGoodsResNberExcel.exportExcel("地包进销存明细报表",workbook,response);
       
    }
	
}
