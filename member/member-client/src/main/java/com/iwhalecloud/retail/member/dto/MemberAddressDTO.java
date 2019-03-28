package com.iwhalecloud.retail.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * MemberAddress
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mem_member_address, 对应实体MemberAddress类")
public class MemberAddressDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * addrId
  	 */
	@ApiModelProperty(value = "addrId")
  	private String addrId;
	
	/**
  	 * 会员ID
  	 */
	@ApiModelProperty(value = "会员ID")
  	private String memberId;
	
	/**
  	 * country
  	 */
	@ApiModelProperty(value = "country")
  	private String country;
	
	/**
  	 * 省份ID
  	 */
	@ApiModelProperty(value = "省份ID")
  	private String provinceId;
	
	/**
  	 * 市县ID
  	 */
	@ApiModelProperty(value = "市县ID")
  	private String cityId;
	
	/**
  	 * 区域ID
  	 */
	@ApiModelProperty(value = "区域ID")
  	private String regionId;
	
	/**
  	 * 区域
  	 */
	@ApiModelProperty(value = "区域")
  	private String region;
	
	/**
  	 * 市县
  	 */
	@ApiModelProperty(value = "市县")
  	private String city;
	
	/**
  	 * 省份
  	 */
	@ApiModelProperty(value = "省份")
  	private String province;
	
	/**
  	 * 具体地址
  	 */
	@ApiModelProperty(value = "具体地址")
  	private String addr;
	

	@ApiModelProperty(value = "邮编")
  	private String zip;
	
	@ApiModelProperty(value = "邮箱")
	private String email;

	/**
  	 * 是否有效地址 1是  0否
  	 */
	@ApiModelProperty(value = "是否有效地址 1是  0否")
  	private String isEffect;
	
	/**
  	 * 是否默认地址 1是  0否
  	 */
	@ApiModelProperty(value = "是否默认地址 1是  0否")
  	private String isDefault;
	
	/**
  	 * 收货人名称
  	 */
	@ApiModelProperty(value = "收货人名称")
  	private String consigeeName;
	
	/**
  	 * 收货人联系方式
  	 */
	@ApiModelProperty(value = "收货人联系方式")
  	private String consigeeMobile;
	
	/**
  	 * 更新时间
  	 */
	@ApiModelProperty(value = "更新时间")
  	private java.util.Date lastUpdate;
	
  	
}
