package com.iwhalecloud.retail.order2b.dto.response;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "订单优惠列表查询返回对象")
@TableName(value = "ord_promotion")
public class PromotionResp implements Serializable{

    private static final long serialVersionUID = 1L;

    private String lanId;
    private String ordPromotioId;
    private String orderId;
    private String orderItemId;
    private String goodsId;
    private String productId;
    private String mktActId;
    private String mktActName;
    private String mktActType;
    private String promotionId;
    private String promotionName;
    private String promotionType;
    private String promotionInstId;
    private String discount;
    private String statusCd;
    private String statusDate;
    private String createDate;
    private String sourceFrom;


}
