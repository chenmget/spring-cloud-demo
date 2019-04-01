package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/4/1
 */
@Data
public class PermissionApplyPassReq implements Serializable {

    private static final long serialVersionUID = -1590333808160805066L;

    @ApiModelProperty(value = "申请单ID(主键)")
    private String applyId;

    @ApiModelProperty(value = "记录状态。1001 未提交，1002 审核中，1003 审核通过，1004 审核不通过")
    private String statusCd;

    @ApiModelProperty(value = "记录每次修改的用户标识")
    private String updateStaff;
}
