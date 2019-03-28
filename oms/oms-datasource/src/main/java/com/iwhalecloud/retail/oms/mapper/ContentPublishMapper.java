package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.oms.entity.ContentPublish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author autoCreate
 * @Class: ContentPublishMapper
 */
@Mapper
public interface ContentPublishMapper extends BaseMapper<ContentPublish> {

    List<ContentPublish> queryContentPublishList(@Param("contentId")Long contentId);

    int deleteContentPublish(@Param("contentPublish")ContentPublish contentPublish);
}