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

    @ApiModelProperty(value = "本地网ID")
    private String lanId;

    @ApiModelProperty(value = "区域ID")
    private String regionId;

    /**
     * 规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限
     */
    @ApiModelProperty(value = "规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限")
    private String ruleType;

    /**
     * 对象类型:
     * RULE_TYPE是1 经营权限时: TARGET_TYPE是： 1 品牌  2 机型  3 区域 4 商家；
     * RULE_TYPE是2 绿色通道权限时: TARGET_TYPE是： 1 产品，TARGET_ID填写PRODUCT_BASE_ID   ,  2 机型，TARGET_ID填写PRODUCT_ID ;
     * RULE_TYPE是3 调拨权限时:  TARGET_TYPE是： 2 机型 3 区域 4 商家；
     */
    @ApiModelProperty(value = "对象类型:  " +
            " RULE_TYPE是1 经营权限时: TARGET_TYPE对应： 1 品牌，TARGET_ID存BRAND_ID  2 机型，TARGET_ID存PRODUCT_ID  3 区域,TARGET_ID存REGION_ID 4 商家TARGET_ID存MERCHANT_ID； " +
            " RULE_TYPE是2 绿色通道权限时: TARGET_TYPE对应： 1 产品，TARGET_ID存PRODUCT_BASE_ID,  2 机型，TARGET_ID存PRODUCT_ID ;" +
            " RULE_TYPE是3 调拨权限时:  TARGET_TYPE对应： 2 机型，TARGET_ID存PRODUCT_ID， 3 区域,TARGET_ID存REGION_ID 4 商家,TARGET_ID存MERCHANT_ID；")
    //@NotEmpty(message = "对象类型不能为空")
    private String targetType;

}
