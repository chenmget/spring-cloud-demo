package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.ContentPublishDTO;
import com.iwhalecloud.retail.oms.entity.ContentPublish;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import com.iwhalecloud.retail.oms.mapper.ContentPublishMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Component
public class ContentPublishManager {
    @Resource
    private ContentPublishMapper contentPublishMapper;


    public List<ContentPublishDTO> queryContentPublishList(Long contentId) {
        List<ContentPublish> contentPublishList = contentPublishMapper.queryContentPublishList(contentId);
        List<ContentPublishDTO> contentPublishDTOList = new ArrayList();
        int index = 0;
        for(ContentPublish contentPublish: contentPublishList){
            ContentPublishDTO contentPublishDTO = new ContentPublishDTO();
            BeanUtils.copyProperties(contentPublish, contentPublishDTO);
            contentPublishDTOList.add(index++, contentPublishDTO);
        }

        return contentPublishDTOList;
    }

    public int deleteContentPublish(ContentPublishDTO dto) {
        ContentPublish contentPublish = new ContentPublish();
        BeanUtils.copyProperties(dto, contentPublish);
        return contentPublishMapper.deleteContentPublish(contentPublish);
    }

    public int createContentPublish(ContentPublishDTO dto) {
        ContentPublish contentPublish = new ContentPublish();
        BeanUtils.copyProperties(dto, contentPublish);
        int t = contentPublishMapper.insert(contentPublish);
        return t;
    }

    public int updateContentPublishStatus(ContentPublish dto) {
        return contentPublishMapper.updateById(dto);
    }
}
