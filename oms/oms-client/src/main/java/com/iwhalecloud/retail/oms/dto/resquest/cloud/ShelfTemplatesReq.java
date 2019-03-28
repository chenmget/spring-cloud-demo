package com.iwhalecloud.retail.oms.dto.resquest.cloud;

import com.iwhalecloud.retail.oms.dto.ShelfTemplatesDetailDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Ji.kai
 * @Date: 2018/11/14 11:00
 * @Description: 云货架末模板新增修改请求对象
 */
@Data
@ApiModel(value = "云货架末模板新增修改请求对象")
public class ShelfTemplatesReq implements Serializable {

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

    @ApiModelProperty(value = "云货架模板详情对象")
    private List<ShelfTemplatesDetailReq> shelfTemplatesDetails;
}
