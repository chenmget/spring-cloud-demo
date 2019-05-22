package com.iwhalecloud.retail.web.controller.b2b.cgmanage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductsPageReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductPageResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.UpdateCorporationPriceReq;
import com.iwhalecloud.retail.order2b.service.GovernmentPriceManageService;
import com.iwhalecloud.retail.order2b.service.JyPurApplyService;
import com.iwhalecloud.retail.order2b.service.PurApplyService;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.report.dto.response.ReportCodeStatementsResp;
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

/**
 * @author liweisong
 * @date 2019-04-16
 * 采购申请管理平台
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/cgmanage/price")
public class GovernmentPriceManageController extends BaseController {

	@Reference
    private ProductService productService;
	
	@Reference
    private MerchantRulesService merchantRulesService;
	
	@Reference
    private PurApplyService purApplyService;
	
	@Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;
	
	@ApiOperation(value = "导出分页查询产品", notes = "导出条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="dcSearchPriceApply")
    @UserLoginToken
    public void dcSearchPriceApply(@RequestBody ProductsPageReq req, HttpServletResponse response) {
		req.setPageNo(1);
        //数据量控制在1万条
        req.setPageSize(60000);
        Boolean isAdminType = UserContext.isAdminType();
        String merchantId = null;
        ResultVO<Page<ProductPageResp>> productPageRespPage = null ;
        Integer userFounder = UserContext.getUser().getUserFounder();
        if (UserContext.getUserOtherMsg() != null && UserContext.getUserOtherMsg().getMerchant() != null && !isAdminType && SystemConst.USER_FOUNDER_8 != userFounder) {
            merchantId = UserContext.getUserOtherMsg().getMerchant().getMerchantId();
        }else if(isAdminType){
            // 管理员查看所有
            productPageRespPage = productService.selectPageProductAdmin(req);
        }else if(SystemConst.USER_FOUNDER_8 == userFounder){
            // 厂商查看自己的产品
            merchantId = UserContext.getUserOtherMsg().getMerchant().getMerchantId();
            req.setManufacturerId(merchantId);
            productPageRespPage = productService.selectPageProductAdmin(req);
        }

        // 供应商、零售商
        ResultVO<List<String>> productIdListVO = merchantRulesService.getProductAndBrandPermission(merchantId);
        log.info("GoodsProductB2BController.selectPageProductAdmin.getProductAndBrandPermission req={}, merchantId={}", merchantId, JSON.toJSONString(productIdListVO));
        if (productIdListVO.isSuccess() && !CollectionUtils.isEmpty(productIdListVO.getResultData())) {
            // // 设置机型权限
            List<String> productIdList = productIdListVO.getResultData();
            List<String> originProductList = req.getProductIdList();
            if (!CollectionUtils.isEmpty(originProductList)) {
                String nullListValue = "null";
                originProductList = originProductList.stream().filter(t -> productIdList.contains(t)).collect(Collectors.toList());
                originProductList = CollectionUtils.isEmpty(originProductList) ? Lists.newArrayList(nullListValue) : originProductList;
                req.setProductIdList(originProductList);
            }else {
                req.setProductIdList(productIdList);
            }
            productPageRespPage = productService.selectPageProductAdmin(req);
        }
        
        List<ProductPageResp> list = productPageRespPage.getResultData().getRecords();
        log.info("GoodsProductB2BController.selectPageProductAdmin req={}, resp={}", JSON.toJSONString(req), JSON.toJSONString(list));
        
        List<ProductPageResp> data = productPageRespPage.getResultData().getRecords();
        
        
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("typeName", "产品类型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("unitTypeName", "产品型号"));
        orderMap.add(new ExcelTitleName("color", "颜色"));
        orderMap.add(new ExcelTitleName("memory", "内存版本"));
        orderMap.add(new ExcelTitleName("sn", "营销资源编码"));
        orderMap.add(new ExcelTitleName("cost", "零售价格"));
        orderMap.add(new ExcelTitleName("corporationPrice", "政企价格"));
        orderMap.add(new ExcelTitleName("purchaseType", "采购类型"));
        orderMap.add(new ExcelTitleName("effDate", "产品生效期"));
        orderMap.add(new ExcelTitleName("expDate", "产品失效期"));
        orderMap.add(new ExcelTitleName("manufacturerName", "所属厂家"));
        
        
      //创建Excel
        Workbook workbook = new HSSFWorkbook();
//      //创建orderItemDetail
        deliveryGoodsResNberExcel.builderOrderExcel(workbook, data,
        		orderMap, "政企价格管理");
        deliveryGoodsResNberExcel.exportExcel("政企价格管理",workbook,response);
      
    }
	
	
	@ApiOperation(value = "修改价格操作", notes = "修改价格操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/updatePrice")
    public ResultVO updatePrice(@RequestBody UpdateCorporationPriceReq req) {
		return purApplyService.updatePrice(req);
    }
	
	
	
}
