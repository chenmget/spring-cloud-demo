package com.iwhalecloud.retail.web.controller.b2b.cgmanage;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.purapply.ApplyHeadResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.CkProcureApplyResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PriCityManagerResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.AddFileReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.AddProductReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.ProcureApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.UpdatePurApplyState;
import com.iwhalecloud.retail.order2b.service.PurApplyService;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;
import com.iwhalecloud.retail.report.service.IReportDataInfoService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
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
@RequestMapping("/api/cgmanage")
public class CgSearchApplyController extends BaseController {

    @Reference
    private PurApplyService purApplyService;
    
    @Reference
    private IReportDataInfoService iReportDataInfoService;
    
	@ApiOperation(value = "查询采购申请单和采购单报表", notes = "查询采购申请单和采购单")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/cgSearchApply")
	//@UserLoginToken
    public ResultVO<Page<PurApplyResp>> cgSearchApply(@RequestBody PurApplyReq req) {
		
		//String userId = UserContext.getUserId();
		String userId = "100028487";
		PriCityManagerResp login = purApplyService.getLoginInfo(userId);
		String userType = login.getUserType();
		//传过来的APPLY_TYPE看
		String lanId = login.getLanId();
		
		log.info("查询采购申请单报表*******************lanId = "+lanId +" **************userType = "+userType);
		if("2".equals(userType) || "2" == userType){//地市管理员
			req.setLanId(lanId);
		}
		log.info("查询采购申请单报表入参*******************lanId = "+req.getLanId() );
		return purApplyService.cgSearchApply(req);
    }
	
	@ApiOperation(value = "提出采购申请单头", notes = "提出采购申请单头")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
	@GetMapping(value="/tcProcureApplybefore")
	@UserLoginToken
    public ResultVO<ApplyHeadResp> tcProcureApplybefore() {
		UserDTO user = UserContext.getUser();
//		UserDTO user = new UserDTO();
//		user.setRelCode("CS00011001");
//		user.setUserName("深圳市金立通信设备有限公司");
//		user.setLanId("731");
//		user.setRegionId("73101");
		if(user == null){
			return null ;
		}
		ApplyHeadResp applyHeadResp = purApplyService.hqShenQingDaoHao();
		Date date = new Date();
		String applyCode = date.getTime() + "";
		String relCode = user.getRelCode();//申请人工号    写表的
		String applyMerchantName = user.getUserName();//申请人名称  展示的
		String lanId = user.getLanId();//申请地市  写表的
		String applyAdress = purApplyService.hqDiShiBuMen(lanId); //申请地市   展示的
		String regionId = user.getRegionId();//申请部门  //写表的
		String applyDepartment = purApplyService.hqDiShiBuMen(regionId);//申请部门   展示的
		applyHeadResp.setApplyAddress(applyAdress);
		applyHeadResp.setApplyDepartment(applyDepartment);
		applyHeadResp.setApplyMerchantName(applyMerchantName);
		applyHeadResp.setApplyMerchantCode(relCode);
		applyHeadResp.setLanId(lanId);
		applyHeadResp.setRegionId(regionId);
		applyHeadResp.setApplyCode(applyCode);
		
		return ResultVO.success(applyHeadResp);
    }
	
	@ApiOperation(value = "提出采购申请单", notes = "提出采购申请单")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/tcProcureApply")
	//@UserLoginToken
    public ResultVO tcProcureApply(@RequestBody ProcureApplyReq req) {
		String isSave = req.getIsSave();
		String statusCd = "10";
		Date date = new Date();
		String updateStaff =UserContext.getUserId();
//		String updateStaff ="1";
		String updateDate = date.toLocaleString();
		String statusDate = date.toLocaleString();
		//情况一，默认是保存,状态就是10，待提交
		//情况二，如果是提交，状态就是20，待审核(分表里面是否有记录)
		if("2".equals(isSave)){
			String applyId = req.getApplyId();
			int isHaveSave = purApplyService.isHaveSave(applyId);
			if(isHaveSave != 0){//表里面有记录的话
				UpdatePurApplyState state = new UpdatePurApplyState();
				state.setApplyId(applyId);
				state.setUpdateDate(updateDate);
				state.setUpdateStaff(updateStaff);
				state.setStatusDate(statusDate);	
				purApplyService.updatePurApply(state);
				return ResultVO.success();
			}
			statusCd = "20";
		}
		String createStaff = UserContext.getUserId();
//		String createStaff = "1";
		
		//获取供应商ID和申请商家ID
		String merchantCode = req.getSupplierCode();
		String applyMerchantCode = req.getApplyMerchantCode();
		String supplier = purApplyService.getMerchantId(merchantCode);
		String applyMerchantId = purApplyService.getMerchantId(applyMerchantCode);
		
		String createDate = date.toLocaleString();//创建时间
		
//		String updateStaff ="1";
		
		req.setStatusCd(statusCd);
		req.setCreateStaff(createStaff);
		req.setCreateDate(createDate);
		req.setUpdateStaff(updateStaff);
		req.setUpdateDate(updateDate);
		req.setStatusDate(statusDate);
		req.setSupplier(supplier);
		req.setApplyMerchantId(applyMerchantId);
		List<AddProductReq> list = req.getAddProductReq();
		for(int i=0;i<list.size();i++){
			AddProductReq addProductReq = list.get(i);
			String itemId = purApplyService.hqSeqItemId();
			addProductReq.setApplyItemId(itemId);
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
		
		List<AddFileReq> fileList = req.getAddFileReq();
		if(fileList != null){
			for(int i=0;i<fileList.size();i++){
				AddFileReq addFileReq = fileList.get(i);
				String fileId = purApplyService.hqSeqFileId();
				addFileReq.setFileId(fileId);
				addFileReq.setApplyId(req.getApplyId());
				addFileReq.setCreateStaff(createStaff);
				addFileReq.setCreateDate(createDate);
				addFileReq.setUpdateStaff(updateStaff);
				addFileReq.setUpdateDate(updateDate);
				purApplyService.crPurApplyFile(addFileReq);
			}
		}

		purApplyService.tcProcureApply(req);
		return ResultVO.success();
    }
	
	@ApiOperation(value = "查询采购申请单报表的删除操作", notes = "查询采购申请单报表的删除操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/delSearchApply")
    public ResultVO delSearchApply(@RequestBody PurApplyReq req) {
		return purApplyService.delSearchApply(req);
    }
	
	@ApiOperation(value = "查询采购申请单报表的查看操作", notes = "查询采购申请单报表的查看操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/ckApplyData")
	public ResultVO<CkProcureApplyResp> ckApplyData(@RequestBody PurApplyReq req){
		//获取申请单
		CkProcureApplyResp procureApplyReq1 = purApplyService.ckApplyData1(req);
		//获取添加的产品信息
		List<AddProductReq> procureApplyReq2 = purApplyService.ckApplyData2(req);
		List<AddFileReq> procureApplyReq3 = purApplyService.ckApplyData3(req);
		procureApplyReq1.setAddProductReq(procureApplyReq2);
		procureApplyReq1.setAddFileReq(procureApplyReq3);
		return ResultVO.success(procureApplyReq1);
	}
	
	
}
