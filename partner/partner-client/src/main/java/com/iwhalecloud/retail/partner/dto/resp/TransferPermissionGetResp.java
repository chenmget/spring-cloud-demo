package com.iwhalecloud.retail.partner.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "调拨权限获取 返回")
public class TransferPermissionGetResp implements Serializable {

    /**
     * 区域ID集合
     */
    @ApiModelProperty(value = "区域ID集合")
    private List<String> regionIdList;

    /**
     * 机型ID集合
     */
    @ApiModelProperty(value = "机型ID集合")
    private List<String> productIdList;

    /**
     * 商家ID集合
     */
    @ApiModelProperty(value = "商家ID集合")
    private List<String> merchantIdList;
}
