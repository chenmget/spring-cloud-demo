package com.iwhalecloud.retail.web.controller.report;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.iwhalecloud.retail.report.service.IReportDataInfoService;
import com.iwhalecloud.retail.report.service.ReportOrderService;
import com.iwhalecloud.retail.report.service.ReportService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.controller.b2b.order.service.OrderExportUtil;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ExcelToNbrUtils;
import com.iwhalecloud.retail.web.controller.partner.utils.ExcelToMerchantListUtils;
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
    private IReportDataInfoService iReportDataInfoService;
	
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
	@UserLoginToken
    public ResultVO<Page<ReportOrderResp>> getReportOrderList1(@RequestBody ReportOrderDaoReq req) {
//		String legacyAccount = req.getLegacyAccount();//判断是云货架还是原系统的零售商，默认云货架
//		String retailerCodes = req.getMerchantCode();//是否输入了零售商账号
		String userType=req.getUserType();
		//String userType = UserContext.getUser().getUserFounder()+"";
//		if("2".equals(legacyAccount) && !"3".equals(userType) && retailerCodes != null){
//			retailerCodes = iReportDataInfoService.retailerCodeBylegacy(retailerCodes);
//			req.setMerchantCode(retailerCodes);
//		}
		//userType 1省级管理员，2地市管理员，3零售商，4供应商，5厂家
		
		if(userType!=null && !userType.equals("") && "3".equals(userType)){//零售商只看自己的
//			String loginName = UserContext.getUser().getLoginName();
//			iReportDataInfoService.getretailerCode(loginName);
			String merchantCode=UserContext.getUser().getRelCode();
			req.setMerchantCode(merchantCode);
		}
		if(userType!=null && !userType.equals("") && "4".equals(userType)){//供应商只看自己的
			String suplierCode=UserContext.getUser().getRelCode();
			req.setSuplierCode(suplierCode);
		}
		if(userType!=null && !userType.equals("") && "2".equals(userType)){
			String regionId = UserContext.getUser().getLanId();
			req.setLanId(regionId);
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
	   
	    @RequestMapping(value = "/orderReportDataExport", method = RequestMethod.POST)
	    @UserLoginToken
	    public void orderReportDataExport(@RequestBody ReportOrderDaoReq req, HttpServletResponse response) {
//	    	String legacyAccount = req.getLegacyAccount();//判断是云货架还是原系统的零售商，默认云货架
//			String retailerCodes = req.getMerchantCode();//是否输入了零售商账号
			String userType=req.getUserType();
			//String userType = UserContext.getUser().getUserFounder()+"";
//			if("2".equals(legacyAccount) && !"3".equals(userType) && retailerCodes != null){
//				retailerCodes = iReportDataInfoService.retailerCodeBylegacy(retailerCodes);
//				req.setMerchantCode(retailerCodes);
//			}
			//userType 1省级管理员，2地市管理员，3零售商，4供应商，5厂家
			
			if(userType!=null && !userType.equals("") && "3".equals(userType)){//零售商只看自己的
//				String loginName = UserContext.getUser().getLoginName();
//				iReportDataInfoService.getretailerCode(loginName);
				String merchantCode=UserContext.getUser().getRelCode();
				req.setMerchantCode(merchantCode);
			}
			if(userType!=null && !userType.equals("") && "4".equals(userType)){//供应商只看自己的
				String suplierCode=UserContext.getUser().getRelCode();
				req.setSuplierCode(suplierCode);
			}
			if(userType!=null && !userType.equals("") && "2".equals(userType)){
				String regionId = UserContext.getUser().getLanId();
				req.setLanId(regionId);
			}
	        ResultVO<List<ReportOrderResp>> resultVO = reportOrderService.getReportOrderList1dc(req);
	        ResultVO result = new ResultVO();
	        if (!resultVO.isSuccess()) {
	            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
	            result.setResultMsg(resultVO.getResultMsg());
	            deliveryGoodsResNberExcel.outputResponse(response,resultVO);
	            return;
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
	        		orderMap, "订单明细报表");
	        deliveryGoodsResNberExcel.exportExcel("订单明细报表",workbook,response);
	        
//	        deliveryGoodsResNberExcel.uploadExcel(workbook);
	        
//	        return deliveryGoodsResNberExcel.uploadExcel(workbook);
	        
	      
//	        OutputStream output = null ;
//	        try{
//	            //创建Excel
//	            String fileName = "订单明细报表";
////	            ExcelToNbrUtils.builderOrderExcel(workbook, data, orderMap, false);
//	            ExcelToMerchantListUtils.builderOrderExcel(workbook, data, orderMap);
//	            output = response.getOutputStream();
//	            response.reset();
//	            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
//	            response.setContentType("application/msexcel;charset=UTF-8");
//	            response.setCharacterEncoding("UTF-8");
//	            workbook.write(output);
////	            output.close();
//	        }catch (Exception e){
//	            log.error("订单明细报表导出失败",e);
//	        } finally {
//	            if (null != output){
//	                try {
//	                    output.close();
//	                } catch (IOException e) {
//	                    e.printStackTrace();
//	                }
//	            }
//	        }
	        
	    }
}
