package com.iwhalecloud.retail.web.controller.report;

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
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.service.IReportDataInfoService;
import com.iwhalecloud.retail.report.service.ReportService;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.interceptor.UserContext;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.report.dto.response.ReportStSaleDaoResp;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;
import com.iwhalecloud.retail.report.dto.request.ReportStSaleDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.service.ReportStoreService;

/**
 * @author zwl
 * @date 2018-11-09
 * （厂家）门店报表
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/reportStore")
public class ReportStoreController extends BaseController {

    @Reference
    private ReportStoreService reportStoreService;
    
    @Reference
    private IReportDataInfoService iReportDataInfoService;
    
    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;
    
	@ApiOperation(value = "查询报表", notes = "查询报表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/getReportStSaleList")
    public ResultVO<Page<ReportStSaleDaoResp>> getReportStSaleList(@RequestBody ReportStSaleDaoReq req) {
		String userId = UserContext.getUser().getUserId();
		ReportStorePurchaserReq req2 = new ReportStorePurchaserReq();
		req2.setUserId(userId);
		ResultVO<List<ReportStorePurchaserResq>> brandview = iReportDataInfoService.getUerRoleForView(req2);
		String brandId = brandview.getResultData().get(0).getBrandId();
		//req.setBrand(brandId);
        return reportStoreService.getReportStSaleList(req);
    }
	
	/**
     * 导出按钮
     */
    @ApiOperation(value = "导出", notes = "导出报表数据")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/cjStorePurchaserReportExport")
    public ResultVO cjStorePurchaserReportExport(@RequestBody ReportStSaleDaoReq req) {
		//userType==5 厂家视图的导出
        ResultVO result = new ResultVO();
        ResultVO<List<ReportStSaleDaoResp>> resultVO = reportStoreService.getReportStSaleListdc(req);
        if (!resultVO.isSuccess()) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultData("失败：" + resultVO.getResultMsg());
            return result;
        }
        List<ReportStSaleDaoResp> data = resultVO.getResultData();
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        
        List<ExcelTitleName> orderMap = new ArrayList<>();
	        orderMap.add(new ExcelTitleName("partnerCode", "零售商编码"));
	        orderMap.add(new ExcelTitleName("partnerName", "零售商名称"));
	        orderMap.add(new ExcelTitleName("businessEntityName", "所属经营主体"));
	        orderMap.add(new ExcelTitleName("cityId", "所属城市"));
	        orderMap.add(new ExcelTitleName("countryId", "所属区县"));
        orderMap.add(new ExcelTitleName("productBaseName", "机型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("typeId", "产品类型"));
        orderMap.add(new ExcelTitleName("theTotalInventory", "入库总量"));
        orderMap.add(new ExcelTitleName("theTotalOutbound", "出库总量"));
        orderMap.add(new ExcelTitleName("stockTotalNum", "库存总量"));
        orderMap.add(new ExcelTitleName("purchaseNum", "交易入库量"));
        orderMap.add(new ExcelTitleName("manualNum", "手工入库量"));
        	orderMap.add(new ExcelTitleName("transInNum", "调拨入库量"));
        	orderMap.add(new ExcelTitleName("purchaseAmount", "进货金额"));
        orderMap.add(new ExcelTitleName("totalSalesNum", "总销售量"));
            orderMap.add(new ExcelTitleName("sellAmount", "总销售额"));
        orderMap.add(new ExcelTitleName("manualSalesNum", "手工销售量"));
        orderMap.add(new ExcelTitleName("crmcontractNum", "CRM合约销量"));
        orderMap.add(new ExcelTitleName("registerNum", "自注册销量"));
        orderMap.add(new ExcelTitleName("returnNum", "退库量"));
        orderMap.add(new ExcelTitleName("averageDailySales", "近7天日均销量"));
        	orderMap.add(new ExcelTitleName("stockAmount", "库存金额"));
        orderMap.add(new ExcelTitleName("stockNum", "库存量"));
        orderMap.add(new ExcelTitleName("stockTurnover", "库存周转率"));
        orderMap.add(new ExcelTitleName("inventoryWarning", "库存预警"));
        
        //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
        		orderMap, "串码");
        return deliveryGoodsResNberExcel.uploadExcel(workbook);
    }

}
