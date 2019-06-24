package com.iwhalecloud.retail.partner.dto.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LSSAddControlReq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String merchantId;//新增的零售商ID
	private String ruleType;//规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限
	private String targetType;
	
    private List<String> targetIdList;//"对象ID集合  用于批量插入"

}
