package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("根据条件查找 商家信息")
public class MerchantGetReq implements Serializable {

    private static final long serialVersionUID = 3471687680823143960L;

    //属性 begin
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 商家编码
     */
    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    /**
      * 用户ID
     */
//    @ApiModelProperty(value = "用户ID")
//    private String userId;


}
