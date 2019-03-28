package com.iwhalecloud.retail.oms.service.gift;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.response.gift.GiftRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListGiftReqDTO;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 奖品增删改
 */
public interface GiftService {

    /**
     * 获取奖品
     * @param t
     * @return
     */
    Page<GiftRespDTO> listGift(ListGiftReqDTO t);


}
