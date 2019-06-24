package com.iwhalecloud.retail.promo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * AccountBalanceItemPayed
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("ACCOUNT_BALANCE_ITEM_PAYED")
@ApiModel(value = "对应模型ACCOUNT_BALANCE_ITEM_PAYED, 对应实体AccountBalanceItemPayed类")
@KeySequence(value="seq_retail_all_tables",clazz = String.class)

public class AccountBalanceItemPayed implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ACCOUNT_BALANCE_ITEM_PAYED";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * acctBalanceItemPayedId
  	 */
	@TableId
	@ApiModelProperty(value = "acctBalanceItemPayedId")
  	private String acctBalanceItemPayedId;
  	
  	/**
  	 * 为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。
  	 */
	@ApiModelProperty(value = "为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。")
  	private String operPayoutId;
  	
  	/**
  	 * 余额支出对应的账目标识
  	 */
	@ApiModelProperty(value = "余额支出对应的账目标识")
  	private String acctItemId;
  	
  	/**
  	 * 操作后余额帐本的余额
  	 */
	@ApiModelProperty(value = "操作后余额帐本的余额")
  	private Long balance;
  	
  	/**
  	 * 状态; 1000 有效，1100 失效
  	 */
	@ApiModelProperty(value = "状态; 1000 有效，1100 失效")
  	private String statusCd;
  	
  	/**
  	 * 状态发生改变的时间
  	 */
	@ApiModelProperty(value = "状态发生改变的时间")
  	private java.util.Date statusDate;

	@ApiModelProperty(value = "账户Id")
	private String acctId;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** acctBalanceItemPayedId. */
		acctBalanceItemPayedId("acctBalanceItemPayedId","ACCT_BALANCE_ITEM_PAYED_ID"),
		
		/** 为每个余额流水生成的唯一编号，只具有逻辑上的含义，没有物理意义。. */
		operPayoutId("operPayoutId","OPER_PAYOUT_ID"),
		
		/** 余额支出对应的账目标识. */
		acctItemId("acctItemId","ACCT_ITEM_ID"),
		
		/** 操作后余额帐本的余额. */
		balance("balance","BALANCE"),
		
		/** 状态; 1000 有效，1100 失效. */
		statusCd("statusCd","STATUS_CD"),
		
		/** 状态发生改变的时间. */
		statusDate("statusDate","STATUS_DATE");

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
