package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.entity.ContentOrderpic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ContentOrderpicMapper
 * @author autoCreate
 */
@Mapper
public interface ContentOrderpicMapper extends BaseMapper<ContentOrderpic>{

    /**
     * 查询轮播图内容信息
     * @param contentId
     * @return
     */
    public List<ContentOrderpic> queryContentOrderPicList(@Param("contentId") Long contentId);


}