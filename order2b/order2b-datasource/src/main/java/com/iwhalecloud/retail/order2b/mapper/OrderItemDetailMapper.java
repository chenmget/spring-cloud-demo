package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.config.WhaleCloudDBKeySequence;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDetailDTO;
import com.iwhalecloud.retail.order2b.dto.response.OrderItemDetailReBateResp;
import com.iwhalecloud.retail.order2b.dto.response.ReBateOrderInDetailResp;
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
     * @return
     */
    List<String> selectResNbrListByIds(OrderItemDetailModel req);

    List<OrderItemDetail> selectOrderItemDetail(OrderItemDetailModel orderItemDetail);

    ResultVO<Page<OrderItemDetailReBateResp>> queryOrderItemDetailByOrderId(Page<OrderItemDetailReBateResp> page,@Param("req") ReBateOrderInDetailReq req);

}
