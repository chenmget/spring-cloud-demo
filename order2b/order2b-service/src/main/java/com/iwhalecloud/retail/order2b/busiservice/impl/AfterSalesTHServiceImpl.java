package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.order2b.busiservice.AfterSalesTHService;
import com.iwhalecloud.retail.order2b.config.DBTableSequence;
import com.iwhalecloud.retail.order2b.config.WhaleCloudKeyGenerator;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.consts.order.OrderServiceType;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.order.DeliveryDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDetailDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderZFlowDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.SendGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleResNbrResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.OrderApplyReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectAfterSalesReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.THReceiveGoodsReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.THSendGoodsRequest;
import com.iwhalecloud.retail.order2b.entity.*;
import com.iwhalecloud.retail.order2b.manager.AfterSaleManager;
import com.iwhalecloud.retail.order2b.manager.DeliveryManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.manager.OrderZFlowManager;
import com.iwhalecloud.retail.order2b.model.AfterSalesModel;
import com.iwhalecloud.retail.order2b.model.OrderItemDetailModel;
import com.iwhalecloud.retail.order2b.model.SelectAfterModel;
import com.iwhalecloud.retail.order2b.reference.ResNbrManagerReference;
import com.iwhalecloud.retail.order2b.reference.TaskManagerReference;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class AfterSalesTHServiceImpl implements AfterSalesTHService {

    @Autowired
    private AfterSaleManager afterSaleManager;

    @Autowired
    private OrderZFlowManager orderZFlowManager;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private DeliveryManager deliveryManager;

    @Autowired
    private WhaleCloudKeyGenerator whaleCloudKeyGenerator;

    @Autowired
    private TaskManagerReference taskManagerReference;

    @Autowired
    private ResNbrManagerReference resNbrManagerReference;

    @Override
    public CommonResultResp check(OrderApplyReq req) {
        CommonResultResp resp = new CommonResultResp();
        if (StringUtils.isEmpty(req.getResNbrs())) {
            resp.setResultMsg("退货，换货串码不能为空");
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resp;
        }

        /**
         * 数量校验
         */
        OrderItem orderItem = orderManager.getOrderItemById(req.getOrderItemId());
        if (orderItem.getReceiveNum() == null) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("请先收货再换货");
            return resp;
        }
        if (req.getSubmitNum() > orderItem.getReceiveNum()) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("换货数量不能大于收货数量");
            return resp;
        }

        /**
         * 串码校验
         */
        req.setResNbrList(Arrays.asList(req.getResNbrs().split("\n")));

        if(req.getSubmitNum() !=req.getResNbrList().size()){
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("换货数量与申请的串码不一致");
            return resp;
        }


        OrderItemDetailModel itemDetailModel = new OrderItemDetailModel();
        itemDetailModel.setItemId(req.getOrderItemId());
        itemDetailModel.setResNbrList(req.getResNbrList());
        itemDetailModel.setState(OrderAllStatus.ORDER_STATUS_6.getCode());
        List<OrderItemDetail> orderItemDetailList = orderManager.selectOrderItemDetail(itemDetailModel);
        if (orderItemDetailList.size() != req.getSubmitNum()) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("串码校验不通过");
            return resp;
        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    @Transactional
    public CommonResultResp builderOrderApply(OrderApplyReq req, OrderApply orderApply) {

        OrderItem orderItem = orderManager.getOrderItemById(req.getOrderItemId());


        /**
         * 冻结
         */
        String nbrstatus=ResourceConst.STATUSCD.RESTORAGEING.getCode();
        if(OrderServiceType.ORDER_SHIP_TYPE_3.getCode().equals(orderApply.getServiceType())){
            nbrstatus=ResourceConst.STATUSCD.EXCHANGEING.getCode();
        }
        CommonResultResp commonResultResp = resNbrManagerReference.resNbrDJ(orderApply.getApplicantId(),
                nbrstatus, req.getResNbrList(),orderItem.getProductId());
        if (commonResultResp.isFailure()) {
            return commonResultResp;
        }

        /**
         * 插入申请单
         */
        afterSaleManager.insertInto(orderApply);
        /**
         * 流程
         */
        orderZFlowManager.insertAfterSaleFlowList(req, orderApply.getOrderApplyId());

        //串码信息组装
        List<OrderApplyDetail> itemDetailList = new ArrayList<>();
        for (String resNer : req.getResNbrList()) {
            OrderApplyDetail orderItemDetail = new OrderApplyDetail();
            orderItemDetail.setCreateUserId(req.getUserId());
            orderItemDetail.setGoodsId(orderItem.getGoodsId());
            orderItemDetail.setProductId(orderItem.getProductId());
            orderItemDetail.setState(OrderAllStatus.ORDER_STATUS_11.getCode());
            orderItemDetail.setItemId(orderItem.getItemId());
            orderItemDetail.setResNbr(resNer);
            orderItemDetail.setOrderApplyId(orderApply.getOrderApplyId());
            orderItemDetail.setOrderId(req.getOrderId());
            itemDetailList.add(orderItemDetail);
        }
        /**
         * 串码保存
         */
        afterSaleManager.insertIntoDetail(itemDetailList);
        commonResultResp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return commonResultResp;
    }

    @Override
    public CommonResultResp returnGoods(THSendGoodsRequest request) {
        CommonResultResp resp = new CommonResultResp();

        OrderApply orderApply = afterSaleManager.selectOrderApplyById(request.getOrderApplyId());
        /**
         * 查询物流信息
         * 组装物流数据
         */
        Delivery delivery = deliveryManager.selectLastByOrderApplyId(request.getOrderApplyId());
        if (delivery == null) {
            delivery = new Delivery();
            delivery.setBatchId(0);
            delivery.setShipNum(0);
            delivery.setOrderId(orderApply.getOrderId());
        }
        delivery.setSourceFrom(request.getSourceFrom());
        delivery.setUserid(request.getUserId());
        delivery.setDeliveryType("1");
        delivery.setCreateTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
        delivery.setBatchId(delivery.getBatchId() + 1);
        delivery.setShipNum(orderApply.getSubmitNum());
        delivery.setDeliveryId(whaleCloudKeyGenerator.tableKeySeq(DBTableSequence.ORD_DELIVERY.getCode()));
        delivery.setLogiId(request.getLogiId());
        delivery.setLogiName(request.getLogiName());
        delivery.setLogiNo(request.getLogiNo());
        delivery.setType("2"); //退货
        delivery.setOrderApplyId(request.getOrderApplyId());

        /**
         * 买家出库
         */
        OrderItemDetailModel detailModel = new OrderItemDetailModel();
        detailModel.setOrderApplyId(request.getOrderApplyId());
        detailModel.setItemId(orderApply.getOrderItemId());
        SendGoodsItemDTO itemDTOList = selectResnbrStock(detailModel);
        if (itemDTOList == null) {
            resp.setResultMsg("查询订单项为空");
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resp;
        }

        resp=resNbrManagerReference.returnGoodsOut(orderApply,itemDTOList);
        if (resp.isFailure()) {
            return resp;
        }

        deliveryManager.insertInto(delivery);

        //更新申请串码信息
        afterSaleManager.updateResNbrByApplyId(request.getOrderApplyId(), OrderAllStatus.ORDER_STATUS_5.getCode());

        /**
         * 更新工作流信息
         */
        OrderZFlowDTO orderZFlowDTO = new OrderZFlowDTO();
        orderZFlowDTO.setFlowType(request.getFlowType());
        orderZFlowDTO.setHandlerId(request.getUserId());
        orderZFlowDTO.setOrderId(request.getOrderApplyId());
        orderZFlowManager.updateFlowList(orderZFlowDTO);

        //更新申请单状态
        OrderApply req = new OrderApply();
        req.setApplyState(OrderAllStatus.ORDER_STATUS_23.getCode()); //待商家收货
        req.setOrderApplyId(orderApply.getOrderApplyId());
        afterSaleManager.updateApplyState(req);

        taskManagerReference.updateTask(orderApply.getOrderApplyId(), request.getUserId());
        taskManagerReference.addTaskByHandlerOne("卖家待收货",orderApply.getOrderApplyId(), request.getUserId(), orderApply.getHandlerId());


        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp receiveGoods(THReceiveGoodsReq request) {

        OrderApply orderApply = afterSaleManager.selectOrderApplyById(request.getOrderApplyId());
        CommonResultResp resp = new CommonResultResp();

        /**
         * 串码检验
         */
        OrderItemDetailModel orderItemDetailModel = new OrderItemDetailModel();
        orderItemDetailModel.setResNbrList(request.getResNbrList());
        orderItemDetailModel.setOrderApplyId(request.getOrderApplyId());
        List<OrderApplyDetail> list = afterSaleManager.selectOrderItem(orderItemDetailModel);
        if (list.size() != request.getResNbrList().size()) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("串码检验未通过");
            return resp;
        }

        /**
         * 卖家入库
         */
        OrderItemDetailModel detailModelreq = new OrderItemDetailModel();
        detailModelreq.setOrderApplyId(request.getOrderApplyId());
        detailModelreq.setItemId(orderApply.getOrderItemId());
        SendGoodsItemDTO itemDTOList = selectResnbrStock(detailModelreq);
        if (itemDTOList == null) {
            resp.setResultMsg("查询订单项为空");
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return resp;
        }
        resp = resNbrManagerReference.returnGoodsIn(orderApply, itemDTOList);
        if (resp.isFailure()) {
            return resp;
        }

        //更新申请串码信息
        afterSaleManager.updateResNbrByApplyId(request.getOrderApplyId(), OrderAllStatus.ORDER_STATUS_6.getCode());

        /**
         * 更新订单串码状态
         */
        OrderItemDetailModel detailModel = new OrderItemDetailModel();
        detailModel.setState(OrderAllStatus.ORDER_STATUS_1_.getCode());
        detailModel.setResNbrList(request.getResNbrList());
        detailModel.setItemId(orderApply.getOrderItemId());
        detailModel.setUOrderApply(request.getOrderApplyId());
        orderManager.updateResNbr(detailModel);

        /**
         * 更新工作流信息
         */
        OrderZFlowDTO orderZFlowDTO = new OrderZFlowDTO();
        orderZFlowDTO.setFlowType(request.getFlowType());
        orderZFlowDTO.setHandlerId(request.getUserId());
        orderZFlowDTO.setOrderId(request.getOrderApplyId());
        orderZFlowManager.updateFlowList(orderZFlowDTO);


        //更新申请单状态
        OrderApply req = new OrderApply();
        if (OrderServiceType.ORDER_SHIP_TYPE_3.getCode().equals(orderApply.getServiceType())) {
            req.setApplyState(OrderAllStatus.ORDER_STATUS_24.getCode()); //换货
            /**
             * 更新订单项
             */
            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(orderApply.getOrderItemId());
            orderItem.setReturnNum(request.getResNbrList().size());
            orderManager.updateOrderItemByItemId(orderItem);
        } else {
            req.setApplyState(OrderAllStatus.ORDER_STATUS_28.getCode()); //待商家退款
        }
        req.setOrderApplyId(request.getOrderApplyId());
        afterSaleManager.updateApplyState(req);

        taskManagerReference.updateTask(orderApply.getOrderApplyId(), request.getUserId());
        taskManagerReference.addTaskByHandlerOne("待退款",orderApply.getOrderApplyId(), request.getUserId(), orderApply.getHandlerId());

        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
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


        /**
         * 串码
         */
        OrderItemDetailModel applyDetail = new OrderItemDetailModel();
        applyDetail.setOrderApplyId(req.getOrderApplyId());
        applyDetail.setState(OrderAllStatus.ORDER_STATUS_5.getCode());
        List<OrderApplyDetail> resList = afterSaleManager.selectOrderItem(applyDetail);
        resNbrDto.setResNbrs(JSON.parseArray(JSON.toJSONString(resList), OrderItemDetailDTO.class));

        /**
         * 物流信息
         */
        Delivery delivery = new Delivery();
        delivery.setOrderApplyId(req.getOrderApplyId());
//        delivery.setSourceFrom(req.getSourceFrom());
        List<Delivery> deliveryList = deliveryManager.selectDeliveryListByOrderId(delivery);
        if (!CollectionUtils.isEmpty(deliveryList)) {
            DeliveryDTO deliveryDTO = new DeliveryDTO();
            BeanUtils.copyProperties(deliveryList.get(0), deliveryDTO);
            resNbrDto.setDeliveryInfo(deliveryDTO);
        }


        commonResultResp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        commonResultResp.setResultData(resNbrDto);
        return commonResultResp;
    }

    @Override
    public SendGoodsItemDTO selectResnbrStock(OrderItemDetailModel orderItemDetailModel) {
        orderItemDetailModel.setOrderApplyId(orderItemDetailModel.getOrderApplyId());
        OrderItem orderItem = orderManager.getOrderItemById(orderItemDetailModel.getItemId());
        List<OrderApplyDetail> list = afterSaleManager.selectOrderItem(orderItemDetailModel);

        if (orderItem == null || CollectionUtils.isEmpty(list)) {
            return null;
        }

        SendGoodsItemDTO goodsItemDTO = new SendGoodsItemDTO();
        goodsItemDTO.setItemId(orderItemDetailModel.getItemId());
        goodsItemDTO.setProductId(orderItem.getProductId());
        goodsItemDTO.setGoodsId(orderItem.getGoodsId());
        goodsItemDTO.setResNbrList(new ArrayList<>(list.size()));
        for (OrderApplyDetail detail : list) {
            goodsItemDTO.getResNbrList().add(detail.getResNbr());
        }
        return goodsItemDTO;
    }

}
