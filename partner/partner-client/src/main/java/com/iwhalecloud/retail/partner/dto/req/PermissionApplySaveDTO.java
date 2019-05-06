package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商家权限申请单 新增请求对象（多表）
 * @author wenlong.zhong
 * @date 2019/4/2
 */
@Data
public class PermissionApplySaveDTO implements Serializable {
    private static final long serialVersionUID = -6201783885092631849L;

    @ApiModelProperty(value = "用户ID, 后端自动获取")
    private String userId;

    @ApiModelProperty(value = "用户名称，后端自动获取")
    private String name;

    @ApiModelProperty(value = "商家ID，后端自动获取")
    private String merchantId;

    @ApiModelProperty(value = "申请单对象")
    private PermissionApplySaveReq permissionApplySaveReq;

    @ApiModelProperty(value = "申请单项 列表")
    private List<PermissionApplyItemSaveReq> itemList;

}
