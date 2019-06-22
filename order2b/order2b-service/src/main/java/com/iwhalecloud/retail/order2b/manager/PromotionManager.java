package com.iwhalecloud.retail.order2b.manager;

import com.iwhalecloud.retail.order2b.entity.Promotion;
import com.iwhalecloud.retail.order2b.mapper.PromotionMapper;
import com.iwhalecloud.retail.order2b.model.PromotionModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class PromotionManager {

    @Resource
    private PromotionMapper promotionMapper;

    public int insertPromotionList(List<Promotion> list){
        return promotionMapper.batchInsertPromotion(list);
    }

    public List<Promotion> selectPromotion(PromotionModel model){
        return promotionMapper.selectPromotion(model);
    }

}
