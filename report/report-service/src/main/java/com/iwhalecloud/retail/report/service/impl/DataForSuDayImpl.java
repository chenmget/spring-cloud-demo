package com.iwhalecloud.retail.report.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.report.dto.request.MktResInstEventReq;
import com.iwhalecloud.retail.report.dto.request.RptSupplierOperatingDayReq;
//import com.iwhalecloud.retail.report.dto.response.MktIdStatusResp;
import com.iwhalecloud.retail.report.dto.response.MktResEventruchu;
import com.iwhalecloud.retail.report.dto.response.MktResInstResq;
import com.iwhalecloud.retail.report.dto.response.ParMerchantResp;
import com.iwhalecloud.retail.report.manager.RptSupplierOperatingDayManager;
import com.iwhalecloud.retail.report.service.DataForSuDay;

@Service
public class DataForSuDayImpl implements DataForSuDay {

	@Autowired
	private RptSupplierOperatingDayManager rptSupplierOperatingDayManager;
	
	@Override
	public void hqDataForRptSupplierOperatingDay() {
		//第一步：获取获取供应商列表,循环处理每一个商家ID
		List<ParMerchantResp> parMerchantlist = rptSupplierOperatingDayManager.hqParMerchant();
		for(int i=0;i<parMerchantlist.size();i++){
			MktResInstEventReq mktreq = new MktResInstEventReq();
			ParMerchantResp parMerchant = parMerchantlist.get(i);
			
			String merchant_id = parMerchant.getMerchantId();//供应商id
			String merchant_code = parMerchant.getMerchantCode();//供应商编码
			String merchant_name = parMerchant.getMerchantName();//供应商名称
			String lan_id = parMerchant.getLanId();//地市
			String city = parMerchant.getCity();//区县
			//第二步：通过商家ID获取供应商仓库
			mktreq.setMerchantId(merchant_id);
				//mktreq.setMerchantId("4301811025392");
			String mkt_res_store_id = rptSupplierOperatingDayManager.hqMktResStore(mktreq);
			mktreq.setMktResStoreId(mkt_res_store_id);
				//mktreq.setMktResStoreId("21");
			//第五步当前库存的多个机型：
			List<MktResInstResq> mktResInstlist= rptSupplierOperatingDayManager.hqMktResInst(mktreq);
			for(int j=0;j<mktResInstlist.size();j++){
				MktResInstResq mktResInst = mktResInstlist.get(j);
				String product_id = mktResInst.getProductId();//产品id
				String product_base_name = mktResInst.getProductBaseName();//型号名称
				String product_name = mktResInst.getProductName();//产品名称
				String brand_id = mktResInst.getBrandId();//品牌id
				String brand_name = mktResInst.getBrandName();//品牌名称
				String price_level = mktResInst.getPriceLevel();//档位
				String product_base_id = mktResInst.getProductBaseId();//型号id
				String type_id = mktResInst.getTypeId();//产品类型
				mktreq.setProductBaseId(product_base_id);
				//每一个机型的入库：
				int manual_num = 0;
				int trans_in_num = 0;
				int purchase_num = 0;
				double purchase_amount = 0.0;
				String goods_id = null;
				double price = 0.0;
					//mktreq.setMktResStoreId("11");
					//mktreq.setProductBaseId("100000112");
				List<MktResEventruchu> mktResEventlistru = rptSupplierOperatingDayManager.hqmktResEventru(mktreq);
				for(int k=0;k<mktResEventlistru.size();k++){
					manual_num = 0;//手工录入量
					trans_in_num = 0;//调入量
					purchase_num = 0;//交易进货量
					purchase_amount = 0;//进货金额
					MktResEventruchu mktResEventru = mktResEventlistru.get(k);
					String event_type = mktResEventru.getEventType();
					goods_id = mktResEventru.getGoodsId();//商品id
					price = Integer.parseInt(mktResEventru.getPrice());
					if("1001".equals(event_type)){
						manual_num ++;
					}else if("1002".equals(event_type)){
						trans_in_num ++;
					}else if("1003".equals(event_type)){
						purchase_num ++;
					}
					purchase_amount = (manual_num+trans_in_num+purchase_num)*price;
				}
				
				
				//每一个机型的出库：
				int trans_out_num = 0;
				int return_num = 0;
				int sell_num = 0;
				double sell_amount = 0.0;
					//mktreq.setMktResStoreId("33");
				List<MktResEventruchu> mktResEventlistchu = rptSupplierOperatingDayManager.hqmktResEventchu(mktreq);
				for(int m=0;m<mktResEventlistchu.size();m++){
					trans_out_num = 0;
					return_num = 0;
					sell_num = 0;//总销量
					sell_amount = 0;//销售额
					MktResEventruchu mktResEventchu = mktResEventlistchu.get(m);
					String event_type = mktResEventchu.getEventType();
					price = Integer.parseInt(mktResEventchu.getPrice());
					if("1002".equals(event_type)){
						trans_out_num ++;
					}else if("1009".equals(event_type)){
						return_num ++;
					}else if("1003".equals(event_type)){
						//如果是订单销售
						sell_num ++;
					}
					sell_amount = sell_num*price;
				}
				int stock_num = manual_num +trans_in_num +purchase_num-trans_out_num-return_num-sell_num;//入库总量-出库总量
				double stock_amount = stock_num*price;
				//写表	
				Calendar ca = Calendar.getInstance();//得到一个Calendar的实例 
				ca.setTime(new Date()); //设置时间为当前时间 
				ca.add(Calendar.DATE, -1);
				Date date = ca.getTime();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
				String nowTime = sf.format(date);
				RptSupplierOperatingDayReq req = new RptSupplierOperatingDayReq();
				req.setItemId(null);
				req.setItemDate(nowTime);
				req.setSupplierId(merchant_id);//供应商id
				req.setSupplierCode(merchant_code);//供应商编码
				req.setSupplierName(merchant_name);//供应商名称
				req.setCityId(lan_id);//地市
				req.setCountyId(city);//区县
				req.setGoodsId(goods_id);//商品id
				req.setProductBaseId(product_base_id);//型号id
				req.setProductBaseName(product_base_name);//型号名称
				req.setProductId(product_id);//产品id
				req.setProductName(product_name);//产品名称
				req.setBrandId(brand_id);//品牌id
				req.setBrandName(brand_name);//品牌名称
				req.setPriceLevel(price_level);//档位
				req.setSellNum(Integer.toString(sell_num));//总销量=地包发货出库量
				req.setSellAmount(Double.toString(sell_amount));//销售额=销量*进店价
				req.setPurchaseAmount(Double.toString(purchase_amount));//进货金额=交易进货量*省包供货价
				req.setPurchaseNum(Integer.toString(purchase_num));//交易进货量
				req.setManualNum(Integer.toString(manual_num));//手工录入量
				req.setTransInNum(Integer.toString(trans_in_num));//调入量
				req.setTransOutNum(Integer.toString(trans_out_num));//调出量
				req.setReturnNum(Integer.toString(return_num));//退库量
				req.setStockNum(Integer.toString(stock_num));//库存总量=入库总量—出库总量
				req.setStockAmount(Double.toString(stock_amount));//库存金额
				req.setCreateDate(nowTime);//创建时间
				req.setTypeId(type_id);
				 rptSupplierOperatingDayManager.getDataForRptSupplierOperatingDay(req);
			}
		}
	}

//	@Override
//	public void testUpdate() {
//		List<MktIdStatusResp> list = rptSupplierOperatingDayManager.getMktResEventId();
//		for(int i=0;i<list.size();i++){
//			MktIdStatusResp mktIdStatusResp = list.get(i);
//			rptSupplierOperatingDayManager.updateMktResEvent(mktIdStatusResp);
//		}
//	}

}
