package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/12/7
 */
@Data
@ApiModel(value = "修改上下架请求类")
public class UpdateMarketEnableReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 8412688440192852273L;

    @NotBlank
    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    @NotNull
    @ApiModelProperty(value = "状态")
    private Integer marketEnable;
}
