package com.iwhalecloud.retail.order.ropservice.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.model.ContractPInfoModel;
import com.iwhalecloud.retail.order.dto.model.OrderModel;
import com.iwhalecloud.retail.order.dto.response.OrderInfoDetailRespDTO;
import com.iwhalecloud.retail.order.dto.response.OrderQueryListDTO;
import com.iwhalecloud.retail.order.dto.resquest.SelectOrderRequest;
import com.iwhalecloud.retail.order.manager.ContractOrderManager;
import com.iwhalecloud.retail.order.manager.DeliveryManager;
import com.iwhalecloud.retail.order.manager.OrderManager;
import com.iwhalecloud.retail.order.ropservice.MemberOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MemberOrderServiceImpl implements MemberOrderService {

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private DeliveryManager deliveryManager;

    @Override
    public CommonResultResp selectOrder(SelectOrderRequest request) {

        IPage<OrderModel> orderList= orderManager.selectMemberOrderList(request);

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


    @Autowired
    private ContractOrderManager contractOrderManager;


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
        data.setOrder(order);

        //订单子项
        data.setOrderItems(orderManager.selectOrderItemsList(request.getOrderId()));
        //查询物流信息
        data.setDeliveryList(deliveryManager.selectDeliverByOrderId(request.getOrderId()));

        //合约信息
        ContractPInfoModel contractPInfoDTO = new ContractPInfoModel();
        contractPInfoDTO.setOrderId(request.getOrderId());
        List<ContractPInfoModel> contractList = contractOrderManager.selectContractInfo(contractPInfoDTO);
        if (!CollectionUtils.isEmpty(contractList)) {
            data.setContractInfo(contractList.get(0));
        }

        result.setResultData(data);
        result.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        return result;
    }
}
