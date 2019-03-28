package com.iwhalecloud.retail.oms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.SelectTagListDTO;
import com.iwhalecloud.retail.oms.dto.TagDTO;
import com.iwhalecloud.retail.oms.dto.resquest.TagPageReq;

import java.util.List;

public interface TagService {

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    int createTag(TagDTO dto);

    /**
     * 删除
     *
     * @param dto
     * @return
     */
    int deleteTag(TagDTO dto);

    /**
     * 编辑
     *
     * @param dto
     * @return
     */
    int editTag(TagDTO dto);

    /**
     * 查询
     *
     * @param page
     * @return
     */
    Page<SelectTagListDTO> queryTagList(TagPageReq page);

    /**
     * @Auther: lin.wh
     * @Date: 2018/11/10
     * @Description:
     */
    TagDTO queryTagDetailByTagName(String tagName);

    /**
     * 查询标签详细信息
     *
     * @param tagIds
     * @return
     */
    List<TagDTO> queryTagListByParam(List<Long> tagIds);

}
