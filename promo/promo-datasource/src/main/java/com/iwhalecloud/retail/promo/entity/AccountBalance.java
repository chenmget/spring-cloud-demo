package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * AccountBalance
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("ACCOUNT_BALANCE")
@ApiModel(value = "对应模型ACCOUNT_BALANCE, 对应实体AccountBalance类")

public class AccountBalance implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ACCOUNT_BALANCE";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 账户余额标识
  	 */
	@TableId(type = IdType.INPUT)
	@ApiModelProperty(value = "账户余额标识")
  	private String accountBalanceId;
  	
  	/**
  	 * 为每个账户生成的唯一编号，只具有逻辑上的含义，没有物理意义。每个账户标识生成之后，账户标识在整个服务提供有效期内保持不变。
  	 */
	@ApiModelProperty(value = "为每个账户生成的唯一编号，只具有逻辑上的含义，没有物理意义。每个账户标识生成之后，账户标识在整个服务提供有效期内保持不变。")
  	private String acctId;
  	
  	/**
  	 * 记录商家唯一标识
  	 */
	@ApiModelProperty(value = "记录商家唯一标识")
  	private String custId;
  	
  	/**
  	 * 余额帐本所属的余额类型。
  	 */
	@ApiModelProperty(value = "余额帐本所属的余额类型。")
  	private String balanceTypeId;
  	
  	/**
  	 * 账户可用余额
  	 */
	@ApiModelProperty(value = "账户可用余额")
  	private Long amount;
  	
  	/**
  	 * 未生效余额
  	 */
	@ApiModelProperty(value = "未生效余额")
  	private Long uneffectiveAmount;
  	
  	/**
  	 * 失效余额
  	 */
	@ApiModelProperty(value = "失效余额")
  	private Long invalidAmount;
  	
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
  	
  	/**
  	 * 限额类型：1 总额封顶，2  余额对象封顶 ，3   帐户封顶，4  服务封顶，5  消费比例封顶
  	 */
	@ApiModelProperty(value = "限额类型：1 总额封顶，2  余额对象封顶 ，3   帐户封顶，4  服务封顶，5  消费比例封顶")
  	private String cycleType;
  	
  	/**
  	 * 余额账本可以使用的金额封顶，限额类型是5时，填入封顶比例
  	 */
	@ApiModelProperty(value = "余额账本可以使用的金额封顶，限额类型是5时，填入封顶比例")
  	private Long cycleUpper;
  	
  	/**
  	 * 使用的最低额。
  	 */
	@ApiModelProperty(value = "使用的最低额。")
  	private Long cycleLower;
  	
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
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 账户余额标识. */
		accountBalanceId("accountBalanceId","ACCOUNT_BALANCE_ID"),
		
		/** 为每个账户生成的唯一编号，只具有逻辑上的含义，没有物理意义。每个账户标识生成之后，账户标识在整个服务提供有效期内保持不变。. */
		acctId("acctId","ACCT_ID"),
		
		/** 记录商家唯一标识. */
		custId("custId","CUST_ID"),
		
		/** 余额帐本所属的余额类型。. */
		balanceTypeId("balanceTypeId","BALANCE_TYPE_ID"),
		
		/** 账户可用余额. */
		amount("amount","AMOUNT"),
		
		/** 未生效余额. */
		uneffectiveAmount("uneffectiveAmount","UNEFFECTIVE_AMOUNT"),
		
		/** 失效余额. */
		invalidAmount("invalidAmount","INVALID_AMOUNT"),
		
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
		remark("remark","REMARK"),
		
		/** 限额类型：1 总额封顶，2  余额对象封顶 ，3   帐户封顶，4  服务封顶，5  消费比例封顶. */
		cycleType("cycleType","CYCLE_TYPE"),
		
		/** 余额账本可以使用的金额封顶，限额类型是5时，填入封顶比例. */
		cycleUpper("cycleUpper","CYCLE_UPPER"),
		
		/** 使用的最低额。. */
		cycleLower("cycleLower","CYCLE_LOWER"),
		
		/** 生效时间. */
		effDate("effDate","EFF_DATE"),
		
		/** 失效时间. */
		expDate("expDate","EXP_DATE");

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
