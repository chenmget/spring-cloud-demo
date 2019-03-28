package com.iwhalecloud.retail.order.dbservice;

import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.model.ContractPInfoModel;
import com.iwhalecloud.retail.order.model.OrderUpdateAttrEntity;

public interface ContractOrderService {

    /**
     * 保存合约信息
     */
    CommonResultResp insertContractInfo(ContractPInfoModel o, OrderUpdateAttrEntity dto);

}
