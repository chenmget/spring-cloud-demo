package com.iwhalecloud.retail.partner.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FactoryMerchantImeiResp implements Serializable {
    private static final long serialVersionUID = -962995418100166485L;
    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    /**
     * 产品列表
     */
    @ApiModelProperty(value = "产品列表")
    private List<MerchantImeiRulesResp> productList;
}
