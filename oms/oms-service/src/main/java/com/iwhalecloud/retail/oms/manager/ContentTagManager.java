package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.entity.ContentTag;
import com.iwhalecloud.retail.oms.dto.ContentTagDTO;
import com.iwhalecloud.retail.oms.entity.ContentTag;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.oms.mapper.ContentTagMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ContentTagManager{
    @Resource
    private ContentTagMapper contentTagMapper;

    public ContentTagDTO insertContentTag(ContentTagDTO contentTagDTO){
        ContentTag contentTag = new ContentTag();
        BeanUtils.copyProperties(contentTagDTO,contentTag);
        contentTagMapper.insert(contentTag);
        BeanUtils.copyProperties(contentTag,contentTagDTO);
        return contentTagDTO;
    }

    public int deleteContentTag(ContentTagDTO contentTagDTO){
        Map<String,Object> contentTagMap = new HashMap<String,Object>();
//        ContentTag contentTag = new ContentTag();
//        BeanUtils.copyProperties(contentTagDTO,contentTag);
        contentTagMap.put("CONTENT_ID",contentTagDTO.getContentId());
        return contentTagMapper.deleteByMap(contentTagMap);
    }

    /**
     * 查询内容标签详情
     * @param contentTagDTO
     * @return
     */
    public List<ContentTagDTO> queryContentTag(ContentTagDTO contentTagDTO){
        return  contentTagMapper.queryContentTag(contentTagDTO);
    }

    /**
     * 通过内容ID查询内容标签详情
     * @param contentId
     * @return
     */
    public List<ContentTagDTO> queryContentTagByContentId(Long contentId){
        ContentTagDTO contentTagDTO = new ContentTagDTO();
        contentTagDTO.setContentId(contentId);
        List<ContentTagDTO> contentTagList = contentTagMapper.queryContentTag(contentTagDTO);
        return contentTagList;
    }
}
