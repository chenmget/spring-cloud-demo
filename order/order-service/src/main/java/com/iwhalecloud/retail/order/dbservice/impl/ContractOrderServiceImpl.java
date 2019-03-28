package com.iwhalecloud.retail.order.dbservice.impl;

import com.iwhalecloud.retail.order.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.model.ContractPInfoModel;
import com.iwhalecloud.retail.order.manager.ContractOrderManager;
import com.iwhalecloud.retail.order.model.ContractPInfoEntity;
import com.iwhalecloud.retail.order.model.OrderUpdateAttrEntity;
import com.iwhalecloud.retail.order.dbservice.ContractOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ContractOrderServiceImpl implements ContractOrderService {

    @Autowired
    private ContractOrderManager contractOrderManager;


    @Override
    @Transactional
    public CommonResultResp insertContractInfo(ContractPInfoModel o, OrderUpdateAttrEntity dto) {
        CommonResultResp resp = new CommonResultResp();
        o.setOrderId(dto.getOrderId());
        resp.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        ContractPInfoEntity bean=new ContractPInfoEntity();
        BeanUtils.copyProperties(o,bean);
        contractOrderManager.insertContractInfo(bean);
        return resp;
    }

}
