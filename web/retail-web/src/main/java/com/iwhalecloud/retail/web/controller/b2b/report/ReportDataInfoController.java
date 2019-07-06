package com.iwhalecloud.retail.web.controller.b2b.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;
import com.iwhalecloud.retail.report.service.IReportDataInfoService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.service.RegionsService;
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


/**
 * @author liweisong
 * @date 2019-02-18
 * 门店进销存机型报表
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/reportDataInfo")
public class ReportDataInfoController extends BaseController {

    @Reference
    private IReportDataInfoService iReportDataInfoService;
    
    @Reference
	private MerchantService merchantService;
    
    @Reference
    private RegionsService regionService;

    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;
    
	@ApiOperation(value = "查询报表", notes = "查询报表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/getStorePurchaserReport")
	@UserLoginToken
    public ResultVO<Page<ReportStorePurchaserResq>> getStorePurchaserReport(@RequestBody ReportStorePurchaserReq req) {
		log.info("****************************ReportStoreController getStorePurchaserReport    req={}",JSON.toJSONString(req));
		int userType=UserContext.getUser().getUserFounder();
		List<String> list = new ArrayList<String>();
		if (userType == SystemConst.USER_FOUNDER_9) { //地市管理员
			list.add(UserContext.getUser().getLanId());
			req.setLanIdList(list);
		}
		
		return iReportDataInfoService.getStorePurchaserReport(req);
    }
	
	 /**
     * 导出按钮
     */
    @ApiOperation(value = "导出", notes = "导出报表数据")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/StorePurchaserReportExport")
    @UserLoginToken
    public void StorePurchaserReportExport(@RequestBody ReportStorePurchaserReq req, HttpServletResponse response) {
    	log.info("****************************ReportStoreController StorePurchaserReportExport    req={}",JSON.toJSONString(req));
    	req.setPageNo(1);
		req.setPageSize(50000);
    	int userType=UserContext.getUser().getUserFounder();
		List<String> list = new ArrayList<String>();
		if (userType == SystemConst.USER_FOUNDER_9) { //地市管理员
			list.add(UserContext.getUser().getLanId());
			req.setLanIdList(list);
		}
        ResultVO<List<ReportStorePurchaserResq>> resultVO = iReportDataInfoService.getStorePurchaserReportdc(req);
        ResultVO result = new ResultVO();
        if (!resultVO.isSuccess()) {
            result.setResultCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMsg(resultVO.getResultMsg());
            deliveryGoodsResNberExcel.outputResponse(response,resultVO);
            return;
        }
        
        List<ReportStorePurchaserResq> data = resultVO.getResultData();
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("productBaseName", "产品类型"));
        orderMap.add(new ExcelTitleName("productType", "零售商编码"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("priceLevel", "机型档位"));
        orderMap.add(new ExcelTitleName("lanIdName", "地市"));
        orderMap.add(new ExcelTitleName("totalInNum", "入库总量"));
        orderMap.add(new ExcelTitleName("totalOutNum", "出库总量"));
        orderMap.add(new ExcelTitleName("stockNum", "库存总量"));
        orderMap.add(new ExcelTitleName("purchaseNum", "交易入库量"));
        orderMap.add(new ExcelTitleName("manualNum", "手工入库量"));
        orderMap.add(new ExcelTitleName("sellNum", "总销售量"));
        orderMap.add(new ExcelTitleName("uncontractNum", "手工销售量"));
        orderMap.add(new ExcelTitleName("contractNum", "CRM合约销量"));
        orderMap.add(new ExcelTitleName("registerNum", "自注册销量"));
        orderMap.add(new ExcelTitleName("returnNum", "退库量"));
        orderMap.add(new ExcelTitleName("weekAvgSellNum", "近7天日均销量"));

//      //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
        		orderMap, "门店进销存机型报表");
        deliveryGoodsResNberExcel.exportExcel("门店进销存机型报表",workbook,response);
    }
    
}
