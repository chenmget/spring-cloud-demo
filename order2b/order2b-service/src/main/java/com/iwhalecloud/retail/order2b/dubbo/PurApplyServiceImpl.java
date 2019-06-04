package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductInfoResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.order2b.consts.PurApplyConsts;
import com.iwhalecloud.retail.order2b.dto.response.purapply.*;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.*;
import com.iwhalecloud.retail.order2b.manager.PurApplyManager;
import com.iwhalecloud.retail.order2b.service.PurApplyService;
import com.iwhalecloud.retail.system.dto.UserDetailDTO;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.NextRouteAndReceiveTaskReq;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
@Slf4j
public class PurApplyServiceImpl implements PurApplyService {

	@Autowired
    private PurApplyManager purApplyManager;

	@Reference
    private TaskService taskService;

	@Reference
    private UserService userService;

	@Reference
	private ProductService productService;
	@Override
	public ResultVO<Page<PurApplyResp>> cgSearchApply(PurApplyReq req) {
		Page<PurApplyResp> purApplyResp = purApplyManager.cgSearchApply(req);
		List<PurApplyResp> list = purApplyResp.getRecords();
		
		for(int i=0;i<list.size();i++){
			PurApplyResp purApplyResps = list.get(i);
			String applyId = purApplyResps.getApplyId();
			WfTaskResp wfTaskResp = purApplyManager.getTaskItemId(applyId);
			if(wfTaskResp != null){
				purApplyResps.setTaskId(wfTaskResp.getTaskId());
				purApplyResps.setTaskItemId(wfTaskResp.getTaskItemId());
			}
		}
		return ResultVO.success(purApplyResp);
	}
	
	@Override
	public ResultVO<Page<PurApplyResp>> cgSearchApplyLan(PurApplyReq req) {
		Page<PurApplyResp> purApplyResp = purApplyManager.cgSearchApplyLan(req);
		return ResultVO.success(purApplyResp);
	}
	//判断价格
	private  int chooseCount (ProcureApplyReq req) {
		List<AddProductReq> addProductList= req.getAddProductReq();
		//如果采购价大于政企价格 要省公司审核
//			List<PurApplyItemResp> purApplyItemList =  purApplyManager.comparePrice(req.getApplyId());
		List<String> prodIds = new ArrayList<String>();

//			for (int i=0;i<purApplyItemList.size();i++) {
//				PurApplyItemResp purApplyItem = purApplyItemList.get(i);
//				String purApplyItemProductId = purApplyItem.getProductId();
//				String tPurPrice = purApplyItem.getPurPrice();
//				if (purApplyItemProductId!=null) {
//					prodIds.add(purApplyItemProductId);
//				}
//			}
		for (int i=0;i<addProductList.size();i++ ) {
			AddProductReq addProductReq = addProductList.get(i);
			String purApplyItemProductId = addProductReq.getProductId();
			String tPurPrice = addProductReq.getPriceInStore();
			if (purApplyItemProductId!=null) {
				prodIds.add(purApplyItemProductId);
			}
		}
		//获取产品政企价格列表
		List<ProductInfoResp> productList =new ArrayList<ProductInfoResp>();
		if(prodIds!=null && prodIds.size()>0) {
			productList= productService.getProductInfoByIds(prodIds);

		}

		//获取产品政企价, 判断采购价是否大于政企价格

		int count=0;
		for (int i=0;i<addProductList.size();i++) {
			AddProductReq addProductReq = addProductList.get(i);
			String tPurPrice = addProductReq.getPriceInStore();
			String productIdItem=addProductReq.getProductId();
			for (ProductInfoResp productInfoResp:productList) {
				Double corporationPrice = productInfoResp.getCorporationPrice();
				String productId = productInfoResp.getProductId();
				if(productIdItem.equals(productId)) {
					if(Double.valueOf(tPurPrice)>corporationPrice) {
						count=count+1;
						break;
					}

				}

			}
		}
		return  count;
	}

	@Override
	@Transactional
	public ResultVO tcProcureApply(ProcureApplyReq req) {
		String isSave = req.getIsSave();
		// 编辑的时候不做插入操作
		if(!isSave.equals(PurApplyConsts.PUR_APPLY_EDIT)) {
			purApplyManager.tcProcureApply(req);
		}
		if (isSave.equals(PurApplyConsts.PUR_APPLY_SUBMIT)) {
			//如果采购价大于政企价格 要省公司审核
			int count =chooseCount(req);
			log.info(req.getApplyId()+"count="+count+"如果count>0 采购价大于政企价格 要省公司审核");
//		System.out.println("count="+count+"如果count>0 采购价大于政企价格 要省公司审核");
			if(count>0) {
				req.setStatusCd("21");
				//更新省公司待审核状态
				purApplyManager.updatePurApplyStatusCd(req);
			}

//		String isSave = req.getIsSave();
			//提交时发起流程审核
			//启动流程
			ProcessStartReq processStartDTO = new ProcessStartReq();

			//如果采购价大于政企价格 要省公司审核
			processStartDTO.setParamsType(WorkFlowConst.TASK_PARAMS_TYPE.JSON_PARAMS.getCode());
			Map map=new HashMap();
			if(count>0) {
				map.put("CGJ","0");//
			}else{
				map.put("CGJ","1");
			}
			processStartDTO.setParamsValue(JSON.toJSONString(map));

			processStartDTO.setTitle("采购申请单审核流程");
			processStartDTO.setFormId(req.getApplyId());
			processStartDTO.setProcessId(PurApplyConsts.PUR_APPLY_AUDIT_SGS_PROCESS_ID);
			processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_3040.getTaskSubType());
			processStartDTO.setApplyUserId(req.getCreateStaff());
			//根据用户id查询名称
			ResultVO<UserDetailDTO> userDetailDTO = userService.getUserDetailByUserId(req.getCreateStaff());
			String userName = "";
			if (userDetailDTO.isSuccess()) {
				userName = userDetailDTO.getResultData().getUserName();
			}
			processStartDTO.setApplyUserName(userName);
			ResultVO resultVO = new ResultVO();
			try {
				resultVO = taskService.startProcess(processStartDTO);
			} catch (Exception e) {
				log.error("PurApplyServiceImpl.tcProcureApply exception={}", e);
				return ResultVO.error();
			} finally {
				log.info("PurApplyServiceImpl.tcProcureApply req={},resp={}",
						JSON.toJSONString(processStartDTO), JSON.toJSONString(resultVO));
			}
		} else if (isSave.equals(PurApplyConsts.PUR_APPLY_EDIT)) {
			//做编辑处理
			int count =chooseCount(req);
			log.info(req.getApplyId()+"count="+count+"如果count>0 采购价大于政企价格 要省公司审核");
//		System.out.println("count="+count+"如果count>0 采购价大于政企价格 要省公司审核");
			if(count>0) {
				req.setStatusCd("21");
				//更新省公司待审核状态
			}else {
				req.setStatusCd("20");
			}
			purApplyManager.updatePurApply(req);
			NextRouteAndReceiveTaskReq nextRouteAndReceiveTaskReq = new  NextRouteAndReceiveTaskReq();
			nextRouteAndReceiveTaskReq.setFormId(req.getApplyId());
			nextRouteAndReceiveTaskReq.setParamsType(WorkFlowConst.TASK_PARAMS_TYPE.JSON_PARAMS.getCode());
			Map map=new HashMap();
			if(count>0) {
				map.put("CGJ","0");//
			}else{
				map.put("CGJ","1");
			}
			nextRouteAndReceiveTaskReq.setParamsValue(JSON.toJSONString(map));
			nextRouteAndReceiveTaskReq.setHandlerUserId(req.getHandleUserId());
			nextRouteAndReceiveTaskReq.setHandlerUserName(req.getHandleUserName());
			taskService.nextRouteAndReceiveTask(nextRouteAndReceiveTaskReq);

		}




//		if (isSave.equals(PurApplyConsts.PUR_APPLY_SUBMIT) && req.getApplyType().equals(PurApplyConsts.PUR_APPLY_TYPE)) {
//
//		}// else if (isSave.equals(PurApplyConsts.PUR_APPLY_SUBMIT) && req.getApplyType().equals(PurApplyConsts.PURCHASE_TYPE)) {
//			//启动流程
//			ProcessStartReq processStartDTO = new ProcessStartReq();
//			processStartDTO.setTitle("采购单审核流程");
//			processStartDTO.setFormId(req.getApplyId());
//			processStartDTO.setProcessId(PurApplyConsts.PURCHASE_AUDIT_PROCESS_ID);
//			processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_3030.getTaskSubType());
//			processStartDTO.setApplyUserId(req.getCreateStaff());
//			//根据用户id查询名称
//			ResultVO<UserDetailDTO> userDetailDTO = userService.getUserDetailByUserId(req.getCreateStaff());
//			String userName = "";
//			if (userDetailDTO.isSuccess()) {
//				userName = userDetailDTO.getResultData().getUserName();
//			}
//			processStartDTO.setApplyUserName(userName);
//			ResultVO resultVO = new ResultVO();
//			try {
//				resultVO = taskService.startProcess(processStartDTO);
//			} catch (Exception e) {
//				log.error("PurApplyServiceImpl.tcProcureApply exception={}", e);
//				return ResultVO.error();
//			} finally {
//				log.info("PurApplyServiceImpl.tcProcureApply req={},resp={}",
//						JSON.toJSONString(processStartDTO), JSON.toJSONString(resultVO));
//			}
//		}
		return ResultVO.successMessage("修改采购申请单成功");
	}
	
	@Override
	@Transactional
	public void crPurApplyFile(AddFileReq req) {
		purApplyManager.crPurApplyFile(req);
	}

	@Override
	@Transactional
	public void crPurApplyItem(AddProductReq req) {
		purApplyManager.crPurApplyItem(req);
	}
	
	@Override
	public PriCityManagerResp getLoginInfo(String userId){
		return purApplyManager.getLoginInfo(userId);
	}
	
	@Override
	@Transactional
	public ResultVO<T> delSearchApply(PurApplyReq req) {
		purApplyManager.delSearchApply(req);
		return ResultVO.success();
	}

	@Override
	public ApplyHeadResp hqShenQingDaoHao() {
		return purApplyManager.hqShenQingDaoHao();
	}

	@Override
	public String hqDiShiBuMen(String dsbm) {
		return purApplyManager.hqDiShiBuMen(dsbm);
	}

	@Override
	public CkProcureApplyResp ckApplyData1(PurApplyReq req) {
		CkProcureApplyResp ckProcureApplyResp  = purApplyManager.ckApplyData1(req);
		String createDate = ckProcureApplyResp.getCreateDate();
		createDate = createDate.substring(0, createDate.length()-2);
		ckProcureApplyResp.setCreateDate(createDate);
		 return ckProcureApplyResp;
	}
	
	@Override
	public List<AddProductReq> ckApplyData2(PurApplyReq req) {
		return purApplyManager.ckApplyData2(req);
	}
	
	@Override
	public List<AddFileReq> ckApplyData3(PurApplyReq req) {
		return purApplyManager.ckApplyData3(req);
	}
	
	@Override
	public List<PurApplyExtReq> ckApplyData4(PurApplyReq req){
		return purApplyManager.ckApplyData4(req);
	}
	
	@Override
	public int isHaveSave(String applyId){
		return purApplyManager.isHaveSave(applyId);
	}
	
	@Override
	public void updatePurApply(ProcureApplyReq state){
		purApplyManager.updatePurApply(state);
	}
	
	@Override
	public void delApplyItem(ProcureApplyReq req){
		purApplyManager.delApplyItem(req);
	}
	
	@Override
	public void delApplyFile(ProcureApplyReq req){
		purApplyManager.delApplyFile(req);
	}
	
	@Override
	public void delPurApplyExt(ProcureApplyReq req){
		purApplyManager.delPurApplyExt(req);
	}
	
	@Override
	public MemMemberAddressReq selectMemMeneberAddr(ProcureApplyReq req){
		return purApplyManager.selectMemMeneberAddr(req);
	}
	
	@Override
	public void insertPurApplyExt(MemMemberAddressReq req){
		purApplyManager.insertPurApplyExt(req);
	}
	
	
	@Override
	public String getMerchantCode(String merchantCode){
		return purApplyManager.getMerchantCode(merchantCode);
	}
	
	@Override
	public String hqSeqFileId(){
		return purApplyManager.hqSeqFileId();
	}
	
	@Override
	public String hqSeqItemId(){
		return purApplyManager.hqSeqItemId();
	}
	
	@Override
	public void addShippingAddress(MemMemberAddressReq req){
		purApplyManager.addShippingAddress(req);
	}
	
	@Override
	public ResultVO updatePrice(UpdateCorporationPriceReq req){
		purApplyManager.updatePrice(req);
		return ResultVO.success();
	}
	
	//需要优化  怎么改为批量更新
	@Override
	public ResultVO commitPriceExcel(UpdateCorporationPriceReq req){
		List<String> snPriceList = req.getSnPrice();
		for(int i=0;i<snPriceList.size();i++){
			String snPrice = snPriceList.get(i);
			String[] splits = snPrice.split("\\|");
			req.setSn(splits[0]);
			req.setCorporationPrice(splits[1]+"00");
			purApplyManager.updatePrice(req);
		}
		return ResultVO.success();
	}
	
//	@Override
//	public ResultVO commitPriceExcel(UpdateCorporationPriceReq req){
//		List<String> snPriceList = req.getSnPrice();
//		List<String> snList = new ArrayList<String>();
//		List<String> priceList = new ArrayList<String>();
//		for(int i=0;i<snPriceList.size();i++){
//			String snPrice = snPriceList.get(i);
//			String[] splits = snPrice.split("\\|");
//			snList.add(splits[0]);
//			priceList.add(splits[1]);
//		}
//		req.setSnList(snList);
//		req.setPriceList(priceList);
//		purApplyManager.commitPriceExcel(req);
//		return ResultVO.success();
//	}
	
}

