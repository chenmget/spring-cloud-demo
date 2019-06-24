package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ResourceReqDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_req_detail, 对应实体ResourceReqDetail类")
public class ResourceReqDetailDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 记录营销资源申请单明细标识
  	 */
	@ApiModelProperty(value = "记录营销资源申请单明细标识")
  	private java.lang.String mktResReqDetailId;
	
	/**
  	 * 营销资源申请单项标识
  	 */
	@ApiModelProperty(value = "营销资源申请单项标识")
  	private java.lang.String mktResReqItemId;

	/**
	 * 营销资源仓库标识
	 */
	@ApiModelProperty(value = "营销资源仓库标识")
	private String mktResStoreId;

	@ApiModelProperty(value = "目标营销资源仓库")
	private String destStoreId;

	@ApiModelProperty(value = "营销资源标识，记录product_id")
	private String mktResId;

	/**
	 * 记录本地网标识。
	 */
	@ApiModelProperty(value = "记录本地网标识。")
	private java.lang.String lanId;

	/**
	 * 指向公共管理区域标识
	 */
	@ApiModelProperty(value = "指向公共管理区域标识")
	private java.lang.String regionId;

	/**
	 * 记录营销资源实例编码。
	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
	private java.lang.String mktResInstNbr;
	/**
  	 * 营销资源实例ID的标识
  	 */
	@ApiModelProperty(value = "营销资源实例ID的标识")
  	private java.lang.String mktResInstId;
	
	/**
  	 * 发货时间，通知发货的时候需要记录发货时间，根据发货时间，如果超过设置的时间，会自动确认收货。
  	 */
	@ApiModelProperty(value = "发货时间，通知发货的时候需要记录发货时间，根据发货时间，如果超过设置的时间，会自动确认收货。")
  	private java.util.Date dispDate;
	
	/**
  	 * 到货时间，确认收货的时候记录到货时间
  	 */
	@ApiModelProperty(value = "到货时间，确认收货的时候记录到货时间")
  	private java.util.Date arriveDate;
	
	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;
	
	/**
  	 * 记录状态。LOVB=PUB-C-0001。
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
  	private java.lang.String statusCd;
	
	/**
  	 * 记录首次创建的用户标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的用户标识。")
  	private java.lang.String createStaff;
	
	/**
  	 * 记录首次创建的时间。
  	 */
	@ApiModelProperty(value = "记录首次创建的时间。")
  	private java.util.Date createDate;
	
	/**
  	 * 记录每次修改的员工标识。
  	 */
	@ApiModelProperty(value = "记录每次修改的员工标识。")
  	private java.lang.String updateStaff;
	
	/**
  	 * 记录每次修改的时间。
  	 */
	@ApiModelProperty(value = "记录每次修改的时间。")
  	private java.util.Date updateDate;
	
	/**
  	 * 记录营销资源实例的数量
  	 */
	@ApiModelProperty(value = "记录营销资源实例的数量")
  	private java.lang.String quantity;
	
	/**
  	 * 记录营销资源实例的数量单位，LOVB=RES-C-0011
  	 */
	@ApiModelProperty(value = "记录营销资源实例的数量单位，LOVB=RES-C-0011")
  	private java.lang.String unit;
	
	/**
  	 * 记录出入库类型,LOVB=RES-C-0012
  	 */
	@ApiModelProperty(value = "记录出入库类型,LOVB=RES-C-0012")
  	private java.lang.String chngType;
	/**
  	 * ct码
  	 */
	@ApiModelProperty(value = "ctCode")
  	private java.lang.String ctCode;
	/**
  	 * 串码类型
  	 */
	@ApiModelProperty(value = "mktResInstType")
  	private java.lang.String mktResInstType;

	/**
	 * SN码
	 */
	@ApiModelProperty(value = "SN码")
	private java.lang.String snCode;

	/**
	 * 网络终端（包含光猫、机顶盒、融合终端）记录MAC码
	 */
	@ApiModelProperty(value = "macCode")
	private java.lang.String macCode;

}
