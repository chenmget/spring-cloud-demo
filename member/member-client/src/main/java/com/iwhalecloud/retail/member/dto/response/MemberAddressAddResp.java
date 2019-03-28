package com.iwhalecloud.retail.member.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/12/10
 **/
@Data
public class MemberAddressAddResp implements Serializable {
    private String addrId;
//    @ApiModelProperty(value = "收货人")
//    private String name;
    @ApiModelProperty(value = "收货人名称")
    private String consigeeName;
    @ApiModelProperty(value = "收货人联系方式")
    private String consigeeMobile;
//    @ApiModelProperty(value = "联系电话")
//    private String mobile;
    @ApiModelProperty(value = "省份")
    private String province;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "区域")
    private String region;
    @ApiModelProperty(value = "详细地址")
    private String addr;
//    @ApiModelProperty(value = "默认地址")
//    private Integer defAddr;
    @ApiModelProperty(value = "是否默认地址 1是  0否")
    private String isDefault;

}
