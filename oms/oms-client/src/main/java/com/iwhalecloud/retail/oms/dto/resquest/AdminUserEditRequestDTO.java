package com.iwhalecloud.retail.oms.dto.resquest;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminUserEditRequestDTO  implements Serializable {

    private String userId; // 用户ID
    private String userName; // 账号
//    private String realName; // 名字
    private String[] roleIds;
    private String userSessionId; // sessionId

}