package com.iwhalecloud.retail.oms.service.gift;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.response.gift.UserGiftExchangeRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListUserGiftExchangeReqDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.UserGiftExchangeReqDTO;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 用户奖品兑换
 */
public interface UserGiftExchangeService {

	/**
     * 添加用户兑奖记录
     * @param t
     * @return
     */
	Integer saveUserGiftExchange(UserGiftExchangeReqDTO t);

    /**
     * 获取用户兑奖记录
     * @param request
     * @return
     */
	Page<UserGiftExchangeRespDTO> listUserPointRecord(ListUserGiftExchangeReqDTO request);
    

}
