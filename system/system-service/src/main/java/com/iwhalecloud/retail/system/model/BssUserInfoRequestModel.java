package com.iwhalecloud.retail.system.model;

import lombok.Data;

import java.util.Date;

/**
 * @author lin.wenhui@iwhalecloud.com
 * @date 2019/3/16 10:50
 */
@Data
public class BssUserInfoRequestModel {

    /**
     * 工号描述 对应SYS_USER.REMARK
     */
    private String sysUserDesc;

    /**
     * 用户工号 对应SYS_USER.LOGIN_NAME
     */
    private String sysUserCode;

    /**
     * 密码 对应SYS_USER.LOGIN_PWD
     */
    private String password;

    /**
     * 用户名称 对应SYS_USER.USER_NAME
     */
    private String userName;

    /**
     * 创建时间 对应SYS_USER.CREATE_DATE
     */
    private Date createDate;

    /**
     * 区域ID 对应SYS_USER.REGION_ID
     */
    private String regionId;

    /**
     * 修改人ID 对应SYS_USER.UPDATE_STAFF
     */
    private String updateStaff;

    /**
     * 员工ID 对应SYS_USER.CREATE_STAFF
     */
    private String staffId;

    /**
     * 更新时间 对应SYS_USER.UPDATE_DATE
     */
    private Date updateDate;

    /**
     * 用户组织ID 对应SYS_USER.ORG_ID
     */
    private String userOrgId;

    /**
     * 状态 对应SYS_USER.STATUS_CD
     */
    private Integer statusCd;

    /**
     * 手机号码 对应SYS_USER.PHONE_NO
     */
    private String pwdSmsTel;
}
