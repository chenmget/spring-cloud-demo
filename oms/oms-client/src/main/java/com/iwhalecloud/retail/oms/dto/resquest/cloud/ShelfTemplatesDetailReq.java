package com.iwhalecloud.retail.oms.dto.resquest.cloud;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Ji.kai
 * @Date: 2018/11/14 11:00
 * @Description: 云货架末模板新增修改请求对象
 */
@Data
@ApiModel(value = "云货架模板详情对象请求参数")
public class ShelfTemplatesDetailReq implements Serializable {

    //属性 begin
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private java.lang.Long id;

    /**
     * defCategoryId
     */
    @ApiModelProperty(value = "默认货架类目id")
    private java.lang.String defCategoryId;

    @ApiModelProperty(value = "关联的货架模板编号")
    private java.lang.String shelfTemplatesNumber;

    @ApiModelProperty(value = "关联的默认运营位编号")
    private java.lang.String operatingPositionId;

    @ApiModelProperty(value = "默认货架类目名称")
    private String defCategoryName;

    @ApiModelProperty(value = "类目顺序")
    private Integer categorySequence;

    @ApiModelProperty(value = "类目展示样式：1001:自定义样式（推荐）、10002:九宫格、1003:轮播样式")
    private Integer categoryStyle;

    /**
     * operatingPositionId
     */
    @ApiModelProperty(value = "操作动作 add：新增 delete：删除")
    private java.lang.String action;
}
