package com.iwhalecloud.retail.web.controller.b2b.goods.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iwhalecloud.retail.goods2b.dto.req.ProductAddReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * Created by he.sw on 2018/12/24.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductBaseAddReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;
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
    private List<String> purchaseType;

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
     * 产品列表
     */
    @ApiModelProperty(value = "产品列表")
    @Valid
    private List<ProductAddReq> productAddReqs;

    /**
     * param21
     */
    @ApiModelProperty(value = "param21")
    private String param21;

    /**
     * param22
     */
    @ApiModelProperty(value = "param22")
    private String param22;

    /**
     * param23
     */
    @ApiModelProperty(value = "param23")
    private String param23;

    /**
     * param24
     */
    @ApiModelProperty(value = "param24")
    private String param24;

    /**
     * param25
     */
    @ApiModelProperty(value = "param25")
    private String param25;

    /**
     * param26
     */
    @ApiModelProperty(value = "param26")
    private String param26;

    /**
     * param27
     */
    @ApiModelProperty(value = "param27")
    private String param27;

    /**
     * param28
     */
    @ApiModelProperty(value = "param28")
    private String param28;

    /**
     * param29
     */
    @ApiModelProperty(value = "param29")
    private String param29;

    /**
     * param30
     */
    @ApiModelProperty(value = "param30")
    private String param30;

    /**
     * param31
     */
    @ApiModelProperty(value = "param31")
    private String param31;

    /**
     * param32
     */
    @ApiModelProperty(value = "param32")
    private String param32;

    /**
     * param33
     */
    @ApiModelProperty(value = "param33")
    private String param33;

    /**
     * param34
     */
    @ApiModelProperty(value = "param34")
    private String param34;

    /**
     * param35
     */
    @ApiModelProperty(value = "param35")
    private String param35;

    /**
     * param36
     */
    @ApiModelProperty(value = "param36")
    private String param36;

    /**
     * param37
     */
    @ApiModelProperty(value = "param37")
    private String param37;

    /**
     * param38
     */
    @ApiModelProperty(value = "param38")
    private String param38;

    /**
     * param39
     */
    @ApiModelProperty(value = "param39")
    private String param39;

    /**
     * param40
     */
    @ApiModelProperty(value = "param40")
    private String param40;

    /**
     * param41
     */
    @ApiModelProperty(value = "param41")
    private String param41;

    /**
     * param42
     */
    @ApiModelProperty(value = "param42")
    private String param42;

    /**
     * param43
     */
    @ApiModelProperty(value = "param43")
    private String param43;

    /**
     * param44
     */
    @ApiModelProperty(value = "param44")
    private String param44;

    /**
     * param45
     */
    @ApiModelProperty(value = "param45")
    private String param45;

    /**
     * param46
     */
    @ApiModelProperty(value = "param46")
    private String param46;

    /**
     * param47
     */
    @ApiModelProperty(value = "param47")
    private String param47;

    /**
     * param48
     */
    @ApiModelProperty(value = "param48")
    private String param48;

    /**
     * param49
     */
    @ApiModelProperty(value = "param49")
    private String param49;

    /**
     * param50
     */
    @ApiModelProperty(value = "param50")
    private String param50;

    /**
     * param51
     */
    @ApiModelProperty(value = "param51")
    private String param51;

    /**
     * param52
     */
    @ApiModelProperty(value = "param52")
    private String param52;

    /**
     * param53
     */
    @ApiModelProperty(value = "param53")
    private String param53;

    /**
     * param54
     */
    @ApiModelProperty(value = "param54")
    private String param54;

    /**
     * param55
     */
    @ApiModelProperty(value = "param55")
    private String param55;

    /**
     * param56
     */
    @ApiModelProperty(value = "param56")
    private String param56;

    /**
     * param57
     */
    @ApiModelProperty(value = "param57")
    private String param57;

    /**
     * param58
     */
    @ApiModelProperty(value = "param58")
    private String param58;

    /**
     * param59
     */
    @ApiModelProperty(value = "param59")
    private String param59;

    /**
     * param60
     */
    @ApiModelProperty(value = "param60")
    private String param60;

    /**
     * param61
     */
    @ApiModelProperty(value = "param61")
    private String param61;

    /**
     * param62
     */
    @ApiModelProperty(value = "param62")
    private String param62;

    /**
     * param63
     */
    @ApiModelProperty(value = "param63")
    private String param63;

    /**
     * param64
     */
    @ApiModelProperty(value = "param64")
    private String param64;

    /**
     * param65
     */
    @ApiModelProperty(value = "param65")
    private String param65;

    /**
     * param66
     */
    @ApiModelProperty(value = "param66")
    private String param66;

    /**
     * param67
     */
    @ApiModelProperty(value = "param67")
    private String param67;

    /**
     * param68
     */
    @ApiModelProperty(value = "param68")
    private String param68;

    /**
     * param69
     */
    @ApiModelProperty(value = "param69")
    private String param69;

    /**
     * param70
     */
    @ApiModelProperty(value = "param70")
    private String param70;
    /**
     * manufacturerId
     */
    @ApiModelProperty(value = "manufacturerId")
    private String manufacturerId;
}
