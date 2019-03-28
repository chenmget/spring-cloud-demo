package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.dto.ContentPicDTO;
import com.iwhalecloud.retail.oms.entity.ContentPic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
/**
 * @Class: ContentPicMapper
 * @author autoCreate
 */
@Mapper
public interface ContentPicMapper extends BaseMapper<ContentPic>{


    public List<ContentPicDTO> queryContentPicList(@Param("contentId") Long contentId);

    public List<ContentPicDTO> queryContentPicListByObjId(@Param("objIds") List<String> objIds);

    public List<Long> queryContentIdListByProductId(@Param("productId") String productId);
}