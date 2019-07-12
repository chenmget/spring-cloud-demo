package com.iwhalecloud.retail.goods2b.dto.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wzy
 * @date 2019/7/12
 */
@Data
public class ProdCrmTypeReq implements Serializable {

    //属性 begin
    /**
     * typeId
     */
    @TableId(type = IdType.ID_WORKER)
    @ApiModelProperty(value = "typeId")
    private String typeId;

    /**
     * typeName
     */
    @ApiModelProperty(value = "typeName")
    private String typeName;

    /**
     * 产品类型的父级类型
     */
    @ApiModelProperty(value = "产品类型的父级类型")
    private String parentTypeId;

    /**
     * catOrder
     */
    @ApiModelProperty(value = "catOrder")
    private Long catOrder;

    /**
     * isDeleted
     */
    @ApiModelProperty(value = "isDeleted")
    private String isDeleted;

    /**
     * createStaff
     */
    @ApiModelProperty(value = "createStaff")
    private String createStaff;

    /**
     * createDate
     */
    @ApiModelProperty(value = "createDate")
    private java.util.Date createDate;

    /**
     * updateStaff
     */
    @ApiModelProperty(value = "updateStaff")
    private String updateStaff;

    /**
     * updateDate
     */
    @ApiModelProperty(value = "updateDate")
    private java.util.Date updateDate;

}
