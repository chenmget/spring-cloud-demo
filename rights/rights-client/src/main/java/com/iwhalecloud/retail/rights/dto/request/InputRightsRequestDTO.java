package com.iwhalecloud.retail.rights.dto.request;

import java.io.Serializable;

import com.iwhalecloud.retail.dto.AbstractRequest;
import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;

@Data
public class InputRightsRequestDTO extends AbstractRequest implements Serializable{
	
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
	
	/**
	 * 创建人
	 */
	private String gmtCreate;
	
	
}
