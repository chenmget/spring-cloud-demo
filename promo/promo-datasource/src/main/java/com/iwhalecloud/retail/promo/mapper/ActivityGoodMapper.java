package com.iwhalecloud.retail.promo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityByMerchantListReq;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityByMerchantResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author li.xinhang
 * @date 2019/3/5
 */
@Mapper
public interface ActivityGoodMapper extends BaseMapper {

    Page<MarketingActivityByMerchantResp> listMarketingActivityByMerchant(Page<MarketingActivityByMerchantResp> page, @Param("req") MarketingActivityByMerchantListReq req);
}
