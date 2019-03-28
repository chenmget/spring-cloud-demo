package com.iwhalecloud.retail.partner.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author My
 * @Date 2018/11/20
 **/
@Data
@ApiModel(value = "对应模型ES_PARTNER,ES_PARTNER_SHOP 对应的混合类")
public class PartnerRelShopDTO implements java.io.Serializable{
    /**
     * 分销商id
     */
    @ApiModelProperty(value = "分销商id")
    private java.lang.String partnerId;
    /**
     * 分销商名称
     */
    @ApiModelProperty(value = "分销商名称")
    private java.lang.String partnerName;
    /**
     * 组织类型
     */
    @ApiModelProperty(value = "组织类型")
    private java.lang.String orgType;

    @ApiModelProperty(value = "供应商ID")
    private java.lang.String supplierId;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private java.lang.String lat;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private java.lang.String lng;

    /**
     * 主键ID  (和导过来的code同值)
     */
    @ApiModelProperty(value = "主键ID  (和导过来的code同值)")
    private java.lang.String partnerShopId;
    /**
     * 网点名字
     */
    @ApiModelProperty(value = "网点名字")
    private java.lang.String name;
    /**
     * 网店地址
     */
    @ApiModelProperty(value = "网店地址")
    private java.lang.String address;
    /**
     * 厅店距离
     */
    @ApiModelProperty(value = "厅店距离")
    private String distance;
    /**
     * 区域标识
     */
    @ApiModelProperty(value = "区域标识")
    private String areaCode;

    /**
     * 所属区域
     */
    @ApiModelProperty(value = "所属区域")
    private java.lang.String regionId;

}
