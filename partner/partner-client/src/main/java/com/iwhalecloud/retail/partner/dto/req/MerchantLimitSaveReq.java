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
@ApiModel(value = "添加商家限额请求对象")
public class MerchantLimitSaveReq implements Serializable {
    private static final long serialVersionUID = 7657953088196836447L;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 限额
     */
    @ApiModelProperty(value = "限额")
    private Long maxSerialNum;

}
