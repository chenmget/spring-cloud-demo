package com.iwhalecloud.retail.system.manager;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.iwhalecloud.retail.system.dto.request.RandomLogAddReq;
import com.iwhalecloud.retail.system.dto.request.RandomLogGetReq;
import com.iwhalecloud.retail.system.dto.request.RandomLogUpdateReq;
import com.iwhalecloud.retail.system.dto.response.RandomLogGetResp;
import com.iwhalecloud.retail.system.entity.RandomLog;
import com.iwhalecloud.retail.system.mapper.RandomLogMapper;

@Component
public class RandomLogManager{
    @Resource
    private RandomLogMapper randomLogMapper;

    /**
	 * 查询
	 * @param req
	 * @return
	 */
    public RandomLogGetResp selectLogIdByRandomCode(RandomLogGetReq req){
    	RandomLog t = new RandomLog();
    	BeanUtils.copyProperties(req, t);
    	RandomLog resp = randomLogMapper.selectLogIdByRandomCode(t);
    	if (resp == null) {
			return null;
		}
    	RandomLogGetResp dto = new RandomLogGetResp();
    	BeanUtils.copyProperties(resp, dto);
    	return dto;
    }

    /**
     * 增加
     * @param req
     * @return
     */
    public Integer insertSelective(RandomLogAddReq req){
    	RandomLog t = new RandomLog();
    	BeanUtils.copyProperties(req, t);
    	return randomLogMapper.insert(t);
//		return randomLogMapper.insertSelective(t);
    }

    /**
     * 更新
     * @param req
     * @return
     */
    public Integer updateByPrimaryKey(RandomLogUpdateReq req){
    	RandomLog t = new RandomLog();
    	BeanUtils.copyProperties(req, t);
    	return randomLogMapper.updateById(t);
//    	return randomLogMapper.updateByPrimaryKey(t);

    }

    
    
}
