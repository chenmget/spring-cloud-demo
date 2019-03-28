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
@TableName("SYS_USER")
@ApiModel(value = "SYS_USER, 对应实体USER类")
@KeySequence(value = "seq_sys_user_id",clazz = String.class)
public class User implements Serializable {
    public static final String TNAME = "SYS_USER";

    @TableId
    @ApiModelProperty(value = "userId")
    private String userId;

    @ApiModelProperty(value = "loginName")
    private String loginName;

    @ApiModelProperty(value = "loginPwd")
    private String loginPwd;

    @ApiModelProperty(value = "statusCd")
    private Integer statusCd;

    @ApiModelProperty(value = "userName")
    private String userName;

    @ApiModelProperty(value = "remark")
    private String remark;

    @ApiModelProperty(value = "userFounder")
    private Integer userFounder;

    @ApiModelProperty(value = "relType")
    private String relType;

    @ApiModelProperty(value = "relNo")
    private String relNo;

    @ApiModelProperty(value = "lanId")
    private String lanId;

    @ApiModelProperty(value = "relCode")
    private String relCode;

    @ApiModelProperty(value = "curLoginTime")
    private Date curLoginTime;

    @ApiModelProperty(value = "lastLoginTime")
    private Date lastLoginTime;

    @ApiModelProperty(value = "failLoginCnt")
    private Integer failLoginCnt;

    @ApiModelProperty(value = "successLoginCnt")
    private Integer successLoginCnt;

    @ApiModelProperty(value = "phoneNo")
    private String phoneNo;

    @ApiModelProperty(value = "orgId")
    private String orgId;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "regionId")
    private String regionId;

    @ApiModelProperty(value = "createStaff")
    private String createStaff;

    @ApiModelProperty(value = "createDate")
    private Date createDate;

    @ApiModelProperty(value = "updateStaff")
    private String updateStaff;

    @ApiModelProperty(value = "updateDate")
    private Date updateDate;

    @ApiModelProperty(value = "sysPostId")
    private Long sysPostId;

    @ApiModelProperty(value = "sysPostName")
    private String sysPostName;

    @ApiModelProperty(value = "userSource")
    private Integer userSource;

    /** 字段名称枚举. */
    public enum FieldNames {
        /** 用户ID. */
        userId("userId","USER_ID"),

        /** 登陆用户名. */
        loginName("loginName","LOGIN_NAME"),

        /** 登陆密码. */
        loginPwd("loginPwd","LOGIN_PWD"),

        /** 状态   1有效、 0 失效. */
        statusCd("statusCd","STATUS_CD"),

        /** 用户昵称. */
        userName("userName","USER_NAME"),

        /** 备注. */
        remark("remark","REMARK"),

        /** 用户类型 */
        userFounder("userFounder","USER_FOUNDER"),

        /** 关联类型. */
        relType("relType","REL_TYPE"),

        /** 关联员工ID. */
        relNo("relNo","REL_NO"),

        /** 用户归属本地网. */
        lanId("lanId","LAN_ID"),

        /** 关联代理商ID  或 供应商ID. */
        relCode("relCode","REL_CODE"),

        /** 当前登陆时间. */
        curLoginTime("curLoginTime","CUR_LOGIN_TIME"),

        /** 上次登陆时间. */
        lastLoginTime("lastLoginTime","LAST_LOGIN_TIME"),

        /** 登陆失败的次数. */
        failLoginCnt("failLoginCnt","FAIL_LOGIN_CNT"),

        /** 登陆成功的次数. */
        successLoginCnt("successLoginCnt","SUCCESS_LOGIN_CNT"),

        /** 用户电话号码. */
        phoneNo("phoneNo","PHONE_NO"),

        /** 关联组织 id. */
        orgId("orgId","ORG_ID"),

        /** 邮箱账号. */
        email("email","EMAIL"),

        /** 区域ID. */
        regionId("regionId","REGION_ID"),

        /** 创建人. */
        createStaff("createStaff","CREATE_STAFF"),

        /** 创建时间. */
        createDate("createDate","CREATE_DATE"),

        /** 修改人. */
        updateStaff("updateStaff","UPDATE_STAFF"),

        /** 修改时间. */
        updateDate("updateDate","UPDATE_DATE"),

        /** 岗位ID. */
        sysPostId("sysPostId","SYS_POST_ID"),

        /** 岗位名称. */
        sysPostName("sysPostName","SYS_POST_NAME"),

        /** 工号来源. */
        userSource("userSource","USER_SOURCE");

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