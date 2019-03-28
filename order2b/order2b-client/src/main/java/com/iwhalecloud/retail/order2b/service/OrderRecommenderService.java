package com.iwhalecloud.retail.order2b.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.response.OrderRecommenderPageResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderRecommenderRankResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderRecommenderResp;
import com.iwhalecloud.retail.order2b.dto.resquest.report.AddOrderRecommenderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.report.ListOrderRecommenderReq;


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
	Integer addOrderRecommender(AddOrderRecommenderReq t);
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