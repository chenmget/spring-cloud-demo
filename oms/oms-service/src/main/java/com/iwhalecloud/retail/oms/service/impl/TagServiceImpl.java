package com.iwhalecloud.retail.oms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.SelectTagListDTO;
import com.iwhalecloud.retail.oms.dto.TagDTO;
import com.iwhalecloud.retail.oms.dto.resquest.TagPageReq;
import com.iwhalecloud.retail.oms.entity.TTag;
import com.iwhalecloud.retail.oms.manager.TagManager;
import com.iwhalecloud.retail.oms.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/23 15:41
 * @Description:
 */

@Slf4j
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagManager tagManager;

    @Override
    public int createTag(TagDTO dto) {
        int t = tagManager.createTag(dto);
        return t;
    }

    @Override
    public int deleteTag(TagDTO dto) {
        int t = tagManager.deleteTag(dto);
        return t;
    }

    @Override
    public int editTag(TagDTO dto) {
        int t = tagManager.editTag(dto);
        return t;
    }

    @Override
    public Page<SelectTagListDTO> queryTagList(TagPageReq page) {
        return tagManager.queryTagList(page);
    }

    @Override
    public TagDTO queryTagDetailByTagName(String tagName) {
        return tagManager.queryTagDetailByTagName(tagName);
    }

    @Override
    public List<TagDTO> queryTagListByParam(List<Long> tagIds) {
        return tagManager.queryTagListByParam(tagIds);
    }
}

