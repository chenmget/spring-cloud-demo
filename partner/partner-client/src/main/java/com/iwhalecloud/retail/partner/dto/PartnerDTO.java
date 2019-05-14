package com.iwhalecloud.retail.partner.dto;

import com.iwhalecloud.retail.partner.common.DesensitizedUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 分销商
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型PAR_PARTNER, 对应实体Partner类")
public class PartnerDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "进驻厅店名称（非该表字段）")
	private java.lang.String partnerShopName;

	@ApiModelProperty(value = "供应商ID（非该表字段）")
	private java.lang.String supplierId;


	//属性 begin
	/**
  	 * 分销商id
  	 */
	@ApiModelProperty(value = "分销商id")
  	private java.lang.String partnerId;
	
	/**
  	 * 分销商编码
  	 */
	@ApiModelProperty(value = "分销商编码")
  	private java.lang.String partnerCode;
	
	/**
  	 * 分销商名称
  	 */
	@ApiModelProperty(value = "分销商名称")
  	private java.lang.String partnerName;
	
	/**
  	 * 联系人
  	 */
	@ApiModelProperty(value = "联系人")
  	private java.lang.String linkman;
	
	/**
  	 * 联系电话
  	 */
	@ApiModelProperty(value = "联系电话")
  	private java.lang.String phoneNo;
	
	/**
  	 * 状态 0申请、1正常、2冻结、3注销,-1审核不通过
  	 */
	@ApiModelProperty(value = "状态 0申请、1正常、2冻结、3注销,-1审核不通过")
  	private java.lang.String state;
	
	/**
  	 * 失效时间
  	 */
	@ApiModelProperty(value = "失效时间")
  	private java.util.Date expDate;

	/**
  	 * 最后更新时间
  	 */
	@ApiModelProperty(value = "最后更新时间")
  	private java.util.Date lastUpdateDate;

	/**
  	 * 所属区域
  	 */
	@ApiModelProperty(value = "所属区域")
  	private java.lang.String regionId;
	
	/**
  	 * 本地网
  	 */
	@ApiModelProperty(value = "本地网")
  	private java.lang.String lanId;
	
	/**
  	 * 进驻厅店
  	 */
	@ApiModelProperty(value = "进驻厅店id")
  	private java.lang.String partnerShopId;
	
	/**
  	 * 驻店商归属经营主体名称
  	 */
	@ApiModelProperty(value = "驻店商归属经营主体名称")
  	private java.lang.String businessEntityName;
	
	/**
  	 * 驻店商归属经营主体编码
  	 */
	@ApiModelProperty(value = "驻店商归属经营主体编码")
  	private java.lang.String businessEntityCode;
	
	/**
  	 * MSS代理商编码
  	 */
	@ApiModelProperty(value = "MSS代理商编码")
  	private java.lang.String mssPartnerCode;
	
	/**
  	 * 客户编码
  	 */
	@ApiModelProperty(value = "客户编码")
  	private java.lang.String customerCode;
	
	/**
  	 * 统一组织编码
  	 */
	@ApiModelProperty(value = "统一组织编码")
  	private java.lang.String orgCode;
	
	/**
  	 * 组织类型
  	 */
	@ApiModelProperty(value = "组织类型")
  	private java.lang.String orgType;


	/**
	 * 重写脱敏
	 */
//	public String getPhoneNo() {
//		return DesensitizedUtils.mobilePhone(phoneNo);
//	}

}
