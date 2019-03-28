package com.iwhalecloud.retail.oms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.response.gift.UserGiftExchangeRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListUserGiftExchangeReqDTO;
import com.iwhalecloud.retail.oms.entity.gif.UserGiftExchange;
import com.iwhalecloud.retail.oms.mapper.UserGiftExchangeMapper;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 用户奖品兑换
 */
@Component
public class UserGiftExchangeManager {

    @Resource
    private UserGiftExchangeMapper userGiftExchangeMapper;

    public Integer insert(UserGiftExchange dto) {
        Integer t = userGiftExchangeMapper.insert(dto);
        return t;
    }


    public Page<UserGiftExchangeRespDTO> listUserGiftExchange(ListUserGiftExchangeReqDTO t){
    	Page<ListUserGiftExchangeReqDTO> page = new Page<ListUserGiftExchangeReqDTO>(t.getPageNo(), t.getPageSize());
    	return userGiftExchangeMapper.listUserGiftExchange(page, t);
    }
    
    public UserGiftExchangeRespDTO getNewestExchange(String userId){
    	return userGiftExchangeMapper.getNewestExchange(userId);
    }
    
}
