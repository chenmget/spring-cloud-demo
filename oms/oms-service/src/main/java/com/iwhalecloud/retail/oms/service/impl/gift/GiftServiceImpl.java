package com.iwhalecloud.retail.oms.service.impl.gift;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.response.gift.GiftRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListGiftReqDTO;
import com.iwhalecloud.retail.oms.manager.GiftManager;
import com.iwhalecloud.retail.oms.service.gift.GiftService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 奖品增删改
 */
@Service
@Slf4j
public class GiftServiceImpl implements GiftService {

    @Autowired
    private GiftManager giftManager;


    @Override
    public Page<GiftRespDTO> listGift(ListGiftReqDTO t) {
        return giftManager.listGift(t);
    }

}
