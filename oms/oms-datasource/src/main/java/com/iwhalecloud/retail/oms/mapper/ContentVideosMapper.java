package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.oms.entity.ContentVideos;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ContentVideosMapper
 * @author autoCreate
 */
@Mapper
public interface ContentVideosMapper extends BaseMapper<ContentVideos>{

    public List<ContentVideos> queryContentVideoDefalutList(@Param("contentIds") List<Long> contentIds);

    /**
     * 根据商品ID集合查询内容ID集合
     * @param productIds
     * @return
     */
    public List<Long> queryVideoContentIdsByProductIds(@Param("productIds") List<String> productIds);

}