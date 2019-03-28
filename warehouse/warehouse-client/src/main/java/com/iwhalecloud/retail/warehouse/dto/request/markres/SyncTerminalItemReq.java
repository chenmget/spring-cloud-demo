package com.iwhalecloud.retail.warehouse.dto.request.markres;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
@ApiModel("移动串码入库请求参数")
public class SyncTerminalItemReq  implements Serializable {
    @NotEmpty(message = "串码不能为空")
    @ApiModelProperty(value = "串码")
    private String barcode;
    @NotEmpty(message = "仓库ID不能为空")
    @ApiModelProperty(value = "仓库ID")
    private String storeid;
    @NotEmpty(message = "省级供货商ID不能为空")
    @ApiModelProperty(value = "省级供货商ID")
    private String provSupplyId;
    @NotEmpty(message = "省级供货商名称不能为空")
    @ApiModelProperty(value = "省级供货商名称")
    private String provSupplyName;

    @ApiModelProperty(value = "地市级供货商ID")
    private String citySupplyId;

    @ApiModelProperty(value = "地市级供货商名称")
    private String citySupplyName;
    @NotEmpty(message = "本地网不能为空")
    @ApiModelProperty(value = "本地网")
    private String lanid;
    @NotEmpty(message = "终端直供价不能为空")
    @ApiModelProperty(value = "终端直供价")
    private String directPrice;
    @NotEmpty(message = "采购类型不能为空")
    @ApiModelProperty(value = "采购类型")
    private String purchasetype;
    @NotEmpty(message = "营销资源Id不能为空")
    @ApiModelProperty(value = "营销资源Id")
    private String productcode;
}
