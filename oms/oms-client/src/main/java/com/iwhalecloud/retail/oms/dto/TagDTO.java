package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/23 15:44
 * @Description:
 */

@Data
@ApiModel(value = "对应模型t_tag, 对应实体TagDTO类")
public class TagDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签ID")
    private java.lang.Long tagId; //标签ID

    @ApiModelProperty(value = "标签名称")
    private java.lang.String tagName; //标签名称

    @ApiModelProperty(value = "标签说明")
    private java.lang.String tagDesc; //标签说明

    @ApiModelProperty(value = "标签样式")
    private java.lang.Integer tagType; //标签样式

    @ApiModelProperty(value = "标签颜色")
    private java.lang.String tagColor; //标签颜色

    @ApiModelProperty(value = "更新时间")
    private Date updDate; //更新时间

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

    public Integer getTagType() {
        return tagType;
    }

    public void setTagType(Integer tagType) {
        this.tagType = tagType;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }
}

