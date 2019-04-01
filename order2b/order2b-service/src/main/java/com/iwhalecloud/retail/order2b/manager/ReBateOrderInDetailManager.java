package com.iwhalecloud.retail.order2b.manager;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDetailDTO;
import com.iwhalecloud.retail.order2b.dto.response.FtpOrderDataResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderItemDetailReBateResp;
import com.iwhalecloud.retail.order2b.dto.response.ReBateOrderInDetailResp;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.ReBateOrderInDetailReq;
import com.iwhalecloud.retail.order2b.mapper.OrderItemDetailMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author lhr 2019-03-30 15:49:30
 */
@Component
public class ReBateOrderInDetailManager {

    @Reference
    private OrderItemDetailMapper orderItemDetailMapper;

    public ResultVO<Page<OrderItemDetailReBateResp>> queryOrderItemDetailByOrderId(ReBateOrderInDetailReq req){
        if (StringUtils.isEmpty(req.getOrderId())){
            return ResultVO.error();
        }
        Page<OrderItemDetailReBateResp> page = new Page<OrderItemDetailReBateResp>(req.getPageNo(), req.getPageSize());
      return orderItemDetailMapper.queryOrderItemDetailByOrderId(page,req);
    }

}
