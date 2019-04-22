package com.iwhalecloud.retail.order2b.manager;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.purapply.ApplyHeadResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.AddFileReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.AddProductReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.ProcureApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.mapper.CartMapper;
import com.iwhalecloud.retail.order2b.mapper.PurApplyMapper;

@Component
public class PurApplyManager {
	
	@Resource
    private PurApplyMapper purApplyMapper;
	
	public Page<PurApplyResp> cgSearchApply(PurApplyReq req) {
		Page<PurApplyResp> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<PurApplyResp> pageReport =purApplyMapper.cgSearchApply(page,req);
		return pageReport;
	}

	public void tcProcureApply(ProcureApplyReq req){
		purApplyMapper.tcProcureApply(req);
	}
	
	public void crPurApplyFile(ProcureApplyReq req){
		purApplyMapper.crPurApplyFile(req);
	}
	
	public void crPurApplyItem(AddProductReq req){
		purApplyMapper.crPurApplyItem(req);
	}
	
	public void delSearchApply(PurApplyReq req){
		purApplyMapper.delSearchApply(req);
	}
	
	public ApplyHeadResp hqShenQingDaoHao() {
		return purApplyMapper.hqShenQingDaoHao();
	}
	
	public String hqDiShiBuMen(String dsbm) {
		return purApplyMapper.hqDiShiBuMen(dsbm);
	}
	
	public ProcureApplyReq ckApplyData1(PurApplyReq req) {
		return purApplyMapper.ckApplyData1(req);
	}
	
	public List<AddProductReq> ckApplyData2(PurApplyReq req) {
		return purApplyMapper.ckApplyData2(req);
	}
	
	public List<AddFileReq> ckApplyData3(PurApplyReq req) {
		return purApplyMapper.ckApplyData3(req);
	}
	
	public int isHaveSave(String applyId){
		return purApplyMapper.isHaveSave(applyId);
	}
	
	public void updatePurApply(String applyId){
		purApplyMapper.updatePurApply(applyId);
	}
	
	public String getMerchantId(String merchantCode){
		return purApplyMapper.getMerchantId(merchantCode);
	}
	
	public String hqSeqFileId(){
		return purApplyMapper.hqSeqFileId();
	}
	
	public String hqSeqItemId(){
		return purApplyMapper.hqSeqItemId();
	}
	
}
