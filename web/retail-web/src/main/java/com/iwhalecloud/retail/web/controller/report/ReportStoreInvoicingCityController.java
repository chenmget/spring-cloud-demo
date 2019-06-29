package com.iwhalecloud.retail.web.controller.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.report.dto.request.ReportStInvCityDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.dto.request.RptPartnerOperatingDay;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;
import com.iwhalecloud.retail.report.service.IReportDataInfoService;
import com.iwhalecloud.retail.report.service.ReportStInvCityService;
import com.iwhalecloud.retail.system.common.SystemConst;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiyou on 2019/4/11.
 * <p>
 * 门店进销地市报表
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/storeInvoicingCity")
public class ReportStoreInvoicingCityController extends BaseController {

    @Reference
    private ReportStInvCityService reportStInvCityService;

    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;

    @ApiOperation(value = "查询报表", notes = "查询报表")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/getStoreInvoicingCityList")
    public ResultVO<Page<RptPartnerOperatingDay>> getStoreInvoicingCityList(@RequestBody ReportStInvCityDaoReq req) {
    	log.info("****************************ReportStoreController getReportStSaleList    req={}",JSON.toJSONString(req));
		//默认最大跨度查询三个月
		String dateStart = req.getDateStart();
		String dateEnd = req.getDateEnd();
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();//日期格式，精确到日  
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -3);
		Date date3 = cal.getTime();
		SimpleDateFormat format3= new SimpleDateFormat("yyyy-MM-dd");
		if(dateStart==null && dateEnd==null){
			dateStart = format3.format(date3);
			dateEnd = df.format(date);
			req.setDateStart(dateStart);
			req.setDateEnd(dateEnd);
		}
		int userType=UserContext.getUser().getUserFounder();
		List<String> list = new ArrayList<String>();
		if(userType == SystemConst.USER_FOUNDER_1  || userType == SystemConst.USER_FOUNDER_2) {//超级管理员  省管理员
		} else if (userType == SystemConst.USER_FOUNDER_9) {//地市管理员
			list.add(UserContext.getUser().getLanId());
			req.setLanIdList(list);
		} else {
			return ResultVO.error("当前用户 没有权限");
		}
        return reportStInvCityService.getReportStInvCityList(req);
    }

    /**
     * 导出按钮
     */
    @ApiOperation(value = "导出", notes = "导出报表数据")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/storeInvoicingCityReportExport")
    public void storeInvoicingCityReportExport(@RequestBody ReportStInvCityDaoReq req, HttpServletResponse response) {

    	//默认最大跨度查询三个月
		String dateStart = req.getDateStart();
		String dateEnd = req.getDateEnd();
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();//日期格式，精确到日  
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -3);
		Date date3 = cal.getTime();
		SimpleDateFormat format3= new SimpleDateFormat("yyyy-MM-dd");
		if(dateStart==null && dateEnd==null){
			dateStart = format3.format(date3);
			dateEnd = df.format(date);
			req.setDateStart(dateStart);
			req.setDateEnd(dateEnd);
		}
		int userType=UserContext.getUser().getUserFounder();
		List<String> list = new ArrayList<String>();
		if(userType == SystemConst.USER_FOUNDER_1  || userType == SystemConst.USER_FOUNDER_2) {//超级管理员  省管理员
		} else if (userType == SystemConst.USER_FOUNDER_9) {//地市管理员
			list.add(UserContext.getUser().getLanId());
			req.setLanIdList(list);
		} else {
			return ;
		}
        ResultVO<List<RptPartnerOperatingDay>> resultVO = reportStInvCityService.getReportStInvCityListExport(req);
        ResultVO result = new ResultVO();
        if (!resultVO.isSuccess()) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultMsg(resultVO.getResultMsg());
            deliveryGoodsResNberExcel.outputResponse(response,resultVO);
            return;
        }
        List<RptPartnerOperatingDay> data = resultVO.getResultData();

        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        List<ExcelTitleName> orderMap = new ArrayList<>();

        orderMap.add(new ExcelTitleName("lanIdName", "地市"));
        orderMap.add(new ExcelTitleName("orgName", "经营单元"));
        orderMap.add(new ExcelTitleName("totalInNum", "入库总量"));
        orderMap.add(new ExcelTitleName("totalOutNum", "出库总量"));
        orderMap.add(new ExcelTitleName("stockNum", "库存量"));
        orderMap.add(new ExcelTitleName("purchaseNum", "交易入库量"));
        orderMap.add(new ExcelTitleName("manualNum", "手工入库量"));
        orderMap.add(new ExcelTitleName("transInNum", "调拨入库量"));
        orderMap.add(new ExcelTitleName("sellNum", "总销售量"));
        orderMap.add(new ExcelTitleName("uncontractNum", "手工核销"));
        orderMap.add(new ExcelTitleName("contractNum", "CRM销量"));
        orderMap.add(new ExcelTitleName("registerNum", "自注册激活量"));
        orderMap.add(new ExcelTitleName("transOutNum", "调拨出库量"));
        orderMap.add(new ExcelTitleName("returnNum", "退库量"));
        orderMap.add(new ExcelTitleName("weekAvgSellNum", "近7天日均销量"));

        // 创建excel
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data, orderMap, "门店进销存地市报表");

        deliveryGoodsResNberExcel.exportExcel("门店进销存地市报表",workbook,response);
    }

    
}
