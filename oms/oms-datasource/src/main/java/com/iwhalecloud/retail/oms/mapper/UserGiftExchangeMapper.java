package com.iwhalecloud.retail.oms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.response.gift.UserGiftExchangeRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListUserGiftExchangeReqDTO;
import com.iwhalecloud.retail.oms.entity.gif.UserGiftExchange;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 用户奖品兑换
 */
@Mapper
public interface UserGiftExchangeMapper extends BaseMapper<UserGiftExchange> {

	/**
     * 用户兑换记录
     * @param page
     * @param request
     * @return
     */
    Page<UserGiftExchangeRespDTO> listUserGiftExchange(Page<ListUserGiftExchangeReqDTO> page, @Param("req")ListUserGiftExchangeReqDTO request);
    
    /**
     * 用户最新兑换记录
     * @param userId
     * @return
     */
    UserGiftExchangeRespDTO getNewestExchange(@Param("userId") String userId);


}
