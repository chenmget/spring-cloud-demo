package com.iwhalecloud.retail.report.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("SYS_MENU")
@KeySequence(value="seq_sys_menu_id",clazz = String.class)
@ApiModel(value = "SYS_MENU, 对应实体Menu类")
public class Menu implements Serializable {
    public static final String TNAME = "SYS_MENU";

    @TableId
    @ApiModelProperty(value = "menuId")
    private String menuId;

    @ApiModelProperty(value = "parentMenuId")
    private String parentMenuId;

    @ApiModelProperty(value = "menuName")
    private String menuName;

    @ApiModelProperty(value = "menuDesc")
    private String menuDesc;

    @ApiModelProperty(value = "menuUrl")
    private String menuUrl;

    @ApiModelProperty(value = "menuType")
    private String menuType;

    @ApiModelProperty(value = "statusCd")
    private String statusCd;

    @ApiModelProperty(value = "createStaff")
    private String createStaff;

    @ApiModelProperty(value = "createDate")
    private Date createDate;

    @ApiModelProperty(value = "updateStaff")
    private String updateStaff;

    @ApiModelProperty(value = "updateDate")
    private Date updateDate;

}
