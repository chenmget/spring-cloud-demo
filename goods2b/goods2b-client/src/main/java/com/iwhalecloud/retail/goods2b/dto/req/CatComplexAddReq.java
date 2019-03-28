package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年02月27日
 * @Description:
 **/
@Data
public class CatComplexAddReq extends AbstractRequest implements Serializable {
    //属性 begin

    /**
     * catId
     */
    @ApiModelProperty(value = "catId")
    private String catId;

    /**
     * targetType
     */
    @ApiModelProperty(value = "targetType")
    private String targetType;

    /**
     * targetId
     */
    @ApiModelProperty(value = "targetId")
    private String targetId;

    /**
     * targetName
     */
    @ApiModelProperty(value = "targetName")
    private String targetName;

    /**
     * targetOrder
     */
    @ApiModelProperty(value = "targetOrder")
    private Long targetOrder;

    /**
     * createDate
     */
    @ApiModelProperty(value = "createDate")
    private java.util.Date createDate;
}
