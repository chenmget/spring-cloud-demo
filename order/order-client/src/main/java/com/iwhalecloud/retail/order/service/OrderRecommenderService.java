package com.iwhalecloud.retail.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderPageResp;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderRankResp;
import com.iwhalecloud.retail.order.dto.response.OrderRecommenderResp;
import com.iwhalecloud.retail.order.dto.resquest.AddOrderRecommenderReqDTO;
import com.iwhalecloud.retail.order.dto.resquest.ListOrderRecommenderReq;

import java.util.List;


public interface OrderRecommenderService{
	/**
	 * 推荐人推荐记录
	 * @param dto
	 * @return
	 */
	public Page<OrderRecommenderPageResp> listOrderRecommender(ListOrderRecommenderReq dto);
	
	/**
	 * 获取订单的推荐记录
	 * @param orderId
	 * @return
	 */
	public List<OrderRecommenderResp> getOrderRecommender(String orderId);

	/**
	 * 增加推荐记录
	 * @param t
	 * @return
	 */
	Integer addOrderRecommender(AddOrderRecommenderReqDTO t);
	/**
	 * 获取推荐人的最新推荐记录
	 * @param staffId
	 * @return
	 */
	public OrderRecommenderResp getNewestOrderRecommender(String staffId);
	/**
	 * 推荐排行榜
	 * @param shopId
	 * @return
	 */
	public List<OrderRecommenderRankResp> listOrderRecommenderRank(String shopId);
}