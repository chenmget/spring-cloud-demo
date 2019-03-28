package com.iwhalecloud.retail.oms.dto;

import java.io.Serializable;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/25 00:10
 * @Description:
 */
public class SelectTagListDTO implements Serializable {
    private String tagId;
    private String tagName;
    private Integer contentNumber;
    private String tagDesc;
    private String tagColor;

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getContentNumber() {
        return contentNumber;
    }

    public void setContentNumber(Integer contentNumber) {
        this.contentNumber = contentNumber;
    }

    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }
}

