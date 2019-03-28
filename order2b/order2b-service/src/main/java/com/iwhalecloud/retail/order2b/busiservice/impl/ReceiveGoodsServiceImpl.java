package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.busiservice.ReceiveGoodsService;
import com.iwhalecloud.retail.order2b.busiservice.SelectOrderService;
import com.iwhalecloud.retail.order2b.busiservice.UpdateOrderFlowService;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.consts.order.OrderServiceType;
import com.iwhalecloud.retail.order2b.consts.order.TypeStatus;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.order.SendGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.resquest.order.ReceiveGoodsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.UpdateOrderStatusRequest;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderApply;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.entity.OrderItemDetail;
import com.iwhalecloud.retail.order2b.manager.AfterSaleManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.model.OrderInfoModel;
import com.iwhalecloud.retail.order2b.model.OrderItemDetailModel;
import com.iwhalecloud.retail.order2b.model.OrderItemInfoModel;
import com.iwhalecloud.retail.order2b.model.SelectOrderDetailModel;
import com.iwhalecloud.retail.order2b.reference.ResNbrManagerReference;
import com.iwhalecloud.retail.order2b.reference.TaskManagerReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReceiveGoodsServiceImpl implements ReceiveGoodsService {


    @Autowired
    private ResNbrManagerReference resNbrManagerReference;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private AfterSaleManager afterSaleManager;

    @Autowired
    private SelectOrderService selectOrderService;

    @Autowired
    private UpdateOrderFlowService updateOrderFlowService;

    @Autowired
    private TaskManagerReference taskManagerReference;

    @Override
    public CommonResultResp receiveGoodsCheck(ReceiveGoodsReq req) {
        CommonResultResp resp = new CommonResultResp();
        if (CollectionUtils.isEmpty(req.getList())) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("串码不能为空");
            return resp;
        }
        List<String> reqList = new ArrayList<>();
        for (SendGoodsItemDTO goodsItemDTO : req.getList()) {
            reqList.addAll(goodsItemDTO.getResNbrList());
        }

        /**
         * 查询未收货串码
         */
        OrderItem orderItem = new OrderItem();
        List<OrderItemInfoModel> resberList = new ArrayList<>();
        if (!StringUtils.isEmpty(req.getOrderId())) {
            orderItem.setOrderId(req.getOrderId());
            orderItem.setSourceFrom(req.getSourceFrom());
            resberList = orderManager.getOrderItemInfoListById(orderItem);
        } else {
            for (SendGoodsItemDTO dto : req.getList()) {
                orderItem.setItemId(dto.getItemId());
                orderItem.setSourceFrom(req.getSourceFrom());
                resberList.addAll(orderManager.getOrderItemInfoListById(orderItem));
            }

        }


        if (CollectionUtils.isEmpty(resberList)) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("未查询到可收货的串码");
            return resp;
        }
        List<String> dbLsit = new ArrayList<>();
        for (OrderItemInfoModel item : resberList) {
            for (OrderItemDetail cm : item.getDetailList()) {
                dbLsit.add(cm.getDetailId());
            }
        }

        //找出不能识别的串码
        reqList.removeAll(dbLsit);
        if (!CollectionUtils.isEmpty(reqList)) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("不能识别的串码：" + JSON.toJSONString(reqList));
            return resp;
        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp inResource(ReceiveGoodsReq request) {
        CommonResultResp resp = new CommonResultResp();
        List<SendGoodsItemDTO> list = new ArrayList<>();
        for (SendGoodsItemDTO goodsItemDTO : request.getList()) {
            SendGoodsItemDTO s = new SendGoodsItemDTO();
            BeanUtils.copyProperties(goodsItemDTO, s);
            if (CollectionUtils.isEmpty(goodsItemDTO.getResNbrList())) {
                resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resp.setResultMsg("串码id不能为空，itemId=" + goodsItemDTO.getItemId());
                return resp;
            }
            s.setResNbrList(orderManager.selectResNbrListByIds(goodsItemDTO.getResNbrList()));
            list.add(s);
        }

        //发货对象
        String handlerUserCode;
        //换货收货
        if(TypeStatus.TYPE_34.getCode().equals(request.getType())){
            OrderApply orderApply=new OrderApply();
            orderApply.setOrderApplyId(request.getOrderApplyId());
            orderApply.setServiceType(OrderServiceType.ORDER_SHIP_TYPE_3.getCode());
            orderApply.setOrderId(request.getOrderId());
            orderApply.setOrderItemId(list.get(0).getItemId());
           List<OrderApply> applyList= afterSaleManager.selectOrderApply(orderApply);
           if(CollectionUtils.isEmpty(applyList)){
               resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
               resp.setResultMsg("未找到换货单处理人");
               return resp;
           }
           handlerUserCode=applyList.get(0).getHandlerId();
         //正常收货
        }else{
          Order order= orderManager.getOrderById(request.getOrderId());
          if(order==null){
              resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
              resp.setResultMsg("未查询到订单");
              return resp;
          }
          handlerUserCode=order.getMerchantId();
        }

        //入库
        ResultVO<Boolean> resultVO = resNbrManagerReference.nbrInResource(handlerUserCode,request, list);
        if (resultVO.isSuccess() && resultVO.getResultData()) {
            resp.setResultCode(resultVO.getResultCode());
            resp.setResultMsg(resultVO.getResultMsg());
        } else {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("入库失败,"+resultVO.getResultMsg());
        }

        return resp;
    }

    @Override
    public CommonResultResp receiveGoods(ReceiveGoodsReq req) {

        CommonResultResp resp = new CommonResultResp();
        List<OrderItem> dbList = new ArrayList<>();
        List<String> reqList = new ArrayList<>();
        for (SendGoodsItemDTO goodsItemDTO : req.getList()) {
            OrderItem orderItem = new OrderItem();
            reqList.addAll(goodsItemDTO.getResNbrList());
            orderItem.setItemId(goodsItemDTO.getItemId());
            orderItem.setReceiveNum(goodsItemDTO.getResNbrList().size());
            dbList.add(orderItem);
        }
        OrderItemDetailModel model = new OrderItemDetailModel();
        model.setDetailList(reqList);
        model.setState(OrderAllStatus.ORDER_STATUS_6.getCode());
        orderManager.updateResNbr(model);

        List<OrderItem> list = orderManager.selectOrderItemsList(req.getOrderId());

        for (OrderItem item : dbList) {
            for (OrderItem i : list) {
                if (i.getItemId().equals(item.getItemId())) {
                    if (i.getReceiveNum() == null) {
                        i.setReceiveNum(0);
                    }
                    item.setReceiveNum(item.getReceiveNum() + i.getReceiveNum());
                    orderManager.updateOrderItemByItemId(item);
                    break;
                }
            }
        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp receiveGoodsFinish(ReceiveGoodsReq request) {
        CommonResultResp resp = new CommonResultResp();
        SelectOrderDetailModel selectOrderDetailModel = new SelectOrderDetailModel();
        selectOrderDetailModel.setOrderId(request.getOrderId());
        selectOrderDetailModel.setSourceFrom(request.getSourceFrom());
        IPage<OrderInfoModel> order = selectOrderService.selectOrderListByOrder(selectOrderDetailModel);

        OrderInfoModel orderInfoModel = order.getRecords().get(0);
        if (CollectionUtils.isEmpty(order.getRecords())) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("未查询到订单");
            return resp;
        }

        //全部入库
        if (orderInfoModel.getGoodsNum().equals(orderInfoModel.getReceiveNum())) {
            UpdateOrderStatusRequest updateReq = new UpdateOrderStatusRequest();
            updateReq.setFlowType(ActionFlowType.ORDER_HANDLER_SH.getCode());
            updateReq.setUserId(request.getUserId());
            updateReq.setOrderId(request.getOrderId());
            updateOrderFlowService.sureReciveGoods(updateReq);

            taskManagerReference.updateTask(request.getOrderId(), request.getUserId());
            taskManagerReference.addTaskByHandlerOne("待评价",request.getOrderId(), request.getUserId(), orderInfoModel.getCreateUserId());
        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }
}
