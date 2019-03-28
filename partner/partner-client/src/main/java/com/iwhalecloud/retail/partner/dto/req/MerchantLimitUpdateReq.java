package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/3/19
 */

@Data
@ApiModel(value = "更新商家限额 请求对象")
public class MerchantLimitUpdateReq implements Serializable {
    private static final long serialVersionUID = 7894237007020974022L;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 本月已使用
     */
    @ApiModelProperty(value = "本月已使用")
    private Long serialNumUsed;
}
