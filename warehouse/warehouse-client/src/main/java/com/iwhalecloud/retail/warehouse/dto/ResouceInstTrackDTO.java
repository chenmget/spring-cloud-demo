package com.iwhalecloud.retail.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * ResouceInstTrack
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型mkt_res_inst_track, 对应实体ResouceInstTrack类")
public class ResouceInstTrackDTO implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
  	private java.lang.String mktResInstNbr;
	
	/**
  	 * 固网终端需要CT码管理时，记录CT码
  	 */
	@ApiModelProperty(value = "固网终端需要CT码管理时，记录CT码")
  	private java.lang.String ctCode;
	
	/**
  	 * 营销资源标识，记录product_id
  	 */
	@ApiModelProperty(value = "营销资源标识，记录product_id")
  	private java.lang.String mktResId;
	
	/**
  	 * 01 交易 02 备机 03 集采
  	 */
	@ApiModelProperty(value = "01 交易 02 备机 03 集采")
  	private java.lang.String mktResInstType;
	
	/**
  	 * 营销资源实例的销售价格
  	 */
	@ApiModelProperty(value = "营销资源实例的销售价格")
  	private java.lang.String salesPrice;
	
	/**
  	 * 记录串码来源，01  厂商 02  供应商 03  零售商
  	 */
	@ApiModelProperty(value = "记录串码来源，01  厂商 02  供应商 03  零售商")
  	private java.lang.String sourceType;
	
	/**
  	 * 记录当前商家标识
  	 */
	@ApiModelProperty(value = "记录当前商家标识")
  	private java.lang.String merchantId;
	
	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private java.lang.String mktResStoreId;
	
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
  	 * 记录状态。LOVB=RES-0008
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=RES-0008")
  	private java.lang.String statusCd;
	
	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;
	
	/**
  	 * 记录CRM状态
  	 */
	@ApiModelProperty(value = "记录CRM状态")
  	private java.lang.String crmStatus;
	
	/**
  	 * 记录自注册状态
  	 */
	@ApiModelProperty(value = "记录自注册状态")
  	private java.lang.String selfRegStatus;
	
	/**
  	 * 是/否
  	 */
	@ApiModelProperty(value = "是/否")
  	private java.lang.String ifPreSubsidy;

	/**
	 * 是否省内直供
	 */
	@ApiModelProperty(value = "是否省内直供")
	private java.lang.String ifDirectSupply;

	/**
	 * 是否地包供货
	 */
	@ApiModelProperty(value = "是否地包供货")
	private java.lang.String ifGroundSupply;

	/**
	 * 是否绿色通道
	 */
	@ApiModelProperty(value = "是否绿色通道")
	private java.lang.String ifGreenChannel;

	/**
	 * typeId
	 */
	@ApiModelProperty(value = "typeId")
	private java.lang.String typeId;

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
