package com.iwhalecloud.retail.oms.manager;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.response.gift.GiftRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListGiftReqDTO;
import com.iwhalecloud.retail.oms.entity.gif.Gift;
import com.iwhalecloud.retail.oms.mapper.GiftMapper;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 奖品增删改
 */
@Component
public class GiftManager {

    @Resource
    private GiftMapper giftMapper;


    public Page<GiftRespDTO> listGift(ListGiftReqDTO t) {
    	Page<ListGiftReqDTO> page = new Page<ListGiftReqDTO>(t.getPageNo(), t.getPageSize());
    	return giftMapper.listGift(page,t);
    }
    
    public GiftRespDTO findById(Long giftId) {
    	GiftRespDTO gift = giftMapper.getById(giftId);
    	return gift;
    }
    
    public Integer updateById(GiftRespDTO gift) {
    	Gift t = new Gift();
    	BeanUtils.copyProperties(gift, t);
    	return giftMapper.updateById(t);
    }
}
