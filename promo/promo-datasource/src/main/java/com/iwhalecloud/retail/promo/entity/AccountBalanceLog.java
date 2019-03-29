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
 * AccountBalanceLog
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("ACCOUNT_BALANCE_LOG")
@ApiModel(value = "对应模型ACCOUNT_BALANCE_LOG, 对应实体AccountBalanceLog类")
@KeySequence(value="seq_retail_all_tables",clazz = String.class)

public class AccountBalanceLog implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ACCOUNT_BALANCE_LOG";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * 余额账本日志标识
  	 */
	@TableId
	@ApiModelProperty(value = "余额账本日志标识")
  	private String balanceLogId;
  	
  	/**
  	 * 账户余额标识
  	 */
	@ApiModelProperty(value = "账户余额标识")
  	private String accountBalanceId;
  	
  	/**
  	 * 为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。
  	 */
	@ApiModelProperty(value = "为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。")
  	private String operIncomeId;
  	
  	/**
  	 * 余额来源原金额
  	 */
	@ApiModelProperty(value = "余额来源原金额")
  	private Long srcAmount;
  	
  	/**
  	 * 支出操作流水
  	 */
	@ApiModelProperty(value = "支出操作流水")
  	private String operPayoutId;
  	
  	/**
  	 * 支出金额
  	 */
	@ApiModelProperty(value = "支出金额")
  	private Long payoutAmount;
  	
  	/**
  	 * 状态时间
  	 */
	@ApiModelProperty(value = "状态时间")
  	private java.util.Date statusDate;
  	
  	/**
  	 * 状态; 1000 有效，1100 失效
  	 */
	@ApiModelProperty(value = "状态; 1000 有效，1100 失效")
  	private String statusCd;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** 余额账本日志标识. */
		balanceLogId("balanceLogId","BALANCE_LOG_ID"),
		
		/** 账户余额标识. */
		accountBalanceId("accountBalanceId","ACCOUNT_BALANCE_ID"),
		
		/** 为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。. */
		operIncomeId("operIncomeId","OPER_INCOME_ID"),
		
		/** 余额来源原金额. */
		srcAmount("srcAmount","SRC_AMOUNT"),
		
		/** 支出操作流水. */
		operPayoutId("operPayoutId","OPER_PAYOUT_ID"),
		
		/** 支出金额. */
		payoutAmount("payoutAmount","PAYOUT_AMOUNT"),
		
		/** 状态时间. */
		statusDate("statusDate","STATUS_DATE"),
		
		/** 状态; 1000 有效，1100 失效. */
		statusCd("statusCd","STATUS_CD");

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
