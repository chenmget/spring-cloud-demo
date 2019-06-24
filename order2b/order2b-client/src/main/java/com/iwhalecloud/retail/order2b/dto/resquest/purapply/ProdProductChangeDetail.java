package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

import java.util.Date;

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
	private String productName;//产品名称
	private String unitType;//产品型号	
	private String unitTypeName;//型号名称
	private String sn;//营销资源实例编码
	private String typeName;//产品类型
	private String brandName;//品牌名称

}
