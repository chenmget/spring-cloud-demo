package com.iwhalecloud.retail.warehouse.dto.request.markresswap;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
@ApiModel("固网串码入库请求参数")
public class EBuyTerminalItemSwapReq implements Serializable {
    @NotEmpty(message = "串码不能为空")
    @ApiModelProperty(value = "串码")
    private String barCode;
    @NotEmpty(message = "规格id不能为空")
    @ApiModelProperty(value = "规格id")
    private String mktId;
    @NotEmpty(message = "仓库ID不能为空")
    @ApiModelProperty(value = "仓库ID")
    private String storeId;
    @NotEmpty(message = "终端价格不能为空")
    @ApiModelProperty(value = "终端价格")
    private String salesPrice;
    @NotEmpty(message = "采购类型不能为空")
    @ApiModelProperty(value = "采购类型")
    private String purchaseType;
    @NotEmpty(message = "本地网不能为空")
    @ApiModelProperty(value = "本地网")
    private String lanId;
    @NotEmpty(message = "固网供货商id不能为空")
    @ApiModelProperty(value = "固网供货商id")
    private String supplyCode;
    @NotEmpty(message = "供货商名称不能为空")
    @ApiModelProperty(value = "供货商名称")
    private String supplyName;
}
