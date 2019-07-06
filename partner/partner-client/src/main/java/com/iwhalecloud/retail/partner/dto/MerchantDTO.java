package com.iwhalecloud.retail.partner.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Merchant
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "商家信息（概要）  对应模型par_merchant, 对应实体Merchant类")
public class MerchantDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;

/*** 非当前表字段 ****/
	/**
	 * 地市
	 */
	@ApiModelProperty(value = "地市名称")
	private java.lang.String lanName;

	/**
	 * 市县
	 */
	@ApiModelProperty(value = "市县名称")
	private java.lang.String cityName;

	/**
	 * 商家所属经营主体	id
	 */
	@ApiModelProperty(value = "商家所属经营主体	id")
	private java.lang.String businessEntityId;

/*** 非当前表字段 ****/


	//属性 begin
	@ApiModelProperty(value = "商家ID")
	private String merchantId;

	/**
	 * 商家编码
	 */
	@ApiModelProperty(value = "商家编码")
	private String merchantCode;

	/**
	 * 商家名称
	 */
	@ApiModelProperty(value = "商家名称")
	private String merchantName;

	/**
	 * 渠道状态:  有效 1000 主动暂停 1001 异常暂停 1002 无效 1100 终止 1101 退出 1102 未生效 1200 已归档 1300 预退出 8922 冻结 8923
	 主动暂停 1001
	 异常暂停 1002
	 无效 1100
	 终止 1101
	 退出 1102
	 未生效 1200
	 已归档 1300
	 预退出 8922
	 冻结 8923

	 */
	@ApiModelProperty(value = "渠道状态:" +
			" 有效1000  主动暂停1001  异常暂停1002 无效1100 终止1101 退出1102 未生效1200" +
			" 已归档1300 预退出8922 冻结8923 主动暂停1001  异常暂停1002   无效1100  终止1101  " +
			" 退出1102  未生效1200  已归档1300 预退出8922  冻结8923 ")
	private java.lang.String status;

	/**
	 * 商家类型:  1 厂商    2 地包商    3 省包商   4 零售商
	 */
	@ApiModelProperty(value = "商家类型:  1 厂商    2 地包商    3 省包商   4 零售商")
	private java.lang.String merchantType;

	/**
	 * 关联sys_user表user_id
	 */
	@ApiModelProperty(value = "关联sys_user表user_id")
	private java.lang.String userId;

	/**
  	 * 商家所属经营主体	
  	 */
	@ApiModelProperty(value = "商家所属经营主体	")
  	private java.lang.String businessEntityName;
	
	/**
  	 * 商家所属经营主体	编码
  	 */
	@ApiModelProperty(value = "商家所属经营主体	编码")
  	private java.lang.String businessEntityCode;
	
	/**
  	 * 客户编码
  	 */
	@ApiModelProperty(value = "客户编码")
  	private java.lang.String customerCode;
	
	/**
  	 * 地市
  	 */
	@ApiModelProperty(value = "地市")
  	private java.lang.String lanId;
	
	/**
  	 * 市县
  	 */
	@ApiModelProperty(value = "市县")
  	private java.lang.String city;

	/**
	 * 销售点编码
	 */
	@ApiModelProperty(value = "销售点编码")
	private java.lang.String shopCode;

	/**
	 * 销售点名称
	 */
	@ApiModelProperty(value = "销售点名称")
	private java.lang.String shopName;

	/**
  	 * (商家)CRM组织ID
  	 */
	@ApiModelProperty(value = "(商家)CRM组织ID	")
  	private java.lang.String parCrmOrgId;

	/**
  	 * (商家)CRM组织编码
  	 */
	@ApiModelProperty(value = "(商家)CRM组织编码")
  	private java.lang.String parCrmOrgCode;

	/**
	 * (商家)CRM组织路径编码
	 */
	@ApiModelProperty(value = "(商家)CRM组织路径编码")
	private java.lang.String parCrmOrgPathCode;
	
	/**
  	 * (商家)联系人	
  	 */
	@ApiModelProperty(value = "(商家)联系人	")
  	private java.lang.String linkman;
	
	/**
  	 * (商家)联系电话
  	 */
	@ApiModelProperty(value = "(商家)联系电话")
  	private java.lang.String phoneNo;
	/**
	 * 旧平台客户编号
	 */
	@ApiModelProperty(value = "旧平台客户编号")
	private java.lang.String oldCustId;

	/**
	 * 是否已赋权
	 */
	@ApiModelProperty(value = "是否已赋权")
	private java.lang.String assignedFlg;

	@ApiModelProperty(value = "公司地址")
	private String address;

	@ApiModelProperty(value = "法人姓名")
	private String legalPerson;

	@ApiModelProperty(value = "经营品牌")
	private String manageBrand;

	@ApiModelProperty(value = "身份证")
	private java.lang.String certNumber;
}
