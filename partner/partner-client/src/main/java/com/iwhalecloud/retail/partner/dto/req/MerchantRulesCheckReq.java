package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商家权限规则校验请求参数
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月22日
 */
@Data
@ApiModel("商家权限规则校验请求参数")
public class MerchantRulesCheckReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("原商家")
    private String sourceMerchantId;

    @ApiModelProperty("目标商家")
    private List<String> targetMerchantIds;
}
