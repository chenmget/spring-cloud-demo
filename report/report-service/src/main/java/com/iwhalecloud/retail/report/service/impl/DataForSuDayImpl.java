package com.iwhalecloud.retail.report.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.report.dto.request.MktResInstEventReq;
import com.iwhalecloud.retail.report.dto.request.RptSupplierOperatingDayReq;
import com.iwhalecloud.retail.report.dto.response.MktResEventruchu;
import com.iwhalecloud.retail.report.dto.response.MktResInstResq;
import com.iwhalecloud.retail.report.dto.response.ParMerchantResp;
import com.iwhalecloud.retail.report.dto.response.PurchaseAmountResp;
import com.iwhalecloud.retail.report.manager.RptSupplierOperatingDayManager;
import com.iwhalecloud.retail.report.service.IReportDataInfoService;
import com.iwhalecloud.retail.report.service.RptSupplierOperatingDayService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class DataForSuDayImpl implements RptSupplierOperatingDayService {
	
	@Reference
	private IReportDataInfoService iReportDataInfoService;
	
	@Reference
	private RptSupplierOperatingDayService rptSupplierOperatingDayService;
	
	@Autowired
	private RptSupplierOperatingDayManager rptSupplierOperatingDayManager;
	
	//获取供应商经营日报表数据
	@Override
	public void hqRptSupplierOperatingDayData() {
		//第一步：获取供应商列表,循环处理每一个商家ID
		List<ParMerchantResp> parMerchantlist = rptSupplierOperatingDayService.hqParMerchantInfo();
		for(ParMerchantResp parMerchant : parMerchantlist) {
			String supplierId = parMerchant.getMerchantId();//供应商id
			String supplierCode = parMerchant.getMerchantCode();//供应商编码
			String supplierName = parMerchant.getMerchantName();//供应商名称
			String cityId = parMerchant.getLanId();//地市
			String countyId = parMerchant.getCity();//区县 
			String mktResStoreId = iReportDataInfoService.getMyMktResStoreId(supplierId);
			//通过仓库ID查库存
			List<MktResInstResq> listMkt = rptSupplierOperatingDayService.hqMktResInstInfo(mktResStoreId);
			for(MktResInstResq mktResInst : listMkt) {
				//写表
				RptSupplierOperatingDayReq req = new RptSupplierOperatingDayReq();
				req.setSupplierId(supplierId);//供应商id
				req.setSupplierCode(supplierCode);//供应商编码
				req.setSupplierName(supplierName);//供应商名称
				req.setCityId(cityId);//地市
				req.setCountyId(countyId);//区县
				req.setGoodsId(null);//商品id
				req.setProductBaseId(mktResInst.getProductBaseId());//型号id
				req.setProductBaseName(mktResInst.getProductBaseName());//型号名称
				req.setProductId(mktResInst.getProductId());//产品id
				req.setProductName(mktResInst.getProductName());//产品名称
				req.setBrandId(mktResInst.getBrandId());//品牌id
				req.setBrandName(mktResInst.getBrandName());//品牌名称
				req.setPriceLevel(mktResInst.getPriceLevel());//档位
				req.setSellNum(null);//总销量=地包发货出库量
				req.setSellAmount(null);//销售额=销量*进店价
				req.setPurchaseAmount(null);//进货金额=交易进货量*省包供货价
				req.setPurchaseNum(null);//交易进货量
				req.setManualNum(null);//手工录入量
				req.setTransInNum(null);//调入量
				req.setTransOutNum(null);//调出量
				req.setReturnNum(null);//退库量
				req.setStockNum(mktResInst.getStockNum());//库存总量
				req.setStockAmount(mktResInst.getStockAmount());//库存金额
				req.setCreateDate(mktResInst.getCreateDate());//创建时间
				req.setTypeId(mktResInst.getTypeId());
				rptSupplierOperatingDayService.getDataForRptSupplierOperatingDay(req);
					
				}
				
			MktResInstEventReq mktResInstEventReq = new MktResInstEventReq();
			mktResInstEventReq.setMktResStoreId(mktResStoreId);
			mktResInstEventReq.setMerchantId(supplierId);
			//通过仓库ID查入库记录
			List<MktResEventruchu> listRu = rptSupplierOperatingDayService.hqmktResEventInfoRu(mktResInstEventReq);
			for(MktResEventruchu mktResEventruRu : listRu) {
				int manualNum = 0;
				int transInNum = 0;
				int purchaseNum = 0;
				String purchaseAmount = "";
				String goodsId = "";
				String eventType = mktResEventruRu.getEventType();
				String mktResEventId = mktResEventruRu.getMktResEventId();
				String orderId = mktResEventruRu.getObjId();
				String productId = mktResEventruRu.getProductId();//获取（mkt_res_id）产品ID
				String num = rptSupplierOperatingDayService.hqEventTypeNum(mktResEventId);
				if("1001".equals(eventType)) {//手工录入量
					manualNum += Integer.parseInt(num) ;
				}else if ("1002".equals(eventType)) {//调入量
					transInNum += Integer.parseInt(num) ;
				}else if ("1003".equals(eventType)) {//交易进货量
					purchaseNum += Integer.parseInt(num) ;
					PurchaseAmountResp purchaseAmountResp = rptSupplierOperatingDayService.hqPurchaseAmount(orderId);//进货金额
					purchaseAmount = purchaseAmountResp.getPurchaseAmount();//进货金额
					goodsId = purchaseAmountResp.getGoodsId();//获取商品ID
				}
				//判断入库的机型库存是否有记录
				mktResInstEventReq.setProductId(productId);
				String itemId = rptSupplierOperatingDayService.hqIsHaveRecord(mktResInstEventReq);
				RptSupplierOperatingDayReq updateRu = new RptSupplierOperatingDayReq();
				updateRu.setItemId(itemId);
				updateRu.setGoodsId(goodsId);
				updateRu.setManualNum(Integer.toString(manualNum));
				updateRu.setTransInNum(Integer.toString(transInNum));
				updateRu.setPurchaseNum(Integer.toString(purchaseNum));
				updateRu.setPurchaseAmount(purchaseAmount);
				rptSupplierOperatingDayService.updateRptSupplierRu(updateRu);
			}
			
			//通过仓库ID查出库记录
			List<MktResEventruchu> listChu = rptSupplierOperatingDayService.hqmktResEventInfoChu(mktResInstEventReq);
			for(MktResEventruchu mktResEventruchu : listChu) {
				int transOutNum = 0;
				int returnNum = 0;
				int sellNum = 0;
				String sellAmount = "";
				String goodsId = "";
				String eventType = mktResEventruchu.getEventType();
				String mktResEventId = mktResEventruchu.getMktResEventId();
				String orderId = mktResEventruchu.getObjId();
				String productId = mktResEventruchu.getProductId();//获取（mkt_res_id）产品ID,先看产品在表中有没有记录，
				String num = rptSupplierOperatingDayService.hqEventTypeNum(mktResEventId);
				if("1002".equals(eventType)) {//调出量
					transOutNum += Integer.parseInt(num) ;
				}else if ("1009".equals(eventType)) {//退库量
					returnNum += Integer.parseInt(num) ;
				}else if ("1003".equals(eventType)) {//订单销售
					sellNum += Integer.parseInt(num) ;
					PurchaseAmountResp purchaseAmountResp = rptSupplierOperatingDayService.hqPurchaseAmount(orderId);//销售金额
					sellAmount = purchaseAmountResp.getPurchaseAmount();
					goodsId = purchaseAmountResp.getGoodsId();
				}
				mktResInstEventReq.setProductId(productId);
				String itemId = rptSupplierOperatingDayService.hqIsHaveRecord(mktResInstEventReq);
				RptSupplierOperatingDayReq updateChu = new RptSupplierOperatingDayReq();
				updateChu.setItemId(itemId);
				updateChu.setTransOutNum(Integer.toString(transOutNum));
				updateChu.setReturnNum(Integer.toString(returnNum));
				updateChu.setSellNum(Integer.toString(sellNum));
				updateChu.setSellAmount(sellAmount);
				updateChu.setGoodsId(goodsId);
				rptSupplierOperatingDayService.updateRptSupplierChu(updateChu);
			}
		}
		
	}
	
	//获取供应商列表
	@Override
	public List<ParMerchantResp> hqParMerchantInfo(){
		return rptSupplierOperatingDayManager.hqParMerchantInfo();
	}
	
	//获取当前库存
	@Override
	public List<MktResInstResq> hqMktResInstInfo(String mktResStoreId) {
        return rptSupplierOperatingDayManager.hqMktResInstInfo(mktResStoreId);
    }
	
	//获取入库记录信息
	@Override
	public List<MktResEventruchu> hqmktResEventInfoRu (MktResInstEventReq req) {
		return rptSupplierOperatingDayManager.hqmktResEventInfoRu(req);
	}
	
	//获取入库记录信息
	@Override
	public List<MktResEventruchu> hqmktResEventInfoChu (MktResInstEventReq req) {
		return rptSupplierOperatingDayManager.hqmktResEventInfoChu(req);
	}
	
	//获取变动事件的数量
	@Override
	public String hqEventTypeNum (String mktResEventId) {
		return rptSupplierOperatingDayManager.hqEventTypeNum(mktResEventId);
	}
	
	//获取进货金额
	@Override
	public PurchaseAmountResp hqPurchaseAmount(String orderId) {
		return rptSupplierOperatingDayManager.hqPurchaseAmount(orderId);
	}
	//看入库的机型在库存里面有没有记录
	@Override
	public String hqIsHaveRecord(MktResInstEventReq req) {
		return rptSupplierOperatingDayManager.hqIsHaveRecord(req);
	}
	//插表
	@Override
	public void getDataForRptSupplierOperatingDay(RptSupplierOperatingDayReq req) {
		rptSupplierOperatingDayManager.getDataForRptSupplierOperatingDay(req);
    }
	//更新入库
	@Override
	public void updateRptSupplierRu(RptSupplierOperatingDayReq req) {
		rptSupplierOperatingDayManager.updateRptSupplierRu(req);
	}
	//更新出库
	@Override
	public void updateRptSupplierChu(RptSupplierOperatingDayReq req) {
		rptSupplierOperatingDayManager.updateRptSupplierChu(req);
	}
}
