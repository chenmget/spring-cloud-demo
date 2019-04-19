package com.iwhalecloud.retail.web.controller.b2b.cgmanage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
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
import com.iwhalecloud.retail.order2b.dto.response.purapply.ApplyHeadResp;
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
	
	@ApiOperation(value = "提出采购申请单头", notes = "提出采购申请单头")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
	@GetMapping(value="/tcProcureApplybefore")
    public ApplyHeadResp tcProcureApplybefore() {
		UserDTO user = UserContext.getUser();
		ApplyHeadResp applyHeadResp = purApplyService.hqShenQingDaoHao();
		String relCode = user.getRelCode();//申请人工号    写表的
		String applyMerchantCode = user.getUserName();//申请人名称  展示的
		String lanId = user.getLanId();//申请地市  写表的
		String applyAdress = purApplyService.hqDiShiBuMen(lanId); //申请地市   展示的
		String regionId = user.getRegionId();//申请部门  //写表的
		String applyDepartment = purApplyService.hqDiShiBuMen(regionId);//申请部门   展示的
		applyHeadResp.setApplyAdress(applyAdress);
		applyHeadResp.setApplyDepartment(applyDepartment);
		applyHeadResp.setApplyMerchantCode(applyMerchantCode);
		applyHeadResp.setRelCode(relCode);
		applyHeadResp.setLanId(lanId);
		applyHeadResp.setRegionId(regionId);
		applyHeadResp.setApplyId(applyHeadResp.getApplyCode());
		return applyHeadResp;
    }
	
	@ApiOperation(value = "提出采购申请单", notes = "提出采购申请单")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/tcProcureApply")
    public void tcProcureApply(@RequestBody ProcureApplyReq req) {
		String isSave = req.getIsSave();
		String statusCd = "10";
		//情况一，默认是保存,状态就是10，待提交
		//情况二，如果是提交，状态就是20，待审核(分表里面是否有记录)
		if("2".equals(isSave)){
			String applyId = req.getApplyId();
			int isHaveSave = purApplyService.isHaveSave(applyId);
			if(isHaveSave != 0){
				purApplyService.updatePurApply(applyId);
				return;
			}
			statusCd = "20";
		}
		String createStaff = UserContext.getUser().getUserId();
		//获取供应商ID和申请商家ID
		String merchantCode = req.getMerchantCode();
		String applyMerchantCode = req.getApplyMerchantCode();
		String supplier = purApplyService.getMerchantId(merchantCode);
		String applyMerchantId = purApplyService.getMerchantId(applyMerchantCode);
		Date date = new Date();
		String createDate = date.toLocaleString();//创建时间
		String updateStaff =UserContext.getUser().getUserId();
		String updateDate = date.toLocaleString();
		String statusDate = date.toLocaleString();
		req.setStatusCd(statusCd);
		req.setCreateStaff(createStaff);
		req.setCreateDate(createDate);
		req.setUpdateStaff(updateStaff);
		req.setUpdateDate(updateDate);
		req.setStatusDate(statusDate);
		req.setSupplier(supplier);
		req.setApplyMerchantId(applyMerchantId);
		req.setFileId(req.getApplyId());
		List<AddProductReq> list = req.getAddProductReq();
		for(int i=0;i<list.size();i++){
			AddProductReq addProductReq = list.get(i);
			addProductReq.setApplyItemId(req.getApplyId());
			addProductReq.setApplyId(req.getApplyId());
			addProductReq.setStatusCd("1000");
			addProductReq.setCreateStaff(createStaff);
			addProductReq.setCreateDate(createDate);
			addProductReq.setUpdateStaff(updateStaff);
			addProductReq.setUpdateDate(updateDate);
			addProductReq.setStatusDate(statusDate);
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
	
	@ApiOperation(value = "查询采购申请单报表的查看操作", notes = "查询采购申请单报表的查看操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/ckApplyData")
	public ProcureApplyReq ckApplyData(@RequestBody PurApplyReq req){
		//获取申请单跟附件
		ProcureApplyReq procureApplyReq1 = purApplyService.ckApplyData1(req);
		//获取添加的产品信息
		List<AddProductReq> procureApplyReq2 = purApplyService.ckApplyData2(req);
		procureApplyReq1.setAddProductReq(procureApplyReq2);
		return procureApplyReq1;
	}
	
}
