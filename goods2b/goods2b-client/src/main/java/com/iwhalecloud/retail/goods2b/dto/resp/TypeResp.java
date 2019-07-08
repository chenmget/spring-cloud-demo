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
public class TypeResp implements Serializable {

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
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Long typeOrder;

    /**
     * is_deleted
     */
    @ApiModelProperty(value = "is_deleted")
    private String isDeleted;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createStaff;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String updateStaff;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private java.util.Date updateDate;

    /**
     * sourceFrom
     */
    @ApiModelProperty(value = "sourceFrom")
    private String sourceFrom;

}
