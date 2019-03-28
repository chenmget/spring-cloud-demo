package com.iwhalecloud.retail.oms.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.SelectTagListDTO;
import com.iwhalecloud.retail.oms.dto.TagDTO;
import com.iwhalecloud.retail.oms.dto.resquest.TagPageReq;
import com.iwhalecloud.retail.oms.entity.TTag;
import com.iwhalecloud.retail.oms.mapper.TagMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/23 16:28
 * @Description:
 */

@Component
public class TagManager {
    @Resource
    private TagMapper tagMapper;

    public int createTag(TagDTO dto) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setUpdDate(new Date());
        dto.setTagId(null);
        TTag total = new TTag();
        BeanUtils.copyProperties(dto, total);
        int i = tagMapper.insert(total);
        return i;
    }

    public int deleteTag(TagDTO dto) {
        return tagMapper.deleteTag(dto);
    }

    public int editTag(TagDTO dto) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setUpdDate(new Date());
        return tagMapper.editTag(dto);
    }

    public Page<SelectTagListDTO> queryTagList(TagPageReq pageReq) {
        Page<SelectTagListDTO> page = new Page<SelectTagListDTO>(pageReq.getPageNo(), pageReq.getPageSize());
        return tagMapper.queryTagList(page, pageReq);
    }

    /**
     * 查询标签详细信息
     *
     * @param tagIds
     * @return
     */
    public List<TagDTO> queryTagListByParam(List<Long> tagIds) {
        return tagMapper.queryTagListByParam(tagIds);
    }

    /**
     * 查询标签详细信息
     *
     * @param tagName
     * @return
     */
    public List<TagDTO> queryTagListByTagName(String tagName) {
        return tagMapper.queryTagListByTagName(tagName);
    }

    public TagDTO queryTagDetailByTagName(String tagName) {
        return tagMapper.queryTagDetailByTagName(tagName);
    }
}

