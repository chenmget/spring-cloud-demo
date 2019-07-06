package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.promo.dto.MarketingActivityDTO;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityByMerchantListReq;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityByMerchantResp;
import com.iwhalecloud.retail.promo.mapper.ActivityGoodMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author li.xinhang
 * @date 2019/3/5
 */
@Component
public class ActivityGoodManager {
    
    @Resource 
    private ActivityGoodMapper activityGoodMapper;
    
    public Page<MarketingActivityDTO> listMarketingActivityByMerchant(MarketingActivityByMerchantListReq req) {
        Page<MarketingActivityDTO> page = new Page<>(req.getPageNo(),req.getPageSize());
        Page<MarketingActivityDTO> prodGoodsPage = activityGoodMapper.listMarketingActivityByMerchant(page, req);
        return prodGoodsPage;
    }
    
    
}
