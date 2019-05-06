package com.iwhalecloud.retail.order2b.dto.response.purapply;

import java.io.Serializable;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class JyPurApplyResp extends PageVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applyId;
	private String applyCode;//申请单号
	private String applyName;//项目名称
	private String applyMerchantId;//申请人
	private String lanId;//申请人所属地市
	private String statusCd;//当前处理环节
	private String createDate;//申请时间

	private String taskId;
	private String taskItemId;
}
