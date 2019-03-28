package com.iwhalecloud.retail.oms.dto.resquest.content;

import lombok.Data;
import java.util.List;
/**
 * ContentBase
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
public class ContentBaseBatchReq implements java.io.Serializable {

  	private static final long serialVersionUID = 1L;

	//属性 begin
	/**
	 * 内容ID
	 */
	private List<Long> contentIds;


}
