package com.iwhalecloud.retail.web.controller.b2b.system.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by z on 2019/1/15.
 */
@Data
public class UserCollectionAddReq implements Serializable {

    /**
     * 对象类型：1. B2C商品 2.B2C门店 3.B2B商品 4.B2B供应商
     */
    @ApiModelProperty(value = "对象类型：1. B2C商品 2.B2C门店 3.B2B商品 4.B2B供应商")
    private java.lang.String objType;

    /**
     * 当对象类型为 1.B2C商品时，记录商品ID 当对象类型为 2.B2C店铺时，记录门店ID 当对象类型为 3.B2B商品时，记录B2B商品ID 当对象类型为 4.B2B供应商时，记录B2B供应商ID
     */
    @ApiModelProperty(value = "当对象类型为 1.B2C商品时，记录商品ID 当对象类型为 2.B2C店铺时，记录门店ID 当对象类型为 3.B2B商品时，记录B2B商品ID 当对象类型为 4.B2B供应商时，记录B2B供应商ID")
    private java.lang.String objId;
}
