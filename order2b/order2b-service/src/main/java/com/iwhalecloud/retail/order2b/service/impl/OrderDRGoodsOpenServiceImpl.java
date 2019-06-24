package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.authpay.PayAuthorizationService;
import com.iwhalecloud.retail.order2b.busiservice.AfterSalesHHService;
import com.iwhalecloud.retail.order2b.busiservice.DeliverGoodsService;
import com.iwhalecloud.retail.order2b.busiservice.ReceiveGoodsService;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.order.TypeStatus;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.order.SendGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.ReceiveGoodsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SendGoodsRequest;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.mapper.OrderMapper;
import com.iwhalecloud.retail.order2b.model.HHReceiveGoodsModel;
import com.iwhalecloud.retail.order2b.model.OrderInfoModel;
import com.iwhalecloud.retail.order2b.service.OrderDRGoodsOpenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderDRGoodsOpenServiceImpl implements OrderDRGoodsOpenService {


    @Autowired
    private DeliverGoodsService deliverGoodsService;


    @Autowired
    private ReceiveGoodsService receiveGoodsService;

    @Autowired
    private AfterSalesHHService afterSalesHHService;

    @Autowired
    private OrderAfterSaleOpenServiceImpl orderAfterSaleOpenService;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private PayAuthorizationService payAuthorizationService;
    
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ResultVO deliverGoods(SendGoodsRequest request) {
        log.info("OrderDRGoodsOpenServiceImpl.deliverGoods req={}", JSON.toJSONString(request));
        ResultVO resultVO = new ResultVO();
        Order order = orderManager.getOrderById(request.getOrderId());
        OrderInfoModel orderInfoModel = new OrderInfoModel();
        List<OrderItem> orderItems = new ArrayList<>();
        BeanUtils.copyProperties(order, orderInfoModel);
        for (SendGoodsItemDTO dto :request.getGoodsItemDTOList()) {
            OrderItem orderItem = new OrderItem();
            BeanUtils.copyProperties(dto, orderItem);
            orderItems.add(orderItem);
        }
        orderInfoModel.setOrderItems(orderItems);
        CommonResultResp resp = deliverGoodsService.nbrOutResource(request);
        if (resp.isFailure()) {
            resultVO.setResultCode(resp.getResultCode());
            resultVO.setResultMsg(resp.getResultMsg());
            return resultVO;
        }

        deliverGoodsService.SendGoodsRecord(request, orderInfoModel);
        deliverGoodsService.sendGoodsFinish(request);

        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultVO;
    }

    //收货确认
    @Override
    public ResultVO receiveGoods(ReceiveGoodsReq request) {

        ResultVO resultVO = new ResultVO();
        if(request.getOrderId() == null) {
        	return ResultVO.error("订单号为空！");
        }
        
        /**
                              * 找出换货的串码
         */
        HHReceiveGoodsModel hhReceiveGoodsModel=afterSalesHHService.hhResNbrNumList(request);

        request=hhReceiveGoodsModel.getReceiveGoodsReq();
        //收货校验
        CommonResultResp commonResultResp = receiveGoodsService.receiveGoodsCheck(request);
        if (commonResultResp.isFailure()) {
            resultVO.setResultCode(commonResultResp.getResultCode());
            resultVO.setResultMsg(commonResultResp.getResultMsg());
            return resultVO;
        }
        commonResultResp = receiveGoodsService.inResource(request);//串码入库
        if (commonResultResp.isFailure()) {
            resultVO.setResultCode(commonResultResp.getResultCode());
            resultVO.setResultMsg(commonResultResp.getResultMsg());
            return resultVO;
        }

        commonResultResp = receiveGoodsService.receiveGoods(request);
        if (commonResultResp.isFailure()) {
            resultVO.setResultCode(commonResultResp.getResultCode());
            resultVO.setResultMsg(commonResultResp.getResultMsg());
            return resultVO;
        }
        commonResultResp = receiveGoodsService.receiveGoodsFinish(request);//订单完成

        if(commonResultResp.isFailure()){
            resultVO.setResultCode(commonResultResp.getResultCode());
            resultVO.setResultMsg(commonResultResp.getResultMsg());
            return resultVO;
        }

        //更新换货单
        if(!CollectionUtils.isEmpty(hhReceiveGoodsModel.getHhReceiveReq())){
            for (ReceiveGoodsReq req: hhReceiveGoodsModel.getHhReceiveReq()){
                req.setType(TypeStatus.TYPE_34.getCode());
                orderAfterSaleOpenService.userReceiveGoods(req);
            }
        }

        //TODO 2修改订单状态时调用翼支付确认支付接口
        Order order = new Order();
        order.setOrderId(request.getOrderId());
        Order resOrder = orderMapper.getOrderById(order);
        List<OrderItem> list = orderManager.selectOrderItemsList(order.getOrderId());
        int num = 0;
        int receiveNum = 0;
        for(int i=0;i<list.size();i++) {
        	OrderItem orderItem = list.get(i);
        	num += orderItem.getNum();
        	receiveNum += orderItem.getReceiveNum();
        }
        if("1".equals(resOrder.getPayType()) && num == receiveNum) {//判断是不是翼支付,判断是不是最后一笔收货订单
        	Boolean flag = payAuthorizationService.authorizationConfirmation(request.getOrderId());
        	if(!flag){
            	resultVO.setResultMsg("关闭订单，翼支付预授权确认失败。");
            	resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            	return resultVO;
            }
        }
        resultVO.setResultCode(commonResultResp.getResultCode());
        resultVO.setResultMsg(commonResultResp.getResultMsg());
        return resultVO;
    }

}
