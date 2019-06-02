package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * ResourceReqDetail
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mkt_res_req_detail")
@ApiModel(value = "对应模型mkt_res_req_detail, 对应实体ResourceReqDetail类")
@KeySequence(value="seq_mkt_res_req_detail_id",clazz = String.class)

public class ResourceReqDetail implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mkt_res_req_detail";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 记录营销资源申请单明细标识
  	 */
	@TableId
	@ApiModelProperty(value = "记录营销资源申请单明细标识")
  	private java.lang.String mktResReqDetailId;
  	
  	/**
  	 * 营销资源申请单项标识
  	 */
	@ApiModelProperty(value = "营销资源申请单项标识")
  	private java.lang.String mktResReqItemId;
  	
  	/**
  	 * 营销资源实例ID的标识
  	 */
	@ApiModelProperty(value = "营销资源实例ID的标识")
  	private java.lang.String mktResInstId;

	/**
	 * 记录营销资源实例编码。
	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
	private java.lang.String mktResInstNbr;


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
  	private java.lang.Long quantity;
  	
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
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;

	/**
	 * 抽检标识
	 */
	@ApiModelProperty(value = "抽检标识")
	private String isInspection;

	/**
	 * CT码
	 */
	@ApiModelProperty(value = "CT码")
	private String ctCode;

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


	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 记录营销资源申请单明细标识. */
		mktResReqDetailId("mktResReqDetailId","MKT_RES_REQ_DETAIL_ID"),
		
		/** 营销资源申请单项标识. */
		mktResReqItemId("mktResReqItemId","MKT_RES_REQ_ITEM_ID"),
		
		/** 营销资源实例ID的标识. */
		mktResInstId("mktResInstId","MKT_RES_INST_ID"),
		
		/** 发货时间，通知发货的时候需要记录发货时间，根据发货时间，如果超过设置的时间，会自动确认收货。. */
		dispDate("dispDate","DISP_DATE"),
		
		/** 到货时间，确认收货的时候记录到货时间. */
		arriveDate("arriveDate","ARRIVE_DATE"),
		
		/** 记录状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录状态。LOVB=PUB-C-0001。. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 记录首次创建的用户标识。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录每次修改的员工标识。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录每次修改的时间。. */
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 记录营销资源实例的数量. */
		quantity("quantity","QUANTITY"),
		
		/** 记录营销资源实例的数量单位，LOVB=RES-C-0011. */
		unit("unit","UNIT"),
		
		/** 记录出入库类型,LOVB=RES-C-0012. */
		chngType("chngType","CHNG_TYPE"),
		
		/** 抽检标识. */
		isInspection("isInspection","IS_INSPECTION"),

		/** CT码. */
		ctCode("ctCode","CT_CODE"),

		/** 备注. */
		remark("remark","REMARK"),

		/**SN码. */
		snCode("snCode","sn_code"),

		/** 网络终端（包含光猫、机顶盒、融合终端）记录MAC码. */
		macCode("macCode","mac_code");

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
