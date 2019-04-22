package com.iwhalecloud.retail.web.controller.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.report.dto.request.ReportStInvCityDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.dto.request.RptPartnerOperatingDay;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;
import com.iwhalecloud.retail.report.service.IReportDataInfoService;
import com.iwhalecloud.retail.report.service.ReportStInvCityService;
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

import java.util.ArrayList;
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

    @Reference
    private IReportDataInfoService iReportDataInfoService;

    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;

    @ApiOperation(value = "查询报表", notes = "查询报表")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/getStoreInvoicingCityList")
    public ResultVO<Page<RptPartnerOperatingDay>> getReportStSaleList(@RequestBody ReportStInvCityDaoReq req) {

        ReportStorePurchaserReq req1 = new ReportStorePurchaserReq();
        String userId = UserContext.getUser().getUserId();
        req1.setUserId(userId);
        ResultVO<List<ReportStorePurchaserResq>> result = iReportDataInfoService.getUerRoleForView(req1);
        if("2".equals(result.getResultData().get(0).getUserType()) || "".equals(req.getCity())){
            req.setCity(UserContext.getUser().getLanId());
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
    public ResultVO storeInvoicingCityReportExport(@RequestBody ReportStInvCityDaoReq req) {

        ReportStorePurchaserReq req1 = new ReportStorePurchaserReq();
        String userId = UserContext.getUser().getUserId();
        req1.setUserId(userId);
        ResultVO<List<ReportStorePurchaserResq>> result0 = iReportDataInfoService.getUerRoleForView(req1);
        if("2".equals(result0.getResultData().get(0).getUserType()) || "".equals(req.getCity())){
            req.setCity(UserContext.getUser().getLanId());
        }

        ResultVO result = new ResultVO();
        ResultVO<List<RptPartnerOperatingDay>> resultVO = reportStInvCityService.getReportStInvCityListExport(req);

        if (!resultVO.isSuccess()) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultData("失败：" + resultVO.getResultMsg());
            return result;
        }

        List<RptPartnerOperatingDay> data = resultVO.getResultData();

        //创建Excel
        Workbook workbook = new HSSFWorkbook();
        List<ExcelTitleName> orderMap = new ArrayList<>();

        orderMap.add(new ExcelTitleName("cityId", "地市"));
        orderMap.add(new ExcelTitleName("countyId", "区县"));
        orderMap.add(new ExcelTitleName("totalInNum", "入库总量"));
        orderMap.add(new ExcelTitleName("totalOutNum", "出库总量"));
        orderMap.add(new ExcelTitleName("purchaseNum", "交易入库量"));

        orderMap.add(new ExcelTitleName("manualNum", "手工入库量"));
        orderMap.add(new ExcelTitleName("sellNum", "总销售量"));
        orderMap.add(new ExcelTitleName("uncontractNum", "手工核销"));
        orderMap.add(new ExcelTitleName("contractNum", "CRM销量"));
        orderMap.add(new ExcelTitleName("registerNum", "自注册激活量"));

        orderMap.add(new ExcelTitleName("transOutNum", "调拨出库量"));
        orderMap.add(new ExcelTitleName("returnNum", "退库量"));
        orderMap.add(new ExcelTitleName("avg7", "近7天日均销量"));
        orderMap.add(new ExcelTitleName("stockNum", "库存量"));
        orderMap.add(new ExcelTitleName("turnoverRate", "库存周转率"));

        orderMap.add(new ExcelTitleName("warnStatus", "预警状态"));

        // 创建excel
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data, orderMap, "门店进销存地市报表");
        return deliveryGoodsResNberExcel.uploadExcel(workbook);
    }

    /**
     * 导出按钮
     */
    @ApiOperation(value = "导出", notes = "导出报表数据")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/getUserLandId")
    public ResultVO<Map<String, Object>> getUserLandId() {
        Map<String, Object> result = new HashMap<String, Object>();
        ResultVO<Map<String, Object>> resultVO = new ResultVO<Map<String, Object>>();

        String landId = UserContext.getUser().getLanId();
        result.put("landId", landId);

        resultVO.setResultCode("0000");
        resultVO.setResultMsg("success");
        resultVO.setResultData(result);
        return resultVO;
    }

}
