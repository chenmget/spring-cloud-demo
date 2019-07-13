package com.iwhalecloud.retail.warehouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * MktResItmsReturnRec
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("mkt_res_itms_return_rec")
@ApiModel(value = "对应模型mkt_res_itms_return_rec, 对应实体MktResItmsReturnRec类")
public class MktResItmsReturnRec implements Serializable {
    /**表名常量*/
    public static final String TNAME = "mkt_res_itms_return_rec";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 营销资源推送标识
  	 */
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "营销资源推送标识")
  	private java.lang.String mktResItmsReturnRecId;
  	
  	/**
  	 * 推送的文件名
  	 */
	@ApiModelProperty(value = "推送的文件名")
  	private java.lang.String returnFileName;
  	
  	/**
  	 * 记录营销资源实例编码。
  	 */
	@ApiModelProperty(value = "记录营销资源实例编码。")
  	private java.lang.String mktResInstNbr;
  	
  	/**
  	 * 串码推送结果 1. 推送成功 0. 推送失败
  	 */
	@ApiModelProperty(value = "串码推送结果 1. 推送成功 0. 推送失败")
  	private java.lang.String statusCd;
  	
  	/**
  	 * 备注
  	 */
	@ApiModelProperty(value = "备注")
  	private java.lang.String remark;
  	
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
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 营销资源推送标识. */
		mktResItmsReturnRecId("mktResItmsReturnRecId","mkt_res_itms_return_rec_id"),
		
		/** 推送的文件名. */
		returnFileName("returnFileName","return_file_name"),
		
		/** 记录营销资源实例编码。. */
		mktResInstNbr("mktResInstNbr","mkt_res_inst_nbr"),
		
		/** 串码推送结果 1. 推送成功 0. 推送失败. */
		statusCd("statusCd","status_cd"),
		
		/** 备注. */
		remark("remark","remark"),
		
		/** 记录首次创建的员工标识。. */
		createStaff("createStaff","create_staff"),
		
		/** 记录首次创建的时间。. */
		createDate("createDate","create_date");

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
