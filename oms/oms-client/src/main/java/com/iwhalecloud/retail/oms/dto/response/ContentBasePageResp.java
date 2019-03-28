package com.iwhalecloud.retail.oms.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "内容列表分页查询响应参数对象")
public class ContentBasePageResp {
    /**
     * 内容ID
     */
    @ApiModelProperty(value = "内容ID")
    private java.lang.Long contentId;

    /**
     * 内容ID
     */
    @ApiModelProperty(value = "内容ID")
    private java.lang.String title;

    /**
     * 内容说明
     */
    @ApiModelProperty(value = "内容说明	")
    private java.lang.String desp;

    /**
     * 归属目录ID
     */
    @ApiModelProperty(value = "归属目录ID")
    private java.lang.Long catalogId;

    /**
     * 生效时间
     */
    @ApiModelProperty(value = "生效时间")
    private java.util.Date effDate;

    /**
     * 失效时间
     */
    @ApiModelProperty(value = "失效时间	")
    private java.util.Date expDate;

    /**
     * 内容类型
     */
    @ApiModelProperty(value = "内容类型")
    private java.lang.Integer type;

    /**
     * 内容状态
     */
    @ApiModelProperty(value = "内容状态")
    private java.lang.Integer status;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private java.lang.String oprId;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updDate;

    /**
     * 文案
     */
    @ApiModelProperty(value = "文案")
    private java.lang.String copywriter;

    /**
     * 最大图片数
     */
    @ApiModelProperty(value = "最大图片数")
    private java.lang.Integer maxpicnum;

    /**
     * 轮播间隔
     */
    @ApiModelProperty(value = "轮播间隔")
    private java.lang.Integer playinterval;

    /**
     * matid
     */
    @ApiModelProperty(value = "matid")
    private java.lang.Integer matid;

    /**
     * name
     */
    @ApiModelProperty(value = "name")
    private java.lang.String name;

    /**
     * path
     */
    @ApiModelProperty(value = "path")
    private java.lang.String path;

    /**
     * thumbpath
     */
    @ApiModelProperty(value = "thumbpath")
    private java.lang.String thumbpath;
}
