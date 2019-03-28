package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/12/24
 */
@Data
@ApiModel(value = "删除商品请求参数")
public class GoodsDeleteReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = -855053114653439648L;

    /**
     * 商品id
     */
    @NotBlank
    @ApiModelProperty(value = "商品id")
    private String goodsId;
}
