package com.iwhalecloud.retail.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("SYS_USER_ROLE")
@ApiModel(value = "SYS_USER_ROLE, 对应实体UserRole类")
@KeySequence(value = "seq_sys_user_role_id",clazz = String.class)
public class UserRole implements Serializable {
    public static final String TNAME = "SYS_USER_ROLE";

    @TableId
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "userId")
    private String userId;

    @ApiModelProperty(value = "userName")
    private String userName;

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

        /** 用户名ID. */
        userId("userId","USER_ID"),

        /** 用户名. */
        userName("userName","USER_NAME"),

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