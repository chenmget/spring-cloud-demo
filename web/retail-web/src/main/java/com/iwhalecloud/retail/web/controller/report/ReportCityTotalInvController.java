package com.iwhalecloud.retail.web.controller.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.report.dto.request.ReportCityTotalInvReq;
import com.iwhalecloud.retail.report.dto.response.ReportCityTotalInvResp;
import com.iwhalecloud.retail.report.service.ReportCityTotalInvService;
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
 * @author lipeng
 * @date 2019-06-10
 * 地市总进销存报表
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/reportData")
public class ReportCityTotalInvController extends BaseController {

    @Reference
    private ReportCityTotalInvService reportCityTotalInvService;
    
    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;

	@ApiOperation(value = "查询地市总进销存报表", notes = "查询地市总进销存报表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/getCityTotalInvList")
	@UserLoginToken
    public ResultVO<Page<ReportCityTotalInvResp>> getCityTotalInvList(@RequestBody ReportCityTotalInvReq req) {
		String userType=req.getUserType();
		if(userType!=null&&!userType.equals("")&&userType.equals("3")){
			String merchantCode=UserContext.getUser().getRelCode();
			req.setMerchantCode(merchantCode);
		}
		if(userType!=null&&!userType.equals("")&&userType.equals("2")){
			String lanId=UserContext.getUser().getRegionId();
			req.setCityId(lanId);
		}
        return reportCityTotalInvService.getCityTotalInvList(req);
    }

	
	  /**
     * 导出订单
     */
    @ApiOperation(value = "导出地市总进销存报表", notes = "导出地市总进销存报表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/exportCityTotalInvListReport")
    @UserLoginToken
    public void exportCityTotalInvListReport(@RequestBody ReportCityTotalInvReq req, HttpServletResponse response) {
		String userType=req.getUserType();
    	//1超级管理员 2普通管理员 3零售商(门店、店中商) 4省包供应商 5地包供应商 6 代理商店员 7经营主体 8厂商 \n12 终端公司管理人员 24 省公司市场部管理人员',
		if(userType!=null&&!userType.equals("")&&userType.equals("3")){
			String merchantCode=UserContext.getUser().getRelCode();
			req.setMerchantCode(merchantCode);
		}
		if(userType!=null&&!userType.equals("")&&userType.equals("2")){
			String lanId=UserContext.getUser().getRegionId();
			req.setCityId(lanId);
		}

        ResultVO<List<ReportCityTotalInvResp>> resultVO = reportCityTotalInvService.exportCityTotalInvListReport(req);

        ResultVO result = new ResultVO();
        if (!resultVO.isSuccess()) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultMsg(resultVO.getResultMsg());
            deliveryGoodsResNberExcel.outputResponse(response,resultVO);
            return;
        }
        
        List<ReportCityTotalInvResp> data = resultVO.getResultData();
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("cityName", "地市"));
        orderMap.add(new ExcelTitleName("countyName", "区县"));
        orderMap.add(new ExcelTitleName("productName", "机型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("priceLevel", "机型档位"));
        orderMap.add(new ExcelTitleName("totalInNum", "入库总量"));
        orderMap.add(new ExcelTitleName("totalOutNum", "出库总量"));
        orderMap.add(new ExcelTitleName("stockNum", "库存总量"));
        orderMap.add(new ExcelTitleName("purchaseNum", "交易进货量"));
        orderMap.add(new ExcelTitleName("purchaseAmount", "交易进货金额"));
        orderMap.add(new ExcelTitleName("manualNum", "手工入库量"));
        orderMap.add(new ExcelTitleName("transInNum", "调拨入库量"));
        orderMap.add(new ExcelTitleName("totalSalesNum", "总销售量"));
        orderMap.add(new ExcelTitleName("manualSalesNum", "手工核销量"));
        orderMap.add(new ExcelTitleName("crmContractNum", "CRM合约销量"));
        orderMap.add(new ExcelTitleName("registerNum", "自注册销量"));
        orderMap.add(new ExcelTitleName("avg7", "近7天日均销量"));
        orderMap.add(new ExcelTitleName("stockAmount", "库存金额"));
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,orderMap, "地市总进销存报表");
        deliveryGoodsResNberExcel.exportExcel("地市总进销存报表",workbook,response);
       
    }
	
}
