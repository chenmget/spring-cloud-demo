package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenlong.zhong
 * @date 2019/3/30
 */
@Data
public class PermissionApplyItemListReq implements Serializable {
    private static final long serialVersionUID = 5778773930337560661L;

    /**
     * 申请单项ID(主键)
     */
    @ApiModelProperty(value = "申请单项ID(主键)")
    private String applyItemId;

    /**
     * 申请单ID(par_permission_apply)表主键
     */
    @ApiModelProperty(value = "申请单ID(par_permission_apply)表主键")
    private String applyId;

    /**
     * 操作类型: A:新增  U:修改  D:删除
     */
    @ApiModelProperty(value = "操作类型: A:新增  U:修改  D:删除")
    private String operationType;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchantId;

    /**
     * 记录状态。LOVB=PUB-C-0001。1000 有效，1100 无效
     */
    @ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。1000 有效，1100 无效")
    private String statusCd;

}
