package com.iwhalecloud.retail.report.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.request.ReportOrderDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportOrderNbrDaoReq;
import com.iwhalecloud.retail.report.dto.response.ReportOrderNbrResp;
import com.iwhalecloud.retail.report.dto.response.ReportOrderResp;
import com.iwhalecloud.retail.report.manager.ReportOrderManager;
import com.iwhalecloud.retail.report.service.ReportOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
@Service
public class ReportOrderServiceImpl implements ReportOrderService {
	 
	@Autowired
	private ReportOrderManager reportOrderManager;

	@Override
	public ResultVO<Page<ReportOrderResp>> getReportOrderList1(ReportOrderDaoReq req) {
		String productName = req.getProductName();
		if(productName != null) {
			productName = productName.replace(" ", "%");
			req.setProductName(productName);
		}
 		Page<ReportOrderResp> page = (Page<ReportOrderResp>) reportOrderManager.listReportOrder(req);
	    List<ReportOrderResp> list = page.getRecords();
	    
	    for(ReportOrderResp dto : list) {
	    	String itemId = dto.getItemId();
	    	String create_time = dto.getCreateTime();
	    	String pay_time = dto.getPayTime();
	    	String receive_time = dto.getReceiveTime();
	    	String paymentType = dto.getPaymentType();
	    	String sourceFrom = dto.getSourceFrom();
	    	if("YHJ".equals(sourceFrom)) {
	    		dto.setSourceFrom("PC");
	    	} else {
	    		dto.setSourceFrom("其他");
	    	}
	    	dto.setPrice(dto.getPrice()/100);
	    	dto.setTotalMoney(dto.getTotalMoney()/100);
	    	if(create_time != null && create_time != "" ){
	    		String[] createTime = create_time.split("\\.");
	    		if(createTime.length>0){
	    			create_time = createTime[0];
	    			dto.setCreateTime(create_time);
	    		}
	    	}
			if(pay_time != null && pay_time != ""){
				String[] payTime = pay_time.split("\\.");
	    		if(payTime.length>0){
	    			pay_time = payTime[0];
	    			dto.setPayTime(pay_time);
	    		}
			}
			
			if(receive_time != null && receive_time != ""){
				String[] receiveTime = receive_time.split("\\.");
	    		if(receiveTime.length>0){
	    			receive_time = receiveTime[0];
	    			dto.setReceiveTime(receive_time);
	    		}
			}
			
			if("1".equals(paymentType)){
				dto.setPaymentType("线上支付");
			}else if ("3".equals(paymentType)) {
				dto.setPaymentType("线下支付");
			}else {
				dto.setPaymentType("其他");
			}
			
			dto.setCouponMoney(dto.getCouponMoney()/100);
			dto.setTotalCouponMoney(dto.getTotalCouponMoney()/100);
	    	// TODO 通过orderId查出串码
	    	List<ReportOrderNbrResp> li =reportOrderManager.listReportOrderNbr(itemId);
	    	
	    	 dto.setNbr(li);
	    	
	    	 dto.setShipNum(li.size());
	    }
	    return ResultVO.success(page);
	}

	//导出
	@Override
	public ResultVO<List<ReportOrderResp>> getReportOrderList1dc(ReportOrderDaoReq req) {
		List<ReportOrderResp> list = (List<ReportOrderResp>) reportOrderManager.ListReportOrder1dc(req);
	    for(ReportOrderResp dto : list) {
	    	String itemId = dto.getItemId();
	    	// TODO 通过orderId查出串码
	    	List<ReportOrderNbrResp> li =reportOrderManager.listReportOrderNbr(itemId);
	    	 dto.setNbr(li);
	    	 dto.setShipNum(li.size());
	    }
	    
	    List<ReportOrderResp> list2 = new ArrayList<ReportOrderResp>();
	    for(ReportOrderResp rr : list){
	    	String status = rr.getStatus();//订单状态
	    	String orderType = rr.getOrderType();//订单类型
	    	String orderCat = rr.getOrderCat();//交易类型
	    	String paymentType = rr.getPaymentType();//支付类型
	    	String lanId = rr.getLanId();
	    	String couponType = rr.getCouponType();//优惠类型
	    	rr.setTotalCouponMoney(rr.getTotalCouponMoney()/100);
	    	rr.setCouponMoney(rr.getCouponMoney()/100);
	    	rr.setPrice(rr.getPrice()/100);
	    	rr.setTotalMoney(rr.getTotalMoney()/100);
	    	if(status != null){
	    		if("2".equals(status)){
	    			rr.setStatus("待支付");
	    		}else if("3".equals(status)){
	    			rr.setStatus("已支付、待受理");
	    		}else if("4".equals(status)){
	    			rr.setStatus("待发货");
	    		}else if("5".equals(status)){
	    			rr.setStatus("已发货待确认");
	    		}else if("6".equals(status)){
	    			rr.setStatus("已收货待评价");
	    		}else if("10".equals(status)){
	    			rr.setStatus("已完成");
	    		}else if("11".equals(status)){
	    			rr.setStatus("待审核");
	    		}else if("12".equals(status)){
	    			rr.setStatus("待卖家确认");
	    		}else if("13".equals(status)){
	    			rr.setStatus("待支付定金");
	    		}else if("14".equals(status)){
	    			rr.setStatus("待支付尾款");
	    		}else if("99".equals(status)){
	    			rr.setStatus("订单异常");
	    		}else if("-8".equals(status)){
	    			rr.setStatus("作废");
	    		}else if("-9".equals(status)){
	    			rr.setStatus("撤单");
	    		}else if("-10".equals(status)){
	    			rr.setStatus("取消订单");
	    		}else if("-11".equals(status)){
	    			rr.setStatus("审核不通过");
	    		}else if("-12".equals(status)){
	    			rr.setStatus("卖家确认不通过");
	    		}else if("0".equals(status)){
	    			rr.setStatus("初始状态");
	    		}else if("-1".equals(status)){
	    			rr.setStatus("已退货");
	    		}else if("-2".equals(status)){
	    			rr.setStatus("已退款");
	    		}else if("-3".equals(status)){
	    			rr.setStatus("已换货");
	    		}else if("41".equals(status)){
	    			rr.setStatus("部分发货");
	    		}else {
	    			rr.setStatus("其他");
	    		}
	    	}
	    	
	    	if(orderType != null){
	    		if("11".equals(orderType)){
	    			rr.setOrderType("省包至地包交易订单");
	    		}else if("12".equals(orderType)){
	    			rr.setOrderType("省包至零售商交易订单");
	    		}else if("13".equals(orderType)){
	    			rr.setOrderType("地包到零售商交易订单");
	    		}else if("14".equals(orderType)){
	    			rr.setOrderType("强制分货零售商订单");
	    		}else if("15".equals(orderType)){
	    			rr.setOrderType("省内代收订单");
	    		}else if("20".equals(orderType)){
	    			rr.setOrderType("零售商到客户交易订单");
	    		}else if("21".equals(orderType)){
	    			rr.setOrderType("分销交易订单");
	    		}else {
	    			rr.setOrderType("其他");
	    		}
	    	}
	    	
	    	if(orderCat != null){
	    		if("0".equals(orderCat)){
	    			rr.setOrderCat("普通分销");
	    		}else if("1".equals(orderCat)){
	    			rr.setOrderCat("预售");
	    		}else {
	    			rr.setOrderCat("其他");
	    		}
	    	}
	    	
	    	if(paymentType != null){
	    		if("1".equals(paymentType)){
	    			rr.setPaymentType("在线支付");
	    		}else if("3".equals(paymentType)){
	    			rr.setPaymentType("线下支付");
	    		} else {
	    			rr.setPaymentType("其他");
	    		} 
	    	}
	    	if(lanId != null){
				if("730".equals(lanId)){
					rr.setLanId("岳阳市");
				}else if("731".equals(lanId)){
					rr.setLanId("长沙市");
				}else if("732".equals(lanId)){
					rr.setLanId("湘潭市");
				}else if("733".equals(lanId)){
					rr.setLanId("株洲市");
				}else if("734".equals(lanId)){
					rr.setLanId("衡阳市");
				}else if("735".equals(lanId)){
					rr.setLanId("郴州市");
				}else if("736".equals(lanId)){
					rr.setLanId("常德市");
				}else if("737".equals(lanId)){
					rr.setLanId("益阳市");
				}else if("738".equals(lanId)){
					rr.setLanId("娄底市");
				}else if("739".equals(lanId)){
					rr.setLanId("邵阳市");
				}else if("743".equals(lanId)){
					rr.setLanId("湘西本地网");
				}else if("744".equals(lanId)){
					rr.setLanId("张家界本地网");
				}else if("745".equals(lanId)){
					rr.setLanId("怀化本地网");
				}else if("746".equals(lanId)){
					rr.setLanId("永州本地网");
				}
				
			}
	    	
	    	if(couponType != null){
	    		if("10".equals(couponType)){
	    			rr.setCouponType("减免(直减)");
	    		}else if("20".equals(couponType)){
	    			rr.setCouponType("卡券");
	    		}else if("30".equals(couponType)){
	    			rr.setCouponType("返利");
	    		}else if("40".equals(couponType)){
	    			rr.setCouponType("赠送");
	    		}else if("50".equals(couponType)){
	    			rr.setCouponType("红包");
	    		}else {
	    			rr.setCouponType("其他");
	    		}
	    	}
	    	
	    	list2.add(rr);
	    }
	    return ResultVO.success(list2);
	}
	
	@Override
	public ResultVO<Page<ReportOrderNbrResp>> getReportOrderList3(ReportOrderNbrDaoReq req) {
		Page<ReportOrderNbrResp> page = (Page<ReportOrderNbrResp>) reportOrderManager.listReportOrderNbr3(req);
		
		return ResultVO.success(page);	
	}

}
