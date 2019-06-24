package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("根据条件查找仓库列表")
public class StoreListReq implements Serializable {

    /**
     * 仓库名称
     */
    @ApiModelProperty(value = "仓库名称")
    private String storeName;

    /**
     * 仓库类型
     */
    @ApiModelProperty(value = "仓库类型")
    private String storeType;
    /**
     * 商家名称
     */
    @ApiModelProperty(value = "仓库细类")
    private String storeSubType;

    /**
     * 仓库对象标识集合
     */
    @ApiModelProperty(value = "仓库对象标识集合")
    private List<String> objIdList;

    /**
     * 指向公共管理区域标识
     */
    @ApiModelProperty(value = "指向公共管理区域标识")
    private String regionId;

    /**
     * 本地网名称
     */
    @ApiModelProperty(value = "本地网标识")
    private String lanId;

}
