package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2019/1/15
 */
@Data
@ApiModel(value = "标签类型")
public class TagTypeDTO implements Serializable {

    /**
     * 标签类型编码
     */
    @ApiModelProperty(value = "tagTypeCode")
    private String tagTypeCode;

    /**
     * 标签类型名称
     */
    @ApiModelProperty(value = "tagTypeName")
    private String tagTypeName;
}
