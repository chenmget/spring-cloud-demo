package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "仓库查询")
public class ListStoreReq implements Serializable {
    @ApiModelProperty(value = "商家IDs")
    private String merchantIds;
    
    /**
     * 商家名称
     */
    @ApiModelProperty(value = "仓库名称")
    private String storeName;
    /**
     * 商家名称
     */
    @ApiModelProperty(value = "仓库类型")
    private String storeType;
    /**
     * 商家名称
     */
    @ApiModelProperty(value = "仓库细类")
    private String storeSubType;
}
