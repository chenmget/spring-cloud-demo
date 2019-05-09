package com.iwhalecloud.retail.system.manager;

import com.iwhalecloud.retail.system.entity.LoginLog;
import com.iwhalecloud.retail.system.mapper.LoginLogMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class LoginlogManager {
    @Resource
    private LoginLogMapper loginlogMapper;

    
    public int saveLoginLog(LoginLog loginLog){
        return loginlogMapper.insert(loginLog);
    }


	public int updatelogoutTimeByUserId(String userId, String date) {
		return loginlogMapper.updatelogoutTimeByUserId(userId, date);
	}

}
