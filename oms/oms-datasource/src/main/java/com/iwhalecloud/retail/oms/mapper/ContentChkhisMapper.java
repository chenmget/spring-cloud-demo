package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.oms.entity.ContentChkhis;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Class: ContentChkhisMapper
 * @author autoCreate
 */
@Mapper
public interface ContentChkhisMapper extends BaseMapper<ContentChkhis>{

    int updateContentChkhis(ContentChkhis contentChkhis);

}