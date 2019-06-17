package com.iwhalecloud.retail.web.controller.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.report.dto.request.ReportOrderDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportOrderNbrDaoReq;
import com.iwhalecloud.retail.report.dto.response.ReportOrderNbrResp;
import com.iwhalecloud.retail.report.dto.response.ReportOrderResp;
import com.iwhalecloud.retail.report.service.IReportDataInfoService;
import com.iwhalecloud.retail.report.service.ReportOrderService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


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
	private MerchantService merchantService;
	
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
		log.info("****************ReportOrderController getReportOrderList1");
		int userType = UserContext.getUser().getUserFounder();
		if(userType == SystemConst.USER_FOUNDER_1  || userType == SystemConst.USER_FOUNDER_2) {//超级管理员  省管理员
		} else if (userType == SystemConst.USER_FOUNDER_9) {//地市管理员
			req.setLanIdName(UserContext.getUser().getLanId());
		} else if (userType == SystemConst.USER_FOUNDER_4) {//省供应商
			MerchantDTO merchantDTO = merchantService.getMerchantInfoById(UserContext.getUser().getRelCode());
			if ( merchantDTO == null) {
				return ResultVO.error("当前用户 没有商家编码");
			}
			req.setSuplierCode(merchantDTO.getMerchantCode());
		} else if (userType == SystemConst.USER_FOUNDER_5) {//地市供应商
			req.setSuplierCode(UserContext.getUser().getRelCode());
			req.setLanIdName(UserContext.getUser().getLanId());
		} else if (userType == SystemConst.USER_FOUNDER_3) {//零售商
			MerchantDTO merchantDTO = merchantService.getMerchantInfoById(UserContext.getUser().getRelCode());
			if ( merchantDTO == null) {
				return ResultVO.error("当前用户 没有商家编码");
			}
			req.setMerchantCode(merchantDTO.getMerchantCode());
		} else {
			return ResultVO.error("当前用户 没有权限");
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
		int userType = UserContext.getUser().getUserFounder();
		req.setPageNo(1);
		req.setPageSize(60000);
		if(userType == SystemConst.USER_FOUNDER_1  || userType == SystemConst.USER_FOUNDER_2) {//超级管理员  省管理员
		} else if (userType == SystemConst.USER_FOUNDER_9) {//地市管理员
			req.setLanIdName(UserContext.getUser().getLanId());
		} else if (userType == SystemConst.USER_FOUNDER_4) {//省供应商
			MerchantDTO merchantDTO = merchantService.getMerchantInfoById(UserContext.getUser().getRelCode());
			if ( merchantDTO == null) {
				return ;
			}
			req.setSuplierCode(merchantDTO.getMerchantCode());
		} else if (userType == SystemConst.USER_FOUNDER_5) {//地市供应商
			req.setSuplierCode(UserContext.getUser().getRelCode());
			req.setLanIdName(UserContext.getUser().getLanId());
		} else if (userType == SystemConst.USER_FOUNDER_3) {//零售商
			MerchantDTO merchantDTO = merchantService.getMerchantInfoById(UserContext.getUser().getRelCode());
			if ( merchantDTO == null) {
				return ;
			}
			req.setMerchantCode(merchantDTO.getMerchantCode());
		} else {
			return ;
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
	    orderMap.add(new ExcelTitleName("orderCat", "订单类别"));
	    orderMap.add(new ExcelTitleName("orderType", "交易类型"));
	    orderMap.add(new ExcelTitleName("paymentType", "支付类型"));
	    orderMap.add(new ExcelTitleName("payType", "支付方式"));
	    orderMap.add(new ExcelTitleName("typeName", "产品类型"));
	    orderMap.add(new ExcelTitleName("brandName", "品牌"));
	    orderMap.add(new ExcelTitleName("productName", "产品名称"));
	    orderMap.add(new ExcelTitleName("unitType", "产品型号"));
	    orderMap.add(new ExcelTitleName("sn", "25位编码"));
	    orderMap.add(new ExcelTitleName("num", "数量"));
	    orderMap.add(new ExcelTitleName("price", "单价"));
	    orderMap.add(new ExcelTitleName("totalMoney", "总金额"));
	    orderMap.add(new ExcelTitleName("shipNum", "发货串码数量"));
	    orderMap.add(new ExcelTitleName("createTime", "下单时间"));
	    orderMap.add(new ExcelTitleName("payTime", "付款时间"));
	    orderMap.add(new ExcelTitleName("receiveTime", "出库时间"));
	    orderMap.add(new ExcelTitleName("supplierName", "供货商名称"));
	    orderMap.add(new ExcelTitleName("supplierCode", "供货商编码"));
	    orderMap.add(new ExcelTitleName("merchantName", "店中商名称"));
	    orderMap.add(new ExcelTitleName("merchantCode", "店中商编码"));
	    orderMap.add(new ExcelTitleName("businessEntityName", "经营主体名称"));
	    orderMap.add(new ExcelTitleName("lanId", "店中商所属地市"));
	    orderMap.add(new ExcelTitleName("city", "店中商所属区县"));
	
	    orderMap.add(new ExcelTitleName("activeName", "营销活动"));
	    orderMap.add(new ExcelTitleName("couponType", "优惠类型"));
	    orderMap.add(new ExcelTitleName("couponMoney", "优惠额度"));
	  //创建orderItemDetail
	    deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
	    		orderMap, "订单明细报表");
	    deliveryGoodsResNberExcel.exportExcel("订单明细报表",workbook,response);
	}
}
