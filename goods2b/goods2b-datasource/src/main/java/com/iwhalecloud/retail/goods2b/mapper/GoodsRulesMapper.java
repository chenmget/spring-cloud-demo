package com.iwhalecloud.retail.goods2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods2b.entity.GoodsRules;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsRulesMapper extends BaseMapper<GoodsRules> {

    /**
     * 批量插入数据
     * @param entityList
     * @return
     */
    int insertBatch(List<GoodsRules> entityList);

    /**
     * 批量更新
     * @param entityList
     * @return
     */
    int updateBatch(List<GoodsRules> entityList);

    /**
     * 修改提货数量
     * @param goodsRules
     * @return
     */
    int updatePurchaseNum(GoodsRules goodsRules);
}
