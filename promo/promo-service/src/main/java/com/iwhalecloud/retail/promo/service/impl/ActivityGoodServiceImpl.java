package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ActivityGoodsDTO;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsProductRelService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MemchantTempService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.ActivityGoodDTO;
import com.iwhalecloud.retail.promo.dto.MarketingActivityDTO;
import com.iwhalecloud.retail.promo.dto.req.ActivityGoodsByMerchantReq;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityByMerchantListReq;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityListReq;
import com.iwhalecloud.retail.promo.dto.resp.ActivityGoodsByMerchantResp;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityByMerchantResp;
import com.iwhalecloud.retail.promo.entity.ActivityProduct;
import com.iwhalecloud.retail.promo.entity.MarketingActivity;
import com.iwhalecloud.retail.promo.manager.ActivityGoodManager;
import com.iwhalecloud.retail.promo.manager.ActivityProductManager;
import com.iwhalecloud.retail.promo.manager.MarketingActivityManager;
import com.iwhalecloud.retail.promo.service.ActivityGoodService;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author li.xinhang
 * @date 2019/3/5
 */
@Slf4j
@Component("activityGoodService")
@Service
public class ActivityGoodServiceImpl implements ActivityGoodService {

    @Autowired
    private MarketingActivityManager marketingActivityManager;

    @Autowired
    private ActivityProductManager activityProductManager;

    @Autowired
    private ActivityGoodManager activityGoodManager;

    @Autowired
    private MarketingActivityService marketingActivityService;

    @Reference
    private GoodsProductRelService goodsProductRelService;

    @Reference
    private MerchantService merchantService;

    /**
     * 根据登录用户及商家ID查询活动列表
     * @param req
     * @return
     */
    @Override
    public ResultVO<Page<MarketingActivityDTO>> listMarketingActivityByMerchant(MarketingActivityByMerchantListReq req) {
        // 1.通过商家id查询商家信息，获取商家pathCode
        MerchantDTO merchantDto = merchantService.getMerchantInfoById(req.getMerchantId());
        String pathCode = (merchantDto==null)?null:merchantDto.getParCrmOrgPathCode();
        // 1.查询参与对象类型为“按条件过滤”且有效的活动
        MarketingActivityListReq activityListReq = new MarketingActivityListReq();
        activityListReq.setActivityParticipantType(PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_30.getCode());
        List<MarketingActivity> marketingActivities = marketingActivityManager.queryAvailableActivityList(activityListReq);
        // 2.遍历获取参与对象包含该商家的活动id
        List<String> activityIds = new ArrayList<>(marketingActivities.size());
        for (MarketingActivity marketingActivity : marketingActivities) {
            String activityId = marketingActivity.getId();
            boolean isisExisting = marketingActivityService.isExistingInParticipantFilterValue(marketingActivity.getId(),req.getMerchantId(),req.getLanId(),pathCode);
            if (isisExisting){
                activityIds.add(activityId);
            }
        }
        //设置活动ids到入参请求体中
        req.setActivityIds(activityIds);
        return ResultVO.success(activityGoodManager.listMarketingActivityByMerchant(req));
    }

    /**
     * 根据活动ID查询活动商品
     * @param req
     * @return
     */
    @Override
    public ResultVO<ActivityGoodsByMerchantResp> listActivityGoodsByActivityId(ActivityGoodsByMerchantReq req) {
        ActivityGoodsByMerchantResp activityGoodsByMerchantResp = new ActivityGoodsByMerchantResp();
        List<ActivityGoodDTO> activityGoodDTOList = new ArrayList<>();
        activityGoodsByMerchantResp.setActivityId(req.getActivityId());
        activityGoodsByMerchantResp.setGoodsList(activityGoodDTOList);
        // 根据活动编码查询参与活动产品
        List<ActivityProduct> activityProductsList = activityProductManager.queryActivityProductByCondition(req.getActivityId());
        if(activityProductsList.size() < 1){
            return ResultVO.success();
        }
        List<String> productIds = new ArrayList();
        Map<String,Long> productPrices = new HashMap<>();
        for (ActivityProduct activityProduct:activityProductsList) {
            productIds.add(activityProduct.getProductId());
            //多个同样产品id时，取第一个产品的baseId和价格（后面要用到），已经包含就不添进去
            if(!productIds.contains(activityProduct.getProductId())){
                productPrices.put(activityProduct.getProductBaseId(),activityProduct.getPrice());
            }
        }
        //通过商家id查询商家信息，获取商家pathCode
        MerchantDTO merchantDto = merchantService.getMerchantInfoById(req.getMerchantId());
        String pathCode = (merchantDto==null)?null:merchantDto.getParCrmOrgPathCode();
        ResultVO<List<ActivityGoodsDTO>> resultVO = goodsProductRelService.qryActivityGoodsId(productIds,req.getMerchantId(),pathCode);
        if(!resultVO.isSuccess()) {
            return ResultVO.error(resultVO.getResultMsg());
        }
        List<ActivityGoodsDTO> activityGoodsDTOS = resultVO.getResultData();
        
        if(activityGoodsDTOS != null) {
            for (int i = 0; i < activityGoodsDTOS.size(); i++) {
                ActivityGoodDTO activityGoodDTO = new ActivityGoodDTO();
                BeanUtils.copyProperties(activityGoodsDTOS.get(i), activityGoodDTO);
                String productBaseId = activityGoodDTO.getProductBaseId();
                //获取活动产品的价格（供货价），设置到活动商品信息中
                Long price = productPrices.get(productBaseId);
                if(price!=null){
                    activityGoodDTO.setWholeSalePrice(String.valueOf(price));
                }
                activityGoodDTOList.add(activityGoodDTO);
            }
        }
        activityGoodsByMerchantResp.setGoodsList(activityGoodDTOList);
                            
        return ResultVO.success(activityGoodsByMerchantResp);
    }
}
