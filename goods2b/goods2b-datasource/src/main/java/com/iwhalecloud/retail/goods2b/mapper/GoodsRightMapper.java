package com.iwhalecloud.retail.goods2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods2b.entity.GoodsRight;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2019/2/23.
 */
@Mapper
public interface GoodsRightMapper extends BaseMapper<GoodsRight> {

    public int batchAddGoodsRight(List<GoodsRight> goodsRightList);
}
