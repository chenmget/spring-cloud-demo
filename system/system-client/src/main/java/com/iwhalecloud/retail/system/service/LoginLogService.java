package com.iwhalecloud.retail.system.service;

import java.util.Date;

import com.iwhalecloud.retail.system.dto.LoginLogDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;

public interface LoginLogService {

    LoginLogDTO saveLoginLog(LoginLogDTO loginLogDTO);

    UserDTO getUserByLoginName(String loginName);

    /**
     * 更新登录日志的登出时间
     * @param userId
     * @param nowDate
     * @return
     */
	int updatelogoutTimeByUserId(String userId, String nowDate);
}