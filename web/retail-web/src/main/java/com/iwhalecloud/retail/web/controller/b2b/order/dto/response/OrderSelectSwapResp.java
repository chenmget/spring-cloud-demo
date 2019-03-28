package com.iwhalecloud.retail.web.controller.b2b.order.dto.response;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.PromotionDTO;
import com.iwhalecloud.retail.order2b.dto.response.OrderSelectDetailResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderSelectResp;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OrderSelectResp类转换
 */
@Data
public class OrderSelectSwapResp extends OrderDTO {

    private List<OrderItemSwapResp> orderItems;




}
