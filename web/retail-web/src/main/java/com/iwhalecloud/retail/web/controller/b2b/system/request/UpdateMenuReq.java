package com.iwhalecloud.retail.web.controller.b2b.system.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateMenuReq implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * menuId
     */
    @ApiModelProperty(value = "menuId")
    private String menuId;

    /**
     * parentMenuId
     */
    @ApiModelProperty(value = "parentMenuId")
    private String parentMenuId;

    /**
     * menuName
     */
    @ApiModelProperty(value = "menuName")
    private String menuName;

    /**
     * menuDesc
     */
    @ApiModelProperty(value = "menuDesc")
    private String menuDesc;

    /**
     * menuUrl
     */
    @ApiModelProperty(value = "menuUrl")
    private String menuUrl;

    /**
     * 1：菜单
     2：按钮
     */
    @ApiModelProperty(value = "1：菜单 2：按钮")
    private String menuType;

    /**
     * 记录状态。LOVB=PUB-C-0001。
     */
    @ApiModelProperty(value = "记录状态。LOVB=PUB-C-0001。")
    private String statusCd;

    /**
     * 记录首次创建的员工标识。
     */
    @ApiModelProperty(value = "记录首次创建的员工标识。")
    private String createStaff;

    /**
     * 记录首次创建的时间。
     */
    @ApiModelProperty(value = "记录首次创建的时间。")
    private java.util.Date createDate;

    /**
     * 记录每次修改的员工标识。
     */
    @ApiModelProperty(value = "记录每次修改的员工标识。")
    private String updateStaff;

    /**
     * 记录每次修改的时间。
     */
    @ApiModelProperty(value = "记录每次修改的时间。")
    private java.util.Date updateDate;

}
