package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/26 11:55
 * 返回账本统计信息
 */
@Data
public class AccountBalanceStResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "可用余额合计")
    private String totalAmount;
    @ApiModelProperty(value = "未生效余额合计")
    private String totalUneffAmount;
    @ApiModelProperty(value = "失效金额合计")
    private String totalInvaildAmount;


}
