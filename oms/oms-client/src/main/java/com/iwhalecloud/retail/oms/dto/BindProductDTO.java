package com.iwhalecloud.retail.oms.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/19 10:58
 * @Description: 运营位商品关联表实体类
 */

@Data
public class BindProductDTO implements Serializable {
    private int id; //id
    private String gmtCreate; //创建时间
    private String gmtModified; //修改时间
    private String creator; //创建人
    private String modifier; //修改人
    private int isDeleted; //是否删除：0未删、1删除
    private String operatingPositionId; //运营位ID
    private String productNumber; //商品编码

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getOperatingPositionId() {
        return operatingPositionId;
    }

    public void setOperatingPositionId(String operatingPositionId) {
        this.operatingPositionId = operatingPositionId;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }
}

