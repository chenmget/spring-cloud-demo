package com.iwhalecloud.retail.warehouse.dto.request.markresswap;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/6 17:09
 */
@Data
@ApiModel("移动串码入库请求参数")
public class SyncTerminalItemSwapReq implements Serializable{

    @NotEmpty(message = "串码不能为空")
    @ApiModelProperty(value = "串码")
    private String barCode;
    @NotEmpty(message = "仓库ID不能为空")
    @ApiModelProperty(value = "仓库ID")
    private String storeId;

    @ApiModelProperty(value = "省级供货商ID")
    private String provSupplyId;

    @ApiModelProperty(value = "省级供货商名称")
    private String provSupplyName;
    @ApiModelProperty(value = "地市级供货商ID")
    private String citySupplyId;
    @ApiModelProperty(value = "地市级供货商名称")
    private String citySupplyName;
    @NotEmpty(message = "本地网不能为空")
    @ApiModelProperty(value = "本地网")
    private String lanId;
    @NotEmpty(message = "终端直供价不能为空")
    @ApiModelProperty(value = "终端直供价")
    private String directPrice;
    @NotEmpty(message = "采购类型不能为空")
    @ApiModelProperty(value = "采购类型")
    private String purchaseType;
    @NotEmpty(message = "营销资源Id不能为空")
    @ApiModelProperty(value = "营销资源Id")
    private String productCode;

}
