package com.iwhalecloud.retail.web.controller.b2b.cgmanage;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductsPageReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductPageResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyReportResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyStatusReportResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReportReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyStatusReportReq;
import com.iwhalecloud.retail.order2b.service.PurApplyService;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
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
 * @author chenbin
 * @date 2019-06-26
 * 采购申请管理平台
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/cgmanage/report")
public class GovernmentReportManageController extends BaseController {

	@Reference
    private ProductService productService;

	@Reference
    private MerchantRulesService merchantRulesService;
    @Reference
    private PurApplyService purApplyService;
    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;

    @ApiOperation(value = "查询政企省内代收报表", notes = "查询政企省内代收报表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/applySearchReport")
    @UserLoginToken
    public ResultVO<Page<PurApplyReportResp>> applySearchReport(@RequestBody PurApplyReportReq req) {
        //采购单的时候点击查看采购申请单，传申请人ID，apply_code和apply_name项目名称查询
      //  String userId = UserContext.getUserId();
//		String userId = "100028487";
//		PriCityManagerResp login = purApplyService.getLoginInfo(userId);
//		Integer userFounder = UserContext.getUser().getUserFounder();
        //传过来的APPLY_TYPE看

//		String lanId = login.getLanId();
//
//		log.info("1查询采购申请单报表*******************lanId = "+lanId +" **************userFounder = "+userFounder);
//		if(userFounder!=null) {
//			if(9==userFounder){//地市管理员
//				log.info("2查询采购申请单报表*******************lanId = "+lanId +" **************userFounder = "+userFounder);
//				req.setLanId(lanId);
//			}
//		}
//		Boolean isMerchant= UserContext.isMerchant();
//		if(isMerchant==true) {
//			req.setMerchantId(UserContext.getMerchantId());
//			log.info("查询采购申请单报表*******************isMerchant = "+isMerchant +" **************UserContext.getMerchantId() = "+UserContext.getMerchantId());
//		}

//		log.info("查询采购申请单报表入参*******************lanId = "+req.getLanId() );
        return purApplyService.applySearchReport(req);
    }

    @ApiOperation(value = "查询政企省内代收报表导出", notes = "查询政企省内代收报表导出")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/applySearchReportExcel")
    @UserLoginToken
    public void applySearchReportExcel(@RequestBody PurApplyReportReq req, HttpServletResponse response) {
        req.setPageNo(1);
        //数据量控制在1万条
        req.setPageSize(60000);
//        Integer userFounder = UserContext.getUser().getUserFounder();

        // 管理员查看所有
        ResultVO<Page<PurApplyReportResp>> PurApplyReportRespPage = purApplyService.applySearchReport(req);

        List<PurApplyReportResp> data = PurApplyReportRespPage.getResultData().getRecords();

        List<ExcelTitleName> orderMap = new ArrayList<>();

        orderMap.add(new ExcelTitleName("applyCode", "申请单号"));
        orderMap.add(new ExcelTitleName("applyName", "项目名称"));
        orderMap.add(new ExcelTitleName("applyCity", "申请地市"));

        orderMap.add(new ExcelTitleName("merchantName", "供应商名称"));

        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("unitType", "产品型号"));

        orderMap.add(new ExcelTitleName("color", "颜色"));
        orderMap.add(new ExcelTitleName("memory", "内存"));
        orderMap.add(new ExcelTitleName("attrValue1", "容量"));
        orderMap.add(new ExcelTitleName("sn", "产品25位编码"));
        orderMap.add(new ExcelTitleName("purType", "采购类型"));
        orderMap.add(new ExcelTitleName("corporationPrice", "政企销售价"));
        orderMap.add(new ExcelTitleName("mktResInstNbr", "串码"));

        orderMap.add(new ExcelTitleName("applyTime", "采购时间"));

        orderMap.add(new ExcelTitleName("deliveryDate", "发货时间"));
        orderMap.add(new ExcelTitleName("revingDate", "完成时间"));
        orderMap.add(new ExcelTitleName("receiveName", "收货人"));
        orderMap.add(new ExcelTitleName("receiveCity", "收货人"));
        orderMap.add(new ExcelTitleName("receiveAddr", "收货地址"));

        //创建Excel
        Workbook workbook = new HSSFWorkbook();
//      //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
                orderMap, "政企省内代收报表");
        deliveryGoodsResNberExcel.exportExcel("政企省内代收报表",workbook,response);

    }

    @ApiOperation(value = "政企省内代收项目状态报表", notes = "政企省内代收项目状态报表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/applyStatusSearchReport")
    @UserLoginToken
    public ResultVO<Page<PurApplyStatusReportResp>> applyStatusSearchReport(@RequestBody PurApplyStatusReportReq req) {

        return purApplyService.applyStatusSearchReport(req);
    }

    @ApiOperation(value = "政企省内代收项目状态报表EXCEL导出", notes = "政企省内代收项目状态报表EXCEL导出")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/applyStatusSearchReportExcel")
    @UserLoginToken
    public void applyStatusSearchReportExcel(@RequestBody PurApplyStatusReportReq req, HttpServletResponse response) {
        req.setPageNo(1);
        //数据量控制在1万条
        req.setPageSize(60000);
//        Integer userFounder = UserContext.getUser().getUserFounder();

        // 管理员查看所有
        ResultVO<Page<PurApplyStatusReportResp>> purApplyStatusReportResp = purApplyService.applyStatusSearchReport(req);

        List<PurApplyStatusReportResp> data = purApplyStatusReportResp.getResultData().getRecords();

        List<ExcelTitleName> orderMap = new ArrayList<>();

        orderMap.add(new ExcelTitleName("applyCode", "申请单号"));
        orderMap.add(new ExcelTitleName("applyName", "项目名称"));
        orderMap.add(new ExcelTitleName("applyCity", "申请地市"));

        orderMap.add(new ExcelTitleName("merchantName", "供应商名称"));

        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("unitType", "产品型号"));

        orderMap.add(new ExcelTitleName("color", "颜色"));
        orderMap.add(new ExcelTitleName("memory", "内存"));
        orderMap.add(new ExcelTitleName("attrValue1", "容量"));
        orderMap.add(new ExcelTitleName("sn", "产品25位编码"));
        orderMap.add(new ExcelTitleName("purType", "采购类型"));

        orderMap.add(new ExcelTitleName("purNum", "数量"));

        orderMap.add(new ExcelTitleName("corporationPrice", "政企销售价"));
        orderMap.add(new ExcelTitleName("statusCd", "项目状态"));


        //创建Excel
        Workbook workbook = new HSSFWorkbook();
//      //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
                orderMap, "政企省内代收项目状态报表");
        deliveryGoodsResNberExcel.exportExcel("政企省内代收项目状态报表",workbook,response);

    }
}
