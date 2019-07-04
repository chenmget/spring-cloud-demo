package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能描述：新增价保返利录入
 *
 * @author huang.huazhang
 * @date 2019年06月03日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddPriceGuaranteeAndRebaseRecordReq extends AbstractRequest implements Serializable {

  private static final long serialVersionUID = 9096483978371837098L;

  @ApiModelProperty(value = "价保返利详情列表")
  private List<AddPriceGuaranteeAndRebaseRecordDetailReq> addPriceGuaranteeAndRebaseRecordDetailReqList;

  @ApiModelProperty(value = "供应商id")
  private String supplierId;

  @ApiModelProperty(value = "活动名称")
  private String marketingActivityName;

  @ApiModelProperty(value = "申请凭证（活动文件）")
  private String applyProof;

  @ApiModelProperty(value = "用户id")
  private String userId;

  @ApiModelProperty(value = "用户姓名")
  private String userName;

  @ApiModelProperty(value = "申请总金额" ,notes = "前端不需要传过来，不用理会这里")
  private long applyAmount;

  @ApiModelProperty(value = "申请记录数" ,notes = "前端不需要传过来，不用理会这里")
  private long applyNum;
}
