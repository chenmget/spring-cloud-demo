package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Account
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("ACCOUNT")
@ApiModel(value = "对应模型ACCOUNT, 对应实体Account类")
@KeySequence(value="seq_retail_all_tables",clazz = String.class)
public class Account implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ACCOUNT";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 为每个账户生成的唯一编号，只具有逻辑上的含义，没有物理意义。每个账户标识生成之后，账户标识在整个服务提供有效期内保持不变。
  	 */
	@TableId
	@ApiModelProperty(value = "为每个账户生成的唯一编号，只具有逻辑上的含义，没有物理意义。每个账户标识生成之后，账户标识在整个服务提供有效期内保持不变。")
  	private String acctId;
  	
  	/**
  	 * 账户注册的名称
  	 */
	@ApiModelProperty(value = "账户注册的名称")
  	private String acctName;
  	
  	/**
  	 * 10余额账户、20返利账户、30价保账户
  	 */
	@ApiModelProperty(value = "10余额账户、20返利账户、30价保账户")
  	private String acctType;
  	
  	/**
  	 * 记录商家唯一标识，作为外键
  	 */
	@ApiModelProperty(value = "记录商家唯一标识，作为外键")
  	private String custId;
  	
  	/**
  	 * 生效时间
  	 */
	@ApiModelProperty(value = "生效时间")
  	private java.util.Date effDate;
  	
  	/**
  	 * 失效时间
  	 */
	@ApiModelProperty(value = "失效时间")
  	private java.util.Date expDate;
  	
  	/**
  	 * 状态; 1000 有效，1100 失效
  	 */
	@ApiModelProperty(value = "状态; 1000 有效，1100 失效")
  	private String statusCd;
  	
  	/**
  	 * 账户状态变更的时间。
  	 */
	@ApiModelProperty(value = "账户状态变更的时间。")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 记录创建的员工。
  	 */
	@ApiModelProperty(value = "记录创建的员工。")
  	private String createStaff;
  	
  	/**
  	 * 记录创建的时间。
  	 */
	@ApiModelProperty(value = "记录创建的时间。")
  	private java.util.Date createDate;
  	
  	/**
  	 * 记录修改的员工。
  	 */
	@ApiModelProperty(value = "记录修改的员工。")
  	private String updateStaff;
  	
  	/**
  	 * 记录修改的时间。
  	 */
	@ApiModelProperty(value = "记录修改的时间。")
  	private java.util.Date updateDate;
  	
  	/**
  	 * 记录备注信息。
  	 */
	@ApiModelProperty(value = "记录备注信息。")
  	private String remark;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 为每个账户生成的唯一编号，只具有逻辑上的含义，没有物理意义。每个账户标识生成之后，账户标识在整个服务提供有效期内保持不变。. */
		acctId("acctId","ACCT_ID"),
		
		/** 账户注册的名称. */
		acctName("acctName","ACCT_NAME"),
		
		/** 10余额账户、20返利账户、30价保账户. */
		acctType("acctType","ACCT_TYPE"),
		
		/** 记录商家唯一标识，作为外键. */
		custId("custId","CUST_ID"),
		
		/** 生效时间. */
		effDate("effDate","EFF_DATE"),
		
		/** 失效时间. */
		expDate("expDate","EXP_DATE"),
		
		/** 状态; 1000 有效，1100 失效. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 账户状态变更的时间。. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 记录创建的员工。. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** 记录创建的时间。. */
		createDate("createDate","CREATE_DATE"),
		
		/** 记录修改的员工。. */
		updateStaff("updateStaff","UPDATE_STAFF"),
		
		/** 记录修改的时间。. */
		updateDate("updateDate","UPDATE_DATE"),
		
		/** 记录备注信息。. */
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
