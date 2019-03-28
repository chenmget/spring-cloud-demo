package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年02月27日
 * @Description:
 **/
@Data
@ApiModel(value = "查询商品分类关联推荐返回")
public class CatComplexQueryResp implements Serializable {

    //属性 begin
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;

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
