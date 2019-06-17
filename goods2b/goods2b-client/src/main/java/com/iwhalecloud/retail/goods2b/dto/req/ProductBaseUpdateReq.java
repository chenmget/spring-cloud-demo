package com.iwhalecloud.retail.goods2b.dto.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by he.sw on 2018/12/24.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductBaseUpdateReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "productBaseId")
    private String productBaseId;

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
     * updateDate
     */
    @ApiModelProperty(value = "updateDate")
    private java.util.Date updateDate;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String updateStaff;

    /**
     * 拓展参数
     */
    @ApiModelProperty(value = "拓展参数")
    private ProductExtUpdateReq productExtUpdateReq;

    /**
     * 产品信息
     */
    @ApiModelProperty(value = "产品信息")
    private List<ProductUpdateReq> productUpdateReqs;

    /***2019年3月19日11:24:54 新增商品修改属性***/
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "产品型号")
    private String unitType;
    @ApiModelProperty(value = "品牌")
    private String brandId;

    @ApiModelProperty(value = "产品分类")
    private String catId;
    @ApiModelProperty(value = "是否有串码")
    private String isImei;
    @ApiModelProperty(value = "类型Id")
    private String typeId;

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
     * 是否固网产品
     */
    @ApiModelProperty(value = "是否固网产品")
    private String isFixedLine;

    /**
     * 是否需要抽检
     */
    @ApiModelProperty(value = "是否需要抽检")
    private String IsInspection;
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

    @ApiModelProperty(value = "oldProductUpdateReqs")
    private List<ProductUpdateReq> oldProductUpdateReqs;

    /**
     * 价格档位
     */
    @ApiModelProperty(value = "价格档位")
    private String priceLevel;

    /**
     * 产品列表
     */
    @ApiModelProperty(value = "产品列表")
    private List<ProductAddReq> productAddReqs;

    /**
     * 卖点
     */
    @ApiModelProperty(value = "卖点")
    private String sallingPoint;

}
