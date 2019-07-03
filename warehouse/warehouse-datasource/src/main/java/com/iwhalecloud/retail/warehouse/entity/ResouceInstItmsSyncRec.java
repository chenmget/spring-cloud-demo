package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ResouceInstTrack
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mkt_res_itms_sync_rec")
@ApiModel(value = "mkt_res_itms_sync_rec, 对应实体ResouceInstItmsSyncRec类")
public class ResouceInstItmsSyncRec implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mkt_res_itms_sync_rec";
  	private static final long serialVersionUID = 1L;


  	//属性 begin
  	/**
  	 * 营销资源推送标识
  	 */
	@TableId
	@ApiModelProperty(value = "营销资源推送标识")
  	private String mktResItmsSyncRecId;

  	/**
  	 * 推送日期
  	 */
	@ApiModelProperty(value = "推送日期")
  	private Date syncDate;

  	/**
  	 * 推送批次。
  	 */
	@ApiModelProperty(value = "推送批次。")
  	private String syncBatchId;

  	/**
  	 * syncFileName
  	 */
	@ApiModelProperty(value = "syncFileName")
  	private String syncFileName;

  	/**
  	 * 营销资源库存变动事件标识
  	 */
	@ApiModelProperty(value = "营销资源库存变动事件标识")
  	private String mktResEventId;

  	/**
  	 * 营销资源库存变动事件明细ID
  	 */
	@ApiModelProperty(value = "营销资源库存变动事件明细ID")
  	private String mktResChngEvtDetailId;

  	/**
  	 * 记录营销资源实例编码
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码")
  	private String mktResInstNbr;

  	/**
  	 * 品牌ID
  	 */
	@ApiModelProperty(value = "品牌ID")
  	private String brandId;

  	/**
  	 * 品牌名称
  	 */
	@ApiModelProperty(value = "品牌名称")
  	private String brandName;

  	/**
  	 * 产品型号
  	 */
	@ApiModelProperty(value = "产品型号")
  	private String unitType;

  	/**
  	 * 推送前串码所在本地网
  	 */
	@ApiModelProperty(value = "推送前串码所在本地网")
  	private String origLanId;

  	/**
  	 * 目标本地网
  	 */
	@ApiModelProperty(value = "目标本地网")
  	private String destLanId;

  	/**
  	 * 串码推送状态 0. 已推送待返回 1. 推送成功 -1. 推送失败
  	 */
	@ApiModelProperty(value = "串码推送状态 0. 已推送待返回 1. 推送成功 -1. 推送失败")
  	private String statusCd;

  	/**
  	 * 记录状态变更的时间
  	 */
	@ApiModelProperty(value = "记录状态变更的时间")
  	private String statusDate;

  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private String remark;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	private String createStaff;

	/**
	 * 记录首次创建的时间
	 */
	@ApiModelProperty(value = "记录首次创建的时间")
	private Date createDate;
	//属性 end

    /** 字段名称枚举. */
    public enum FieldNames {
		/** 营销资源推送标识. */
		mktResItmsSyncRecId("mktResItmsSyncRecId","MKT_RES_ITMS_SYNC_REC_ID"),
		
		/** 推送日期 */
		syncDate("syncDate","SYNC_DATE"),
		
		/** 推送批次 */
		syncBatchId("syncBatchId","SYNC_BATCH_ID"),
		
		/**  */
		syncFileName("syncFileName","SYNC_FILE_NAME"),
		
		/** 营销资源库存变动事件标识*/
		mktResEventId("mktResEventId","MKT_RES_EVENT_ID"),
		
		/** 营销资源库存变动事件明细ID */
		mktResChngEvtDetailId("mktResChngEvtDetailId","MKT_RES_CHNG_EVT_DETAIL_ID"),
		
		/** 记录营销资源实例编码. */
		mktResInstNbr("mktResInstNbr","MKT_RES_INST_NBR"),
		
		/** 品牌ID */
		brandId("brandId","BRAND_ID"),
		
		/** 品牌名称 */
		brandName("brandName","BRAND_NAME"),
		
		/** 产品型号*/
		unitType("unitType","UNIT_TYPE"),

		/** 推送前串码所在本地网*/
		origLanId("origLanId","ORIG_LAN_ID"),

		/** 目标本地网*/
		destLanId("destLanId","DEST_LAN_ID"),

		/** 串码推送状态 0. 已推送待返回 1. 推送成功 -1. 推送失败 */
		statusCd("statusCd","STATUS_CD"),

		/** 记录状态变更的时间. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 备注. */
		remark("remark","REMARK"),

		/** 创建人. */
		createStaff("createStaff","CREATE_STAFF"),

		/** 记录首次创建的时间. */
		createDate("createDate","CREATE_DATE");


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
