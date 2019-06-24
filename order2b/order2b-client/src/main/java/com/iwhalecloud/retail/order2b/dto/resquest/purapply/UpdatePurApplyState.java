package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdatePurApplyState extends PageVO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applyId;
	private String updateStaff;
	private String updateDate;
	private String statusDate;
	private String statusCd;

}
