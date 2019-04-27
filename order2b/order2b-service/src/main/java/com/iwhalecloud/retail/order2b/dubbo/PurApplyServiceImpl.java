package com.iwhalecloud.retail.order2b.dubbo;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order2b.consts.PurApplyConsts;
import com.iwhalecloud.retail.system.dto.UserDetailDTO;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
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
import com.iwhalecloud.retail.order2b.manager.PurApplyManager;
import com.iwhalecloud.retail.order2b.service.PurApplyService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PurApplyServiceImpl implements PurApplyService {

	@Autowired
    private PurApplyManager purApplyManager;
//	@Reference
//    private TaskService taskService;
//
//	@Reference
//    private UserService userService;

	
	@Override
	public ResultVO<Page<PurApplyResp>> cgSearchApply(PurApplyReq req) {
		Page<PurApplyResp> purApplyResp = purApplyManager.cgSearchApply(req);
		return ResultVO.success(purApplyResp);
	}

	@Override
	@Transactional
	public ResultVO tcProcureApply(ProcureApplyReq req) {
		purApplyManager.tcProcureApply(req);
//		//启动流程
//		ProcessStartReq processStartDTO = new ProcessStartReq();
//		processStartDTO.setTitle("通知公告审核流程");
//		processStartDTO.setFormId(req.getApplyId());
//		processStartDTO.setProcessId(PurApplyConsts.PURAPPLY_AUDIT_PROCESS_ID);
//		processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_3020.getTaskSubType());
//		processStartDTO.setApplyUserId(req.getCreateStaff());
//		//根据用户id查询名称
//		ResultVO<UserDetailDTO> userDetailDTO = userService.getUserDetailByUserId(req.getCreateStaff());
//		String userName = "";
//		if (userDetailDTO.isSuccess()) {
//			userName = userDetailDTO.getResultData().getUserName();
//		}
//		processStartDTO.setApplyUserName(userName);
//		ResultVO resultVO = new ResultVO();
//		try {
//			resultVO = taskService.startProcess(processStartDTO);
//		} catch (Exception e) {
//			log.error("PurApplyServiceImpl.tcProcureApply exception={}", e);
//			return ResultVO.error();
//		} finally {
//			log.info("PurApplyServiceImpl.tcProcureApply req={},resp={}",
//					JSON.toJSONString(processStartDTO), JSON.toJSONString(resultVO));
//		}
		return ResultVO.successMessage("新增采购申请单成功");
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
		return purApplyManager.ckApplyData1(req);
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
	public int isHaveSave(String applyId){
		return purApplyManager.isHaveSave(applyId);
	}
	
	@Override
	public void updatePurApply(UpdatePurApplyState state){
		purApplyManager.updatePurApply(state);
	}
	
	@Override
	public String getMerchantId(String merchantCode){
		return purApplyManager.getMerchantId(merchantCode);
	}
	
	@Override
	public String hqSeqFileId(){
		return purApplyManager.hqSeqFileId();
	}
	
	@Override
	public String hqSeqItemId(){
		return purApplyManager.hqSeqItemId();
	}
	
}

