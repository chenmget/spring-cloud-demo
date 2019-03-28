package com.iwhalecloud.retail.rights.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.CouponNotUsedReq;
import com.iwhalecloud.retail.rights.dto.request.CouponUsedReq;
import com.iwhalecloud.retail.rights.dto.response.CouponNotUsedResp;
import com.iwhalecloud.retail.rights.dto.response.CouponUsedResp;
import com.iwhalecloud.retail.rights.manager.CardTicketManager;
import com.iwhalecloud.retail.rights.service.CardTicketService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author lin.wenhui@iwhalecloud.com
 * @date 2019/2/25 15:40
 * @description: 权益--我的卡券
 */

@Service
public class CardTicketServiceImpl implements CardTicketService {

    @Autowired
    private CardTicketManager cardTicketManager;

    @Override
    public Page<CouponNotUsedResp> queryAllBusinessCouponNotUsed(CouponNotUsedReq req) {
        return cardTicketManager.queryAllBusinessCouponNotUsed(req);
    }

    @Override
    public Page<CouponUsedResp> queryAllBusinessCouponUsed(CouponUsedReq req) {
        return cardTicketManager.queryAllBusinessCouponUsed(req);
    }

    @Override
    public List<CouponUsedResp> queryAllBusinessCouponUsedNotPage(CouponUsedReq req) {
        return cardTicketManager.queryAllBusinessCouponUsedNotPage(req);
    }

}

