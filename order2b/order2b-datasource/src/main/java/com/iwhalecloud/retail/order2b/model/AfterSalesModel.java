package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.entity.OrderApply;
import com.iwhalecloud.retail.order2b.entity.OrderItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AfterSalesModel extends OrderApply {

    OrderItem orderItems;

    private String handlerName;

    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    @ApiModelProperty(value = "商家所属经营主体	")
    private String businessEntityName;


}
