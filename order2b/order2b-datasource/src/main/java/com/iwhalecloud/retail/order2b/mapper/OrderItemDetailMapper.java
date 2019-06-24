package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.config.WhaleCloudDBKeySequence;
import com.iwhalecloud.retail.order2b.dto.response.OrderItemDetailReBateResp;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.ReBateOrderInDetailReq;
import com.iwhalecloud.retail.order2b.entity.OrderItemDetail;
import com.iwhalecloud.retail.order2b.model.OrderItemDetailModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemDetailMapper extends BaseMapper<OrderItemDetail> {

    @WhaleCloudDBKeySequence
    int insertByList(List<OrderItemDetail> list);

    int updateResNbr(OrderItemDetailModel model);

    /**
     * return(通过detailId 查询串码)
     */
    List<String> selectResNbrListByIds(OrderItemDetailModel req);

    /**
     * return(通过串码 查询订单号)
     */
    List<String> selectOrderIdByresNbr(OrderItemDetailModel resNbr);

    List<OrderItemDetail> selectOrderItemDetail(OrderItemDetailModel orderItemDetail);

    Page<OrderItemDetailReBateResp> queryOrderItemDetailByOrderId(Page<OrderItemDetailReBateResp> page,@Param("req") ReBateOrderInDetailReq req);

}
