package com.iwhalecloud.retail.goods.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/12/14
 */
@Data
@ApiModel(value = "修改购买数量请求类")
public class UpdateBuyCountByIdReq implements Serializable {

    private static final long serialVersionUID = -6076873814976574171L;

    @NotBlank
    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @NotBlank
    @ApiModelProperty(value = "购买数量")
    private Integer buyCount;
}
