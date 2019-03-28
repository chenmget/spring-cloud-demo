package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/12 20:09
 */
@Data
public class VerifyProductPurchasesLimitResp implements Serializable {

    @ApiModelProperty("响应编码")
    private String resultCode;
    @ApiModelProperty("响应消息")
    private String resultMsg;
}
