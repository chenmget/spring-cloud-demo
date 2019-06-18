package com.iwhalecloud.retail.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.MenuDTO;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportOrderDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportOrderNbrDaoReq;
import com.iwhalecloud.retail.report.dto.response.ReportOrderNbrResp;
import com.iwhalecloud.retail.report.dto.response.ReportOrderResp;
import com.iwhalecloud.retail.report.entity.Menu;
import com.iwhalecloud.retail.report.manager.MenuManager;
import com.iwhalecloud.retail.report.manager.ReportManager;
import com.iwhalecloud.retail.report.manager.ReportOrderManager;
import com.iwhalecloud.retail.report.service.ReportOrderService;
import com.iwhalecloud.retail.report.service.ReportService;
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
	    	String orderId = dto.getOrderId();
	    	String create_time = dto.getCreateTime();
	    	String pay_time = dto.getPayTime();
	    	String receive_time = dto.getReceiveTime();
	    	String couponMoney = dto.getCouponMoney();
	    	String paymentType = dto.getPaymentType();
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
			}else if ("2".equals(paymentType)) {
				dto.setPaymentType("线下支付");
			}
			
			if(couponMoney != null && couponMoney != "" && couponMoney.length()>2){
				couponMoney = couponMoney.substring(0,couponMoney.length()-2);
				dto.setCouponMoney(couponMoney);
			}
	    	// TODO 通过orderId查出串码
	    	List<ReportOrderNbrResp> li =reportOrderManager.listReportOrderNbr(orderId);
	    	
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
	    	String orderId = dto.getOrderId();
	    	// TODO 通过orderId查出串码
	    	List<ReportOrderNbrResp> li =reportOrderManager.listReportOrderNbr(orderId);
	    	 dto.setNbr(li);
	    	 dto.setShipNum(li.size());
	    }
	    
	    List<ReportOrderResp> list2 = new ArrayList<ReportOrderResp>();
	    for(ReportOrderResp rr : list){
	    	String status = rr.getStatus();//订单状态
	    	String orderType = rr.getOrderType();//订单类型
	    	String type = rr.getOrderCat();//交易类型
//	    	String paymentType = rr.getPaymentType();//支付类型
	    	String payType = rr.getPayType();//支付方式
	    	String lanId = rr.getLanId();
	    	String couponType = rr.getCouponType();//优惠类型

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
	    		}
	    	}
	    	
	    	if(orderType != null){
	    		if("2".equals(orderType)){
	    			rr.setOrderType("未知");
	    		}else if("11".equals(orderType)){
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
	    		}
	    	}
	    	
	    	if(type != null){
	    		if("0".equals(type)){
	    			rr.setOrderCat("普通分销");
	    		}else if("1".equals(type)){
	    			rr.setOrderCat("预售");
	    		}
	    	}
	    	
//	    	if(paymentType != null){
//	    		if("1".equals(paymentType)){
//	    			rr.setPaymentType("在线支付");
//	    		}else if("2".equals(paymentType)){
//	    			rr.setPaymentType("线下支付");
//	    		}
//	    	}
	    	
	    	if(payType != null){
	    		if("1".equals(payType)){
	    			rr.setPayType("翼支付");
	    		}else if("2".equals(payType)){
	    			rr.setPayType("微信支付");
	    		}else if("3".equals(payType)){
	    			rr.setPayType("支付宝支付");
	    		}else if("4".equals(payType)){
	    			rr.setPayType("线下支付");
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
