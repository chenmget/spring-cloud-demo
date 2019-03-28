package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.model.order.UserMemberDTO;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleResNbrResp;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.*;
import com.iwhalecloud.retail.order2b.service.OrderAfterSaleOpenService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrderAfterSaleDServiceImpl implements OrderAfterSaleOpenService {

    @Autowired
    private OrderAfterSaleOpenService orderAfterSaleService;

    @Override
    public ResultVO<UserMemberDTO> getHHUserInfo(GetOrderUserInfoReq request) {
        return orderAfterSaleService.getHHUserInfo(request);
    }

    @Override
    public ResultVO<AfterSaleResp> createAfter(OrderApplyReq request) {
        return orderAfterSaleService.createAfter(request);
    }

    @Override
    public ResultVO handlerApplying(UpdateApplyStatusRequest request) {
        return orderAfterSaleService.handlerApplying(request);
    }

    @Override
    public ResultVO userReturnGoods(THSendGoodsRequest request) {
        return orderAfterSaleService.userReturnGoods(request);
    }

    @Override
    public ResultVO sellerReceiveGoods(THReceiveGoodsReq request) {
        return orderAfterSaleService.sellerReceiveGoods(request);
    }

    @Override
    public ResultVO sellerDeliverGoods(SendGoodsRequest request) {
        return orderAfterSaleService.sellerDeliverGoods(request);
    }

    @Override
    public ResultVO userReceiveGoods(ReceiveGoodsReq request) {
        return orderAfterSaleService.userReceiveGoods(request);
    }

    @Override
    public ResultVO returnAmount(PayOrderRequest payOrderRequest) {
        return orderAfterSaleService.returnAmount(payOrderRequest);
    }

    @Override
    public ResultVO<AfterSaleResNbrResp> selectTHReceiveResNbr(SelectAfterSalesReq req) {
        return orderAfterSaleService.selectTHReceiveResNbr(req);
    }

    @Override
    public ResultVO<AfterSaleResNbrResp> selectHHReceiveResNbr(SelectAfterSalesReq req) {
        return orderAfterSaleService.selectHHReceiveResNbr(req);
    }

    @Override
    public ResultVO cancelApply(UpdateApplyStatusRequest request) {
        return orderAfterSaleService.cancelApply(request);
    }
}
