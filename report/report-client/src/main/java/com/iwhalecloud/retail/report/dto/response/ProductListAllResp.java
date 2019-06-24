package com.iwhalecloud.retail.report.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductListAllResp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private String productId;//机型id
	  private String productName;//机型name
}
