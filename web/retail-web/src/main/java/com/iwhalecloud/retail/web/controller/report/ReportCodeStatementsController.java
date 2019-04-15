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
		//省管理，地市，零售商，供应商，厂商
		if(userType!=null && !"".equals(userType) && "2".equals(userType)){
			String lanId=UserContext.getUser().getRegionId();
			req.setLanId(lanId);
		}
		if(userType!=null && !"".equals(userType) && "3".equals(userType)){
			String lssCode = UserContext.getUser().getRelCode();
			req.setLssCode(lssCode);
		}
		if(userType!=null && !"".equals(userType) && "4".equals(userType)){
			String gysCode = UserContext.getUser().getRelCode();
			req.setGysCode(gysCode);
		}
		if(userType!=null &&!"".equals(userType) && "5".equals(userType)){
			String manufacturerCode = UserContext.getUser().getRelCode();
			req.setManufacturerCode(manufacturerCode);
		}
		if(!"1".equals(userType) && !"2".equals(userType) && !"3".equals(userType) && !"4".equals(userType) && !"5".equals(userType)){
			req.setLanId("999");
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
    public ResultVO StorePurchaserReportExport(@RequestBody ReportCodeStatementsReq req) {
    	String userType=req.getUserType();
		//省管理，地市，零售商，供应商，厂商
		if(userType!=null && !"".equals(userType) && "2".equals(userType)){
			String lanId=UserContext.getUser().getRegionId();
			req.setLanId(lanId);
		}
		if(userType!=null && !"".equals(userType) && "3".equals(userType)){
			String lssCode = UserContext.getUser().getRelCode();
			req.setLssCode(lssCode);
		}
		if(userType!=null && !"".equals(userType) && "4".equals(userType)){
			String gysCode = UserContext.getUser().getRelCode();
			req.setGysCode(gysCode);
		}
		if(userType!=null &&!"".equals(userType) && "5".equals(userType)){
			String manufacturerCode = UserContext.getUser().getRelCode();
			req.setManufacturerCode(manufacturerCode);
		}
		if(!"1".equals(userType) && !"2".equals(userType) && !"3".equals(userType) && !"4".equals(userType) && !"5".equals(userType)){
			req.setLanId("999");
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
        ResultVO<List<ReportCodeStatementsResp>> resultVO = reportCodeStateService.getCodeStatementsReportdc(req);
        if (!resultVO.isSuccess()) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultData("失败：" + resultVO.getResultMsg());
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
        return deliveryGoodsResNberExcel.uploadExcel(workbook);
    }
    
}
