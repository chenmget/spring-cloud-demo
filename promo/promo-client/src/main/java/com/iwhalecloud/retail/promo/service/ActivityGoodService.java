package com.iwhalecloud.retail.promo.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.MarketingActivityDTO;
import com.iwhalecloud.retail.promo.dto.req.ActivityGoodsByMerchantReq;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityByMerchantListReq;
import com.iwhalecloud.retail.promo.dto.resp.ActivityGoodsByMerchantResp;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityByMerchantResp;

public interface ActivityGoodService {

    ResultVO<Page<MarketingActivityDTO>> listMarketingActivityByMerchant(MarketingActivityByMerchantListReq req);

    ResultVO<ActivityGoodsByMerchantResp> listActivityGoodsByActivityId(ActivityGoodsByMerchantReq req);
}
