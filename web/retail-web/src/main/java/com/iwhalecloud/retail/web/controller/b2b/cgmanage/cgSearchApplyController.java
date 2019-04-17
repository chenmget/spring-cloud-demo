package com.iwhalecloud.retail.web.controller.b2b.cgmanage;

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
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.AddProductReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.ProcureApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.service.PurApplyService;
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
 * @date 2019-04-16
 * 门店报表
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/cgmanage")
public class cgSearchApplyController extends BaseController {

    @Reference
    private PurApplyService purApplyService;
    
    
	@ApiOperation(value = "查询采购申请单报表", notes = "查询采购申请单报表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/cgSearchApply")
    public ResultVO<Page<PurApplyResp>> cgSearchApply(@RequestBody PurApplyReq req) {
		String userType=req.getUserType();
		if(userType!=null && !userType.equals("") && "2".equals(userType)){//地市管理员
			String regionId = UserContext.getUser().getRegionId();
			req.setLanId(regionId);
		}
		return purApplyService.cgSearchApply(req);
    }
	
	@ApiOperation(value = "提出采购申请单", notes = "提出采购申请单")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/tcProcureApply")
    public void tcProcureApply(@RequestBody ProcureApplyReq req) {
		List<AddProductReq> list = req.getAddProductReq();
		for(int i=0;i<list.size();i++){
			AddProductReq addProductReq = list.get(i);
			//写表PUR_APPLY_ITEM(采购申请单项)
			purApplyService.crPurApplyItem(addProductReq);
		}
		//写表PUR_APPLY_FILE(采购申请单附件表)
		purApplyService.crPurApplyFile(req);
		//写表PUR_APPLY(采购申请单)
		purApplyService.tcProcureApply(req);
		
    }
	
	@ApiOperation(value = "查询采购申请单报表的删除操作", notes = "查询采购申请单报表的删除操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/delSearchApply")
    public void delSearchApply(@RequestBody PurApplyReq req) {
		purApplyService.delSearchApply(req);
    }
}
