package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.order2b.busiservice.AfterSalesHHService;
import com.iwhalecloud.retail.order2b.busiservice.DeliverGoodsService;
import com.iwhalecloud.retail.order2b.config.DBTableSequence;
import com.iwhalecloud.retail.order2b.config.WhaleCloudKeyGenerator;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDetailDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderZFlowDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.SendGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleResNbrResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.ReceiveGoodsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectAfterSalesReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SendGoodsRequest;
import com.iwhalecloud.retail.order2b.entity.*;
import com.iwhalecloud.retail.order2b.manager.AfterSaleManager;
import com.iwhalecloud.retail.order2b.manager.DeliveryManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.manager.OrderZFlowManager;
import com.iwhalecloud.retail.order2b.model.*;
import com.iwhalecloud.retail.order2b.reference.TaskManagerReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AfterSalesHHServiceImpl implements AfterSalesHHService {


    @Autowired
    private DeliverGoodsService deliverGoodsService;

    @Autowired
    private DeliveryManager deliveryManager;

    @Autowired
    private AfterSaleManager afterSaleManager;

    @Autowired
    private WhaleCloudKeyGenerator whaleCloudKeyGenerator;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private OrderZFlowManager orderZFlowManager;

    @Autowired
    private TaskManagerReference taskManagerReference;

    @Override
    public CommonResultResp sellerCheckDeliverGoods(SendGoodsRequest request) {
        CommonResultResp resp = new CommonResultResp();
        OrderApply orderApply = afterSaleManager.selectOrderApplyById(request.getOrderApplyId());

        if (!request.getShipNum().equals(orderApply.getSubmitNum())) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("换货数量与本次发货数量不匹配");
            return resp;
        }

        /**
         * 出库
         */
        resp = deliverGoodsService.nbrOutResource(request);
        if (resp.isFailure()) {
            return resp;
        }
        taskManagerReference.updateTask(orderApply.getOrderApplyId(), request.getUserId());
        taskManagerReference.addTaskByHandlerOne("待收货",orderApply.getOrderApplyId(), request.getUserId(), orderApply.getApplicantId());

        request.setOrderId(orderApply.getOrderId());
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp sellerDeliverGoods(SendGoodsRequest request) {

        CommonResultResp resp = new CommonResultResp();
        /**
         * 查询物流信息
         * 组装物流数据
         */
        Delivery delivery = deliveryManager.selectLastByOrderId(request.getOrderId());
        if (delivery == null) {
            delivery = new Delivery();
            delivery.setBatchId(0);
            delivery.setShipNum(0);
            delivery.setOrderId(request.getOrderId());
        }
        delivery.setSourceFrom(request.getSourceFrom());
        delivery.setUserid(request.getUserId());
        delivery.setDeliveryType("1");
        delivery.setCreateTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
        delivery.setBatchId(delivery.getBatchId() + 1);
        delivery.setShipNum(request.getShipNum());
        delivery.setDeliveryId(whaleCloudKeyGenerator.tableKeySeq(DBTableSequence.ORD_DELIVERY.getCode()));
        delivery.setLogiId(request.getLogiId());
        delivery.setLogiName(request.getLogiName());
        delivery.setLogiNo(request.getLogiNo());
        delivery.setType("3"); //换货
        delivery.setOrderApplyId(request.getOrderApplyId());

        //记录订单串码，状态为代收获
        //串码信息组装
        List<OrderItemDetail> itemDetailList = new ArrayList<>();
        for (SendGoodsItemDTO goodsItemDTO : request.getGoodsItemDTOList()) {
            for (String resNer : goodsItemDTO.getResNbrList()) {
                OrderItemDetail orderItemDetail = new OrderItemDetail();
                orderItemDetail.setBatchId(delivery.getBatchId());
                orderItemDetail.setCreateUserId(request.getUserId());
                orderItemDetail.setGoodsId(goodsItemDTO.getGoodsId());
                orderItemDetail.setProductId(goodsItemDTO.getProductId());
                orderItemDetail.setState(OrderAllStatus.ORDER_STATUS_5.getCode());
                orderItemDetail.setItemId(goodsItemDTO.getItemId());
                orderItemDetail.setResNbr(resNer);
                orderItemDetail.setOrderId(request.getOrderId());
                orderItemDetail.setOrderApplyId(request.getOrderApplyId());
                itemDetailList.add(orderItemDetail);
            }
        }

        deliveryManager.insertInto(delivery);
        orderManager.insertOrderItemDetailByList(itemDetailList);

        /**
         * 更新工作流信息
         */
        OrderZFlowDTO orderZFlowDTO = new OrderZFlowDTO();
        orderZFlowDTO.setFlowType(ActionFlowType.ORDER_HANDLER_MJFH.getCode());
        orderZFlowDTO.setHandlerId(request.getUserId());
        orderZFlowDTO.setOrderId(request.getOrderApplyId());
        orderZFlowManager.updateFlowList(orderZFlowDTO);

        //更新申请单状态
        OrderApply req = new OrderApply();
        req.setApplyState(OrderAllStatus.ORDER_STATUS_25.getCode()); //待买家收货
        req.setOrderApplyId(request.getOrderApplyId());
        afterSaleManager.updateApplyState(req);


        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    @Transactional
    public CommonResultResp userReceiveGoods(ReceiveGoodsReq request) {

        CommonResultResp resp = new CommonResultResp();

        //更新串码状态
        List<String> reqList = new ArrayList<>();
        for (SendGoodsItemDTO goodsItemDTO : request.getList()) {
            OrderItem orderItem = new OrderItem();
            reqList.addAll(goodsItemDTO.getResNbrList());
            orderItem.setItemId(goodsItemDTO.getItemId());
            orderItem.setReceiveNum(goodsItemDTO.getResNbrList().size());
        }
        OrderItemDetailModel model = new OrderItemDetailModel();
        model.setDetailList(reqList);
        model.setState(OrderAllStatus.ORDER_STATUS_6.getCode());
        orderManager.updateResNbr(model);

        resp=hhFinish(request);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp hhFinish(ReceiveGoodsReq request) {
        CommonResultResp resp = new CommonResultResp();


        //更新申请单状态
        OrderApply req = new OrderApply();
        req.setApplyState(OrderAllStatus.ORDER_STATUS_3_.getCode()); //换货完成
        req.setOrderApplyId(request.getOrderApplyId());
        afterSaleManager.updateApplyState(req);

        /**
         * 更新工作流信息
         */
        OrderZFlowDTO orderZFlowDTO = new OrderZFlowDTO();
        orderZFlowDTO.setFlowType(ActionFlowType.ORDER_HANDLER_HHWC.getCode());
        orderZFlowDTO.setHandlerId(request.getUserId());
        orderZFlowDTO.setOrderId(request.getOrderApplyId());
        orderZFlowManager.updateFlowList(orderZFlowDTO);

        //更新串码为已换货
        OrderItemDetailModel model = new OrderItemDetailModel();
        model.setOrderApplyId(request.getOrderApplyId());
        model.setState(OrderAllStatus.ORDER_STATUS_6.getCode());
        List<Integer> states=new ArrayList<>();
        states.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_5.getCode()));
        model.setStates(states);
        orderManager.updateResNbr(model);

        //更新订单项 数量
        for (SendGoodsItemDTO dto : request.getList()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(dto.getItemId());
            orderItem.setReplaceNum(dto.getResNbrList().size());
            orderManager.updateOrderItemByItemId(orderItem);
        }

        taskManagerReference.updateTask(request.getOrderApplyId(), request.getUserId());

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public HHReceiveGoodsModel hhResNbrNumList(ReceiveGoodsReq request) {
        HHReceiveGoodsModel hhReceiveGoodsModel = new HHReceiveGoodsModel();

        List<ReceiveGoodsReq> hhReceiveReq=new ArrayList<>();
        OrderItemDetailModel orderItemDetailModel = new OrderItemDetailModel();
        for (SendGoodsItemDTO dto : request.getList()) {
            orderItemDetailModel.setDetailList(new ArrayList<>());
            orderItemDetailModel.getDetailList().addAll(dto.getResNbrList());
            orderItemDetailModel.setItemId(dto.getItemId());
            List<OrderItemDetail> orderItemDetails = orderManager.selectOrderItemDetail(orderItemDetailModel);

            if (CollectionUtils.isEmpty(orderItemDetails)) {
                continue;
            }

            //找出换货单
            Map<String, List<String>> map = new HashMap<>();
            for (OrderItemDetail detial : orderItemDetails) {
                if (StringUtils.isEmpty(detial.getOrderApplyId())) {
                    continue;
                }
                //避免正常收货和换货收货重复
                dto.getResNbrList().remove(detial.getDetailId());

                List<String> itemDetails = map.get(detial.getOrderApplyId());
                if (CollectionUtils.isEmpty(itemDetails)) {
                    itemDetails = new ArrayList<>();
                    itemDetails.add(detial.getDetailId());
                    map.put(detial.getOrderApplyId(), itemDetails);
                } else {
                    itemDetails.add(detial.getDetailId());
                }
            }

            //更新换货单
            for (String orderApplyId : map.keySet()) {
                ReceiveGoodsReq req = new ReceiveGoodsReq();
                req.setOrderApplyId(orderApplyId);
                List<SendGoodsItemDTO> sendGoodsItemDTOS = new ArrayList<>();
                sendGoodsItemDTOS.add(dto);
                dto.setResNbrList(map.get(orderApplyId));
                req.setList(sendGoodsItemDTOS);
                hhReceiveReq.add(req);
            }
        }

        hhReceiveGoodsModel.setReceiveGoodsReq(request);
        hhReceiveGoodsModel.setHhReceiveReq(hhReceiveReq);
        return hhReceiveGoodsModel;
    }

    @Override
    public CommonResultResp<AfterSaleResNbrResp> selectReceiveResNbr(SelectAfterSalesReq req) {
        CommonResultResp<AfterSaleResNbrResp> commonResultResp = new CommonResultResp<>();
        SelectAfterModel model = new SelectAfterModel();
        model.setOrderApplyId(req.getOrderApplyId());
        model.setSourceFrom(req.getSourceFrom());
        IPage<AfterSalesModel> apply = afterSaleManager.selectAfterSales(model);
        if (CollectionUtils.isEmpty(apply.getRecords())) {
            commonResultResp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            commonResultResp.setResultMsg("未查询到申请单");
            return commonResultResp;
        }

        AfterSaleResNbrResp resNbrDto = JSON.parseObject(JSON.toJSONString(apply.getRecords().get(0)), AfterSaleResNbrResp.class);


        OrderItemDetailModel applyDetail=new OrderItemDetailModel();
        applyDetail.setOrderApplyId(req.getOrderApplyId());
        List<Integer> states=new ArrayList<>();
        states.add(Integer.parseInt(OrderAllStatus.ORDER_STATUS_5.getCode()));
        applyDetail.setStates(states);
        List<OrderItemDetail> resList=orderManager.selectOrderItemDetail(applyDetail);
        resNbrDto.setResNbrs(JSON.parseArray(JSON.toJSONString(resList), OrderItemDetailDTO.class));

        commonResultResp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        commonResultResp.setResultData(resNbrDto);
        return commonResultResp;
    }
}
