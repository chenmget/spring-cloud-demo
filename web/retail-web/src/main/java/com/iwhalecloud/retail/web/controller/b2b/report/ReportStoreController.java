package com.iwhalecloud.retail.web.controller.b2b.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.report.dto.request.ReportStSaleDaoReq;
import com.iwhalecloud.retail.report.dto.response.ReportStSaleDaoResp;
import com.iwhalecloud.retail.report.service.ReportStoreService;
import com.iwhalecloud.retail.system.common.SystemConst;
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
 * @author zwl
 * @date 2018-11-09
 * 门店进销存明细报表
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/reportStore")
public class ReportStoreController extends BaseController {

    @Reference
    private ReportStoreService reportStoreService;
    
    @Reference
	private MerchantService merchantService;
    
    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;
    
	@ApiOperation(value = "查询报表", notes = "查询报表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/getReportStSaleList")
	@UserLoginToken
    public ResultVO<Page<ReportStSaleDaoResp>> getReportStSaleList(@RequestBody ReportStSaleDaoReq req) {
		log.info("****************************ReportStoreController getReportStSaleList    req={}",JSON.toJSONString(req));
		int userType=UserContext.getUser().getUserFounder();
		List<String> list = new ArrayList<String>();
		if (userType == SystemConst.USER_FOUNDER_9) {//地市管理员
			list.add(UserContext.getUser().getLanId());
			req.setLanIdList(list);
		} else if (userType == SystemConst.USER_FOUNDER_3 ) {//零售商 （只能查看自己的仓库）
			MerchantDTO merchantDTO = merchantService.getMerchantInfoById(UserContext.getUser().getRelCode());
			if ( merchantDTO == null) {
				return ResultVO.error("当前用户 没有商家编码");
			}
			req.setPartnerCode(merchantDTO.getMerchantCode());
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
    @UserLoginToken
    public void cjStorePurchaserReportExport(@RequestBody ReportStSaleDaoReq req, HttpServletResponse response) {
    	req.setPageNo(1);
		req.setPageSize(50000);
    	int userType=UserContext.getUser().getUserFounder();
    	List<String> list = new ArrayList<String>();
		if (userType == SystemConst.USER_FOUNDER_9) {//地市管理员
			list.add(UserContext.getUser().getLanId());
			req.setLanIdList(list);
		} else if (userType == SystemConst.USER_FOUNDER_3 ) {//零售商 （只能查看自己的仓库）
			MerchantDTO merchantDTO = merchantService.getMerchantInfoById(UserContext.getUser().getRelCode());
			if ( merchantDTO == null) {
				return ;
			}
			req.setPartnerCode(merchantDTO.getMerchantCode());
		}
        ResultVO<List<ReportStSaleDaoResp>> resultVO = reportStoreService.getReportStSaleListdc(req);
        ResultVO result = new ResultVO();
        if (!resultVO.isSuccess()) {
            result.setResultCode(ResultCodeEnum.ERROR.getCode());
            result.setResultMsg(resultVO.getResultMsg());
            deliveryGoodsResNberExcel.outputResponse(response,resultVO);
            return;
        }
        List<ReportStSaleDaoResp> data = resultVO.getResultData();
        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("partnerName", "零售商名称"));
        orderMap.add(new ExcelTitleName("partnerCode", "零售商编码"));
        orderMap.add(new ExcelTitleName("businessEntityName", "所属经营主体"));
        orderMap.add(new ExcelTitleName("cityId", "零售商所属地市"));
        orderMap.add(new ExcelTitleName("orgName", "经营单元"));
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("productBaseName", "产品型号"));
        orderMap.add(new ExcelTitleName("typeName", "产品类型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("priceLevel", "机型档位"));
        orderMap.add(new ExcelTitleName("totalInNum", "总入库量"));
        orderMap.add(new ExcelTitleName("totalOutNum", "总出库量"));
        orderMap.add(new ExcelTitleName("stockNum", "总库存量"));
        orderMap.add(new ExcelTitleName("purchaseNum", "交易入库量"));
        orderMap.add(new ExcelTitleName("manualNum", "手工入库量"));
        orderMap.add(new ExcelTitleName("transInNum", "调拨入库量"));
    	orderMap.add(new ExcelTitleName("purchaseAmount", "进货金额"));
    	orderMap.add(new ExcelTitleName("sellNum", "总销售量"));
        orderMap.add(new ExcelTitleName("sellAmount", "总销售额"));
        orderMap.add(new ExcelTitleName("uncontractNum", "手工销售量"));
        orderMap.add(new ExcelTitleName("contractNum", "CRM合约销量"));
        orderMap.add(new ExcelTitleName("registerNum", "自注册销量"));
        orderMap.add(new ExcelTitleName("weekAvgSellNum", "近7天日均销量"));
        orderMap.add(new ExcelTitleName("stockAmount", "库存金额"));
        orderMap.add(new ExcelTitleName("turnoverRate", "库存周转率"));
        orderMap.add(new ExcelTitleName("stockWarning", "库存预警"));
        
      //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
        		orderMap, "门店进销存明细报表");
        deliveryGoodsResNberExcel.exportExcel("门店进销存明细报表",workbook,response);
        
    }

}
