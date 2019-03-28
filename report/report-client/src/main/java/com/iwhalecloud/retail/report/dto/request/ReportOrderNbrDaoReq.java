package com.iwhalecloud.retail.report.dto.request;

import java.io.Serializable;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class ReportOrderNbrDaoReq extends PageVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderId;//订单编码
	
}
