package com.iwhalecloud.retail.order.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order.dto.model.OrderItemModel;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderPageResp;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderRankResp;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderResp;
import com.iwhalecloud.retail.order.dto.resquest.AddOrderRecommenderReqDTO;
import com.iwhalecloud.retail.order.dto.resquest.ListOrderRecommenderReq;
import com.iwhalecloud.retail.order.entity.OrderRecommender;
import com.iwhalecloud.retail.order.manager.OrderManager;
import com.iwhalecloud.retail.order.manager.OrderRecommenderManager;
import com.iwhalecloud.retail.order.service.OrderRecommenderService;
import com.iwhalecloud.retail.partner.dto.PartnerShopDTO;
import com.iwhalecloud.retail.partner.dto.PartnerStaffDTO;
import com.iwhalecloud.retail.partner.service.PartnerShopService;
import com.iwhalecloud.retail.partner.service.PartnerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Component("orderRecommenderService")
@Service
public class OrderRecommenderServiceImpl implements OrderRecommenderService {

    @Autowired
    private OrderRecommenderManager orderRecommenderManager;
    @Autowired
    private OrderManager orderManager;
    @Reference
    private PartnerStaffService partnerStaffService;
    @Reference
    private PartnerShopService partnerShopService;

	@Override
    public Page<OrderRecommenderPageResp> listOrderRecommender(ListOrderRecommenderReq dto) {
    	return orderRecommenderManager.listOrderRecommender(dto);
    }


	@Override
	public List<OrderRecommenderResp> getOrderRecommender(String orderId) {
		return orderRecommenderManager.getOrderRecommender(orderId);
	}


	@Override
	@Transactional
	public Integer addOrderRecommender(AddOrderRecommenderReqDTO rqeuest) {
		String staffName = "";
		String shopId = "";
		String shopAddress = "";
		String shopName = "";
		PartnerStaffDTO staff = partnerStaffService.getPartnerStaff(rqeuest.getStaffId());
		if (null != staff) {
			staffName = staff.getStaffName();
			shopId = staff.getPartnerShopId();
			PartnerShopDTO shop = partnerShopService.getPartnerShop(staff.getPartnerShopId());
			shopAddress = (shop == null) ? shopAddress : shop.getAddress();
			shopName = (shop == null) ? shopName : shop.getName();
		}
		// TODO 导购员信息无效的情况，以及非当前厅店导购员的情况，不允许进行揽装关系的录入
		// 查询订单商品信息
        List<OrderItemModel> orderItemsList = orderManager.selectOrderItemsList(rqeuest.getOrderId());
        if (null == orderItemsList || orderItemsList.isEmpty()) {
			return -1;
		}
        
        // 获取此用户的最新推荐记录
        OrderRecommenderResp newestOrderRecommender = orderRecommenderManager.getNewestOrderRecommender(rqeuest.getStaffId());
        
        Integer num = 0;
        StringBuilder recommendIds = new StringBuilder();
        for (int i = 0; i < orderItemsList.size(); i++) {
        	OrderItemModel orderItems = orderItemsList.get(i);
        	OrderRecommender dto = new OrderRecommender();
        	dto.setStaffId(rqeuest.getStaffId());
        	dto.setStaffName(staffName);
        	dto.setShopId(shopId);
        	dto.setOrderId(rqeuest.getOrderId());
        	dto.setGoodAmount(orderItems.getNum());
        	dto.setGoodId(orderItems.getGoodsId());
        	dto.setGoodName(orderItems.getName());
        	dto.setShopAddress(shopAddress);
        	dto.setShopName(shopName);
        	
        	// 计算总提成
    		Double totalOrderMoney = orderItems.getPrice() * orderItems.getNum();
    		Integer totalOrderAmount = orderItems.getNum();
    		if (null != newestOrderRecommender) {
    			totalOrderMoney += newestOrderRecommender.getTotalOrderMoney();
    			totalOrderAmount += newestOrderRecommender.getTotalOrderAmount();
    		}
    		
            dto.setCreateDate(new Date());
            dto.setTotalOrderMoney(totalOrderMoney);
            dto.setTotalOrderAmount(totalOrderAmount);
            dto.setCreateDate(new Date());
            num += orderRecommenderManager.addOrderRecommender(dto);
            if (i == (orderItemsList.size() - 1)) {
            	recommendIds.append(dto.getRecommenderId());
			}else {
				recommendIds.append(dto.getRecommenderId()).append(",");
				
			}
		}
        orderManager.updateRecommdId(rqeuest.getOrderId(), recommendIds.toString());
		
		return num;
	}


	@Override
	public OrderRecommenderResp getNewestOrderRecommender(String staffId) {
		return orderRecommenderManager.getNewestOrderRecommender(staffId);
	}


	@Override
	public List<OrderRecommenderRankResp> listOrderRecommenderRank(String shopId) {
		return orderRecommenderManager.listOrderRecommenderRank(shopId);
	}

}