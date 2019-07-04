package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述：价保返利录入详情添加对象
 *
 * @author huang.huazhang
 * @date 2019年06月03日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddPriceGuaranteeAndRebaseRecordDetailReq extends AbstractRequest implements Serializable {

  private static final long serialVersionUID = 4817965850679960441L;

  @ApiModelProperty(value = "供应商id")
  private String supplierId;

  @ApiModelProperty(value = "供应商编码")
  private String supplierCode;

  @ApiModelProperty(value = "供应商名称")
  private String supplierName;

  @ApiModelProperty(value = "商家编码（零售商）")
  private String merchantCode;

  @ApiModelProperty(value = "商家名称（零售商）")
  private String merchantName;

  @ApiModelProperty(value = "商家类型")
  private String merchantType;

  @ApiModelProperty(value = "产品编码（营销资源编码）")
  private String productSn;

  @ApiModelProperty(value = "数量")
  private Long num;

  @ApiModelProperty(value = "单台发放金额")
  private Long amount;

  @ApiModelProperty(value = "总金额", notes = "前端计算一下")
  private Long totalAmount;

  @ApiModelProperty(value = "生效时间", notes = "生效时间的格式为选中时间的当天零时零分零秒")
  private Date effTime;

  @ApiModelProperty(value = "失效时间", notes = "失效时间的格式为选中时间的当天零时零分零秒")
  private Date expTime;

  @ApiModelProperty(value = "备注")
  private String remark;

}
