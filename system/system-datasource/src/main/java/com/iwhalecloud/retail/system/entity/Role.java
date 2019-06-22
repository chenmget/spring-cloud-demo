package com.iwhalecloud.retail.system.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("SYS_ROLE")
@ApiModel(value = "SYS_ROLE, 对应实体Role类")
@KeySequence(value = "seq_sys_role_id",clazz = String.class)
public class Role implements Serializable {
    public static final String TNAME = "SYS_ROLE";

    @TableId
    @ApiModelProperty(value = "roleId")
    private String roleId;

    @ApiModelProperty(value = "roleName")
    private String roleName;

    @ApiModelProperty(value = "roleDesc")
    private String roleDesc;

    @ApiModelProperty(value = "createStaff")
    private String createStaff;

    @ApiModelProperty(value = "createDate")
    private Date createDate;

    @ApiModelProperty(value = "updateStaff")
    private String updateStaff;

    @ApiModelProperty(value = "updateDate")
    private Date updateDate;

}