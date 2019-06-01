package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * ProdType
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_type, 对应实体ProdType类")
public class TypeDetailResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型ID
     */
    @ApiModelProperty(value = "类型ID")
    private String typeId;

    /**
     * 类型名称
     */
    @ApiModelProperty(value = "类型名称")
    private String typeName;

    /**
     * 上级类型ID
     */
    @ApiModelProperty(value = "上级类型ID")
    private String parentTypeId;

    /**
     * 详细分类编码
     */
    @ApiModelProperty(value = "上级类型ID")
    private String detailCode;

    /**
     * 详细分类名称
     */
    @ApiModelProperty(value = "上级类型ID")
    private String detailName;
}
