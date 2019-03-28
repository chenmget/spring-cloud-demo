package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.ContentVideosDTO;
import com.iwhalecloud.retail.oms.entity.ContentVideos;
import com.iwhalecloud.retail.oms.mapper.ContentVideosMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ContentVideosManager {
    @Resource
    private ContentVideosMapper contentVideosMapper;

    public ContentVideosDTO insertContentVideosDefaultContent(ContentVideosDTO contentVideosDTO){
        ContentVideos contentVideos = new ContentVideos();
        BeanUtils.copyProperties(contentVideosDTO, contentVideos);
        contentVideosMapper.insert(contentVideos);
        BeanUtils.copyProperties(contentVideos, contentVideosDTO);
        return contentVideosDTO;
    }

    public ContentVideosDTO updateContentVideosDefaultContent(ContentVideosDTO contentVideosDTO){
        ContentVideos contentVideos = new ContentVideos();
        BeanUtils.copyProperties(contentVideosDTO, contentVideos);
        contentVideosMapper.updateById(contentVideos);
        BeanUtils.copyProperties(contentVideos, contentVideosDTO);
        return contentVideosDTO;
    }

    public List<ContentVideosDTO> queryContentVideoDefalutList(List<Long> contentIds){

        List<ContentVideos> contentVideosList = contentVideosMapper.queryContentVideoDefalutList(contentIds);
        List<ContentVideosDTO> contentVideosDTOList = new ArrayList<>();
        int index = 0;
        for(ContentVideos contentVideos : contentVideosList){
            ContentVideosDTO contentVideosDTO = new ContentVideosDTO();
            BeanUtils.copyProperties(contentVideos, contentVideosDTO);
            contentVideosDTOList.add(index++, contentVideosDTO);
        }
        return contentVideosDTOList;
    }

    public int deleteContentVideosDefaultContent(ContentVideosDTO contentVideosDTO){
        Map<String,Object> contentVideosDefaultContentMap = new HashMap<String,Object>();
        contentVideosDefaultContentMap.put("CONTENTID", contentVideosDTO.getContentid());
        return contentVideosMapper.deleteByMap(contentVideosDefaultContentMap);
    }

    public List<Long> queryVideoContentIdsByProductIds(List<String> productIds){
        return contentVideosMapper.queryVideoContentIdsByProductIds(productIds);
    }

}
