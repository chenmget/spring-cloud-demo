package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.entity.ContentPicset;
import com.iwhalecloud.retail.oms.dto.ContentPicsetDTO;
import com.iwhalecloud.retail.oms.dto.response.ContentPicsetListRespDTO;
import com.iwhalecloud.retail.oms.entity.ContentPicset;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.oms.mapper.ContentPicsetMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ContentPicsetManager{
    @Resource
    private ContentPicsetMapper contentPicsetMapper;

    /**
     * 查询推广内容信息
     * @param contentId
     * @return
     */
    public List<ContentPicsetDTO> queryContentPicsetList(Long contentId){
        List<ContentPicset> contentPicsetList = contentPicsetMapper.queryContentPicsetList(contentId);
        List<ContentPicsetDTO> contentTagDTOList = new ArrayList<>();
        int index = 0;
        for(ContentPicset contentPicset: contentPicsetList){
            ContentPicsetDTO contentPicsetDTO = new ContentPicsetDTO();
            BeanUtils.copyProperties(contentPicset,contentPicsetDTO);
            contentTagDTOList.add(index++, contentPicsetDTO);
        }
        return contentTagDTOList;
    }

    public ContentPicsetDTO insertContentPicset(ContentPicsetDTO contentPicsetDTO){
        ContentPicset contentPicset = new ContentPicset();
        BeanUtils.copyProperties(contentPicsetDTO,contentPicset);
        contentPicsetMapper.insert(contentPicset);
        BeanUtils.copyProperties(contentPicset,contentPicsetDTO);
        return contentPicsetDTO;
    }

    public int deleteContentPicset(ContentPicsetDTO contentPicsetDTO){
        Map<String,Object> contentPicsetMap = new HashMap<String,Object>();
        contentPicsetMap.put("CONTENTID",contentPicsetDTO.getContentid());
        return contentPicsetMapper.deleteByMap(contentPicsetMap);
    }

    public List<Long> queryContentIdsByProductIds(List<String> productIds){
        return contentPicsetMapper.queryContentIdsByProductIds(productIds);
    }

}
