package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import java.util.Date;

import com.iwhalecloud.retail.dto.PageVO;

import lombok.Data;

@Data
public class ProdProductChangeDetail extends PageVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String changeDetailId;//变更明细流水
	private String changeId;//变更流水
	private String operType;//操作类型 ADD：新增 MOD：修改 DEL：删除
	private String verNum;//版本号
	private String tableName;//表名
	private String changeField;//	变更字段英文名
	private String changeFieldName;//变更字段中文注释的名字
	private String oldValue;//	原始值
	private String newValue;//	变更值
	private String keyValue;//product_id	业务ID
	private Date createDate;//创建时间
	private String createStaff;//创建人
	
	private String priceStatus;//政企价格审核状态
	private String productId;

}
