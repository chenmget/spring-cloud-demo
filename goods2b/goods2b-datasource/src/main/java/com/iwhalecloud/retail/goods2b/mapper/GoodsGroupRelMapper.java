package com.iwhalecloud.retail.goods2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods2b.entity.GoodsGroupRel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author My
 * @Date 2018/11/5
 **/
@Mapper
public interface GoodsGroupRelMapper extends BaseMapper<GoodsGroupRel> {

    int getGoodsNum(String groupId);
}
