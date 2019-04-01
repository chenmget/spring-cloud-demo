package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * ResouceInstTrackDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mkt_res_inst_track_detail")
@KeySequence(value="seq_mkt_res_inst_track_detail_id",clazz = String.class)
@ApiModel(value = "对应模型mkt_res_inst_track_detail, 对应实体ResouceInstTrackDetail类")
public class ResouceInstTrackDetail implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mkt_res_inst_track_detail";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 营销资源实例的标识，主键
  	 */
	@TableId
	@ApiModelProperty(value = "营销资源实例的标识，主键")
  	private java.lang.String trackDetailId;
  	
  	/**
  	 * 入库类型：交易入库、调拨入库、领用入库、零售商绿色通道、供应商自有资源、厂商录入
  	 */
	@ApiModelProperty(value = "入库类型：交易入库、调拨入库、领用入库、零售商绿色通道、供应商自有资源、厂商录入")
  	private java.lang.String storageType;
  	
  	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
  	private java.lang.String mktResInstNbr;
  	
  	/**
  	 * 源商家ID，没源商家时该字段为空
  	 */
	@ApiModelProperty(value = "源商家ID，没源商家时该字段为空")
  	private java.lang.String sourceMerchantId;
  	
  	/**
  	 * 目标商家ID
  	 */
	@ApiModelProperty(value = "目标商家ID")
  	private java.lang.String targetMerchantId;
  	
  	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private java.lang.String sourceStoreId;
  	
  	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private java.lang.String targetStoreId;
  	
  	/**
  	 * 记录出库时间
  	 */
	@ApiModelProperty(value = "记录出库时间")
  	private java.util.Date outTime;
  	
  	/**
  	 * 记录入库时间
  	 */
	@ApiModelProperty(value = "记录入库时间")
  	private java.util.Date inTime;

	/**
	 * 记录状态。LOVB=RES-C-0008
	 */
	@ApiModelProperty(value = "记录状态。LOVB=RES-C-0008")
	private java.lang.String statusCd;

	/**
	 * 记录修改变更时间
	 */
	@ApiModelProperty(value = "记录变更时间")
	private java.util.Date updateDate;
  	
  	/**
  	 * 记录源本地网标识。
  	 */
	@ApiModelProperty(value = "记录源本地网标识。")
  	private java.lang.String sourceLanId;
  	
  	/**
  	 * 记录目标本地网标识。
  	 */
	@ApiModelProperty(value = "记录目标本地网标识。")
  	private java.lang.String targetLanId;
  	
  	/**
  	 * 记录源本地网标识。
  	 */
	@ApiModelProperty(value = "记录源本地网标识。")
  	private java.lang.String sourceRegionId;
  	
  	/**
  	 * 记录目标本地网标识。
  	 */
	@ApiModelProperty(value = "记录目标本地网标识。")
  	private java.lang.String targetRegionId;
  	
  	/**
  	 * orderId
  	 */
	@ApiModelProperty(value = "orderId")
  	private java.lang.String orderId;
  	
  	/**
  	 * orderTime
  	 */
	@ApiModelProperty(value = "orderTime")
  	private java.util.Date orderTime;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 营销资源实例的标识，主键. */
		trackDetailId("trackDetailId","TRACK_DETAIL_ID"),
		
		/** 入库类型：交易入库、调拨入库、领用入库、零售商绿色通道、供应商自有资源、厂商录入. */
		storageType("storageType","STORAGE_TYPE"),
		
		/** 记录营销资源实例编码。. */
		mktResInstNbr("mktResInstNbr","MKT_RES_INST_NBR"),
		
		/** 源商家ID，没源商家时该字段为空. */
		sourceMerchantId("sourceMerchantId","SOURCE_MERCHANT_ID"),
		
		/** 目标商家ID. */
		targetMerchantId("targetMerchantId","TARGET_MERCHANT_ID"),
		
		/** 营销资源仓库标识. */
		sourceStoreId("sourceStoreId","SOURCE_STORE_ID"),
		
		/** 营销资源仓库标识. */
		targetStoreId("targetStoreId","TARGET_STORE_ID"),
		
		/** 记录出库时间. */
		outTime("outTime","OUT_TIME"),
		
		/** 记录入库时间. */
		inTime("inTime","IN_TIME"),
		
		/** 记录源本地网标识。. */
		sourceLanId("sourceLanId","SOURCE_LAN_ID"),
		
		/** 记录目标本地网标识。. */
		targetLanId("targetLanId","TARGET_LAN_ID"),
		
		/** 记录源本地网标识。. */
		sourceRegionId("sourceRegionId","SOURCE_REGION_ID"),
		
		/** 记录目标本地网标识。. */
		targetRegionId("targetRegionId","TARGET_REGION_ID"),
		
		/** orderId. */
		orderId("orderId","ORDER_ID"),
		
		/** orderTime. */
		orderTime("orderTime","ORDER_TIME");

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
