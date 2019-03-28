package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.entity.ContentPicset;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ContentPicsetMapper
 * @author autoCreate
 */
@Mapper
public interface ContentPicsetMapper extends BaseMapper<ContentPicset>{

    public List<ContentPicset> queryContentPicsetList(@Param("contentId") Long contentId);

    /**
     * 根据商品ID集合查询内容ID集合
     * @param productIds
     * @return
     */
    public List<Long> queryContentIdsByProductIds(@Param("productIds") List<String> productIds);

}