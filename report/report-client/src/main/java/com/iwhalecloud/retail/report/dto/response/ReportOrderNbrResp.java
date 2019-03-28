package com.iwhalecloud.retail.report.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ReportOrderNbrResp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String unitName;//机型名称
	private String resNbr;//串码
	private String createTime;//创建时间
	private String state;//状态
}
