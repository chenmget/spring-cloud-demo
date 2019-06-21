package com.iwhalecloud.retail.order2b.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.response.purapply.*;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.*;
import com.iwhalecloud.retail.order2b.entity.PurApply;
import com.iwhalecloud.retail.order2b.mapper.PurApplyMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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
	
	public String getProductBaseIdByProductId(String sn) {
		return purApplyMapper.getProductBaseIdByProductId(sn);
	}
	public void insertProdProductChangeDetail(ProdProductChangeDetail req){
		purApplyMapper.insertProdProductChangeDetail(req);
	}
	
	public String selectOldValue(String sn) {
		return purApplyMapper.selectOldValue(sn);
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
	
	public void updateProductChange(ProdProductChangeReq req) {
		purApplyMapper.updateProductChange(req);
	}
	
	public void updateProductCorpPrice(ProdProductChangeDetail req) {
		purApplyMapper.updateProductCorpPrice(req);
	}
	
	public String selectProductIdByChangeId(String changeId) {
		return purApplyMapper.selectProductIdByChangeId(changeId);
	}
	
	public void updateProdNoPassPrice(ProdProductChangeDetail req) {
		purApplyMapper.updateProdNoPassPrice(req);
	}
	
	public List<ProdProductChangeDetail> selectProdProductChangeDetail(String keyValue) {
		return purApplyMapper.selectProdProductChangeDetail(keyValue);
	}

	/**
	 * 通过采购申请单查询采购申请单项
	 * @param applyId
	 * @return
	 */
	public  PurApply getPurApplyByAppId(String applyId) {
//        purApplyMapper.selectById(applyId);
		return purApplyMapper.getPurApplyByAppId(applyId);
	}
	/**
	 * 查询已发货的串码
	 * @param req
	 * @return
	 */
	public List<String> countPurApplyItemDetail(PurApplyItemReq req) {
		return purApplyMapper.countPurApplyItemDetail(req);
	}

	/**
	 * 查询已确认收货的串码
	 * @param req
	 * @return
	 */
	public int countPurApplyItemDetailReving(PurApplyItemReq req) {
		return purApplyMapper.countPurApplyItemDetailReving(req);
	}

	/**
	 * 更新串码状态
	 * @param mktResInstNbrList
	 * @return
	 */
	public Integer updatePurApplyItemDetailStatusCd(List<String> mktResInstNbrList) {
		return purApplyMapper.updatePurApplyItemDetailStatusCd(mktResInstNbrList);
	}
	/**
	 * 更新采购类型
	 * @param req
	 * @return
	 */
	public Integer updatePurTypeByApplyId(AddProductReq req) {
		return purApplyMapper.updatePurTypeByApplyId(req);
	}

	
}
