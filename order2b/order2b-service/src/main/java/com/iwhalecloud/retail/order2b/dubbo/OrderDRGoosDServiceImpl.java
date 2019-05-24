package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.authpay.PayAuthorizationService;
import com.iwhalecloud.retail.order2b.busiservice.DeliverGoodsService;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.order.TypeStatus;
import com.iwhalecloud.retail.order2b.dto.response.DeliveryGoodsResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.ReceiveGoodsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SendGoodsRequest;
import com.iwhalecloud.retail.order2b.service.OrderAfterSaleOpenService;
import com.iwhalecloud.retail.order2b.service.OrderDRGoodsOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class OrderDRGoosDServiceImpl implements OrderDRGoodsOpenService {


    @Autowired
    private OrderDRGoodsOpenService orderDRGoodsOpenService;

    @Autowired
    private PayAuthorizationService payAuthorizationService;

    @Reference
    private OrderAfterSaleOpenService orderAfterSaleOpenService;

    @Autowired
    private DeliverGoodsService deliverGoodsService;


    @Override
    public ResultVO deliverGoods(SendGoodsRequest request) {

        ResultVO valieNbrResultVO = deliverGoodsService.valieNbr(request);
        if (!valieNbrResultVO.isSuccess()) {
            return valieNbrResultVO;
        }
        List<DeliveryGoodsResp> list = (List<DeliveryGoodsResp>)valieNbrResultVO.getResultData();
        if (!CollectionUtils.isEmpty(list)) {
            return ResultVO.success(list);
        }

        //默认正常发货
        if (StringUtils.isEmpty(request.getType())) {
            return orderDRGoodsOpenService.deliverGoods(request);
        } else {
            TypeStatus typeStatus = TypeStatus.matchOpCode(request.getType(), TypeStatus.APPLY_HH);
            switch (typeStatus) {
                case TYPE_14:
                    //正常发货
                    return orderDRGoodsOpenService.deliverGoods(request);
                case TYPE_34:
                    //换货发货
                    return orderAfterSaleOpenService.sellerDeliverGoods(request);
                default:
                    ResultVO resultVO = new ResultVO();
                    resultVO.setResultMsg("type类型不匹配");
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    return resultVO;
            }
        }
    }

    @Override
    public ResultVO receiveGoods(ReceiveGoodsReq request) {
        //默认正常收货
        if (StringUtils.isEmpty(request.getType())) {
            ResultVO rvo = orderDRGoodsOpenService.receiveGoods(request);
            if("0".equals(rvo.getResultCode())){//调用翼支付确认
                payAuthorizationService.authorizationConfirmation(request.getOrderId());
            }
            return rvo;
        } else {
            TypeStatus typeStatus = TypeStatus.matchOpCode(request.getType(), TypeStatus.APPLY_HH);
            switch (typeStatus) {
                case TYPE_14:
                    //正常收货
                    ResultVO rvo = orderDRGoodsOpenService.receiveGoods(request);
                    if("0".equals(rvo.getResultCode())){//调用翼支付确认
                        payAuthorizationService.authorizationConfirmation(request.getOrderId());
                    }
                    return rvo;
                case TYPE_34:
                    //换货收货
                    return orderAfterSaleOpenService.userReceiveGoods(request);
                default:
                    ResultVO resultVO = new ResultVO();
                    resultVO.setResultMsg("type类型不匹配");
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    return resultVO;
            }
        }
    }
}
