package com.iwhalecloud.retail.order.manager;

import com.iwhalecloud.retail.order.dto.model.ContractPInfoModel;
import com.iwhalecloud.retail.order.dto.resquest.OrderInfoEntryRequest;
import com.iwhalecloud.retail.order.mapper.ContractPInfoMapper;
import com.iwhalecloud.retail.order.model.ContractPInfoEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ContractOrderManager {


    @Resource
    private ContractPInfoMapper contractPInfoMapper;


    public List<ContractPInfoModel> selectContractInfo(ContractPInfoModel o) {
        return contractPInfoMapper.selectContractInfo(o);
    }


    public int insertContractInfo(ContractPInfoEntity o) {
        return contractPInfoMapper.insertContractInfo(o);
    }

    public int updateHYJOrderId(OrderInfoEntryRequest o) {
        return contractPInfoMapper.updateHYJOrderId(o);
    }


}
