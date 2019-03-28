package com.iwhalecloud.retail.web.controller.report;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
//import com.iwhalecloud.retail.report.dto.request.ReportDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportOrderDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportOrderNbrDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.dto.response.ReportOrderNbrResp;
//import com.iwhalecloud.retail.report.dto.response.ReportDaoResp;
import com.iwhalecloud.retail.report.dto.response.ReportOrderResp;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;
import com.iwhalecloud.retail.report.service.ReportOrderService;
import com.iwhalecloud.retail.report.service.ReportService;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.interceptor.UserContext;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;


/**
 * @author lws
 * @date 2019-03-22
 * 订单报表
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/reportOrder")
public class ReportOrderController extends BaseController {

    @Reference
    private ReportOrderService reportOrderService;

    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;
    
	@ApiOperation(value = "查询报表", notes = "查询报表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/getReportOrderList1")
    public ResultVO<Page<ReportOrderResp>> getReportOrderList1(@RequestBody ReportOrderDaoReq req) {
		//userType 1省级管理员，2地市管理员，3供应商，4零售商，5厂家
		String userType=req.getUserType();
		if(userType!=null && !userType.equals("") && "4".equals(userType)){//零售商只看自己的
			String merchantCode=UserContext.getUser().getRelCode();
			req.setMerchantCode(merchantCode);
		}
		if(userType!=null && !userType.equals("") && "3".equals(userType)){//供应商只看自己的
			String suplierCode=UserContext.getUser().getRelCode();
			req.setSuplierCode(suplierCode);
		}
		if(userType!=null && !userType.equals("") && "2".equals(userType)){
			String regionId = UserContext.getUser().getRegionId();
			req.setLanId(regionId);
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
        return reportOrderService.getReportOrderList1(req);
    }
	
	@ApiOperation(value = "查询串码详情报表", notes = "查询串码详情报表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
	 @PostMapping("/getReportOrderList3")
    public ResultVO<Page<ReportOrderNbrResp>> getReportOrderList3(@RequestBody ReportOrderNbrDaoReq req) {
        return reportOrderService.getReportOrderList3(req);
    }
	 
	 
	 /**
	     * 导出按钮
	     */
	    @ApiOperation(value = "导出", notes = "导出报表数据")
	    @ApiResponses({
	            @ApiResponse(code=400,message="请求参数没填好"),
	            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
	    })
	    @PostMapping(value="/orderReportDataExport")
	    public ResultVO orderReportDataExport(@RequestBody ReportOrderDaoReq req) {
			String userType=req.getUserType();
			if(userType!=null && !userType.equals("") && "4".equals(userType)){
				String merchantCode=UserContext.getUser().getRelCode();
				req.setMerchantCode(merchantCode);
			}
			if(userType!=null && !userType.equals("") && "3".equals(userType)){
				String suplierCode=UserContext.getUser().getRelCode();
				req.setSuplierCode(suplierCode);
			}
			if(userType!=null && !userType.equals("") && "2".equals(userType)){
				String regionId = UserContext.getUser().getRegionId();
				req.setLanId(regionId);
			}
	        ResultVO result = new ResultVO();
	        ResultVO<List<ReportOrderResp>> resultVO = reportOrderService.getReportOrderList1dc(req);
	        if (!resultVO.isSuccess()) {
	            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
	            result.setResultData("失败：" + resultVO.getResultMsg());
	            return result;
	        }
	        List<ReportOrderResp> data = resultVO.getResultData();
	        //创建Excel
	        Workbook workbook = new HSSFWorkbook();
	        
	        List<ExcelTitleName> orderMap = new ArrayList<>();
	        //orderMap.add(new ExcelTitleName("id", "序号"));
	        orderMap.add(new ExcelTitleName("orderId", "订单编号"));
	        orderMap.add(new ExcelTitleName("status", "订单状态"));
	        orderMap.add(new ExcelTitleName("sourceFrom", "订单来源"));
	        orderMap.add(new ExcelTitleName("orderType", "订单类型"));
	        orderMap.add(new ExcelTitleName("type", "交易类型"));
	        orderMap.add(new ExcelTitleName("paymentType", "支付类型"));
	        orderMap.add(new ExcelTitleName("payType", "支付方式"));
	        orderMap.add(new ExcelTitleName("catId", "产品类型"));
	        orderMap.add(new ExcelTitleName("brandId", "品牌"));
	        orderMap.add(new ExcelTitleName("unitName", "产品名称"));
	        orderMap.add(new ExcelTitleName("unitType", "产品型号"));
	        orderMap.add(new ExcelTitleName("sn", "25位编码"));
	        orderMap.add(new ExcelTitleName("num", "数量"));
	        orderMap.add(new ExcelTitleName("price", "单价"));
	        orderMap.add(new ExcelTitleName("sumMoney", "总金额"));
	        orderMap.add(new ExcelTitleName("shipNum", "发货串码数量"));
	        orderMap.add(new ExcelTitleName("createTime", "下单时间"));
	        orderMap.add(new ExcelTitleName("payTime", "付款时间"));
	        orderMap.add(new ExcelTitleName("receiveTime", "出库时间"));
	        orderMap.add(new ExcelTitleName("merchantName", "供货商名称"));
	        orderMap.add(new ExcelTitleName("merchantCode", "供货商编码"));
	        orderMap.add(new ExcelTitleName("merchantName1", "店中商名称"));
	        orderMap.add(new ExcelTitleName("merchantCode1", "店中商编码"));
	        orderMap.add(new ExcelTitleName("businessEntityName", "经营主体名称"));
	        orderMap.add(new ExcelTitleName("lanId", "店中商所属地市"));
	        orderMap.add(new ExcelTitleName("city", "店中商所属区县"));

	        //创建orderItemDetail
	        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
	        		orderMap, "串码");
	        return deliveryGoodsResNberExcel.uploadExcel(workbook);
	    }

}
