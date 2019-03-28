package com.iwhalecloud.retail.rights.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 优惠券和产品的对应关系
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月01日
 */
@Data
public class ProductOrderItemDTO extends AbstractRequest implements Serializable{

    @ApiModelProperty("优惠券实例Id")
    private String couponInstId;

    @ApiModelProperty("产品Id")
    private String productId;
}
