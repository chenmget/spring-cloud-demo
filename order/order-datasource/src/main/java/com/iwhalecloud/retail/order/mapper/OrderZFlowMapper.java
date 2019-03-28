package com.iwhalecloud.retail.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order.config.WhaleCloudDBKeySequence;
import com.iwhalecloud.retail.order.dto.model.OrderZFlowModel;
import com.iwhalecloud.retail.order.model.OrderFlowInitEntity;
import com.iwhalecloud.retail.order.model.ZFlowEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface OrderZFlowMapper extends BaseMapper<ZFlowEntity> {

    List<OrderZFlowModel> selectFlowList(OrderZFlowModel dto);

    int updateFlowList(OrderZFlowModel dto);

    @WhaleCloudDBKeySequence
    int insertFlowList(List<ZFlowEntity> dto);

    String currentFlow(OrderZFlowModel dto );

    List<OrderZFlowModel> orderReminderList(@Param("remindFlag") String remindFlag, @Param("flowType") String flowType, @Param("interval") int interval);

    int orderReminderUpdate(OrderZFlowModel dto);

    List<OrderFlowInitEntity> selectFlowInit(OrderFlowInitEntity dot);
}
