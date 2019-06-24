package com.iwhalecloud.retail.warehouse.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("根据条件查找 商家信息 分页列表")
public class AllocateStorePageReq extends PageVO {

    /**
     * 商家ID集合
     */
    @ApiModelProperty(value = "商家ID集合")
    private List<String> merchantIdList;

    /**
     * 仓库类型
     */
    @ApiModelProperty(value = "仓库类型")
    private String storyType;
    /**
     * 仓库类型
     */
    @ApiModelProperty(value = "仓库类型")
    private String storeSubType;
    /**
     * 仓库类型
     */
    @ApiModelProperty(value = "仓库名称")
    private String storeName;
    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String merchantName;
    /**
     * 商家编码
     */
    @ApiModelProperty(value = "商家编码")
    private String merchantCode;
    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;
    /**
     * 商家类型
     */
    @ApiModelProperty(value = "商家类型")
    private String merchantType;

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

}
