package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ResourceInstStore
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mkt_res_inst_store")
@ApiModel(value = "对应模型mkt_res_inst_store, 对应实体ResourceInstStore类")
@KeySequence(value="seq_mkt_res_inst_store_id",clazz = String.class)

public class ResourceInstStore implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mkt_res_inst_store";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 营销资源实例的标识，主键
  	 */
	@TableId
	@ApiModelProperty(value = "营销资源实例的标识，主键")
  	private java.lang.String mktResInstStoreId;
  	
  	/**
  	 * 营销资源标识
  	 */
	@ApiModelProperty(value = "营销资源标识")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 营销资源仓库标识
  	 */
	@ApiModelProperty(value = "营销资源仓库标识")
  	private java.lang.String mktResStoreId;
  	
  	/**
  	 * 记录营销资源实例的数量
  	 */
	@ApiModelProperty(value = "记录营销资源实例的数量")
  	private java.lang.Long quantity;
  	
  	/**
  	 * 记录营销资源实例的数量单位.LOVB=RES-C-0001
  	 */
	@ApiModelProperty(value = "记录营销资源实例的数量单位.LOVB=RES-C-0001")
  	private java.lang.String unit;
  	
  	/**
  	 * 记录营销资源实例的剩余数量，针对无序资源。
  	 */
	@ApiModelProperty(value = "记录营销资源实例的剩余数量，针对无序资源。")
  	private java.lang.Long restQuantity;
  	
  	/**
  	 * 记录营销资源实例的在途数量
  	 */
	@ApiModelProperty(value = "记录营销资源实例的在途数量")
  	private java.lang.Long onwayQuantity;
  	
  	/**
  	 * 记录营销资源实例的损坏数量
  	 */
	@ApiModelProperty(value = "记录营销资源实例的损坏数量")
  	private java.lang.Long ruinQuantity;

	/**
	 * 商家标识
	 */
	@ApiModelProperty(value = "商家标识")
	private String merchantId;
  	
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
  	 * 记录状态。LOVB=PUB-C-0001。
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
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
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 营销资源实例的标识，主键. */
		mktResInstStoreId("mktResInstStoreId","MKT_RES_INST_STORE_ID"),
		
		/** 营销资源标识. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 营销资源仓库标识. */
		mktResStoreId("mktResStoreId","MKT_RES_STORE_ID"),
		
		/** 记录营销资源实例的数量. */
		quantity("quantity","QUANTITY"),
		
		/** 记录营销资源实例的数量单位.LOVB=RES-C-0001. */
		unit("unit","UNIT"),
		
		/** 记录营销资源实例的剩余数量，针对无序资源。. */
		restQuantity("restQuantity","REST_QUANTITY"),
		
		/** 记录营销资源实例的在途数量. */
		onwayQuantity("onwayQuantity","ONWAY_QUANTITY"),
		
		/** 记录营销资源实例的损坏数量. */
		ruinQuantity("ruinQuantity","RUIN_QUANTITY"),
		
		/** 商家标识 */
		merchantId("merchantId","MERCHANT_ID"),
		
		/** 记录本地网标识。. */
		lanId("lanId","LAN_ID"),
		
		/** 指向公共管理区域标识. */
		regionId("regionId","REGION_ID"),
		
		/** 备注. */
		remark("remark","REMARK"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录状态。LOVB=PUB-C-0001。. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 记录首次创建的员工标识。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录每次修改的员工标识。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录每次修改的时间。. */
		updateDate("updateDate","UPDATE_DATE");

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
