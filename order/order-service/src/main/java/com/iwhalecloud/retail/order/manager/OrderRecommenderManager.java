package com.iwhalecloud.retail.order.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderPageResp;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderRankResp;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderResp;
import com.iwhalecloud.retail.order.dto.resquest.ListOrderRecommenderReq;
import com.iwhalecloud.retail.order.entity.OrderRecommender;
import com.iwhalecloud.retail.order.mapper.OrderRecommenderMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class OrderRecommenderManager {
    @Resource
    private OrderRecommenderMapper orderRecommenderMapper;
    
    /**
	 * 推荐人推荐记录
	 * @param dto
	 * @return
	 */
    public Page<OrderRecommenderPageResp> listOrderRecommender(ListOrderRecommenderReq dto) {
    	Page<ListOrderRecommenderReq> page = new Page<ListOrderRecommenderReq>(dto.getPageNo(), dto.getPageSize());
    	return orderRecommenderMapper.listOrderRecommender(page, dto);
    }
    
    /**
	 * 订单ID查询的推荐记录
	 * @param orderId
	 * @return
	 */
    public List<OrderRecommenderResp> getOrderRecommender(String orderId) {
    	return orderRecommenderMapper.getOrderRecommender(orderId);
    }
    
    /**
	 * 获取推荐人的最新订单的推荐记录
	 * @param staffId
	 * @return
	 */
    public OrderRecommenderResp getNewestOrderRecommender(String staffId) {
    	return orderRecommenderMapper.getNewestOrderRecommender(staffId);
    }
    
    /**
     * 增加推荐记录
     * @param dto
     * @return
     */
    public Integer addOrderRecommender(OrderRecommender dto) {
    	return orderRecommenderMapper.insert(dto);
    }
    /**
	 * 推荐排行榜
	 * @param shopId
	 * @return
	 */
    public List<OrderRecommenderRankResp> listOrderRecommenderRank(String shopId) {
    	return orderRecommenderMapper.listOrderRecommenderRank(shopId);
    }
}
