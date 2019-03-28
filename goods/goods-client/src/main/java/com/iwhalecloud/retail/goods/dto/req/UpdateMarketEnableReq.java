package com.iwhalecloud.retail.goods.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/12/7
 */
@Data
@ApiModel(value = "修改上下架请求类")
public class UpdateMarketEnableReq implements Serializable {

    private static final long serialVersionUID = 8412688440192852273L;

    @NotBlank
    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @NotBlank
    @ApiModelProperty(value = "状态")
    private String marketEnable;
}
