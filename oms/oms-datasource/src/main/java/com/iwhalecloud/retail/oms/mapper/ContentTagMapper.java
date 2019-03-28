package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import com.iwhalecloud.retail.oms.dto.ContentTagDTO;
import com.iwhalecloud.retail.oms.entity.ContentTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: ContentTagMapper
 * @author autoCreate
 */
@Mapper
public interface ContentTagMapper extends BaseMapper<ContentTag>{

    /**
     * 查询内容标签详情
     * @param contentTagDTO
     * @return
     */
    public List<ContentTagDTO> queryContentTag(@Param("contentTagDTO")ContentTagDTO contentTagDTO);

    /**
     * 通过内容ID查询内容标签详情
     * @param contentId
     * @return
     */
    public List<ContentTag> queryContentTagByContentId(@Param("contentId")Long contentId);

}