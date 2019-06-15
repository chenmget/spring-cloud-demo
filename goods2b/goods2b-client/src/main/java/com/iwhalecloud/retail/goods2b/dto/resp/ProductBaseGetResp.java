package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fadeaway on 2018/12/24.
 */
@Data
public class ProductBaseGetResp implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * productBaseId
     */
    @ApiModelProperty(value = "productBaseId")
    private String productBaseId;

    /**
     * 产品类别
     */
    @ApiModelProperty(value = "产品类别")
    private String catId;

    /**
     * 产品分类
     */
    @ApiModelProperty(value = "产品类型")
    private String typeId;

    /**
     * 品牌
     */
    @ApiModelProperty(value = "品牌")
    private String brandId;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 产品型号
     */
    @ApiModelProperty(value = "产品型号")
    private String unitType;

    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    private String unitTypeName;

    /**
     * effDate
     */
    @ApiModelProperty(value = "effDate")
    private java.util.Date effDate;

    /**
     * expDate
     */
    @ApiModelProperty(value = "expDate")
    private java.util.Date expDate;

    /**
     * 是否有串码
     */
    @ApiModelProperty(value = "是否有串码")
    private String isImei;

    /**
     * 是否推送ITMS
     */
    @ApiModelProperty(value = "是否推送ITMS")
    private String isItms;

    /**
     * 是否需要CT码
     */
    @ApiModelProperty(value = "是否需要CT码")
    private String isCtCode;

    /**
     * 是否需要抽检
     */
    @ApiModelProperty(value = "是否需要抽检")
    private String IsInspection;

    /**
     * 是否固网产品
     */
    @ApiModelProperty(value = "是否固网产品")
    private String isFixedLine;

    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private String productCode;

    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String unit;

    /**
     * 换货对象
     */
    @ApiModelProperty(value = "换货对象")
    private String exchangeObject;

    /**
     * 采购类型
     */
    @ApiModelProperty(value = "采购类型")
    private String purchaseType;

    /**
     * 参数1
     */
    @ApiModelProperty(value = "参数1")
    private String param1;

    /**
     * 参数2
     */
    @ApiModelProperty(value = "参数2")
    private String param2;

    /**
     * 参数3
     */
    @ApiModelProperty(value = "参数3")
    private String param3;

    /**
     * 参数4
     */
    @ApiModelProperty(value = "参数4")
    private String param4;

    /**
     * 参数5
     */
    @ApiModelProperty(value = "参数5")
    private String param5;

    /**
     * 参数6
     */
    @ApiModelProperty(value = "参数6")
    private String param6;

    /**
     * 参数7
     */
    @ApiModelProperty(value = "参数7")
    private String param7;

    /**
     * 参数8
     */
    @ApiModelProperty(value = "参数8")
    private String param8;

    /**
     * 参数9
     */
    @ApiModelProperty(value = "参数9")
    private String param9;

    /**
     * 参数10
     */
    @ApiModelProperty(value = "参数10")
    private String param10;

    /**
     * 参数11
     */
    @ApiModelProperty(value = "参数11")
    private String param11;

    /**
     * 参数12
     */
    @ApiModelProperty(value = "参数12")
    private String param12;

    /**
     * 参数13
     */
    @ApiModelProperty(value = "参数13")
    private String param13;

    /**
     * 参数14
     */
    @ApiModelProperty(value = "参数14")
    private String param14;

    /**
     * 参数15
     */
    @ApiModelProperty(value = "参数15")
    private String param15;

    /**
     * 参数16
     */
    @ApiModelProperty(value = "参数16")
    private String param16;

    /**
     * 参数17
     */
    @ApiModelProperty(value = "参数17")
    private String param17;

    /**
     * 参数18
     */
    @ApiModelProperty(value = "参数18")
    private String param18;

    /**
     * 参数19
     */
    @ApiModelProperty(value = "参数19")
    private String param19;

    /**
     * 参数20
     */
    @ApiModelProperty(value = "参数20")
    private String param20;

    /**
     * isDeleted
     */
    @ApiModelProperty(value = "isDeleted")
    private String isDeleted;

    /**
     * 归属厂家
     */
    @ApiModelProperty(value = "归属厂家")
    private String manufacturerId;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createStaff;

    /**
     * createDate
     */
    @ApiModelProperty(value = "createDate")
    private java.util.Date createDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String updateStaff;

    /**
     * updateDate
     */
    @ApiModelProperty(value = "updateDate")
    private java.util.Date updateDate;

    /**
     * 类型名称
     */
    @ApiModelProperty(value = "类型名称")
    private String typeName;
    /**
     * 类别名称
     */
    @ApiModelProperty(value = "类别名称")
    private String catName;
    /**
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称")
    private String brandName;
    /**
     * 厂商名称
     */
    @ApiModelProperty(value = "厂商名称")
    private String manufacturerName;
    /**
     * 厂商ID
     */
    @ApiModelProperty(value = "厂商ID")
    private String manufacturerID;

    /**
     * productBase关联product数量
     */
    @ApiModelProperty(value = "productBase关联product数量")
    private Integer snCount;

    /**
     * 平均供货价
     */
    @ApiModelProperty(value = "平均供货价")
    private Double avgSupplyPrice;

    /**
     * 零售商标签
     */
    @ApiModelProperty(value = "零售商标签")
    private List<String> tagList;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "内存")
    private String memory;

    @ApiModelProperty(value = "25位产品编码")
    private String sn;

    @ApiModelProperty(value = "销售价")
    private String cost;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "attrValue1")
    private String attrValue1;

    @ApiModelProperty(value = "attrValue2")
    private String attrValue2;

    @ApiModelProperty(value = "attrValue3")
    private String attrValue3;

    /**
     * 价格档位
     */
    @ApiModelProperty(value = "价格档位")
    private String priceLevel;

    /**
     * 卖点
     */
    @ApiModelProperty(value = "卖点")
    private String sallingPoint;
}
