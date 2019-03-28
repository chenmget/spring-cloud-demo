package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.model.order.UserMemberDTO;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleResNbrResp;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.*;

public interface OrderAfterSaleOpenService {

    /**
     * * 创建换货申请单时，查询换货用户的名称
     */
    ResultVO<UserMemberDTO> getHHUserInfo(GetOrderUserInfoReq request);

    /**
     *创建申请单
     */
    ResultVO<AfterSaleResp> createAfter(OrderApplyReq request);

    /**
     * 处理申请单
     * 退款：审核，商家退款，买家确认收款(完成)
     * 退货：审核，商家退款，买家确认收款(完成)
     * 换货：审核
     */
    ResultVO handlerApplying(UpdateApplyStatusRequest request);


    /**
     * 买家退货
     */
    ResultVO userReturnGoods(THSendGoodsRequest request);


    /**
     * 商家收货
     */
    ResultVO sellerReceiveGoods(THReceiveGoodsReq request);


    /**
     * 商家发货
     */
    ResultVO sellerDeliverGoods(SendGoodsRequest request);



    /**
     * 买家换货完成（确认收货）
     */
    ResultVO userReceiveGoods(ReceiveGoodsReq request);

    /**
     * 退款
     */
    ResultVO returnAmount(PayOrderRequest payOrderRequest);

    /**
     *  退货收获查询
     */
    ResultVO<AfterSaleResNbrResp> selectTHReceiveResNbr(SelectAfterSalesReq req);

    /**
     *  换货收获查询
     */
    ResultVO<AfterSaleResNbrResp> selectHHReceiveResNbr(SelectAfterSalesReq req);

    /**
     * 取消售后申请单
     */
    ResultVO cancelApply(UpdateApplyStatusRequest request);
}
