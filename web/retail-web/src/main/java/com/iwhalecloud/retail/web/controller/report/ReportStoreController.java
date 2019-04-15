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
		String userType=req.getUserType();
		if(userType!=null && !userType.equals("") && "2".equals(userType)){//地市管理员
			String regionId = UserContext.getUser().getRegionId();
			req.setLanId(regionId);
		}
		if(userType!=null && !userType.equals("") && "3".equals(userType)){//零售商
			String retailerCode=UserContext.getUser().getRelCode();
			req.setRetailerCode(retailerCode);
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
    	String userType=req.getUserType();
		if(userType!=null && !userType.equals("") && "2".equals(userType)){//地市管理员
			String regionId = UserContext.getUser().getRegionId();
			req.setLanId(regionId);
		}
		if(userType!=null && !userType.equals("") && "3".equals(userType)){//零售商
			String retailerCode=UserContext.getUser().getRelCode();
			req.setRetailerCode(retailerCode);
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
        ResultVO<List<ReportStSaleDaoResp>> resultVO = reportStoreService.getReportStSaleListdc(req);
        if (!resultVO.isSuccess()) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultData("失败：" + resultVO.getResultMsg());
            return result;
        }
        List<ReportStSaleDaoResp> data = resultVO.getResultData();
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        //门店进销存明细的导出
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("partnerCode", "零售商编码"));
        orderMap.add(new ExcelTitleName("partnerName", "零售商名称"));
        orderMap.add(new ExcelTitleName("businessEntityName", "所属经营主体"));
        orderMap.add(new ExcelTitleName("cityId", "所属城市"));
        orderMap.add(new ExcelTitleName("countryId", "所属区县"));
        orderMap.add(new ExcelTitleName("typeId", "产品类型"));
        orderMap.add(new ExcelTitleName("redStatus", "库存预警"));
        orderMap.add(new ExcelTitleName("productBaseName", "机型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("priceLevel", "档位"));
        orderMap.add(new ExcelTitleName("theTotalInventory", "入库总量"));
        orderMap.add(new ExcelTitleName("theTotalOutbound", "出库总量"));
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
        
        //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
        		orderMap, "串码");
        return deliveryGoodsResNberExcel.uploadExcel(workbook);
    }

}
