package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.SelectTagListDTO;
import com.iwhalecloud.retail.oms.dto.TagDTO;
import com.iwhalecloud.retail.oms.dto.resquest.TagPageReq;
import com.iwhalecloud.retail.oms.entity.TTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<TTag> {

    int createTag(TagDTO dto);

    int deleteTag(TagDTO dto);

    int editTag(TagDTO dto);

    Page<SelectTagListDTO> queryTagList(Page<SelectTagListDTO> page, @Param("pageReq") TagPageReq pageReq);

    /**
     * 查询标签详细信息
     *
     * @param tagIds
     * @return
     */
    List<TagDTO> queryTagListByParam(List<Long> tagIds);

    /**
     * 查询标签详细信息
     *
     * @param tagName
     * @return
     */
    List<TagDTO> queryTagListByTagName(@Param("tagName") String tagName);

    TagDTO queryTagDetailByTagName(String tagName);
}
