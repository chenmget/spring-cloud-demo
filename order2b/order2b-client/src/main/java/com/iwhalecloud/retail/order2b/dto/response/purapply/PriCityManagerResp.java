package com.iwhalecloud.retail.order2b.dto.response.purapply;

import java.io.Serializable;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class PriCityManagerResp extends PageVO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userType;
	private String lanId;

}
