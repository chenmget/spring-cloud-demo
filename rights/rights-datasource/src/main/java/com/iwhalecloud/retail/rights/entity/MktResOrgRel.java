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
 * 记录营销资源与组织的关系，包括商品类营销资源在商铺或实体营业厅进行销售的关系，优惠券类营销资源在商铺或实体营业厅进行兑换使用的关系。
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("MKT_RES_ORG_REL")
@ApiModel(value = "对应模型MKT_RES_ORG_REL, 对应实体MktResOrgRel类")
@KeySequence(value="seq_mkt_res_org_rel",clazz = String.class)
public class MktResOrgRel implements Serializable {
    /**表名常量*/
    public static final String TNAME = "MKT_RES_ORG_REL";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 营销资源组织关系标识
  	 */
	@TableId
	@ApiModelProperty(value = "营销资源组织关系标识")
  	private java.lang.Long mktResOrgRelId;
  	
  	/**
  	 * 营销资源标识
  	 */
	@ApiModelProperty(value = "营销资源标识")
  	private java.lang.String mktResId;
  	
  	/**
  	 * 组织标识，可以是商铺或者实体渠道等
  	 */
	@ApiModelProperty(value = "组织标识，可以是商铺或者实体渠道等")
  	private java.lang.Long orgId;
  	
  	/**
  	 * 关系类型,LOVB=RES-C-0043
销售关系：记录商品等营销资源在商铺或实体营业厅进行售卖的关系
使用关系：记录优惠券等营销资源在商铺或实体营业厅进行兑换使用的关系
  	 */
	@ApiModelProperty(value = "关系类型,LOVB=RES-C-0043")
  	private java.lang.String relType;
  	
  	/**
  	 * 记录状态。LOVB=PUB-C-0001。
  	 */
	@ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
  	private java.lang.String statusCd;
  	
  	/**
  	 * 记录状态变更的时间。
  	 */
	@ApiModelProperty(value = "记录状态变更的时间。")
  	private java.util.Date statusDate;
  	
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
		/** 营销资源组织关系标识. */
		mktResOrgRelId("mktResOrgRelId","MKT_RES_ORG_REL_ID"),
		
		/** 营销资源标识. */
		mktResId("mktResId","MKT_RES_ID"),
		
		/** 组织标识，可以是商铺或者实体渠道等. */
		orgId("orgId","ORG_ID"),
		
		/** 关系类型,LOVB=RES-C-0043
销售关系：记录商品等营销资源在商铺或实体营业厅进行售卖的关系
使用关系：记录优惠券等营销资源在商铺或实体营业厅进行兑换使用的关系. */
		relType("relType","REL_TYPE"),
		
		/** 记录状态。LOVB=PUB-C-0001。. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
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
