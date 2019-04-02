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
import com.iwhalecloud.retail.report.dto.request.ReportCodeStatementsReq;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.dto.response.ReportCodeStatementsResp;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;
import com.iwhalecloud.retail.report.service.ReportCodeStateService;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.interceptor.UserContext;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/reportCodeState")
public class ReportCodeStatementsController extends BaseController  {

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
    public ResultVO<Page<ReportCodeStatementsResp>> getCodeStatementsReport(@RequestBody ReportCodeStatementsReq req) {
		String userType=req.getUserType();
		if(userType!=null&&!userType.equals("")&&userType.equals("2")){
			String lanId=UserContext.getUser().getRegionId();
			req.setLanId(lanId);
		}else if("3".equals(userType)){
			String lssCode = UserContext.getUser().getRelCode();
			req.setLssCode(lssCode);
		}else if("4".equals(userType)){
			String gysCode = UserContext.getUser().getRelCode();
			req.setGysCode(gysCode);
		}else if("5".equals(userType)){
			String manufacturerCode = UserContext.getUser().getRelCode();
			req.setManufacturerCode(manufacturerCode);
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
    public ResultVO StorePurchaserReportExport(@RequestBody ReportCodeStatementsReq req, HttpServletResponse response) {
		String userType=req.getUserType();
		if(userType!=null && !userType.equals("") && "2".equals(userType)){//地市管理员
			String regionId = UserContext.getUser().getRegionId();
			req.setLanId(regionId);
		}
        ResultVO result = new ResultVO();
        ResultVO<List<ReportCodeStatementsResp>> resultVO = reportCodeStateService.getCodeStatementsReportdc(req);
        if (!resultVO.isSuccess()) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultData("失败：" + resultVO.getResultMsg());
            deliveryGoodsResNberExcel.outputResponse(response, resultVO);
            return result;
        }
        List<ReportCodeStatementsResp> data = resultVO.getResultData();
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("mktResInstNbr", "串码"));
        orderMap.add(new ExcelTitleName("storageType", "在库状态"));
        orderMap.add(new ExcelTitleName("mktResInstType", "串码类型"));
        orderMap.add(new ExcelTitleName("sourceType", "串码来源"));
        orderMap.add(new ExcelTitleName("productType", "产品类型"));
        orderMap.add(new ExcelTitleName("brandId", "品牌"));
        orderMap.add(new ExcelTitleName("productBaseName", "产品名称"));
        orderMap.add(new ExcelTitleName("productName", "产品型号"));
        orderMap.add(new ExcelTitleName("productCode", "产品编码"));
        orderMap.add(new ExcelTitleName("orderId", "订单编号"));
        orderMap.add(new ExcelTitleName("createTime", "下单时间"));
        orderMap.add(new ExcelTitleName("supplierName", "供货商名称"));
        orderMap.add(new ExcelTitleName("supplierCode", "供货商编码"));
        orderMap.add(new ExcelTitleName("partnerName", "零售商名称"));
        orderMap.add(new ExcelTitleName("partnerCode", "店中商编码"));
        orderMap.add(new ExcelTitleName("cityId", "店中商所属地市"));
        orderMap.add(new ExcelTitleName("countyId", "店中商所属区县"));
        orderMap.add(new ExcelTitleName("businessEntityName", "店中商所属经营主体名称"));
        orderMap.add(new ExcelTitleName("receiveTime", "入库时间"));
        orderMap.add(new ExcelTitleName("outTime", "出库时间"));
        orderMap.add(new ExcelTitleName("stockAge", "库龄"));
        orderMap.add(new ExcelTitleName("day30", "是否超过30天久库存"));
        orderMap.add(new ExcelTitleName("day60", "是否超过60天久库存"));
        orderMap.add(new ExcelTitleName("day90", "是否超过90天久库存"));
        orderMap.add(new ExcelTitleName("destMerchantId", "串码流向"));
        orderMap.add(new ExcelTitleName("destCityId", "串码流向所属地市"));
        orderMap.add(new ExcelTitleName("destCountyId", "串码流向所属区县"));
        orderMap.add(new ExcelTitleName("selfRegStatus", "自注册状态"));

        //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
        		orderMap, "串码");
        deliveryGoodsResNberExcel.exportExcel("串码明细报表导出", workbook, response);
        
        return deliveryGoodsResNberExcel.uploadExcel(workbook);
    }
    
}
