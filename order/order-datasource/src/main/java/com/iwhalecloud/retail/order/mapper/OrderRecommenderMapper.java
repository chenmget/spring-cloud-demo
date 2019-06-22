package com.iwhalecloud.retail.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderPageResp;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderRankResp;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderResp;
import com.iwhalecloud.retail.order.dto.resquest.ListOrderRecommenderReq;
import com.iwhalecloud.retail.order.entity.OrderRecommender;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: OrderRecommenderMapper
 * @author he.sw
 */
@Mapper
public interface OrderRecommenderMapper extends BaseMapper<OrderRecommender>{

	/**
	 * 推荐人推荐记录
	 * @param dto
	 * @return
	 */ 
	public Page<OrderRecommenderPageResp> listOrderRecommender(Page<ListOrderRecommenderReq> page, @Param("req") ListOrderRecommenderReq dto);
	/**
	 * 订单的推荐记录
	 * @param orderId
	 * @return
	 */
	public List<OrderRecommenderResp> getOrderRecommender(String orderId);
	/**
	 * 获取推荐人的最新订单的推荐记录
	 * @param staffId
	 * @return
	 */
	public OrderRecommenderResp getNewestOrderRecommender(@Param(value="staffId") String staffId);
	/**
	 * 提成排行榜
	 * @param shopId
	 * @return
	 */
	public List<OrderRecommenderRankResp> listOrderRecommenderRank(@Param(value="shopId") String shopId);
}