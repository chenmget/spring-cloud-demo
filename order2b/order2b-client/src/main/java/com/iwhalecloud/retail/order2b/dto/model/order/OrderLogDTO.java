package com.iwhalecloud.retail.order2b.dto.model.order;

import com.iwhalecloud.retail.order2b.dto.base.SelectModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * OrderLog
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ord_order_log, 对应实体OrderLog类")
public class OrderLogDTO extends SelectModel implements java.io.Serializable {
    
  	private static final long serialVersionUID = 1L;


  	//属性 begin
	/**
  	 * 变更流水
  	 */
	@ApiModelProperty(value = "变更流水")
  	private java.lang.String logId;
	
	/**
  	 * 订单ID
  	 */
	@ApiModelProperty(value = "订单ID")
  	private java.lang.String orderId;
	
	/**
  	 * 变更动作
  	 */
	@ApiModelProperty(value = "变更动作")
  	private java.lang.String changeAction;
	
	/**
  	 * 变更原因
  	 */
	@ApiModelProperty(value = "变更原因")
  	private java.lang.String changeReson;
	
	/**
  	 * 变更描述
  	 */
	@ApiModelProperty(value = "变更描述")
  	private java.lang.String desc;
	
	/**
  	 * 变更前状态
  	 */
	@ApiModelProperty(value = "变更前状态")
  	private java.lang.String preStatus;
	
	/**
  	 * 变更后状态
  	 */
	@ApiModelProperty(value = "变更后状态")
  	private java.lang.String postStatus;
	
	/**
  	 * 用户ID
  	 */
	@ApiModelProperty(value = "用户ID")
  	private java.lang.String userId;
	
	/**
  	 * 创建时间
  	 */
	@ApiModelProperty(value = "创建时间")
  	private java.util.Date createTime;
	
  	
}
