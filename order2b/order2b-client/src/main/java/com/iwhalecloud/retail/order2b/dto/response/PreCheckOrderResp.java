package com.iwhalecloud.retail.order2b.dto.response;

import com.iwhalecloud.retail.order2b.dto.model.cart.CartOrderAmountDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.PromotionDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PreCheckOrderResp implements Serializable {

   private CartOrderAmountDTO orderAmount;

   private List<PromotionDTO> promotionList;
}
