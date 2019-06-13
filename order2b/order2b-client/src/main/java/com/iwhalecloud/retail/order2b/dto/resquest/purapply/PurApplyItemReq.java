package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class PurApplyItemReq  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applyItem;	//条目ID
	private String productId;	//产品ID

	
}
