package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.busiservice.SelectOrderService;
import com.iwhalecloud.retail.order2b.config.Order2bContext;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import com.iwhalecloud.retail.order2b.dto.model.order.*;
import com.iwhalecloud.retail.order2b.dto.response.*;
import com.iwhalecloud.retail.order2b.dto.resquest.order.AdvanceOrderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.DeliveryReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectNbrReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectOrderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.pay.OrderPayInfoReq;
import com.iwhalecloud.retail.order2b.entity.*;
import com.iwhalecloud.retail.order2b.manager.*;
import com.iwhalecloud.retail.order2b.model.*;
import com.iwhalecloud.retail.order2b.reference.MemberInfoReference;
import com.iwhalecloud.retail.order2b.service.OrderSelectOpenService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.system.common.SystemConst;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderSelectOpenServiceImpl implements OrderSelectOpenService {

    @Autowired
    private SelectOrderService selectOrderService;

    @Autowired
    private OrderZFlowManager orderZFlowManager;

    @Autowired
    private DeliveryManager deliveryManager;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private MemberInfoReference memberInfoReference;

    @Autowired
    private AdvanceOrderManager advanceOrderManager;

    @Autowired
    private PayLogManager payLogManager;

    @Override
    public ResultVO<IPage<OrderSelectResp>> purchaseOrderList(SelectOrderReq reqest) {
        SelectOrderDetailModel req = new SelectOrderDetailModel();
        BeanUtils.copyProperties(reqest, req);
        ResultVO respResultVO = new ResultVO<>();
        //订单状态
        List<String> statusList = new ArrayList<>();
        if (StringUtils.isEmpty(req.getStatus())) {
            statusList.addAll(
                    selectOrderService.getOrderStatusByUser(OrderManagerConsts.USER_EXPORT_TYPE_1,""));
        } else {
            String[] statusArr = req.getStatus().split(",");
            statusList = Arrays.asList(statusArr);
        }
        req.setStatusAll(statusList);
        req.setCreateUserId(req.getUserId());
        if (!StringUtils.isEmpty(req.getSupplierName())) {
            req.setSupplierName("%" + req.getSupplierName() + "%");
//            List<String> supperiD=memberInfoReference.selectSuperBySupper(req.getSupplierName());
//            if(!CollectionUtils.isEmpty(supperiD)){
//                req.setSupperIds(supperiD);
//            }
        }
        req.setIsDelete("0");
        IPage list = selectOrderService.selectOrderListByOrder(req);
        respResultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        List<OrderSelectResp> orderSelectResp = JSON.parseArray(JSON.toJSONString(list.getRecords()), OrderSelectResp.class);
        list.setRecords(orderSelectResp);
        respResultVO.setResultData(list);
        return respResultVO;
    }

    @Override
    public ResultVO<IPage<OrderSelectResp>> managerOrderList(SelectOrderReq reqest) {
        SelectOrderDetailModel req = new SelectOrderDetailModel();
        BeanUtils.copyProperties(reqest, req);
        ResultVO respResultVO = new ResultVO<>();
        req.setUserId(null);
        req.setUserCode(null);

        //供应商名称查询
        if (!StringUtils.isEmpty(req.getSupplierName())) {
            req.setSupplierName("%" + req.getSupplierName() + "%");
//            List<String> supperiD=memberInfoReference.selectSuperBySupper(req.getSupplierName());
//            if(!CollectionUtils.isEmpty(supperiD)){
//                req.setSupperIds(supperiD);
//            }
        }

        //零售商查询
        if (!StringUtils.isEmpty(req.getUserName())) {
            req.setUserName("%" + req.getUserName() + "%");
//            List<String> userIds=memberInfoReference.selectSuperBySupper(req.getSupplierName());
//            if(!CollectionUtils.isEmpty(userIds)){
//                req.setUserIds(userIds);
//            }
        }

        //订单状态
        List<String> statusList = new ArrayList<>();
        if (StringUtils.isEmpty(req.getStatus())) {
            statusList.addAll(
                    selectOrderService.getOrderStatusByUser(OrderManagerConsts.USER_EXPORT_TYPE_3,""));
        } else {
            String[] statusArr = req.getStatus().split(",");
            statusList = Arrays.asList(statusArr);
//            statusList.add(req.getStatus());
        }
        req.setStatusAll(statusList);
        req.setIsDelete(null);
        IPage list = selectOrderService.selectOrderListByOrder(req);
        respResultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        List<OrderSelectResp> orderSelectResp = JSON.parseArray(JSON.toJSONString(list.getRecords()), OrderSelectResp.class);
        // 设置买家信息
        if (!CollectionUtils.isEmpty(orderSelectResp)) {
            for (OrderSelectResp resp : orderSelectResp) {
                String buyerCode = resp.getBuyerCode();
                if (!StringUtils.isEmpty(buyerCode)) {
                    MerchantDTO merchantDTO = memberInfoReference.getMerchantByCode(buyerCode);
                    resp.setBuyerName(null != merchantDTO ? merchantDTO.getMerchantName() : null);
                    resp.setBuyerType(null != merchantDTO ? merchantDTO.getMerchantType() : null);
                }
            }
        }
        list.setRecords(orderSelectResp);
        respResultVO.setResultData(list);
        return respResultVO;
    }


    @Override
    public ResultVO<IPage<OrderSelectResp>> salesOrderList(SelectOrderReq reqest) {
        SelectOrderDetailModel req = new SelectOrderDetailModel();
        BeanUtils.copyProperties(reqest, req);
        req.setMerchantId(reqest.getUserCode());
        req.setUserCode(null);

        ResultVO respResultVO = new ResultVO<>();
        //订单状态
        List<String> statusList = new ArrayList<>();
        if (StringUtils.isEmpty(req.getStatus())) {
            statusList.addAll(
                    selectOrderService.getOrderStatusByUser(OrderManagerConsts.USER_EXPORT_TYPE_2,""));
        } else {
            String[] statusArr = req.getStatus().split(",");
            statusList = Arrays.asList(statusArr);
        }
        req.setIsDelete("0");
        if (!StringUtils.isEmpty(req.getUserName())) {
            req.setUserName("%" + req.getUserName() + "%");
//            List<String> userIds=memberInfoReference.selectSuperBySupper(req.getSupplierName());
//            if(!CollectionUtils.isEmpty(userIds)){
//                req.setUserIds(userIds);
//            }
        }
        req.setStatusAll(statusList);

        /**
         * 多个lanId查询
         */
        OrderRequest bContext=Order2bContext.getDubboRequest();
        if(!StringUtils.isEmpty(bContext.getLanId()) && bContext.getLanId().contains(",")){
            req.setLanIdList(Arrays.asList(bContext.getLanId().split(",")));
            bContext.setLanId(null);
        }

        IPage list = selectOrderService.selectOrderListByOrder(req);
        respResultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        List<OrderSelectResp> orderSelectResp = JSON.parseArray(JSON.toJSONString(list.getRecords()), OrderSelectResp.class);
        // 设置买家信息
        if (!CollectionUtils.isEmpty(orderSelectResp)) {
            for (OrderSelectResp resp : orderSelectResp) {
                String buyerCode = resp.getBuyerCode();
                if (!StringUtils.isEmpty(buyerCode)) {
                    MerchantDTO merchantDTO = memberInfoReference.getMerchantByCode(buyerCode);
                    resp.setBuyerName(null != merchantDTO ? merchantDTO.getMerchantName() : null);
                    resp.setBuyerType(null != merchantDTO ? merchantDTO.getMerchantType() : null);
                }
            }
        }
        list.setRecords(orderSelectResp);
        respResultVO.setResultData(list);
        return respResultVO;
    }


    @Override
    public ResultVO<OrderListExportResp> orderExport(AdvanceOrderReq req) {
        ResultVO<OrderListExportResp> result = new ResultVO<>();
        if (StringUtils.isEmpty(req.getUserExportType())) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultMsg("userExportType 类型不匹配");
            return result;
        }

        OrderListExportResp resp = new OrderListExportResp();
        List<Integer> states = new ArrayList<>();
        ResultVO resultVO=null;
        switch (req.getUserExportType()) {
            case OrderManagerConsts.USER_EXPORT_TYPE_1: //采购
                if(OrderManagerConsts.ORDER_CAT_1.equals(req.getOrderCat())){
                    req.setUserId(req.getUserCode());
                    resultVO=queryAdvanceOrderList(req);
                }else{
                    resultVO = purchaseOrderList(req);
                }

                states.add(6); //已确认
                break;
            case OrderManagerConsts.USER_EXPORT_TYPE_2: //销售

                if(OrderManagerConsts.ORDER_CAT_1.equals(req.getOrderCat())){
                    AdvanceOrderReq selectReq=new AdvanceOrderReq();
                    BeanUtils.copyProperties(req,selectReq);
                    selectReq.setMerchantId(req.getUserCode());
                    selectReq.setUserId(null);
                    selectReq.setUserCode(null);
                    resultVO=queryAdvanceOrderList(selectReq);
                }else{
                    resultVO = salesOrderList(req);
                }
                states.add(5); //已发货，待确认
                states.add(6); //已确认
                break;
            case OrderManagerConsts.USER_EXPORT_TYPE_3: //管理
                if(OrderManagerConsts.ORDER_CAT_1.equals(req.getOrderCat())){
                    resultVO=queryAdvanceOrderList(req);
                }else{
                    resultVO = managerOrderList(req);
                }

                states.add(5); //已发货，待确认
                states.add(6); //已确认
                break;
            default:
                result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                result.setResultMsg("userExportType 类型不匹配");
                return result;
        }
        ResultVO<IPage<AdvanceOrderResp>> pageResultVO=new ResultVO<>();
        BeanUtils.copyProperties(resultVO,pageResultVO);

        if (pageResultVO.getResultData() == null || CollectionUtils.isEmpty(pageResultVO.getResultData().getRecords())) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultMsg("导出失败，未查询到订单数据");
            return result;
        }


        List<OrderDTO> orderList = new ArrayList<>(); //订单
        List<OrderItemDTO> orderItemList = new ArrayList<>(); //订单项
        List<OrderItemDetailDTO> orderItemDetailList = new ArrayList<>(); //订单项详情（串码）
        List<AdvanceDTO> advanceList = new ArrayList<>();
        List<AdvanceOrderResp> advanceOrderResps=JSON.parseArray(JSON.toJSONString
                (pageResultVO.getResultData().getRecords()),AdvanceOrderResp.class);
        for (AdvanceOrderResp orderSelectResp : advanceOrderResps) {
            /**
             *  订单类型
             */
            if (OrderManagerConsts.ORDER_CAT_1.equals(req.getOrderCat())) {
                AdvanceDTO advanceDTO = new AdvanceDTO();
                AdvanceOrderDTO advanceOrder = orderSelectResp.getAdvanceOrder();
                if(advanceOrder==null){
                    continue;
                }
                BeanUtils.copyProperties(advanceOrder, advanceDTO);
                BeanUtils.copyProperties(orderSelectResp, advanceDTO);
                advanceList.add(advanceDTO);
            } else {
                OrderDTO orderDTO = new OrderDTO();
                BeanUtils.copyProperties(orderSelectResp, orderDTO);
                orderList.add(orderDTO);
            }
            orderItemList.addAll(orderSelectResp.getOrderItems());

            //查询串码
            List<String> list = new ArrayList<>();
            for (OrderItemDTO itemDTO : orderSelectResp.getOrderItems()) {
                list.add(itemDTO.getItemId());
            }
            OrderItemDetailModel reqModle = new OrderItemDetailModel();
            reqModle.setItemIds(list);
            reqModle.setStates(states);
            List<OrderItemDetail> itemDetails = orderManager.selectOrderItemDetail(reqModle);

            if (CollectionUtils.isEmpty(itemDetails)) {
                continue;
            }
            //遍历，加入商品名称和产品名称
            List<OrderItemDetailDTO> itemDetailDTOS = new ArrayList<>();
            for (OrderItemDTO itemDTO : orderSelectResp.getOrderItems()) {
                for (OrderItemDetail detail : itemDetails) {
                    if (itemDTO.getItemId().equals(detail.getItemId())) {
                        OrderItemDetailDTO detailDTO = new OrderItemDetailDTO();
                        BeanUtils.copyProperties(detail, detailDTO);
                        detailDTO.setProductName(itemDTO.getProductName());
                        detailDTO.setGoodsName(itemDTO.getGoodsName());
                        itemDetailDTOS.add(detailDTO);
                        continue;
                    }
                }
            }
            orderItemDetailList.addAll(itemDetailDTOS);

        }
        resp.setAdvanceList(advanceList);
        resp.setOrderList(orderList);
        resp.setOrderItemList(orderItemList);
        resp.setOrderItemDetailList(orderItemDetailList);

        result.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        result.setResultData(resp);
        return result;
    }

    @Override
    public ResultVO<CommonListResp<OrderItemDetailDTO>> orderItemDetailExport(SelectNbrReq req) {
        ResultVO resultVO = new ResultVO<>();
        CommonListResp<OrderItemDetailDTO> commonListResp = new CommonListResp<>();
        List<Integer> states = new ArrayList<>();
        switch (req.getUserExportType()) {
            case OrderManagerConsts.USER_EXPORT_TYPE_1: //采购
                states.add(6); //已确认
                break;
            case OrderManagerConsts.USER_EXPORT_TYPE_2: //销售
                states.add(5); //已发货，待确认
                states.add(6); //已确认
                break;
            case OrderManagerConsts.USER_EXPORT_TYPE_3: //管理
                states.add(5); //已发货，待确认
                states.add(6); //已确认
                break;
            default:
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resultVO.setResultMsg("userExportType 类型不匹配");
                return resultVO;
        }
        List<String> itemList = new ArrayList<>();
        itemList.add(req.getItemId());
        OrderItem itemDTO = orderManager.getOrderItemById(req.getItemId());
        OrderItemDetailModel reqModel = new OrderItemDetailModel();
        reqModel.setStates(states);
        reqModel.setItemIds(itemList);
        List<OrderItemDetail> itemDetails = orderManager.selectOrderItemDetail(reqModel);

        if (CollectionUtils.isEmpty(itemDetails) || itemDTO == null) {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("没有可导出的串码");
            return resultVO;
        }

        //遍历，加入商品名称和产品名称
        List<OrderItemDetailDTO> itemDetailDTOS = new ArrayList<>();
        for (OrderItemDetail detail : itemDetails) {
            OrderItemDetailDTO detailDTO = new OrderItemDetailDTO();
            BeanUtils.copyProperties(detail, detailDTO);
            detailDTO.setProductName(itemDTO.getProductName());
            detailDTO.setGoodsName(itemDTO.getGoodsName());
            itemDetailDTOS.add(detailDTO);
        }
        commonListResp.setList(itemDetailDTOS);
        resultVO.setResultData(commonListResp);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return resultVO;
    }

    @Override
    public ResultVO<OrderDTO> selectOrderById(String orderId) {
        ResultVO<OrderDTO> resultVO = new ResultVO<>();
        if (StringUtils.isEmpty(orderId)) {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("订单号不能为空");
            return resultVO;
        }
        Order order = orderManager.getOrderById(orderId);
        OrderDTO orderDTO = null;
        if (order != null) {
            orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);
        }

        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(orderDTO);
        return resultVO;
    }

    @Override
    public ResultVO<IPage<AdvanceOrderResp>> queryAdvanceOrderList(AdvanceOrderReq request) {
        ResultVO respResultVO = new ResultVO<>();
        SelectOrderDetailModel req = new SelectOrderDetailModel();
        BeanUtils.copyProperties(request, req);
        // 买家查询
        if (!StringUtils.isEmpty(req.getUserName())) {
            req.setUserName("%" + req.getUserName() + "%");
        }
        // 供应商查询
        if (!StringUtils.isEmpty(req.getSupplierName())) {
            req.setSupplierName("%" + req.getSupplierName() + "%");
        }
        //订单状态
        List<String> statusList = new ArrayList<>();
        if (StringUtils.isEmpty(req.getStatus())) {
            statusList.addAll(
                    selectOrderService.getOrderStatusByUser("",OrderManagerConsts.ORDER_CAT_1));
        } else {
            String[] statusArr = req.getStatus().split(",");
            statusList = Arrays.asList(statusArr);
        }
        req.setStatusAll(statusList);
        // 订单类型为预售
        req.setOrderCat(OrderManagerConsts.ORDER_CAT_1);

        /**
         * 多个lanId查询
         */
        OrderRequest bContext=Order2bContext.getDubboRequest();
        if(!StringUtils.isEmpty(bContext.getLanId()) && bContext.getLanId().contains(",")){
            req.setLanIdList(Arrays.asList(bContext.getLanId().split(",")));
            bContext.setLanId(null);
        }

        IPage list = selectOrderService.queryadvanceOrderList(req);
        respResultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        List<AdvanceOrderResp> advanceOrderResp = JSON.parseArray(JSON.toJSONString(list.getRecords()), AdvanceOrderResp.class);
        list.setRecords(advanceOrderResp);
        respResultVO.setResultData(list);
        return respResultVO;

    }

    @Override
    public ResultVO<OrderSelectDetailResp> orderDetail(SelectOrderReq reqest) {
        SelectOrderDetailModel req = new SelectOrderDetailModel();
        BeanUtils.copyProperties(reqest, req);
        req.setUserCode(null);
        req.setUserId(null);
        IPage<OrderInfoModel> list = selectOrderService.selectOrderListByOrder(req);
        ResultVO respResultVO = new ResultVO<>();
        if (CollectionUtils.isEmpty(list.getRecords())) {
            respResultVO.setResultMsg("未查询到订单");
            respResultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return respResultVO;
        }

        UserInfoModel userInfoModel = memberInfoReference.selectUserInfo(reqest.getUserId());
        if (userInfoModel == null) {
            respResultVO.setResultMsg("不能识别的身份");
            respResultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return respResultVO;
        }

        OrderInfoModel orderInfoModel = list.getRecords().get(0);
        OrderSelectDetailResp orderSelectDetailResp = JSON.parseObject(JSON.toJSONString(orderInfoModel), OrderSelectDetailResp.class);
        //身份校验
        if (SystemConst.USER_FOUNDER_1 != userInfoModel.getUserFounder()) { //超级管理员
            if (reqest.getUserCode() != null) {
                if (orderSelectDetailResp.getUserId().equals(reqest.getUserCode())  //买家
                        || orderSelectDetailResp.getMerchantId().equals(reqest.getUserCode()) //商家
                        ) {
                } else {
                    respResultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    respResultVO.setResultMsg("无权限查看该订单");
                    return respResultVO;
                }
            }
        }

        // 设置买家信息
        if (!StringUtils.isEmpty(orderInfoModel.getBuyerCode())) {
            MerchantDTO merchantDTO = memberInfoReference.getMerchantByCode(orderInfoModel.getBuyerCode());
            if (null != merchantDTO) {
                orderSelectDetailResp.setBuyerName(merchantDTO.getMerchantName());
                orderSelectDetailResp.setBuyerType(merchantDTO.getMerchantType());
            }
        }

        //当前要处理的过程
        orderSelectDetailResp.setCurrentFlowType(orderZFlowManager.selectCurrentFlowType(req.getOrderId()));

        //查询会员信息
        UserInfoModel resp = memberInfoReference.selectUserInfo(orderSelectDetailResp.getCreateUserId());
        UserMemberDTO userMemberModel = new UserMemberDTO();
        BeanUtils.copyProperties(resp, userMemberModel);
        orderSelectDetailResp.setMemberInfo(userMemberModel);

        //工作流信息
        ZFlow orderZFlowDTO = new ZFlow();
        orderZFlowDTO.setOrderId(req.getOrderId());
        orderZFlowDTO.setSourceFrom(req.getSourceFrom());
        orderSelectDetailResp.setZFlowList(orderZFlowManager.selectFlowList(orderZFlowDTO));

        //订单类型：
        if (orderSelectDetailResp.getUserId().equals(reqest.getUserCode())) {
            orderSelectDetailResp.setUserOrderType(OrderManagerConsts.USER_EXPORT_TYPE_1); //采购订单
        } else if(orderSelectDetailResp.getMerchantId().equals(reqest.getUserCode())) {
            orderSelectDetailResp.setUserOrderType(OrderManagerConsts.USER_EXPORT_TYPE_2); //销售定单
        }

        //物流信息
        Delivery delivery = new Delivery();
        delivery.setOrderId(req.getOrderId());
        delivery.setSourceFrom(req.getSourceFrom());
        List<Delivery> deliveryList = deliveryManager.selectDeliveryListByOrderId(delivery);
        if (!CollectionUtils.isEmpty(deliveryList)) {
            orderSelectDetailResp.setDeliveryModels(JSON.parseArray(JSON.toJSONString(deliveryList), DeliveryDTO.class));
        }

        //如果是预售单
        if (OrderManagerConsts.ORDER_CAT_1.equals(orderSelectDetailResp.getOrderCat())) {
            AdvanceOrder advanceOrder = advanceOrderManager.getAdvanceOrderByOrderId(orderSelectDetailResp.getOrderId());
            if (advanceOrder != null) {
                AdvanceOrderDTO advanceOrderDTO = new AdvanceOrderDTO();
                BeanUtils.copyProperties(advanceOrder, advanceOrderDTO);
                orderSelectDetailResp.setAdvanceOrder(advanceOrderDTO);
            }
        }

        //支付信息
        OrderPayInfoReq payReq = new OrderPayInfoReq();
        payReq.setOrderId(req.getOrderId());
        List<OrderPayInfoResp> payLogList = payLogManager.qryOrderPayInfoList(payReq);
        orderSelectDetailResp.setPayLogList(payLogList);

        respResultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        respResultVO.setResultData(orderSelectDetailResp);
        return respResultVO;
    }

    @Override
    public ResultVO<CommonListResp<OrderItemDetailListDTO>> selectOrderItemDetail(SelectOrderReq req) {
        ResultVO resultVO = new ResultVO();

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(req.getOrderId());
        orderItem.setSourceFrom(req.getSourceFrom());
        List<OrderItemInfoModel> resList = selectOrderService.selectOrderItemInfoListById(orderItem);
        List<OrderItemDetailListDTO> list = JSON.parseArray(JSON.toJSONString(resList), OrderItemDetailListDTO.class);
        CommonListResp<OrderItemDetailListDTO> data = new CommonListResp<>();
        data.setList(list);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(data);
        return resultVO;
    }

    /**
     * 根据串码获取订单项详情
     * @param resNbr
     * @return
     */
    @Override
    public ResultVO<OrderItemDetailDTO> selectOrderItemDetailBySn(String resNbr){
        OrderItemDetailModel detailReq = new OrderItemDetailModel();
        List resNbrList = new ArrayList();
        resNbrList.add(resNbr);
        detailReq.setResNbrList(resNbrList);
        List<OrderItemDetail> orderItemDetails = orderManager.selectOrderItemDetail(detailReq);
        OrderItemDetailDTO orderItemDetailDTO = new OrderItemDetailDTO();
        if(!CollectionUtils.isEmpty(orderItemDetails)){
            BeanUtils.copyProperties(orderItemDetails.get(0),orderItemDetailDTO);
            return ResultVO.success(orderItemDetailDTO);
        }

        return ResultVO.error("未找到串码对应的订单项明细数据");

    }

    /**
     * 根据订单项Id查询订单项信息
     * @param itemId
     * @return
     */
    @Override
    public OrderItemDTO getOrderItemById(String itemId) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        OrderItem orderItem = orderManager.getOrderItemById(itemId);
        if(!ObjectUtils.isEmpty(orderItem)) {
            BeanUtils.copyProperties(orderItem,orderItemDTO);
            return orderItemDTO;
        }

        return null;

    }

    @Override
    public DeliveryDTO selectDeliveryListByOrderIdAndBatchId(String orderId, String batchId) {
        DeliveryDTO  deliveryDTO = new DeliveryDTO();
        DeliveryReq deliveryReq = new DeliveryReq();
        deliveryReq.setOrderId(orderId);
        deliveryReq.setBatchId(batchId);
        List<Delivery> deliveryList = deliveryManager.selectDeliveryListByOrderIdAndBatchId(deliveryReq);
        if(!CollectionUtils.isEmpty(deliveryList)) {
            BeanUtils.copyProperties(deliveryList.get(0),deliveryDTO);
            return  deliveryDTO;
        }

        return null;
    }

}
