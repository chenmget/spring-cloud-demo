package com.iwhalecloud.retail.order.mapper;

import com.iwhalecloud.retail.order.config.WhaleCloudDBKeySequence;
import com.iwhalecloud.retail.order.dto.model.ContractPInfoModel;
import com.iwhalecloud.retail.order.dto.resquest.OrderInfoEntryRequest;
import com.iwhalecloud.retail.order.model.ContractPInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContractPInfoMapper {

    List<ContractPInfoModel> selectContractInfo(ContractPInfoModel o);


    @WhaleCloudDBKeySequence
    int insertContractInfo(ContractPInfoEntity o);

    int updatePhoneStatus(ContractPInfoModel o);

    int updateHYJOrderId(OrderInfoEntryRequest o);
}
