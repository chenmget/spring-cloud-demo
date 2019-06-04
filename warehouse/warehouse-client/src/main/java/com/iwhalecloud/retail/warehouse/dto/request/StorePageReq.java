package com.iwhalecloud.retail.warehouse.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("根据条件查找 商家信息 分页列表")
public class StorePageReq extends PageVO {

    /**
     * 商家编码
     */
    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    /**
     * 商家IDs
     */
    @ApiModelProperty(value = "商家IDs")
    private List<String> merchantIds;

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
     * 本地网标识集合
     */
    @ApiModelProperty(value = "本地网标识集合")
    private List<String> lanIdList;

    /**
     * 指向公共管理区域标识
     */
    @ApiModelProperty(value = "指向公共管理区域标识")
    private java.lang.String regionId;

    @ApiModelProperty(value = "商家类型")
    private java.lang.String merchantType;

    /**
     * 本地网名称
     */
    @ApiModelProperty(value = "本地网名称")
    private java.lang.String lanIdName;

    /**
     * 仓库层级
     */
    @ApiModelProperty(value = "仓库层级")
    private String storeGrade;

    /**
     * mktResStoreId
     */
    @ApiModelProperty(value = "mktResStoreId")
    private String mktResStoreId;

}
