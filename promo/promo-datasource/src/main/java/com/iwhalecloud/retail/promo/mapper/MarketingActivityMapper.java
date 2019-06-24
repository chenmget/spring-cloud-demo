package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.promo.dto.MarketingActivityDTO;
import com.iwhalecloud.retail.promo.dto.MarketingActivityReq;
import com.iwhalecloud.retail.promo.dto.MarketingActivityResp;
import com.iwhalecloud.retail.promo.dto.req.AdvanceActivityProductInfoReq;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityListReq;
import com.iwhalecloud.retail.promo.dto.resp.AdvanceActivityProductInfoResp;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityListResp;
import com.iwhalecloud.retail.promo.entity.MarketingActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author autoCreate
 * @Class: MarketingActivityMapper
 */
@Mapper
public interface MarketingActivityMapper extends BaseMapper<MarketingActivity> {
    /**
     * 查询活动列表(分页)
     * @param page
     * @param req
     * @return
     */
    Page<MarketingActivityListResp> listMarketingActivity(Page<MarketingActivityListResp> page, @Param("req") MarketingActivityListReq req);

    /**
	 * 通过营销活动名字查询营销活动
	 * @param req
	 * @return
	 */
    List<MarketingActivityResp> getMarketingCampaign(@Param("req") MarketingActivityReq req);
    /**
     * 查询预售活动的单个产品信息
     *
     * @param advanceActivityProductInfoReq
     * @return
     */
    AdvanceActivityProductInfoResp getAdvanceActivityProductInfo(@Param("req") AdvanceActivityProductInfoReq advanceActivityProductInfoReq);

    /**
     * 根据ID查询营销活动
     * @param id
     * @return
     */
    MarketingActivityDTO getMarketingActivityDtoById(String id);
    
    public MarketingActivity queryMarketingActivityTime(@Param("marketingActivityId") String marketingActivityId);

    /**
     * 查询生效的前置补贴活动
     * @param marketingActivityListReq
     * @return
     */
    List<MarketingActivityListResp> queryInvalidActivity(MarketingActivityListReq marketingActivityListReq);
}