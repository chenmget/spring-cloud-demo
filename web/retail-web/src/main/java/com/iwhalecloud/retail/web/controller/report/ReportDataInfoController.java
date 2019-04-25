package com.iwhalecloud.retail.web.controller.report;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.alibaba.fastjson.JSON;
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
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ExcelToNbrUtils;
import com.iwhalecloud.retail.web.controller.partner.utils.ExcelToMerchantListUtils;
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
	@UserLoginToken
    public ResultVO<Page<ReportStorePurchaserResq>> getStorePurchaserReport(@RequestBody ReportStorePurchaserReq req) {
		//1超级管理员 2普通管理员 3零售商(门店、店中商) 4省包供应商 5地包供应商 6 代理商店员 7经营主体 8厂商 \n12 终端公司管理人员 24 省公司市场部管理人员',
		String legacyAccount = req.getLegacyAccount();//判断是云货架还是原系统的零售商，默认云货架
		String retailerCodes = req.getRetailerCode();//是否输入了零售商账号
		String userType=req.getUserType();
		//String userType = UserContext.getUser().getUserFounder()+"";
		if("2".equals(legacyAccount) && !"3".equals(userType)){
			retailerCodes = iReportDataInfoService.retailerCodeBylegacy(retailerCodes);
			req.setRetailerCode(retailerCodes);
		}
		if(userType!=null && !userType.equals("") && "3".equals(userType)){//零售商
			String retailerCode=UserContext.getUser().getRelCode();
			req.setRetailerCode(retailerCode);
		}
		if(userType!=null && !userType.equals("") && "2".equals(userType)){//地市管理员
			String regionId = UserContext.getUser().getLanId();
			req.setLanId(regionId);
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
    @UserLoginToken
    public void StorePurchaserReportExport(@RequestBody ReportStorePurchaserReq req, HttpServletResponse response) {
    	log.info("ReportDataInfoController.StorePurchaserReportExport() ", JSON.toJSONString(req));
        //req.setPageNo(1);
        //数据量控制在1万条
        //req.setPageSize(10000);
    	//1超级管理员 2普通管理员 3零售商(门店、店中商) 4省包供应商 5地包供应商 6 代理商店员 7经营主体 8厂商 \n12 终端公司管理人员 24 省公司市场部管理人员',
		String legacyAccount = req.getLegacyAccount();//判断是云货架还是原系统的零售商，默认云货架
		String retailerCodes = req.getRetailerCode();//是否输入了零售商账号
		String userType=req.getUserType();
		//String userType = UserContext.getUser().getUserFounder()+"";
		if("2".equals(legacyAccount) && !"3".equals(userType)){
			retailerCodes = iReportDataInfoService.retailerCodeBylegacy(retailerCodes);
			req.setRetailerCode(retailerCodes);
		}
		if(userType!=null && !userType.equals("") && "3".equals(userType)){//零售商
			String retailerCode=UserContext.getUser().getRelCode();
			req.setRetailerCode(retailerCode);
		}
		if(userType!=null && !userType.equals("") && "2".equals(userType)){//地市管理员
			String regionId = UserContext.getUser().getLanId();
			req.setLanId(regionId);
		}
		
        ResultVO<List<ReportStorePurchaserResq>> resultVO = iReportDataInfoService.getStorePurchaserReportdc(req);
        
        List<ReportStorePurchaserResq> data = resultVO.getResultData();
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("productBaseName", "机型"));
        orderMap.add(new ExcelTitleName("partnerName", "零售商名称"));
        orderMap.add(new ExcelTitleName("partnerCode", "零售商编码"));
        orderMap.add(new ExcelTitleName("businessEntityName", "所属经营主体"));
        
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("typeId", "产品类型"));
        orderMap.add(new ExcelTitleName("theTotalInventory", "入库总量"));
        orderMap.add(new ExcelTitleName("theTotalOutbound", "出库总量"));
        orderMap.add(new ExcelTitleName("stockTotalNum", "库存总量"));
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
        orderMap.add(new ExcelTitleName("inventoryWarning", "库存预警"));
        
//      //创建orderItemDetail
//        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
//        		orderMap, "门店进销存机型报表");
//        return deliveryGoodsResNberExcel.uploadExcel(workbook);
        OutputStream output = null;
        
        try{
            //创建Excel
            String fileName = "门店进销存机型报表";
//            ExcelToNbrUtils.builderOrderExcel(workbook, data, orderMap, false);
            ExcelToMerchantListUtils.builderOrderExcel(workbook, data, orderMap);
            output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            workbook.write(output);
//            output.close();
          
        }catch (Exception e){
            log.error("门店进销存机型报表导出失败",e);
        } finally {
            if (null != output){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
}
