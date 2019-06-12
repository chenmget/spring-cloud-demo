package com.iwhalecloud.retail.order2b.manager;

import java.util.List;

import javax.annotation.Resource;

import com.iwhalecloud.retail.order2b.dto.response.purapply.*;
import com.iwhalecloud.retail.order2b.entity.PurApplyItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.AddFileReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.AddProductReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.MemMemberAddressReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.ProcureApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.ProdProductChangeDetail;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.ProdProductChangeReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyExtReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.UpdateCorporationPriceReq;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.UpdatePurApplyState;
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
	
	public WfTaskResp getTaskItemId(String applyId){
		return purApplyMapper.getTaskItemId(applyId);
	}
	public Page<PurApplyResp> cgSearchApplyLan(PurApplyReq req) {
		Page<PurApplyResp> page=new Page<>(req.getPageNo(),req.getPageSize());
		Page<PurApplyResp> pageReport =purApplyMapper.cgSearchApplyLan(page,req);
		return pageReport;
	}

	public void tcProcureApply(ProcureApplyReq req){
		purApplyMapper.tcProcureApply(req);
	}
	
	public List<ProdProductChangeDetail> searchCommitPriceInfo(UpdateCorporationPriceReq req){
		return purApplyMapper.searchCommitPriceInfo(req);
	}
	
	public void crPurApplyFile(AddFileReq req){
		purApplyMapper.crPurApplyFile(req);
	}
	
	public void crPurApplyItem(AddProductReq req){
		purApplyMapper.crPurApplyItem(req);
	}
	
	public PriCityManagerResp getLoginInfo(String userId){
		return purApplyMapper.getLoginInfo(userId);
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
	
	public CkProcureApplyResp ckApplyData1(PurApplyReq req) {
		return purApplyMapper.ckApplyData1(req);
	}
	
	public List<AddProductReq> ckApplyData2(PurApplyReq req) {
		return purApplyMapper.ckApplyData2(req);
	}
	
	public List<AddFileReq> ckApplyData3(PurApplyReq req) {
		return purApplyMapper.ckApplyData3(req);
	}
	
	public List<PurApplyExtReq> ckApplyData4(PurApplyReq req) {
		return purApplyMapper.ckApplyData4(req);
	}
	
	public int isHaveSave(String applyId){
		return purApplyMapper.isHaveSave(applyId);
	}
	
	public void updatePurApply(ProcureApplyReq state){
		purApplyMapper.updatePurApply(state);
	}
	
	public void delApplyItem(ProcureApplyReq req){
		purApplyMapper.delApplyItem(req);
	}
	
	public void delApplyFile(ProcureApplyReq req){
		purApplyMapper.delApplyFile(req);
	}	
	
	public void delPurApplyExt(ProcureApplyReq req){
		purApplyMapper.delPurApplyExt(req);
	}
	
	public MemMemberAddressReq selectMemMeneberAddr(ProcureApplyReq req){
		return purApplyMapper.selectMemMeneberAddr(req);
	}
	
	public void insertPurApplyExt(MemMemberAddressReq req){
		purApplyMapper.insertPurApplyExt(req);
	}
	
	public String getMerchantCode(String merchantCode){
		return purApplyMapper.getMerchantCode(merchantCode);
	}
	
	public String hqSeqFileId(){
		return purApplyMapper.hqSeqFileId();
	}
	
	public String hqSeqItemId(){
		return purApplyMapper.hqSeqItemId();
	}
	
	public void addShippingAddress(MemMemberAddressReq req){
		purApplyMapper.addShippingAddress(req);
	}
	
	public void insertProdChangePrice(ProdProductChangeReq req){
		purApplyMapper.insertProdChangePrice(req);
	}
	
	public void updateProdProduct(ProdProductChangeReq req){
		purApplyMapper.updateProdProduct(req);
	}
	
	public String getProductBaseIdByProductId(String productId) {
		return purApplyMapper.getProductBaseIdByProductId(productId);
	}
	public void insertProdProductChangeDetail(ProdProductChangeDetail req){
		purApplyMapper.insertProdProductChangeDetail(req);
	}
	
	public String selectNextChangeId() {
		return purApplyMapper.selectNextChangeId();
	}
	public String selectNextChangeDetailId() {
		return purApplyMapper.selectNextChangeDetailId();
	}
	public String selectOldValue(String productId) {
		return purApplyMapper.selectOldValue(productId);
	}
	
	public void commitPriceExcel(UpdateCorporationPriceReq req){
		purApplyMapper.commitPriceExcel(req);
	}
	public List<PurApplyItemResp> comparePrice(String applyId){
		return purApplyMapper.comparePrice(applyId);
	}

	public void updatePurApplyStatusCd(ProcureApplyReq req){
		purApplyMapper.updatePurApplyStatusCd(req);
	}
	
	public int updateProductChange(ProdProductChangeReq req) {
		return purApplyMapper.updateProductChange(req);
	}
	
	public int updateProductCorpPrice(ProdProductChangeDetail req) {
		return purApplyMapper.updateProductCorpPrice(req);
	}
	
	public List<ProdProductChangeDetail> selectProdProductChangeDetail(String keyValue) {
		return purApplyMapper.selectProdProductChangeDetail(keyValue);
	}
	
}
