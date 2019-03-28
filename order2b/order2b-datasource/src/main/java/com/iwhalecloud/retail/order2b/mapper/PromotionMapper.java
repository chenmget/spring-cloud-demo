package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.order2b.config.WhaleCloudDBKeySequence;
import com.iwhalecloud.retail.order2b.entity.Promotion;
import com.iwhalecloud.retail.order2b.model.PromotionModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PromotionMapper extends BaseMapper<Promotion>{

    @WhaleCloudDBKeySequence
    int batchInsertPromotion(List<Promotion> list);

    List<Promotion> selectPromotion(PromotionModel promotionModel);





}
