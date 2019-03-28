package com.iwhalecloud.retail.rights.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 记录营销资源适用地区。
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("MKT_RES_REGION")
@ApiModel(value = "对应模型MKT_RES_REGION, 对应实体MktResRegion类")
@KeySequence(value="seq_mkt_res_region_mkt_res_id",clazz = String.class)
public class MktResRegion implements Serializable {
    /**表名常量*/
    public static final String TNAME = "MKT_RES_REGION";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 记录营销资源适用地区标识。
  	 */
	@TableId
	@ApiModelProperty(value = "记录营销资源适用地区标识。")
  	private java.lang.Long mktResRegionId;
  	
  	/**
  	 * 营销资源标识
  	 */
	@ApiModelProperty(value = "营销资源标识")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 适用区域标识
  	 */
	@ApiModelProperty(value = "适用区域标识")
  	private java.lang.Long applyRegionId;
  	
  	/**
  	 * 记录营销资源的成本价格。
  	 */
	@ApiModelProperty(value = "记录营销资源的成本价格。")
  	private java.lang.Long costPrice;
  	
  	/**
  	 * 记录建议的零售价格。
  	 */
	@ApiModelProperty(value = "记录建议的零售价格。")
  	private java.lang.Long salesPrice;
  	
  	/**
  	 * 记录建议的结算价格。
  	 */
	@ApiModelProperty(value = "记录建议的结算价格。")
  	private java.lang.Long clearingPrice;
  	
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
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 记录营销资源适用地区标识。. */
		mktResRegionId("mktResRegionId","MKT_RES_REGION_ID"),
		
		/** 营销资源标识. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 适用区域标识. */
		applyRegionId("applyRegionId","APPLY_REGION_ID"),
		
		/** 记录营销资源的成本价格。. */
		costPrice("costPrice","COST_PRICE"),
		
		/** 记录建议的零售价格。. */
		salesPrice("salesPrice","SALES_PRICE"),
		
		/** 记录建议的结算价格。. */
		clearingPrice("clearingPrice","CLEARING_PRICE"),
		
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
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 备注. */
		remark("remark","REMARK");

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
