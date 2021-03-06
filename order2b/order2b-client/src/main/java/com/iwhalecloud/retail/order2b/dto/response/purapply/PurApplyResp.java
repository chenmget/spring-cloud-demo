package com.iwhalecloud.retail.order2b.dto.response.purapply;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class PurApplyResp extends PageVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applyId;
	private String applyCode;		//申请单号
	private String applyName;		//项目名称
	private String applyTime;	//申请时间
	private String applyAddress; 	//申请地市名称
	private String supplierName;  //供应商名称
	private String status;	//处理状态
	private String option;	//操作
	private String lanId; // 地址id
	private String applyMerchantName; //申请名称
	private String applyMerchantId; //申请id
	private String merchantId; //供应商id
	private String createStaff; // 申请人

	private String taskId;
	private String taskItemId;

}
