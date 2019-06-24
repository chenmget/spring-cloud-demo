package com.iwhalecloud.retail.web.controller.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.report.dto.request.ReportCodeStatementsReq;
import com.iwhalecloud.retail.report.dto.response.ReportCodeStatementsResp;
import com.iwhalecloud.retail.report.service.ReportCodeStateService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.StorePageReq;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/reportCodeState")
public class ReportCodeStatementsController extends BaseController  {
	
	@Reference
	private ResouceStoreService resouceStoreService;
	
	@Reference
    private ReportCodeStateService reportCodeStateService;
	
	@Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;
	
	@ApiOperation(value = "查询串码报表", notes = "查询串码报表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/getCodeStatementsReport")
	@UserLoginToken
    public ResultVO<Page<ReportCodeStatementsResp>> getCodeStatementsReport(@RequestBody ReportCodeStatementsReq req) {
		log.info("****************ReportOrderController getCodeStatementsReport()  ************start param={}",JSON.toJSONString(req));
		String xdCreateTimeStart = req.getXdCreateTimeStart();
		String xdCreateTimeEnd = req.getXdCreateTimeEnd();
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();//日期格式，精确到日  
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -3);
		Date date3 = cal.getTime();
		SimpleDateFormat format3= new SimpleDateFormat("yyyy-MM-dd");
		if(xdCreateTimeStart==null && xdCreateTimeEnd==null){
			xdCreateTimeStart = format3.format(date3);
			xdCreateTimeEnd = df.format(date);
			req.setXdCreateTimeStart(xdCreateTimeStart);
			req.setXdCreateTimeEnd(xdCreateTimeEnd);
		}
		int userType=UserContext.getUser().getUserFounder();
		List<String> list = new ArrayList<String>();
		if(userType == SystemConst.USER_FOUNDER_1  || userType == SystemConst.USER_FOUNDER_2) {//超级管理员  省管理员
			return reportCodeStateService.getCodeStatementsReportAdmin(req);
		} else if (userType == SystemConst.USER_FOUNDER_9) {//地市管理员
			list.add(UserContext.getUser().getLanId());
			req.setLanIdName(list);
			return reportCodeStateService.getCodeStatementsReportAdmin(req);
		} else if (userType == SystemConst.USER_FOUNDER_4 || userType == SystemConst.USER_FOUNDER_5 
				|| userType == SystemConst.USER_FOUNDER_3 || userType == SystemConst.USER_FOUNDER_8) {//省供应商4，地市供应商5，零售商3,厂商8
			StorePageReq storePageReq = new StorePageReq();
			List<String> merchantIds = new ArrayList<String>();
			merchantIds.add(UserContext.getUser().getRelCode());
			storePageReq.setMerchantIds(merchantIds);
			Page<ResouceStoreDTO> pageResouceStoreDTO = resouceStoreService.pageStore(storePageReq);
			if(pageResouceStoreDTO == null) {
				return ResultVO.error("当前商家没有仓库");
			}
			req.setShangJiaId(UserContext.getUser().getRelCode());
			req.setMktResStoreId(pageResouceStoreDTO.getRecords().get(0).getMktResStoreId());
		} else {
			return ResultVO.error("当前用户 没有权限");
		}
		
			return reportCodeStateService.getCodeStatementsReport(req);
    }
	
	/**
     * 导出按钮
     */
    @ApiOperation(value = "导出", notes = "导出报表数据")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/codeStatementsReportExport")
    @UserLoginToken
    public void StorePurchaserReportExport(@RequestBody ReportCodeStatementsReq req, HttpServletResponse response) {
		req.setPageNo(1);
		req.setPageSize(60000);
		ResultVO<List<ReportCodeStatementsResp>> resultVO = null;
		log.info("****************ReportOrderController getCodeStatementsReport()  ************start param={}",JSON.toJSONString(req));
		String xdCreateTimeStart = req.getXdCreateTimeStart();
		String xdCreateTimeEnd = req.getXdCreateTimeEnd();
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();//日期格式，精确到日  
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -3);
		Date date3 = cal.getTime();
		SimpleDateFormat format3= new SimpleDateFormat("yyyy-MM-dd");
		if(xdCreateTimeStart==null && xdCreateTimeEnd==null){
			xdCreateTimeStart = format3.format(date3);
			xdCreateTimeEnd = df.format(date);
			req.setXdCreateTimeStart(xdCreateTimeStart);
			req.setXdCreateTimeEnd(xdCreateTimeEnd);
		}
		int userType=UserContext.getUser().getUserFounder();
		List<String> list = new ArrayList<String>();
		if(userType == SystemConst.USER_FOUNDER_1  || userType == SystemConst.USER_FOUNDER_2) {//超级管理员  省管理员
			resultVO = reportCodeStateService.getCodeStatementsReportAdmindc(req);
		} else if (userType == SystemConst.USER_FOUNDER_9) {//地市管理员
			list.add(UserContext.getUser().getLanId());
			req.setLanIdName(list);
			resultVO = reportCodeStateService.getCodeStatementsReportAdmindc(req);
		} else if (userType == SystemConst.USER_FOUNDER_4 || userType == SystemConst.USER_FOUNDER_5 || userType == SystemConst.USER_FOUNDER_3) {//省供应商4，地市供应商5，零售商3
			StorePageReq storePageReq = new StorePageReq();
			List<String> merchantIds = new ArrayList<String>();
			merchantIds.add(UserContext.getUser().getRelCode());
			storePageReq.setMerchantIds(merchantIds);
			Page<ResouceStoreDTO> pageResouceStoreDTO = resouceStoreService.pageStore(storePageReq);
			if(pageResouceStoreDTO == null) {
				return ;
			}
			req.setMktResStoreId(pageResouceStoreDTO.getRecords().get(0).getMktResStoreId());
			
			resultVO = reportCodeStateService.getCodeStatementsReportdc(req);
		} else {
			return ;
		}
		
        ResultVO result = new ResultVO();
        if (!resultVO.isSuccess()) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultMsg(resultVO.getResultMsg());
            deliveryGoodsResNberExcel.outputResponse(response,resultVO);
            return;
        }
        
        List<ReportCodeStatementsResp> data = resultVO.getResultData();
        
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("mktResInstNbr", "串码"));
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("unitType", "产品型号"));
        orderMap.add(new ExcelTitleName("productType", "产品类型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("attrValue1", "规格1"));
        orderMap.add(new ExcelTitleName("attrValue2", "规格2"));
        orderMap.add(new ExcelTitleName("attrValue3", "规格3"));
        orderMap.add(new ExcelTitleName("statusCd", "在库状态"));
        orderMap.add(new ExcelTitleName("mktResInstType", "串码类型"));
        orderMap.add(new ExcelTitleName("sourceType", "串码来源"));
        orderMap.add(new ExcelTitleName("productCode", "营销资源实例编码"));
        orderMap.add(new ExcelTitleName("orderId", "订单编号"));
        orderMap.add(new ExcelTitleName("createTime", "下单时间"));
        orderMap.add(new ExcelTitleName("supplierName", "供货商名称"));
        orderMap.add(new ExcelTitleName("supplierCode", "供货商编码"));
        orderMap.add(new ExcelTitleName("partnerName", "零售商名称"));
        orderMap.add(new ExcelTitleName("partnerCode", "店中商编码"));
        orderMap.add(new ExcelTitleName("cityId", "地市"));
        orderMap.add(new ExcelTitleName("countyOrgName", "经营单元"));
        orderMap.add(new ExcelTitleName("businessEntityName", "店中商所属经营主体名称"));
        orderMap.add(new ExcelTitleName("receiveTime", "入库时间"));
        orderMap.add(new ExcelTitleName("outTime", "出库时间"));
        orderMap.add(new ExcelTitleName("stockAge", "库龄"));
        orderMap.add(new ExcelTitleName("day30", "是否超过30天久库存"));
        orderMap.add(new ExcelTitleName("day60", "是否超过60天久库存"));
        orderMap.add(new ExcelTitleName("day90", "是否超过90天久库存"));
        orderMap.add(new ExcelTitleName("destMerchantId", "串码流向"));
        orderMap.add(new ExcelTitleName("destCityId", "串码流向所属地市"));
        orderMap.add(new ExcelTitleName("destCountyOrgName", "串码流向所属经营单元"));
        orderMap.add(new ExcelTitleName("crmStatus", "CRM状态"));
        orderMap.add(new ExcelTitleName("selfRegStatus", "自注册状态"));
        
      //创建Excel
        Workbook workbook = new HSSFWorkbook();
//      //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
        		orderMap, "串码明细报表");
        deliveryGoodsResNberExcel.exportExcel("串码明细报表",workbook,response);
        
    }
    
}
