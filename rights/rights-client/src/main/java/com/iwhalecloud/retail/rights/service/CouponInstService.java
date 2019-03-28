package com.iwhalecloud.retail.rights.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.dto.request.*;
import com.iwhalecloud.retail.rights.dto.response.OrderCouponListResp;
import com.iwhalecloud.retail.rights.dto.response.QueryCouponInstRespDTO;

import java.text.ParseException;

/**
 * 权益实例管理
 */
public interface CouponInstService {

    
    /**
     * 权益实例入库
     * @param listRightsRequestDTO
     * @return
     * @throws ParseException 
     */
    public ResultVO inputRights(InputRightsRequestDTO listRightsRequestDTO) throws ParseException ;

    /**
     * 权益领取
     * @param saveRightsRequestDTO
     * @return
     */
    public ResultVO saveRights(SaveRightsRequestDTO saveRightsRequestDTO);

    /**
     *  权益核销校验
     * @param checkRightsRequestDTO
     * @return
     */
    public ResultVO checkRights(CheckRightsRequestDTO checkRightsRequestDTO);

    /**
     * 权益核销
     * @param userightsRequestDTO
     * @return
     */
    public ResultVO userights(UserightsRequestDTO userightsRequestDTO);

    /**
     * 权益实例查询
     * @param dto
     * @return
     */
    public Page<QueryCouponInstRespDTO> queryCouponinst(QueryCouponInstPageReq dto);

    /**
     * 订单使用优惠券
     */
    ResultVO<OrderUseCouponResp> OrderUseCoupon(OrderUseCouponReq req);

    /**
     * 订单优惠券列表查询
     */
    ResultVO<OrderCouponListResp> orderCouponList(OrderCouponListReq req);

    /**
     * 主动推送优惠券到商家
     * @param autoPushCouponReq
     * @return
     */
    ResultVO autoPushCoupon(AutoPushCouponReq autoPushCouponReq);

    /**
     * 权益领取（new）
     * @param saveRightsRequestDTO
     * @return
     */
    ResultVO receiveRights(SaveRightsRequestDTO saveRightsRequestDTO);
}
