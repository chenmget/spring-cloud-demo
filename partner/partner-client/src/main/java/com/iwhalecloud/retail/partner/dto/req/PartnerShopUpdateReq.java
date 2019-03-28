package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * PartnerShop
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "厅店信息修改")
public class PartnerShopUpdateReq implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;

	//属性 begin
	/**
	 * 主键ID  (和导过来的code同值)
	 */
	@NotEmpty(message = "厅店ID不能为空")
	@ApiModelProperty(value = "主键ID  (和导过来的code同值)")
	private java.lang.String partnerShopId;

	/**
	 * 网点编码
	 */
	@ApiModelProperty(value = "网点编码")
	private java.lang.String code;

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
	 * 网点类型  网厅分销商专区、电视直销、电话直销、互联网代理、互联网分销商、电子商务平台自营商铺、电子商务平台分销商、社会自助终端
	 */
	@ApiModelProperty(value = "网点类型  网厅分销商专区、电视直销、电话直销、互联网代理、互联网分销商、电子商务平台自营商铺、电子商务平台分销商、社会自助终端")
	private java.lang.String netType;

	/**
	 * 区域标识
	 */
	@ApiModelProperty(value = "区域标识")
	private java.lang.String areaCode;

	/**
	 * 渠道类型  默认社会电子渠道
	 */
	@ApiModelProperty(value = "渠道类型  默认社会电子渠道")
	private java.lang.String channelType;

	/**
	 * 1000有效  1100无效  1001主动暂停 1002异常暂停 1101终止  1102退出  1200未生效  1300已归档      0申请 1-正常 2-冻结
	 */
	@ApiModelProperty(value = "1000有效  1100无效  1001主动暂停 1002异常暂停 1101终止  1102退出  1200未生效  1300已归档      0申请 1-正常 2-冻结")
	private java.lang.String state;

	/**
	 * shopDefaultImage
	 */
	@ApiModelProperty(value = "shopDefaultImage")
	private java.lang.String shopDefaultImage;

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
	 * 店铺面积
	 */
	@ApiModelProperty(value = "店铺面积")
	private java.lang.String shopAcreage;

	/**
	 * 所在省
	 */
	@ApiModelProperty(value = "所在省")
	private java.lang.String province;

	/**
	 * 所在市
	 */
	@ApiModelProperty(value = "所在市")
	private java.lang.String city;

	/**
	 * 所在县
	 */
	@ApiModelProperty(value = "所在县")
	private java.lang.String country;

	/**
	 * 网点地理区域编码
	 */
	@ApiModelProperty(value = "网点地理区域编码")
	private java.lang.String commonRegionId;

	/**
	 * 网点地理区域名称
	 */
	@ApiModelProperty(value = "网点地理区域名称")
	private java.lang.String commonRegionName;

	/**
	 * 归属经营主体编码
	 */
	@ApiModelProperty(value = "归属经营主体编码")
	private java.lang.String operatorsNbr;

	/**
	 * 归属经营主体名称
	 */
	@ApiModelProperty(value = "归属经营主体名称")
	private java.lang.String operatorsName;

	/**
	 * 归属经验场所
	 */
	@ApiModelProperty(value = "归属经验场所")
	private java.lang.String busiStore;

	/**
	 * 省内网点大类
	 */
	@ApiModelProperty(value = "省内网点大类")
	private java.lang.String provSupChannelType;

	/**
	 * 本地网
	 */
	@ApiModelProperty(value = "本地网")
	private java.lang.String lanId;

	/**
	 * 所属区域
	 */
	@ApiModelProperty(value = "所属区域")
	private java.lang.String regionId;

	/**
	 * 组织名
	 */
	@ApiModelProperty(value = "组织名")
	private java.lang.String orgName;

	/**
	 * 统一组织编码
	 */
	@ApiModelProperty(value = "统一组织编码")
	private java.lang.String unionOrgCode;

	/**
	 * 组织类型id
	 */
	@ApiModelProperty(value = "组织类型id")
	private java.lang.String orgTypeId;

	/**
	 * 渠道单元地址
	 */
	@ApiModelProperty(value = "渠道单元地址")
	private java.lang.String channelUnitAddr;

	/**
	 * 业务范围编码
	 */
	@ApiModelProperty(value = "业务范围编码")
	private java.lang.String businessScope;

}
