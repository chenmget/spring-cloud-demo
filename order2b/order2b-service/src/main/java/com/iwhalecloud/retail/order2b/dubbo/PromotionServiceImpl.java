package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.PromotionResp;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.PromotionListReq;
import com.iwhalecloud.retail.order2b.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author he.sw
 * @Date 2019/07/08
 **/
@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionService promotionService;

    @Override
    public ResultVO<List<PromotionResp>> selectPromotion(PromotionListReq req){
        return promotionService.selectPromotion(req);
    }

}
