package com.iwhalecloud.retail.order2b.mapper;

import java.util.List;

import com.iwhalecloud.retail.order2b.dto.response.purapply.*;
import com.iwhalecloud.retail.order2b.entity.PurApplyItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.iwhalecloud.retail.order2b.entity.PurApply;

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
	
	public String getProductBaseIdByProductId(@Param("productId") String productId);
	public void insertProdProductChangeDetail(@Param("req") ProdProductChangeDetail req);
	
	public String selectNextChangeId() ;
	public String selectNextChangeDetailId() ;
	public String selectOldValue(@Param("productId") String productId);
	
	public void commitPriceExcel(@Param("req") UpdateCorporationPriceReq req);

	public List<PurApplyItemResp> comparePrice(@Param("applyId") String applyId);

	public void updatePurApplyStatusCd(@Param("req") ProcureApplyReq req);

	public int updateProductChange(@Param("req") ProdProductChangeReq req);
	
	public int updateProductCorpPrice(@Param("req") ProdProductChangeDetail req);
	
	public int updateProdNoPassPrice(@Param("req") ProdProductChangeDetail req);
	
	public List<ProdProductChangeDetail> selectProdProductChangeDetail(@Param("keyValue") String keyValue);
	
}
