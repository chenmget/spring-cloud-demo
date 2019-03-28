package com.iwhalecloud.retail.rights.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.dto.request.CouponNotUsedReq;
import com.iwhalecloud.retail.rights.dto.request.CouponUsedReq;
import com.iwhalecloud.retail.rights.dto.response.CouponNotUsedResp;
import com.iwhalecloud.retail.rights.dto.response.CouponUsedResp;
import com.iwhalecloud.retail.rights.mapper.CardTicketMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lin.wenhui@iwhalecloud.com
 * @date 2019/2/25 15:45
 * @description: 权益--我的卡券
 */

@Component
public class CardTicketManager {

    @Resource
    private CardTicketMapper cardTicketMapper;

    public Page<CouponNotUsedResp> queryAllBusinessCouponNotUsed(CouponNotUsedReq req) {
        Page<CouponNotUsedReq> page = new Page<CouponNotUsedReq>(req.getPageNo(), req.getPageSize());
        return cardTicketMapper.queryAllBusinessCouponNotUsed(page, req);
    }

    public Page<CouponUsedResp> queryAllBusinessCouponUsed(CouponUsedReq req) {
        Page<CouponUsedResp> page = new Page<CouponUsedResp>(req.getPageNo(), req.getPageSize());
        return cardTicketMapper.queryAllBusinessCouponUsed(page, req);
    }

    public List<CouponUsedResp> queryAllBusinessCouponUsedNotPage(CouponUsedReq req) {
        return cardTicketMapper.queryAllBusinessCouponUsedNotPage(req);
    }
}

