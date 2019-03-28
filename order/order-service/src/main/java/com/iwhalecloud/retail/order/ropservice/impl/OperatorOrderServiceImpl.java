package com.iwhalecloud.retail.order.ropservice.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.member.dto.response.MemberResp;
import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.model.OrderModel;
import com.iwhalecloud.retail.order.dto.model.UserMemberModel;
import com.iwhalecloud.retail.order.dto.response.OrderInfoDetailRespDTO;
import com.iwhalecloud.retail.order.dto.response.OrderQueryListDTO;
import com.iwhalecloud.retail.order.dto.model.ContractPInfoModel;
import com.iwhalecloud.retail.order.dto.model.OrderZFlowModel;
import com.iwhalecloud.retail.order.dto.resquest.SelectOrderRequest;
import com.iwhalecloud.retail.order.manager.*;
import com.iwhalecloud.retail.order.reference.MemberInfoReference;
import com.iwhalecloud.retail.order.ropservice.OperatorOrderService;
import com.iwhalecloud.retail.order.util.SequenceTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OperatorOrderServiceImpl implements OperatorOrderService {

    @Autowired
    private ContractOrderManager contractOrderManager;

    @Autowired
    private OrderZFlowManager orderZFlowManager;

    @Autowired
    private OrderManager orderManager;
    @Autowired
    private DeliveryManager deliveryManager;

    @Autowired
    private MemberInfoReference memberInfoReference;

    @Override
    public CommonResultResp selectOrder(SelectOrderRequest request) {

        IPage<OrderModel> orderList= orderManager.selectManagerOrderList(request);

        CommonResultResp<IPage> resp=new CommonResultResp();
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resp.setResultData(orderList);

        List<OrderQueryListDTO> list=new ArrayList<>();
        for (OrderModel order: orderList.getRecords()) {
            OrderQueryListDTO query=new OrderQueryListDTO();
            query.setOrder(order);
            query.setOrderItems(orderManager.selectOrderItemsList(order.getOrderId()));
            list.add(query);
        }
        resp.getResultData().setRecords(list);
        return resp;
    }

    @Override
    public CommonResultResp selectOrderDetail(SelectOrderRequest request) {

        CommonResultResp result = new CommonResultResp();
        if (StringUtils.isEmpty(request.getOrderId())) {
            result.setResultMsg("订单id不能为空");
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return result;
        }
        OrderInfoDetailRespDTO data = new OrderInfoDetailRespDTO();
        data.setOrderId(request.getOrderId());
        //订单基本信息
        OrderModel order = orderManager.selectOrder(request.getOrderId());

        if(order==null){
            result.setResultMsg("未查询到订单");
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            return result;
        }
        order.setGetGoodsCode(null);
        data.setOrder(order);

        //订单子项
        data.setOrderItems(orderManager.selectOrderItemsList(request.getOrderId()));

        //合约信息
        ContractPInfoModel contractPInfoDTO = new ContractPInfoModel();
        contractPInfoDTO.setOrderId(request.getOrderId());
        List<ContractPInfoModel> contractList = contractOrderManager.selectContractInfo(contractPInfoDTO);
        if (!CollectionUtils.isEmpty(contractList)) {
            data.setContractInfo(contractList.get(0));
        }

        //订单流
        OrderZFlowModel zFlowDTO = new OrderZFlowModel();
        zFlowDTO.setOrderId(request.getOrderId());
        data.setFlowList(orderZFlowManager.selectFlowList(zFlowDTO));

        //查询物流信息
        data.setDeliveryList(deliveryManager.selectDeliverByOrderId(request.getOrderId()));

        //当前要处理的过程
        data.setCurrentFlowType(orderZFlowManager.selectCurrentFlowType(request.getOrderId()));

        data.setCurrTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        //不同的订单阶段，有不同的结束时间
        for (OrderZFlowModel flowDTO : data.getFlowList()) {

            //当前支付环节
            if (ActionFlowType.ORDER_HANDLER_ZF.getCode().equals(data.getCurrentFlowType())
                    && ActionFlowType.ORDER_HANDLER_XD.getCode().equals(flowDTO.getFlowType())) {
                data.setFlowEndTime(SequenceTools.AddMinuteADay(30,0,flowDTO.getUpdateTime()));
                break;
            }
            //当前确认收货环节
            if (ActionFlowType.ORDER_HANDLER_SH.getCode().equals(data.getCurrentFlowType())
                    && ActionFlowType.ORDER_HANDLER_FH.getCode().equals(flowDTO.getFlowType())) {
                data.setFlowEndTime(SequenceTools.AddMinuteADay(0,7,flowDTO.getUpdateTime()));
                break;
            }
        }

        //查询会员信息
        MemberResp resp=memberInfoReference.selectMember(order.getMemberId());
        UserMemberModel userMemberModel=new UserMemberModel();
        BeanUtils.copyProperties(resp,userMemberModel);
        data.setMemberInfo(userMemberModel);

        result.setResultData(data);
        result.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return result;
    }

}
