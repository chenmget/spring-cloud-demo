package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ActivityGoodsDTO;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsProductRelService;
import com.iwhalecloud.retail.promo.dto.ActivityGoodDTO;
import com.iwhalecloud.retail.promo.dto.req.ActivityGoodsByMerchantReq;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityByMerchantListReq;
import com.iwhalecloud.retail.promo.dto.resp.ActivityGoodsByMerchantResp;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityByMerchantResp;
import com.iwhalecloud.retail.promo.entity.ActivityProduct;
import com.iwhalecloud.retail.promo.manager.ActivityGoodManager;
import com.iwhalecloud.retail.promo.manager.ActivityProductManager;
import com.iwhalecloud.retail.promo.service.ActivityGoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li.xinhang
 * @date 2019/3/5
 */
@Slf4j
@Component("activityGoodService")
@Service
public class ActivityGoodServiceImpl implements ActivityGoodService {

    @Autowired
    private ActivityProductManager activityProductManager;

    @Autowired
    private ActivityGoodManager activityGoodManager;
    
    @Reference
    private GoodsProductRelService goodsProductRelService;

    /**
     * 根据登录用户及商家ID查询活动列表
     * @param req
     * @return
     */
    @Override
    public ResultVO<Page<MarketingActivityByMerchantResp>> listMarketingActivityByMerchant(MarketingActivityByMerchantListReq req) {
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
        List<ActivityProduct> activityGoodsList = activityProductManager.queryActivityProductByCondition(req.getActivityId());
        if(activityGoodsList.size() < 1){
            return ResultVO.success();
        }
        List<String> productIds = new ArrayList();
        for (int i = 0; i < activityGoodsList.size(); i++) {
            productIds.add(activityGoodsList.get(i).getProductId());
        }
        ResultVO<List<ActivityGoodsDTO>> resultVO = goodsProductRelService.qryActivityGoodsId(productIds,req.getRegionId(),req.getLanId(),req.getMerchantId());
        if(!resultVO.isSuccess()) {
            return ResultVO.error(resultVO.getResultMsg());
        }
        List<ActivityGoodsDTO> activityGoodsDTOS = resultVO.getResultData();
        
        if(activityGoodsDTOS != null) {
            for (int i = 0; i < activityGoodsDTOS.size(); i++) {
                ActivityGoodDTO activityGoodDTO = new ActivityGoodDTO();
                BeanUtils.copyProperties(activityGoodsDTOS.get(i), activityGoodDTO);
                activityGoodDTOList.add(activityGoodDTO);
            }
        }
        activityGoodsByMerchantResp.setGoodsList(activityGoodDTOList);
                            
        return ResultVO.success(activityGoodsByMerchantResp);
    }
}
