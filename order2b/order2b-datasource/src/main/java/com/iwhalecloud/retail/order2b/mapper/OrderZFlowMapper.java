package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order2b.config.WhaleCloudDBKeySequence;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderZFlowDTO;
import com.iwhalecloud.retail.order2b.entity.OrderFlowInit;
import com.iwhalecloud.retail.order2b.entity.ZFlow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderZFlowMapper extends BaseMapper<ZFlow> {

    List<OrderZFlowDTO> selectFlowList(ZFlow dto);

    int updateFlowList(ZFlow dto);

    @WhaleCloudDBKeySequence
    int insertFlowList(List<ZFlow> dto);

    ZFlow currentFlow(ZFlow dto);

    List<OrderFlowInit> selectFlowInit(OrderFlowInit dot);
}
