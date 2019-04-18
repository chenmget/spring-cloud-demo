package com.iwhalecloud.retail.partner.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author he.sw
 * @date 2019/04/16
 */
@Data
@ApiModel(value = "轻巧的商家信息 对应实体Merchant类")
public class MerchantLigthResp implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 商家编码
     */
    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    /**
     * 商家所属经营主体
     */
    @ApiModelProperty(value = "商家所属经营主体	")
    private String businessEntityName;

}
