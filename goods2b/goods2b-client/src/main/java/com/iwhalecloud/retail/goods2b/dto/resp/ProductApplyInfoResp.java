package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author chenb
 * @Date 2019/06/26
 **/
@Data
public class ProductApplyInfoResp implements Serializable {

    //产品信息字段
    private  String productId; // 产品Id
    private  String productName; // 产品名称
    private  String productBaseId; // 产品基础Id


    private  String corporationPrice;// 政企价格
    private  String sn;// 产品25位编码


    private String unitType;//产品型号
    private String unitTypeName; //型号名称

    private String brandName;// 品牌名称

    private String color; //颜色
    private  String memory;//内存

    private  String attrValue1;//容量-规格1 字段

    private  String attrValue2;//容量-规格1 字段

    private  String attrValue3;//容量-规格1 字段

    private  String attrValue4;//容量-规格1 字段

    private  String attrValue5;//容量-规格1 字段

    private  String attrValue6;//容量-规格1 字段

    private  String attrValue7;//容量-规格1 字段

    private  String attrValue8;//容量-规格1 字段

    private  String attrValue9;//容量-规格1 字段

    private  String attrValue10;//容量-规格1 字段



    private  String purType;

    private  String purchaseType;

    private  String num;

    private  String defaultImages;


}
