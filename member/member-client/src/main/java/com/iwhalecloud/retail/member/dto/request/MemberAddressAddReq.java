package com.iwhalecloud.retail.member.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/28
 **/
@Data
public class MemberAddressAddReq implements Serializable {

    
	private static final long serialVersionUID = 1L;
	
	
	@ApiModelProperty(value = "会员ID")
    private String memberId;

    @ApiModelProperty(value = "收货人名称")
    private String consigeeName;

    @ApiModelProperty(value = "所在国家")
    private String country;

    @ApiModelProperty(value = "省份")
    private Long provinceId;

    @ApiModelProperty(value = "城市ID")
    private Long cityId;

    @ApiModelProperty(value = "区域ID")
    private Long regionId;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区域")
    private String region;

    @ApiModelProperty(value = "详细地址")
    private String addr;

    @ApiModelProperty(value = "zip邮编")
    private String zip;

//    @ApiModelProperty(value = "联系电话")
//    private String tel;
//    @ApiModelProperty(value = "联系电话")
//    private String mobile;
    @ApiModelProperty(value = "收货人联系方式")
    private String consigeeMobile;

//    @ApiModelProperty(value = "默认地址")
//    private Integer defAddr =0;
    @ApiModelProperty(value = "是否默认地址 1是  0否")
    private String isDefault;

//    @ApiModelProperty(value = "更新时间")
//    private java.util.Date lastUpdate;

    @ApiModelProperty(value = "邮箱")
    private String email;

}
