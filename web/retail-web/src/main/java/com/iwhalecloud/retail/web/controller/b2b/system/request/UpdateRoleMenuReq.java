package com.iwhalecloud.retail.web.controller.b2b.system.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateRoleMenuReq implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    //属性 begin
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private java.lang.String id;

    /**
     * menuId
     */
    @ApiModelProperty(value = "menuId")
    private java.lang.String menuId;

    /**
     * menuName
     */
    @ApiModelProperty(value = "menuName")
    private java.lang.String menuName;

    /**
     * roleId
     */
    @ApiModelProperty(value = "roleId")
    private java.lang.String roleId;

    /**
     * roleName
     */
    @ApiModelProperty(value = "roleName")
    private java.lang.String roleName;

    /**
     * 记录首次创建的员工标识。
     */
    @ApiModelProperty(value = "记录首次创建的员工标识。")
    private java.lang.String createStaff;

    /**
     * 记录首次创建的时间。
     */
    @ApiModelProperty(value = "记录首次创建的时间。")
    private java.util.Date createDate;
}
