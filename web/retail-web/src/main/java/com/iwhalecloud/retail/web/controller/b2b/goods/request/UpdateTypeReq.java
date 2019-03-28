package com.iwhalecloud.retail.web.controller.b2b.goods.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateTypeReq implements Serializable {

    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 类型ID
     */
    @ApiModelProperty(value = "类型ID")
    private java.lang.String typeId;

    /**
     * 类型名称
     */
    @ApiModelProperty(value = "类型名称")
    private java.lang.String typeName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private java.lang.Long typeOrder;

    /**
     * is_deleted
     */
    @ApiModelProperty(value = "is_deleted")
    private java.lang.String isDeleted;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private java.lang.String createStaff;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private java.lang.String updateStaff;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private java.util.Date updateDate;

    /**
     * sourceFrom
     */
    @ApiModelProperty(value = "sourceFrom")
    private java.lang.String sourceFrom;

}
