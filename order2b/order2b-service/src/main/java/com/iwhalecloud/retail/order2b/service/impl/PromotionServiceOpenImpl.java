package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.PromotionResp;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.PromotionListReq;
import com.iwhalecloud.retail.order2b.entity.Promotion;
import com.iwhalecloud.retail.order2b.manager.PromotionManager;
import com.iwhalecloud.retail.order2b.model.PromotionModel;
import com.iwhalecloud.retail.order2b.service.PromotionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author he.sw
 * @Date 2019/07/08
 **/
@Service
public class PromotionServiceOpenImpl implements PromotionService {

    @Autowired
    private PromotionManager promotionManager;

    @Override
    public ResultVO<List<PromotionResp>> selectPromotion(PromotionListReq req){
        PromotionModel promotionModel = new PromotionModel();
        BeanUtils.copyProperties(req, promotionModel);

        List<Promotion> promotionList = promotionManager.selectPromotion(promotionModel);
        if (CollectionUtils.isEmpty(promotionList)) {
            return ResultVO.success();
        }

        List<PromotionResp> respList = new ArrayList<>(promotionList.size());
        for (Promotion promotion : promotionList){
            PromotionResp resp = new PromotionResp();
            BeanUtils.copyProperties(promotion, resp);
            respList.add(resp);
        }
        return ResultVO.success(respList);
    }

}
