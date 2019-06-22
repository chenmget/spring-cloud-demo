package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.system.dto.LoginLogDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.entity.LoginLog;
import com.iwhalecloud.retail.system.entity.User;
import com.iwhalecloud.retail.system.manager.LoginlogManager;
import com.iwhalecloud.retail.system.manager.UserManager;
import com.iwhalecloud.retail.system.service.LoginLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class LoginLogServiceImpl implements LoginLogService {
	
    @Autowired
    private LoginlogManager loginlogManager;
    @Autowired
    private UserManager userManager;

	@Override
	public LoginLogDTO saveLoginLog(LoginLogDTO loginLogDTO) {
		LoginLog loginLog = new LoginLog();
		
		BeanUtils.copyProperties(loginLogDTO, loginLog);
		int num = loginlogManager.saveLoginLog(loginLog);
		if (num == 0){
			return null;
		}
		return loginLogDTO;
	}

	@Override
	public UserDTO getUserByLoginName(String loginName) {
		UserDTO userDTO = new UserDTO();
		User userinfo = userManager.login(loginName);
		if(null == userinfo){
			return userDTO;
		}
		BeanUtils.copyProperties(userinfo, userDTO);
		return userDTO;
	}

	@Override
	public int updatelogoutTimeByUserId(String userId, String date) {
		int num = loginlogManager.updatelogoutTimeByUserId(userId, date);
		return num;
	}
	
  
}