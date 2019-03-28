package com.iwhalecloud.retail.order2b.dto.resquest.cart;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/27
 **/
@Data
public class ListCartReq extends OrderRequest implements Serializable {
    @ApiModelProperty(value = "会员ID")
    private String userId;
    @ApiModelProperty(value = "分销商ID,B2b弃用")
    private String partnerId;

    private String supplierId;
}
