package com.iwhalecloud.retail.web.controller.b2b.rights.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
public class InputRightsReq implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 权益内容标识
	 */
	@NotBlank
	private String rightsId;
	
	/**
	 * 起始编码
	 */
	private Long beginNum;
	
	/**
	 * 入库数量
	 */
	private Integer inventoryNum;
	
	
}
