package com.iwhalecloud.retail.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("SYS_ROLE_MENU")
@ApiModel(value = "SYS_ROLE_MENU, 对应实体RoleMenu类")
@KeySequence(value = "seq_sys_role_menu_id",clazz = String.class)
public class RoleMenu {
    public static final String TNAME = "SYS_ROLE_MUNE";

    @TableId
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "menuId")
    private String menuId;

    @ApiModelProperty(value = "menuName")
    private String menuName;

    @ApiModelProperty(value = "roleId")
    private String roleId;

    @ApiModelProperty(value = "roleName")
    private String roleName;

    @ApiModelProperty(value = "createStaff")
    private String createStaff;

    @ApiModelProperty(value = "createDate")
    private Date createDate;

    /** 字段名称枚举. */
    public enum FieldNames {
        /** ID. */
        id("id","ID"),

        /** 菜单ID. */
        menuId("menuId","MENU_ID"),
        /** 菜单名称. */
        menuName("menuName","MENU_NAME"),

        /** 角色ID. */
        roleId("roleId","ROLE_ID"),

        /** 角色名称. */
        roleName("roleName","ROLE_NAME"),

        /** 状态   创建人员ID */
        createStaff("createStaff","CREATE_STAFF"),

        /** 创建时间. */
        createDate("createDate","CREATE_DATE");

        private String fieldName;
        private String tableFieldName;
        FieldNames(String fieldName, String tableFieldName){
            this.fieldName = fieldName;
            this.tableFieldName = tableFieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getTableFieldName() {
            return tableFieldName;
        }
    }
}