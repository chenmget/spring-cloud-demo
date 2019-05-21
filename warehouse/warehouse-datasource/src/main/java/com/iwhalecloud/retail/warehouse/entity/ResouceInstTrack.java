package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ResouceInstTrack
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mkt_res_inst_track")
@ApiModel(value = "对应模型mkt_res_inst_track, 对应实体ResouceInstTrack类")
public class ResouceInstTrack implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mkt_res_inst_track";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 记录营销资源实例编码。
  	 */
	@TableId
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
	 * 产品类型
	 */
	@ApiModelProperty(value = "产品类型")
	private java.lang.String typeId;

	//属性 end
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 记录营销资源实例编码。. */
		mktResInstNbr("mktResInstNbr","MKT_RES_INST_NBR"),
		
		/** 固网终端需要CT码管理时，记录CT码. */
		ctCode("ctCode","CT_CODE"),
		
		/** 营销资源标识，记录product_id. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 01 交易 02 备机 03 集采. */
		mktResInstType("mktResInstType","MKT_RES_INST_TYPE"),
		
		/** 营销资源实例的销售价格. */
		salesPrice("salesPrice","SALES_PRICE"),
		
		/** 记录串码来源，01  厂商 02  供应商 03  零售商. */
		sourceType("sourceType","SOURCE_TYPE"),
		
		/** 记录当前商家标识. */
		merchantId("merchantId","MERCHANT_ID"),
		
		/** 营销资源仓库标识. */
		mktResStoreId("mktResStoreId","MKT_RES_STORE_ID"),
		
		/** 记录本地网标识。. */
		lanId("lanId","LAN_ID"),
		
		/** 指向公共管理区域标识. */
		regionId("regionId","REGION_ID"),
		
		/** 记录状态。LOVB=RES-0008. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录CRM状态. */
		crmStatus("crmStatus","CRM_STATUS"),
		
		/** 记录自注册状态. */
		selfRegStatus("selfRegStatus","SELF_REG_STATUS"),
		
		/** 是/否. */
		ifPreSubsidy("ifPreSubsidy","IF_PRE_SUBSIDY"),

		/** 是否省内直供. */
		ifDirectSuppLy("ifDirectSuppLy","IF_DIRECT_SUPPLY"),

		/** 是否地包供货. */
		ifGroundSupply("ifGroundSupply","IF_GROUND_SUPPLY"),

		/**是否绿色通道. */
		ifGreenChannel("ifGreenChannel","IF_GREEN_CHANNEL"),

		/**产品类型. */
		typeId("typeId","TYPE_ID");

		private String fieldName;
		private String tableFieldName;
		FieldNames(String fieldName, String tableFieldName){
			this.fieldName = fieldName;
			this.tableFieldName = tableFieldName;
		}

		public String getFieldName() {
			return fieldName;
		}

		public String getTableFieldName() {
			return tableFieldName;
		}
	}

}
