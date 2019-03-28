package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order2b.config.WhaleCloudDBKeySequence;
import com.iwhalecloud.retail.order2b.entity.OrderApplyDetail;
import com.iwhalecloud.retail.order2b.model.OrderItemDetailModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderApplyDetailMapper extends BaseMapper<OrderApplyDetail> {

    @WhaleCloudDBKeySequence
    int insertByList(List<OrderApplyDetail> list);


    int updateResNbr(OrderApplyDetail detail);



    List<OrderApplyDetail> selectOrderItem(OrderItemDetailModel orderItemDetail);
}
