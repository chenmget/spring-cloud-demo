package com.iwhalecloud.retail.oms.dto.resquest;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zwl
 * 员工绑定 系统用户
 */
@Data
public class StaffBindingAdminUserReq implements Serializable {

    private String staffId; // 员工ID
    private String userName; // 账号
    private String realName; // 名字
    private String passWord; // 密码
    private String[] roleIds;
    private String parentUserId; // 创建人的用户ID
    private String userSessionId; // sessionId

}
