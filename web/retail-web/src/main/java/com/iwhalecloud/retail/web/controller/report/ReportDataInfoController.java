package com.iwhalecloud.retail.web.controller.report;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.dto.response.ProductListAllResp;
import com.iwhalecloud.retail.report.dto.response.ReportDeSaleDaoResq;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;
import com.iwhalecloud.retail.report.service.IReportDataInfoService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.RegionsListReq;
import com.iwhalecloud.retail.system.dto.response.RegionsGetResp;
import com.iwhalecloud.retail.system.service.RegionsService;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.controller.system.RegionController;
import com.iwhalecloud.retail.web.interceptor.UserContext;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;


/**
 * @author liweisong
 * @date 2019-02-18
 * 门店报表
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/reportDataInfo")
public class ReportDataInfoController extends BaseController {

    @Reference
    private IReportDataInfoService iReportDataInfoService;
    
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
    public ResultVO<Page<ReportStorePurchaserResq>> getStorePurchaserReport(@RequestBody ReportStorePurchaserReq req) {
		String userType=req.getUserType();
		//省公司，地市，零售商三种权限
		if(userType!=null && !userType.equals("") && "2".equals(userType)){//地市管理员
			String regionId = UserContext.getUser().getRegionId();
			req.setLanId(regionId);
		}
		if(userType!=null && !userType.equals("") && "3".equals(userType)){//零售商
			String retailerCode=UserContext.getUser().getRelCode();
			req.setRetailerCode(retailerCode);
		}
		if(!"1".equals(userType) && !"2".equals(userType) && !"3".equals(userType)){
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
		return iReportDataInfoService.getStorePurchaserReport(req);
    }
	
	@ApiOperation(value = "区县查询", notes = "区县查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
	@GetMapping(value="/getRegionIdForCity")
	public ResultVO<List<RegionsGetResp>> getRegionIdForCity() {
		RegionsListReq req = new RegionsListReq();
		String regionId = UserContext.getUser().getRegionId();
		//regionId = "430100";
		//String lanId = UserContext.getUser().getLanId();//430100
		if(StringUtils.isNotEmpty(regionId)){
			req.setRegionParentId(regionId);	
		}else{
			req.setRegionParentId("430000");
		}
		return regionService.listRegions(req);
    }
	
	
	@ApiOperation(value = "查询用户角色", notes = "查询用户角色")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
	@GetMapping(value="/getUerRoleForView")
    public ResultVO<List<ReportStorePurchaserResq>> getUerRoleForView() {
		ReportStorePurchaserReq req = new ReportStorePurchaserReq();
		String userId = UserContext.getUser().getUserId();
		req.setUserId(userId);
		return iReportDataInfoService.getUerRoleForView(req);
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
    public ResultVO StorePurchaserReportExport(@RequestBody ReportStorePurchaserReq req) {
		String userType=req.getUserType();
		if(userType!=null && !userType.equals("") && "3".equals(userType)){//零售商
			String retailerCode=UserContext.getUser().getRelCode();
			req.setRetailerCode(retailerCode);
		}
		if(userType!=null && !userType.equals("") && "2".equals(userType)){//地市管理员
			String regionId = UserContext.getUser().getRegionId();
			req.setLanId(regionId);
		}
		if(!"1".equals(userType) && !"2".equals(userType) && !"3".equals(userType)){
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
        ResultVO<List<ReportStorePurchaserResq>> resultVO = iReportDataInfoService.getStorePurchaserReportdc(req);
        if (!resultVO.isSuccess()) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultData("失败：" + resultVO.getResultMsg());
            return result;
        }
        List<ReportStorePurchaserResq> data = resultVO.getResultData();
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        //机型的导出
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("productBaseName", "机型"));
        orderMap.add(new ExcelTitleName("partnerCode", "零售商编码"));
        orderMap.add(new ExcelTitleName("partnerName", "零售商名称"));
        orderMap.add(new ExcelTitleName("businessEntityName", "所属经营主体"));
        orderMap.add(new ExcelTitleName("typeId", "产品类型"));
        orderMap.add(new ExcelTitleName("cityId", "所属城市"));
        orderMap.add(new ExcelTitleName("countryId", "所属区县"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("redStatus", "库存预警"));
        orderMap.add(new ExcelTitleName("theTotalInventory", "入库总量"));
        orderMap.add(new ExcelTitleName("theTotalOutbound", "出库总量"));
        orderMap.add(new ExcelTitleName("purchaseNum", "交易入库量"));
        orderMap.add(new ExcelTitleName("manualNum", "手工入库量"));
        orderMap.add(new ExcelTitleName("totalSalesNum", "总销售量"));
        orderMap.add(new ExcelTitleName("manualSalesNum", "手工销售量"));
        orderMap.add(new ExcelTitleName("crmcontractNum", "CRM合约销量"));
        orderMap.add(new ExcelTitleName("registerNum", "自注册销量"));
        orderMap.add(new ExcelTitleName("returnNum", "退库量"));
        orderMap.add(new ExcelTitleName("averageDailySales", "近7天日均销量"));
        orderMap.add(new ExcelTitleName("stockNum", "库存量"));
        orderMap.add(new ExcelTitleName("stockTurnover", "库存周转率"));
        
        //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
        		orderMap, "串码");
        return deliveryGoodsResNberExcel.uploadExcel(workbook);
    }

    
}

