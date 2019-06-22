package com.iwhalecloud.retail.oms.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.response.gift.UserPointRecordRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListUserPointRecordReqDTO;
import com.iwhalecloud.retail.oms.entity.gif.UserPointRecord;
import com.iwhalecloud.retail.oms.mapper.UserPointRecordMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 用户流水记录
 */
@Component
public class UserPointRecordManager {

    @Resource
    private UserPointRecordMapper userPointRecordMapper;

    public Integer insert(UserPointRecord dto) {
        Integer t = userPointRecordMapper.insert(dto);
        return t;
    }


    public Page<UserPointRecordRespDTO> listUserPointRecord(ListUserPointRecordReqDTO t){
    	Page<ListUserPointRecordReqDTO> page = new Page<ListUserPointRecordReqDTO>(t.getPageNo(), t.getPageSize());
    	return userPointRecordMapper.listUserPointRecord(page,t);
    }
    
    public UserPointRecordRespDTO getUserPoint(Long userId){
    	return userPointRecordMapper.getUserPoint(userId);
    }
    
}
