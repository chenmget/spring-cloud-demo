package com.iwhalecloud.retail.oms.service.impl.gift;

import com.iwhalecloud.retail.oms.dto.UserPointRecordDTO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.dto.response.gift.UserPointRecordRespDTO;
import com.iwhalecloud.retail.oms.entity.gif.UserPointRecord;
import com.iwhalecloud.retail.oms.manager.UserPointRecordManager;
import com.iwhalecloud.retail.oms.service.gift.UserPointRecordService;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 用户积分记录
 */
@Service
@Slf4j
public class UserPointRecordServiceImpl implements UserPointRecordService {

    @Autowired
    private UserPointRecordManager userPointRecordManager;

	@Override
	public Integer saveUserPointRecord(UserPointRecordDTO t) {

		UserPointRecord entity = new UserPointRecord();
		BeanUtils.copyProperties(t,entity);
		int insert = userPointRecordManager.insert(entity);
		return insert;
	}


	@Override
	public UserPointRecordRespDTO getUserPoint(Long userId) {
		return userPointRecordManager.getUserPoint(userId);
	}

   
}
