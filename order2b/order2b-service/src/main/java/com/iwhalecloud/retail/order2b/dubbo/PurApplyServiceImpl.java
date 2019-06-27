package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductApplyInfoResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.order2b.consts.PurApplyConsts;
import com.iwhalecloud.retail.order2b.dto.response.purapply.*;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.*;
import com.iwhalecloud.retail.order2b.manager.PurApplyDeliveryManager;
import com.iwhalecloud.retail.order2b.manager.PurApplyManager;
import com.iwhalecloud.retail.order2b.service.PurApplyService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.dto.UserDetailDTO;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.NextRouteAndReceiveTaskReq;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;



@Service
@Slf4j
public class PurApplyServiceImpl implements PurApplyService {

	@Autowired
   private PurApplyManager purApplyManager;
    @Autowired
    private PurApplyDeliveryManager purApplyDeliveryManager;

	@Reference
    private TaskService taskService;

	@Reference
    private UserService userService;

	@Reference
	private ProductService productService;
	@Reference
	MerchantService merchantService;
	@Override
	public ResultVO<Page<PurApplyResp>> cgSearchApply(PurApplyReq req) {
		log.info("cgSearchApply参数   req={}"+JSON.toJSONString(req));
		if (req.getLanId()!=null) {
			req.setRegionId(req.getLanId());
		}
		//req.setRegionId();
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
//			String createDate = purApplyResps.getApplyTime();
//			createDate = createDate.substring(0, createDate.length()-2);
//			purApplyResps.setApplyTime(createDate);
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
		//List<String> prodIds = new ArrayList<String>();

//			for (int i=0;i<purApplyItemList.size();i++) {
//				PurApplyItemResp purApplyItem = purApplyItemList.get(i);
//				String purApplyItemProductId = purApplyItem.getProductId();
//				String tPurPrice = purApplyItem.getPurPrice();
//				if (purApplyItemProductId!=null) {
//					prodIds.add(purApplyItemProductId);
//				}
//			}
	/*	for (int i=0;i<addProductList.size();i++ ) {
			AddProductReq addProductReq = addProductList.get(i);
			String purApplyItemProductId = addProductReq.getProductId();
		//	String tPurPrice = addProductReq.getPriceInStore();
			if (purApplyItemProductId!=null) {
				prodIds.add(purApplyItemProductId);
			}
		}*/
		//获取产品政企价格列表
//		List<ProductInfoResp> productList =new ArrayList<ProductInfoResp>();
//		if (prodIds!=null && prodIds.size()>0) {
//			productList= productService.getProductInfoByIds(prodIds);
//
//		}

		//获取产品政企价, 判断采购价是否大于政企价格

//
		int count=0;
		for (int i=0;i<addProductList.size();i++) {
			AddProductReq addProductReq = addProductList.get(i);
			String tPurPrice = addProductReq.getPriceInStore();
			String corporationPrice = addProductReq.getCorporationPrice();
			if (null == corporationPrice) {
				corporationPrice = "0";
			}
			if (null == tPurPrice) {
				tPurPrice = "0";
			}
			if ( Double.valueOf(tPurPrice)>Double.valueOf(corporationPrice)) {
				count=count+1;
				break;
			}
		}
		return  count;
	}

	@Override
	@Transactional
	public ResultVO tcProcureApply(ProcureApplyReq req) {
		log.info("tcProcureApply****************************************ProcureApplyReq = "+JSON.toJSONString(req));
        PurApplyReq  pReq = new  PurApplyReq();
        pReq.setApplyId(req.getApplyId());
		String isSave = req.getIsSave();
		// 编辑的时候不做插入操作
//		if(!isSave.equals(PurApplyConsts.PUR_APPLY_EDIT)) {
//			purApplyManager.tcProcureApply(req);
//		}
		if (isSave.equals(PurApplyConsts.PUR_APPLY_SUBMIT)) {
			Map map=new HashMap();
			//如果采购价大于政企价格 要省公司审核
			int count =chooseCount(req);
			log.info(req.getApplyId()+"count="+count+"如果count>0 采购价大于政企价格 要省公司审核");
//		System.out.println("count="+count+"如果count>0 采购价大于政企价格 要省公司审核");
//			if (count>0) {
//				//根据商家ID获取 商家userId
//				map.put("CGJ","0");//
//			} else {
//				map.put("CGJ","1");
//			}
			if (count>0) {
				map.put("CGJ",PurApplyConsts.PUR_APPLY_ADMIN_VALUE);// 0
//				req.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_ADMIN_PASS);//
                pReq.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_ADMIN_PASS);
				//更新省公司待审核状态
            //    purApplyDeliveryManager.updatePurApplyStatus(pReq);
			}else {
                pReq.setStatusCd(PurApplyConsts.SGS_PUR_APPLY_STATUS_PASS);

                map.put("CGJ",PurApplyConsts.PUR_APPLY_VALUE); // 1
				List<AddProductReq> productList =  req.getAddProductReq();
				for (AddProductReq addProductReq:productList) {
//					String parentTypeId = addProductReq.getParentTypeId();
					String  purchaseType= addProductReq.getPurchaseType();
//					if ("10000".equals(parentTypeId)) {
//						移动终端默认选择集采，如果选择社采则需要地市管理审核，然后供应商再审核
						if (PurApplyConsts.PUR_APPLY_SOCIAL_TYPE.equals(purchaseType)) {
							map.put("CGJ",PurApplyConsts.PUR_APPLY_ADMIN_VALUE);// 0
							//更新省公司待审核状态
//							req.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_ADMIN_PASS);
//							purApplyManager.updatePurApplyStatusCd(req);
                            pReq.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_ADMIN_PASS);
                            //更新省公司待审核状态
							break;
						}
//					}
				}
			}
            purApplyDeliveryManager.updatePurApplyStatus(pReq);

//		String isSave = req.getIsSave();
			//提交时发起流程审核
			//启动流程
			ProcessStartReq processStartDTO = new ProcessStartReq();

			//如果采购价大于政企价格 要省公司审核
			processStartDTO.setParamsType(WorkFlowConst.TASK_PARAMS_TYPE.JSON_PARAMS.getCode());


			processStartDTO.setParamsValue(JSON.toJSONString(map));

			processStartDTO.setTitle("采购申请单审核流程");
			processStartDTO.setFormId(req.getApplyId());
			processStartDTO.setProcessId(PurApplyConsts.PUR_APPLY_AUDIT_SGS_PROCESS_ID);
			processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_3040.getTaskSubType());
			processStartDTO.setApplyUserId(req.getCreateStaff());
			//
//			processStartDTO.setParamsType(2);
//			processStartDTO.setParamsValue(req.getApplyMerchantId());
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
			NextRouteAndReceiveTaskReq nextRouteAndReceiveTaskReq = new  NextRouteAndReceiveTaskReq();
			nextRouteAndReceiveTaskReq.setFormId(req.getApplyId());
			nextRouteAndReceiveTaskReq.setParamsType(WorkFlowConst.TASK_PARAMS_TYPE.JSON_PARAMS.getCode());
			Map map=new HashMap();
			if (count>0) {
//				req.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_ADMIN_PASS);
				pReq.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_ADMIN_PASS);
				map.put("CGJ",PurApplyConsts.PUR_APPLY_ADMIN_VALUE);//
			} else {
//				req.setStatusCd(PurApplyConsts.SGS_PUR_APPLY_STATUS_PASS);
                pReq.setStatusCd(PurApplyConsts.SGS_PUR_APPLY_STATUS_PASS);

                map.put("CGJ",PurApplyConsts.PUR_APPLY_VALUE);
				List<AddProductReq> productList =  req.getAddProductReq();
				for (AddProductReq addProductReq:productList) {
//					String parentTypeId = addProductReq.getParentTypeId();
					String  purchaseType= addProductReq.getPurchaseType();
//					if ("10000".equals(parentTypeId)) {
//						移动终端默认选择集采，如果选择社采则需要地市管理审核，然后供应商再审核
						if (PurApplyConsts.PUR_APPLY_SOCIAL_TYPE.equals(purchaseType)) {
							map.put("CGJ",PurApplyConsts.PUR_APPLY_ADMIN_VALUE);//
							//更新省公司待审核状态
                            pReq.setStatusCd(PurApplyConsts.PUR_APPLY_STATUS_ADMIN_PASS);
							break;
						}
//					}
				}
			}
//			purApplyManager.updatePurApplyStatusCd(req);
            purApplyDeliveryManager.updatePurApplyStatus(pReq);
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
//		String createDate = ckProcureApplyResp.getCreateDate();
//		createDate = createDate.substring(0, createDate.length()-2);
//		ckProcureApplyResp.setCreateDate(createDate);
		 return ckProcureApplyResp;
	}
	
	@Override
	public List<AddProductReq> ckApplyData2(PurApplyReq req) {
		List<AddProductReq> result = purApplyManager.ckApplyData2(req);
		for (AddProductReq p:result) {
			PurApplyItemReq PurApplyItemReq = new PurApplyItemReq();
			PurApplyItemReq.setApplyItem(p.getApplyItemId());
			PurApplyItemReq.setProductId(p.getProductId());
			List<String> deliverMktResInstNbrList =  purApplyManager.countPurApplyItemDetail(PurApplyItemReq);
			log.info("ckApplyData2 data deliverMktResInstNbrList="+JSON.toJSONString(deliverMktResInstNbrList));
			Integer count = deliverMktResInstNbrList.size();//查询发货的条数
			if (count !=null) {
				p.setDeliverCount(String.valueOf(count));
			}
			p.setDeliverMktResInstNbrList(deliverMktResInstNbrList);
		}
		return result;
	}
	
	@Override
	public List<AddFileReq> ckApplyData3(PurApplyReq req) {
		List<AddFileReq> list = purApplyManager.ckApplyData3(req);
//		for ( AddFileReq file:list ) {
//			String fileUrl = file.getFileUrl();
//			String realUrl = dfsShowIp+fileUrl;
//			file.setFileUrl(realUrl);
//			log.info("ckApplyData3 =====realUrl="+realUrl +" fileUrl="+fileUrl);
//		}
		return list;
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
	@Transactional
	public ResultVO updatePrice(UpdateCorporationPriceReq req){
		log.info(req.getBatchId()+"********************************************************************************************");
		String isFixedLine = productService.selectisFixedLineByBatchId(req.getSn());
		String applyUserId = req.getApplyUserId();//移动终端 余玲 200012864664           固网终端  胡亚玲  200012829198
		if("200012829198".equals(applyUserId)) {//固网
			if(!"1".equals(isFixedLine)) {
				return ResultVO.error("当前用户没有权限修改移动终端政企价格");
			}
		}else if("200012864664".equals(applyUserId)) {//移动
			if("1".equals(isFixedLine)) {
				return ResultVO.error("当前用户没有权限修改固网终端政企价格");
			}
		}else {
			return ResultVO.error("当前用户没有权限修改政企价格");
		}
		
		log.info(req.getBatchId()+"********************************************************************************************"+isFixedLine);
		//政企价格修改提交启动流程
		ProcessStartReq processStartDTO = new ProcessStartReq();
		//政企价格修改审核
		processStartDTO.setParamsType(WorkFlowConst.TASK_PARAMS_TYPE.JSON_PARAMS.getCode());
		Map map=new HashMap();
		map.put("GWZD", "1");
		processStartDTO.setParamsValue(JSON.toJSONString(map));
		//业务ID->批次ID
		processStartDTO.setFormId(req.getBatchId());//单个修改政企价格也加个批次号
		if("1".equals(isFixedLine)) {//如果是固网
			processStartDTO.setTitle("固网终端政企价格修改审核流程");
			processStartDTO.setProcessId(PurApplyConsts.GWPROD_PRODUCT_CORPORATION_PRICE_ID);
			processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_9605.getTaskSubType());
		} else {
			processStartDTO.setTitle("移动终端政企价格修改审核流程");
			processStartDTO.setProcessId(PurApplyConsts.YDPROD_PRODUCT_CORPORATION_PRICE_ID);
			processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_9604.getTaskSubType());
		}
		processStartDTO.setApplyUserId(req.getApplyUserId());
		//根据用户id查询名称
		ResultVO<UserDetailDTO> userDetailDTO = userService.getUserDetailByUserId(req.getApplyUserId());
		String userName = "";
		if (userDetailDTO.isSuccess()) {
			userName = userDetailDTO.getResultData().getUserName();
		}
		processStartDTO.setApplyUserName(userName);
		ResultVO resultVO = new ResultVO();
		try {
			log.info("---------PurApplyServiceImpl.updatePrice()  政企价格修改提交启动流程start*********************");
			log.info("****************************************processStartDTO = "+JSON.toJSONString(processStartDTO));
			resultVO = taskService.startProcess(processStartDTO);
			log.info("---------PurApplyServiceImpl.updatePrice()  政企价格修改提交启动流程end*********************");
			log.info("****************************************resultVO = "+JSON.toJSONString(resultVO));
		} catch (Exception e) {
			log.error("PurApplyServiceImpl.updatePrice catch exception={}", e);
			return ResultVO.error();
		} finally {
			log.info("PurApplyServiceImpl.updatePrice finally req={},resp={}",
					JSON.toJSONString(processStartDTO), JSON.toJSONString(resultVO));
		}
		
		//把数据写到PROD_PRODUCT_CHANGE_DETAIL(产品变更记录明细表)，PROD_PRODUCT_CHANGE(产品变更记录表)
		String productBaseId = purApplyManager.getProductBaseIdByProductId(req.getSn());
		String changeId = productService.selectNextChangeId();
		ProdProductChangeReq prodProductChangeReq = new ProdProductChangeReq();
		prodProductChangeReq.setChangeId(changeId);
		prodProductChangeReq.setVerNum("1.0");
		prodProductChangeReq.setProductBaseId(productBaseId);
		prodProductChangeReq.setAuditState(PurApplyConsts.AUDIT_STATE_WAIT);
		prodProductChangeReq.setCreateDate(new Date());
		prodProductChangeReq.setCreateStaff(req.getApplyUserId());
		prodProductChangeReq.setBatchId(req.getBatchId());
		prodProductChangeReq.setSn(req.getSn());
		purApplyManager.insertProdChangePrice(prodProductChangeReq);
		
		String changeDetailId = productService.selectNextChangeDetailId();
		String oldValue = purApplyManager.selectOldValue(req.getSn());
		ProdProductChangeDetail prodProductChangeDetail = new ProdProductChangeDetail();
		prodProductChangeDetail.setChangeDetailId(changeDetailId);
		prodProductChangeDetail.setChangeId(changeId);
		prodProductChangeDetail.setOperType("MOD");//操作类型 ADD：新增 MOD：修改 DEL：删除
		prodProductChangeDetail.setVerNum("1.0");//版本号
		prodProductChangeDetail.setTableName("PROD_PRODUCT");//表名
		prodProductChangeDetail.setChangeField("CORPORATION_PRICE");//	变更字段英文名
		prodProductChangeDetail.setChangeFieldName("政企供货价");//变更字段中文注释的名字
		prodProductChangeDetail.setOldValue(oldValue);//	原始值
		prodProductChangeDetail.setNewValue(req.getCorporationPrice());//	变更值
		prodProductChangeDetail.setKeyValue(req.getSn());//product_id	业务ID
		prodProductChangeDetail.setCreateDate(new Date());//创建时间
		prodProductChangeDetail.setCreateStaff(req.getApplyUserId());//创建人
		purApplyManager.insertProdProductChangeDetail(prodProductChangeDetail);
		//把订单的状态改成待审核
		purApplyManager.updateProdProduct(prodProductChangeReq);
		return ResultVO.success();
	}
	
	@Override
	@Transactional
	public ResultVO commitPriceExcel(UpdateCorporationPriceReq req){
		String applyUserId = req.getApplyUserId();//移动终端 余玲 200012864664           固网终端  胡亚玲  200012829198
		List<String> listProd = new ArrayList<String>();
		String isFixedLine = null;
		if("200012829198".equals(applyUserId)) {//固网终端
			isFixedLine = "1";
			List<String> listProductPrice = req.getProductPrice();
			if(listProductPrice!= null && listProductPrice.size() > 0) {
				//判断所有产品ID是同一类型
				for(int i=0;i<listProductPrice.size();i++) {
					String sn = listProductPrice.get(i).split("\\|")[0];
					String isFixedLineMa = productService.selectisFixedLineByBatchId(sn);
					if(!isFixedLine.equals(isFixedLineMa)) {
						listProd.add(sn);
					}
				}
				if(listProd.size() > 0) {
					return ResultVO.error("这些产品不是固网终端 ： "+String.valueOf(listProd));
				}
			}
		} else if("200012864664".equals(applyUserId)) {//移动终端
			isFixedLine = "0";
			List<String> listProductPrice = req.getProductPrice();
			if(listProductPrice!= null && listProductPrice.size() > 0) {
				//判断所有产品ID是同一类型
				for(int i=0;i<listProductPrice.size();i++) {
					String sn = listProductPrice.get(i).split("\\|")[0];
					String isFixedLineMa = productService.selectisFixedLineByBatchId(sn);
					if(!isFixedLine.equals(isFixedLineMa)) {
						listProd.add(sn);
					}
				}
				if(listProd.size() > 0) {
					return ResultVO.error("这些产品不是移动终端 ： " + String.valueOf(listProd));
				}
			}
		} else {
			return ResultVO.error("当前用户没有权限修改政企价格");
		}
		
		//政企价格修改提交启动流程
		ProcessStartReq processStartDTO = new ProcessStartReq();
		//政企价格修改审核
		processStartDTO.setParamsType(WorkFlowConst.TASK_PARAMS_TYPE.JSON_PARAMS.getCode());
		Map map=new HashMap();
		map.put("GWZD", "1");
		processStartDTO.setParamsValue(JSON.toJSONString(map));
		//业务ID->批次ID
		processStartDTO.setFormId(req.getBatchId());//单个修改政企价格也加个批次号
		if("1".equals(isFixedLine)) {//如果是固网
			processStartDTO.setTitle("固网终端政企价格修改审核流程");
			processStartDTO.setProcessId(PurApplyConsts.GWPROD_PRODUCT_CORPORATION_PRICE_ID);
			processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_9605.getTaskSubType());
		} else {
			processStartDTO.setTitle("移动终端政企价格修改审核流程");
			processStartDTO.setProcessId(PurApplyConsts.YDPROD_PRODUCT_CORPORATION_PRICE_ID);
			processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_9604.getTaskSubType());
		}
		processStartDTO.setApplyUserId(req.getApplyUserId());
		//根据用户id查询名称
		ResultVO<UserDetailDTO> userDetailDTO = userService.getUserDetailByUserId(req.getApplyUserId());
		String userName = "";
		if (userDetailDTO.isSuccess()) {
			userName = userDetailDTO.getResultData().getUserName();
		}
		processStartDTO.setApplyUserName(userName);
		ResultVO resultVO = new ResultVO();
		try {
			log.info("---------PurApplyServiceImpl.updatePrice()  政企价格修改提交启动流程start*********************");
			log.info("****************************************processStartDTO = "+JSON.toJSONString(processStartDTO));
			resultVO = taskService.startProcess(processStartDTO);
			log.info("---------PurApplyServiceImpl.updatePrice()  政企价格修改提交启动流程end*********************");
			log.info("****************************************resultVO = "+JSON.toJSONString(resultVO));
		} catch (Exception e) {
			log.error("PurApplyServiceImpl.updatePrice catch exception={}", e);
			return ResultVO.error();
		} finally {
			log.info("PurApplyServiceImpl.updatePrice finally req={},resp={}",
					JSON.toJSONString(processStartDTO), JSON.toJSONString(resultVO));
		}
		
		List<String> productPriceList = req.getProductPrice();
		for(int i=0;i<productPriceList.size();i++){
			String productPrice = productPriceList.get(i);
			String[] splits = productPrice.split("\\|");
			req.setSn(splits[0]);
			req.setCorporationPrice(splits[1]+"00");
			String productBaseId = purApplyManager.getProductBaseIdByProductId(req.getSn());
			//循环插入变更表
			String changeId = productService.selectNextChangeId();//1151
			ProdProductChangeReq prodProductChangeReq = new ProdProductChangeReq();
			prodProductChangeReq.setChangeId(changeId);
			prodProductChangeReq.setVerNum("1.0");
			prodProductChangeReq.setProductBaseId(productBaseId);
			prodProductChangeReq.setAuditState(PurApplyConsts.AUDIT_STATE_WAIT);
			prodProductChangeReq.setCreateDate(new Date());
			prodProductChangeReq.setCreateStaff(req.getApplyUserId());
			prodProductChangeReq.setBatchId(req.getBatchId());
			prodProductChangeReq.setSn(req.getSn());
			purApplyManager.insertProdChangePrice(prodProductChangeReq);
			
			//把订单的状态改成待审核
			
			String changeDetailId = productService.selectNextChangeDetailId();//1131
			String oldValue = purApplyManager.selectOldValue(req.getSn());
			ProdProductChangeDetail prodProductChangeDetail = new ProdProductChangeDetail();
			prodProductChangeDetail.setChangeDetailId(changeDetailId);
			prodProductChangeDetail.setChangeId(changeId);
			prodProductChangeDetail.setOperType("MOD");//操作类型 ADD：新增 MOD：修改 DEL：删除
			prodProductChangeDetail.setVerNum("1.0");//版本号
			prodProductChangeDetail.setTableName("PROD_PRODUCT");//表名
			prodProductChangeDetail.setChangeField("CORPORATION_PRICE");//	变更字段英文名
			prodProductChangeDetail.setChangeFieldName("政企供货价");//变更字段中文注释的名字
			prodProductChangeDetail.setOldValue(oldValue);//	原始值
			prodProductChangeDetail.setNewValue(req.getCorporationPrice());//	变更值
			prodProductChangeDetail.setKeyValue(req.getSn());//product_id	业务ID
			prodProductChangeDetail.setCreateDate(new Date());//创建时间
			prodProductChangeDetail.setCreateStaff(req.getApplyUserId());//创建人
			purApplyManager.insertProdProductChangeDetail(prodProductChangeDetail);
			//把订单的状态改成待审核
			purApplyManager.updateProdProduct(prodProductChangeReq);
		}
		return ResultVO.success();
	}


	@Override
	public void insertTcProcureApply(ProcureApplyReq req) {
		purApplyManager.tcProcureApply(req);
	}

	@Override
	public ResultVO applyPurchase(ProcureApplyReq req) {
		return null;
	}

	@Override
	@Transactional
	public ResultVO updatePurTypeByApplyId(ProcureApplyReq req) {
		log.info("updatePurTypeByApplyId  req= "+JSON.toJSONString(req));
		List<AddProductReq> list=req.getAddProductReq();
		ResultVO resultVO = new ResultVO();
		if ( list!=null && list.size()>0 ) {
			for (AddProductReq product:list) {
				log.info("updatePurTypeByApplyId  product= "+JSON.toJSONString(product));
				Integer count = purApplyManager.updatePurTypeByApplyId(product);
				log.info("updatePurTypeByApplyId  count= "+count);
				if (count<=0) {
					return resultVO.error("更新采购单采购类型失败");
				}

			}

		}


		return resultVO;
	}

	@Override
	public ResultVO<Page<PurApplyReportResp>> applySearchReport(PurApplyReportReq req) {
		log.info("cgSearchApply参数   req={}"+JSON.toJSONString(req));
		if (req.getLanId()!=null) {
			req.setRegionId(req.getLanId());
		}
		Boolean flag = productParamCheck(req);
        if (flag == true) {
			ProductGetIdReq productGetIdReq = new ProductGetIdReq();
			BeanUtils.copyProperties(req,productGetIdReq);
			List<String> productIdList = productService.getProductIdListForApply(productGetIdReq);
			req.setProductIdList(productIdList);
		}
        if (req.getMerchantName()!=null && req.getMerchantName().length()>0) {
			List<String> merchantIdList = merchantService.getMerchantIdList(req.getMerchantName());
			req.setMerchantIdList(merchantIdList);
		}

		//req.setRegionId();
		Page<PurApplyReportResp> purApplyReportResp = purApplyManager.applySearchReport(req);
		List<PurApplyReportResp>  list=purApplyReportResp.getRecords();
		if (list ==null || list.size()==0 ) {
			return ResultVO.success(purApplyReportResp);
		}
		for (PurApplyReportResp purApplyReport: list) {
			String productId= purApplyReport.getProductId();
			String merchantId = purApplyReport.getMerchantId();
			// 获取产品信息
			if (productId !=null && productId.length()>0) {
				ProductApplyInfoResp productApplyInfoResp= productService.getProductApplyInfo(productId);
				if(null != productApplyInfoResp)
				BeanUtils.copyProperties(productApplyInfoResp,purApplyReport);
			}
			// 获取商家名称
			if (merchantId !=null  && merchantId.length()>0) {
				MerchantDTO merchantDTO = merchantService.getMerchantInfoById(merchantId);
				if(null != merchantDTO)
				purApplyReport.setMerchantName(merchantDTO.getMerchantName());
			}
		}
		return ResultVO.success(purApplyReportResp);
	}
    public boolean productParamCheck(PurApplyReportReq req) {
        if (req.getProductName()!=null && req.getProductName().length()>0) {
			return true;
		}
		if (req.getProductCode()!=null && req.getProductCode().length()>0) {
			return true;
		}
		if (req.getUnitType()!=null && req.getUnitType().length()>0) {
			return true;
		}
		if (req.getMemory()!=null && req.getMemory().length()>0) {
			return true;
		}
		if (req.getColor()!=null && req.getColor().length()>0) {
			return true;
		}
		return false;

	}
	public ResultVO<List<ProdProductChangeDetail>> searchCommitPriceInfo(UpdateCorporationPriceReq req){
		List<ProdProductChangeDetail> list = purApplyManager.searchCommitPriceInfo(req);
		return ResultVO.success(list);
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

