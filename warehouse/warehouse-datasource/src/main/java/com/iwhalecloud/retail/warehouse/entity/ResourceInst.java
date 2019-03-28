package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ResourceInst
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mkt_res_inst")
@ApiModel(value = "对应模型mkt_res_inst, 对应实体ResourceInst类")
@KeySequence(value="seq_mkt_res_inst_id",clazz = String.class)

public class ResourceInst implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mkt_res_inst";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 营销资源实例的标识，主键
  	 */
	@TableId(type= IdType.INPUT)
	@ApiModelProperty(value = "营销资源实例的标识，主键")
  	private java.lang.String mktResInstId;
  	
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
  	 * 记录录入批次
  	 */
	@ApiModelProperty(value = "记录录入批次")
  	private java.lang.String mktResBatchId;
  	
  	/**
  	 * 营销资源标识，记录product_id
  	 */
	@ApiModelProperty(value = "营销资源标识，记录product_id")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private java.lang.String mktResStoreId;
  	
  	/**
  	 * 01 交易
            02 非交易
            03 备机
  	 */
	@ApiModelProperty(value = "01 交易 02 非交易 03 备机")
  	private java.lang.String mktResInstType;
  	
  	/**
  	 * 根资源实例的标识，资源拆分时，记录最初的资源实例标识，便于描述新实例的来源
  	 */
	@ApiModelProperty(value = "根资源实例的标识，资源拆分时，记录最初的资源实例标识，便于描述新实例的来源")
  	private java.lang.String rootInstId;
  	
  	/**
  	 * 营销资源实例的销售价格
  	 */
	@ApiModelProperty(value = "营销资源实例的销售价格")
  	private java.lang.Double salesPrice;
  	
  	/**
  	 * 入库类型，
	 * 1000交易入库、1001调拨入库、1002领用入库、1003绿色通道
  	 */
	@ApiModelProperty(value = "入库类型")
  	private java.lang.String storageType;
  	/**
  	 * 记录串码来源，
	 * 1厂商，2 供应商，3零售商
  	 */
	@ApiModelProperty(value = "记录串码来源,1厂商，2 供应商，3零售商")
  	private java.lang.String sourceType;

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
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
  	
  	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 记录状态。LOVB=RES-0008
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=RES-0008")
  	private java.lang.String statusCd;
  	
  	/**
  	 * 记录首次创建的员工标识。
  	 */
	@ApiModelProperty(value = "记录首次创建的员工标识。")
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
  	 * 记录营销资源实例的回收类型,LOVB=RES-C-0040
  	 */
	@ApiModelProperty(value = "记录营销资源实例的回收类型,LOVB=RES-C-0040")
  	private java.lang.String recycleType;
  	
  	/**
  	 * 交易入库的串码记录订单号
  	 */
	@ApiModelProperty(value = "交易入库的串码记录订单号")
  	private java.lang.String orderId;
  	
  	/**
  	 * 记录订单下单时间
  	 */
	@ApiModelProperty(value = "记录订单下单时间")
  	private java.util.Date createTime;
  	
  	/**
  	 * 记录供应商编码
  	 */
	@ApiModelProperty(value = "记录供应商编码")
  	private java.lang.String supplierCode;
  	
  	/**
  	 * 记录供应商名称
  	 */
	@ApiModelProperty(value = "记录供应商名称")
  	private java.lang.String supplierName;
  	
  	/**
  	 * 商家标识
  	 */
	@ApiModelProperty(value = "商家标识")
  	private java.lang.String merchantId;
  	
  	/**
  	 * 商家名称
  	 */
	@ApiModelProperty(value = "商家名称")
  	private java.lang.String merchantName;
  	
  	/**
  	 * 商家编码
  	 */
	@ApiModelProperty(value = "商家编码")
  	private java.lang.String merchantCode;

	/**
	 * 商家类型
	 */
	@ApiModelProperty(value = "商家类型")
	private java.lang.String merchantType;
  	
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
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 营销资源实例的标识，主键. */
		mktResInstId("mktResInstId","MKT_RES_INST_ID"),
		
		/** 记录营销资源实例编码。. */
		mktResInstNbr("mktResInstNbr","MKT_RES_INST_NBR"),
		
		/** 固网终端需要CT码管理时，记录CT码. */
		ctCode("ctCode","CT_CODE"),
		
		/** 记录录入批次. */
		mktResBatchId("mktResBatchId","MKT_RES_BATCH_ID"),
		
		/** 营销资源标识，记录product_id. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 营销资源仓库标识. */
		mktResStoreId("mktResStoreId","MKT_RES_STORE_ID"),
		
		/** 01 交易
            02 非交易
            03 备机. */
		mktResInstType("mktResInstType","MKT_RES_INST_TYPE"),
		
		/** 根资源实例的标识，资源拆分时，记录最初的资源实例标识，便于描述新实例的来源. */
		rootInstId("rootInstId","ROOT_INST_ID"),
		
		/** 营销资源实例的销售价格. */
		salesPrice("salesPrice","SALES_PRICE"),
		
		/** 记录串码来源，
            01  厂商
            02  绿色通道. */
		sourceType("sourceType","SOURCE_TYPE"),
		
		/** 记录本地网标识。. */
		lanId("lanId","LAN_ID"),
		
		/** 指向公共管理区域标识. */
		regionId("regionId","REGION_ID"),
		
		/** 备注. */
		remark("remark","REMARK"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录状态。LOVB=RES-0008. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 记录首次创建的员工标识。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录每次修改的员工标识。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录每次修改的时间。. */
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 记录营销资源实例的回收类型,LOVB=RES-C-0040. */
		recycleType("recycleType","RECYCLE_TYPE"),
		
		/** 交易入库的串码记录订单号. */
		orderId("orderId","ORDER_ID"),
		
		/** 记录订单下单时间. */
		createTime("createTime","CREATE_TIME"),
		
		/** 记录供应商编码. */
		supplierCode("supplierCode","SUPPLIER_CODE"),
		
		/** 记录供应商名称. */
		supplierName("supplierName","SUPPLIER_NAME"),
		
		/** 资源店中商ID. */
		partnerId("merchantId","MERCHANT_ID"),
		
		/** 记录零售商名称. */
		partnerName("merchantName","MERCHANT_NAME"),
		
		/** 记录零售商编码. */
		partnerCode("merchantCode","MERCHANT_CODE"),
		/** 商家类型 */
		merchantType("merchantType","MERCHANT_TYPE"),

		/** 记录CRM状态. */
		crmStatus("crmStatus","CRM_STATUS"),
		
		/** 记录自注册状态. */
		selfRegStatus("selfRegStatus","SELF_REG_STATUS");

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
