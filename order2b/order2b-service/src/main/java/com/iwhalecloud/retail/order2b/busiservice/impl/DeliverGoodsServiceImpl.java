package com.iwhalecloud.retail.order2b.busiservice.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.order2b.busiservice.DeliverGoodsService;
import com.iwhalecloud.retail.order2b.busiservice.SelectOrderService;
import com.iwhalecloud.retail.order2b.busiservice.UpdateOrderFlowService;
import com.iwhalecloud.retail.order2b.config.DBTableSequence;
import com.iwhalecloud.retail.order2b.config.WhaleCloudKeyGenerator;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.order.OrderAllStatus;
import com.iwhalecloud.retail.order2b.consts.order.OrderShipType;
import com.iwhalecloud.retail.order2b.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order2b.dto.model.order.SendGoodsItemDTO;
import com.iwhalecloud.retail.order2b.dto.response.DeliveryGoodsResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SendGoodsRequest;
import com.iwhalecloud.retail.order2b.entity.Delivery;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import com.iwhalecloud.retail.order2b.entity.OrderItemDetail;
import com.iwhalecloud.retail.order2b.manager.DeliveryManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.model.MemberAddrModel;
import com.iwhalecloud.retail.order2b.model.OrderInfoModel;
import com.iwhalecloud.retail.order2b.model.OrderUpdateAttrModel;
import com.iwhalecloud.retail.order2b.model.SelectOrderDetailModel;
import com.iwhalecloud.retail.order2b.reference.GoodsManagerReference;
import com.iwhalecloud.retail.order2b.reference.MemberInfoReference;
import com.iwhalecloud.retail.order2b.reference.ResNbrManagerReference;
import com.iwhalecloud.retail.order2b.reference.TaskManagerReference;
import com.iwhalecloud.retail.warehouse.dto.request.DeliveryValidResourceInstReq;
import com.iwhalecloud.retail.warehouse.dto.response.DeliveryValidResourceInstItemResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class DeliverGoodsServiceImpl implements DeliverGoodsService {

    @Autowired
    private ResNbrManagerReference resNbrManagerReference;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private DeliveryManager deliveryManager;

    @Autowired
    private WhaleCloudKeyGenerator whaleCloudKeyGenerator;

    @Autowired
    private MemberInfoReference memberInfoReference;

    @Autowired
    private SelectOrderService selectOrderService;

    @Autowired
    private UpdateOrderFlowService updateOrderFlowService;

    @Autowired
    private TaskManagerReference taskManagerReference;

    @Autowired
    private DeliverGoodsService deliverGoodsService;

    @Autowired
    private GoodsManagerReference goodsManagerReference;


    @Override
    public CommonResultResp nbrOutResource(SendGoodsRequest request) {
        CommonResultResp resp = new CommonResultResp();
        //出库
        Order order=orderManager.getOrderById(request.getOrderId());
        ResultVO<Boolean> resultVO = resNbrManagerReference.nbrOutResource(order,request.getUserCode(), request);
        if(resultVO.isSuccess() && resultVO.getResultData()){
            resp.setResultMsg(resultVO.getResultMsg());
            resp.setResultCode(resultVO.getResultCode());
        }else{
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("出库失败");
        }

        return resp;
    }

    @Override
    @Transactional
    public CommonResultResp SendGoodsRecord(SendGoodsRequest request, OrderInfoModel orderInfoModel) {
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
            delivery.setShipType(orderInfoModel.getShipType());
            MemberAddrModel model = memberInfoReference.selectReceiveAddrById(orderInfoModel.getReceiveAddrId());
            if (model != null) {
                BeanUtils.copyProperties(model, delivery);
                delivery.setShipAddr(model.getReceiveAddr());
                delivery.setShipEmail(model.getReceiveEmail());
                delivery.setShipName(model.getReceiveName());
                delivery.setShipMobile(model.getReceiveMobile());
            }
            delivery.setOrderId(request.getOrderId());
        }
        delivery.setUserid(request.getUserId());
        delivery.setSourceFrom(request.getSourceFrom());
        delivery.setDeliveryType("1");
        delivery.setCreateTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
        delivery.setBatchId(delivery.getBatchId() + 1);
        delivery.setShipNum(request.getShipNum());
        delivery.setDeliveryId(whaleCloudKeyGenerator.tableKeySeq(DBTableSequence.ORD_DELIVERY.getCode()));
        delivery.setLogiId(request.getLogiId());
        delivery.setLogiName(request.getLogiName());
        delivery.setLogiNo(request.getLogiNo());
        //发货
        delivery.setType("1");

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
                itemDetailList.add(orderItemDetail);
            }
        }

        deliveryManager.insertInto(delivery);
        orderManager.insertOrderItemDetailByList(itemDetailList);

        //更新发货数量
        for (OrderItem orderItem : orderInfoModel.getOrderItems()) {
            orderManager.updateOrderItemByItemId(orderItem);
        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public CommonResultResp sendGoodsFinish(SendGoodsRequest request) {

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

        //全部发货完成
        if (orderInfoModel.getGoodsNum().equals(orderInfoModel.getDeliveryNum())) {
            OrderUpdateAttrModel updateAttrModel = new OrderUpdateAttrModel();
            updateAttrModel.setOrderId(request.getOrderId());
            updateAttrModel.setShipUserId(request.getUserId());
            updateOrderFlowService.sendGoods(updateAttrModel);

            taskManagerReference.updateTask(request.getOrderId(), request.getUserId());
            taskManagerReference.addTaskByHandlerOne("待收货",request.getOrderId(), request.getUserId(), orderInfoModel.getCreateUserId());
        }else{
            //部分发货
            OrderUpdateAttrModel orderUpdateAttrModel=new OrderUpdateAttrModel();
            orderUpdateAttrModel.setOrderId(request.getOrderId());
            orderUpdateAttrModel.setStatus(OrderAllStatus.ORDER_STATUS_41.getCode());
            orderManager.updateOrderAttr(orderUpdateAttrModel);
        }
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    @Override
    public ResultVO resNbrValidity(DeliveryValidResourceInstReq req) {
        ResultVO nbrValidity = resNbrManagerReference.resNbrValidity(req);
        return nbrValidity;
    }


    @Override
    public ResultVO valieNbr(SendGoodsRequest request) {
        ResultVO resp = new ResultVO();
        Order order = orderManager.getOrderById(request.getOrderId());
        List<OrderItem> orderItemList = orderManager.selectOrderItemsList(request.getOrderId());
        if (order == null || CollectionUtils.isEmpty(orderItemList)) {
            resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resp.setResultMsg("未查询到订单");
            return resp;
        }
        //如果是配送方式是客户自提，需要录入手机串码
        OrderShipType shipType= OrderShipType.matchOpCode(order.getShipType());
        switch (shipType) {
            case ORDER_SHIP_TYPE_2:
                if(order.getGetGoodsCode()!=null){
                    if(!order.getGetGoodsCode().equals(request.getGetCode())){
                        resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                        resp.setResultMsg("自提码不匹配");
                        return resp;
                    }
                }
                break;
            default:
                if(StringUtils.isEmpty(request.getLogiNo())){
                    resp.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    resp.setResultMsg("物流单号不能为空");
                    return resp;
                }
                break;
        }
        //获取订单项全部不重复的productId
        List<String> productIdList = orderItemList.stream().map(OrderItem::getProductId).collect(Collectors.toList());
        productIdList = productIdList.stream().distinct().collect(Collectors.toList());

        DeliveryValidResourceInstReq validResourceInstReq = new DeliveryValidResourceInstReq();
        validResourceInstReq.setMerchantId(request.getMerchantId());
        validResourceInstReq.setMktResInstNbrList(request.getResNbrList());
        validResourceInstReq.setProductIdList(productIdList);
        ResultVO commonResultResp = deliverGoodsService.resNbrValidity(validResourceInstReq);
        if (!commonResultResp.isSuccess()) {
            return commonResultResp;
        }
        DeliveryValidResourceInstItemResp validResourceInstItemResp = (DeliveryValidResourceInstItemResp)commonResultResp.getResultData();
        Map<String, List<String>> productIdAndNbrList = validResourceInstItemResp.getProductIdAndNbrList();
        List<String> notExistsNbrList = validResourceInstItemResp.getNotExistsNbrList();
        List<DeliveryGoodsResp> list = new ArrayList<DeliveryGoodsResp>();
        if (!CollectionUtils.isEmpty(notExistsNbrList)) {
            list.addAll(deliveryRespData(null, null, notExistsNbrList, "1", "串码不存在"));
        }
        List<String> notMatchProductIdNbrList = validResourceInstItemResp.getNotMatchProductIdNbrList();
        if (!CollectionUtils.isEmpty(notMatchProductIdNbrList)) {
            list.addAll(deliveryRespData(null, null, notMatchProductIdNbrList, "1", "串码不属于该订单销售的商品范围"));
        }
        List<String> wrongStatusNbrList = validResourceInstItemResp.getWrongStatusNbrList();
        if (!CollectionUtils.isEmpty(wrongStatusNbrList)) {
            list.addAll(deliveryRespData(null, null, wrongStatusNbrList, "1", "串码状态不正确"));
        }

        List<SendGoodsItemDTO> goodsItemDTOList = new ArrayList<>();
        Integer shipNum = 0;
        for (OrderItem item : orderItemList) {
            String productId = item.getProductId();
            List<String> nbrList = productIdAndNbrList.get(productId);
            if (!CollectionUtils.isEmpty(nbrList)) {
                Integer delivery = item.getNum() - item.getDeliveryNum();
                if (nbrList.size() > delivery) {
                    List aboveNbrList = nbrList.subList(delivery, nbrList.size());
                    list.addAll(deliveryRespData(productId, null, aboveNbrList, "1", "串码发货数量超出购买数量"));
                }else {
                    SendGoodsItemDTO dto = new SendGoodsItemDTO();
                    dto.setGoodsId(item.getGoodsId());
                    dto.setItemId(item.getItemId());
                    dto.setProductId(productId);
                    dto.setResNbrList(nbrList);
                    dto.setDeliveryNum(item.getDeliveryNum() + nbrList.size());
                    goodsItemDTOList.add(dto);
                    shipNum += 1;
                }
            }
        }
        request.setShipNum(shipNum);
        request.setGoodsItemDTOList(goodsItemDTOList);
        resp.setResultData(list);
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resp;
    }

    private List<DeliveryGoodsResp> deliveryRespData(String productId, String goodName, List<String> nbrList, String result, String resultDesc) {
        String color = null;
        String memory = null;
        if (!StringUtils.isEmpty(productId)) {
            ProductResp resp = goodsManagerReference.getProduct(productId);
            if (null != resp) {
                color = resp.getAttrValue2();
                memory = resp.getAttrValue3();
            }
        }
        List<DeliveryGoodsResp> data = new ArrayList<DeliveryGoodsResp>(nbrList.size());
        for (String nbr : nbrList) {
            DeliveryGoodsResp deliveryGoodsResp = new DeliveryGoodsResp();
            deliveryGoodsResp.setNbr(nbr);
            deliveryGoodsResp.setColor(color);
            deliveryGoodsResp.setMemory(memory);
            deliveryGoodsResp.setGoodNanme(goodName);
            deliveryGoodsResp.setRusult(result);
            deliveryGoodsResp.setRusultDesc(resultDesc);
            data.add(deliveryGoodsResp);
        }
        return data;
    }

}
