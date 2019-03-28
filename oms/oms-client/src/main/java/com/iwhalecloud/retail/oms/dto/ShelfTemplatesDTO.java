package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/16 11:00
 * @Description: 云货架模板实体类
 */

@Data
@ApiModel(value = "对应模型shelf_templates, 对应实体ShelfTemplatesDTO类")
public class ShelfTemplatesDTO extends PageVO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id; //id

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate; //创建时间

    @ApiModelProperty(value = "修改时间")
    private Date gmtModified; //修改时间

    @ApiModelProperty(value = "创建人")
    private String creator; //创建人

    @ApiModelProperty(value = "修改人")
    private String modifier; //修改人

    @ApiModelProperty(value = "是否删除")
    private int isDeleted; //是否删除：0未删、1删除

    @ApiModelProperty(value = "货架默认模板编号")
    private String tempNumber; //货架模板编号

    @ApiModelProperty(value = "货架默认模板名称")
    private String shelfTemplatesName; //货架默认模板名称

    @ApiModelProperty(value = "货架默认模板描述")
    private String shelfTemplatesDesc; //货架默认模板描述

    @ApiModelProperty(value = "货架背景图片地址")
    private String backgroudImgUrl; //货架背景图片地址

    @ApiModelProperty(value = "货架默认模板风格：1000（默认风格）、2000（苹果风格）")
    private Integer shelfTemplatesStyle;// 货架默认模板风格：1000（默认风格）、2000（苹果风格）

    @ApiModelProperty(value = "货架封面图url")
    private String coverUrl;// 货架封面图url

    @ApiModelProperty(value = "货架关联类目运营位详情")
    private List<ShelfTemplatesDetailDTO> shelfTemplatesDetails;
}

