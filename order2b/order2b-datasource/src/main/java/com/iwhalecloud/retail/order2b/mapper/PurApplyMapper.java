package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.response.purapply.*;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.*;
import com.iwhalecloud.retail.order2b.entity.PurApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurApplyMapper extends BaseMapper<PurApply>  {

	public Page<PurApplyResp> cgSearchApply(Page<PurApplyResp> page,@Param("req") PurApplyReq purApplyReq) ;

	public WfTaskResp getTaskItemId(@Param("applyId") String applyId);

	public Page<PurApplyResp> cgSearchApplyLan(Page<PurApplyResp> page,@Param("req") PurApplyReq purApplyReq) ;

	public void tcProcureApply(@Param("req") ProcureApplyReq procureApplyReq);

	public List<ProdProductChangeDetail> searchCommitPriceInfo(@Param("req") UpdateCorporationPriceReq req);

	public void crPurApplyFile(@Param("req") AddFileReq procureApplyReq);

	public void crPurApplyItem(@Param("req") AddProductReq addProductReq);

	public PriCityManagerResp getLoginInfo(@Param("userId") String userId);

	public void delSearchApply(@Param("req") PurApplyReq purApplyReq);

	public ApplyHeadResp hqShenQingDaoHao();

	public String hqDiShiBuMen(@Param("dsbm") String dsbm) ;

	public void delSearchApplyItem(@Param("req") PurApplyReq purApplyReq);

	public CkProcureApplyResp ckApplyData1(@Param("req") PurApplyReq req);
	public List<AddProductReq> ckApplyData2(@Param("req") PurApplyReq req) ;
	public List<AddFileReq> ckApplyData3(@Param("req") PurApplyReq req) ;
	public List<PurApplyExtReq> ckApplyData4(@Param("req") PurApplyReq req);

	public int isHaveSave(@Param("applyId") String applyId);

	public void updatePurApply(@Param("req") ProcureApplyReq state);

	public void delApplyItem(@Param("req") ProcureApplyReq req);

	public void delApplyFile(@Param("req") ProcureApplyReq req);

	public void delPurApplyExt(@Param("req") ProcureApplyReq req);

	public MemMemberAddressReq selectMemMeneberAddr(@Param("req") ProcureApplyReq req);

	public void insertPurApplyExt(@Param("req") MemMemberAddressReq req);

	public String getMerchantCode(@Param("merchantId") String merchantId);

	public String hqSeqFileId();

	public String hqSeqItemId();

	public void addShippingAddress(@Param("req") MemMemberAddressReq req);

	public void insertProdChangePrice(@Param("req") ProdProductChangeReq req);
	public void updateProdProduct(@Param("req") ProdProductChangeReq req);

	public String getProductBaseIdByProductId(@Param("sn") String sn);
	public void insertProdProductChangeDetail(@Param("req") ProdProductChangeDetail req);

	public String selectOldValue(@Param("sn") String sn);

	public void commitPriceExcel(@Param("req") UpdateCorporationPriceReq req);

	public List<PurApplyItemResp> comparePrice(@Param("applyId") String applyId);

	public void updatePurApplyStatusCd(@Param("req") ProcureApplyReq req);

	public void updateProductChange(@Param("req") ProdProductChangeReq req);

	public void updateProductCorpPrice(@Param("req") ProdProductChangeDetail req);

	public String selectProductIdByChangeId(@Param("changeId") String changeId);

	public void updateProdNoPassPrice(@Param("req") ProdProductChangeDetail req);

	public List<ProdProductChangeDetail> selectProdProductChangeDetail(@Param("keyValue") String keyValue);

	public List<String> countPurApplyItemDetail(@Param("req") PurApplyItemReq req);
	public int countPurApplyItemDetailReving(@Param("req") PurApplyItemReq req);

	public Integer updatePurApplyItemDetailStatusCd(@Param("mktResInstNbrList") List<String> mktResInstNbrList,@Param("updateDate")String updateDate,@Param("updateUserId")String updateUserId);


	public PurApply getPurApplyByAppId(@Param("applyId") String applyId);

	public Integer updatePurTypeByApplyId(@Param("req")AddProductReq req);

	public Page<PurApplyReportResp> applySearchReport(Page<PurApplyReportResp> page,@Param("req") PurApplyReportReq req) ;

	public Page<PurApplyStatusReportResp> applyStatuSearchReport(Page<PurApplyStatusReportResp> page,@Param("req") PurApplyStatusReportReq req) ;

	List<PurApplyItemResp>getDeliveryInfoByAppId(@Param("applyId") String applyId);
	public List<String> countDelivery(@Param("applyId") String applyId);


}
