package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.ContentOrderpicDTO;
import com.iwhalecloud.retail.oms.entity.ContentOrderpic;
import com.iwhalecloud.retail.oms.dto.response.ContentOrderpicListRespDTO;
import com.iwhalecloud.retail.oms.entity.ContentPic;
import org.apache.ibatis.annotations.Param;
import com.iwhalecloud.retail.oms.entity.ContentOrderpic;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.oms.mapper.ContentOrderpicMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ContentOrderpicManager{
    @Resource
    private ContentOrderpicMapper contentOrderpicMapper;

    /**
     * 查询轮播图内容信息
     * @param contentId
     * @return
     */
    public List<ContentOrderpicDTO> queryContentOrderPicList(Long contentId){
        List<ContentOrderpic> contentOrderpicList = contentOrderpicMapper.queryContentOrderPicList(contentId);
        List<ContentOrderpicDTO> contentOrderpicDTOList = new ArrayList<>();
        int index = 0;
        for(ContentOrderpic contentOrderpic: contentOrderpicList){
            ContentOrderpicDTO contentOrderpicDTO = new ContentOrderpicDTO();
            BeanUtils.copyProperties(contentOrderpic,contentOrderpicDTO);
            contentOrderpicDTOList.add(index++, contentOrderpicDTO);
        }
        return contentOrderpicDTOList;
    }

    public ContentOrderpicDTO insertContentOrderpic(ContentOrderpicDTO contentOrderpicDTO){
        ContentOrderpic contentOrderpic = new ContentOrderpic();
        BeanUtils.copyProperties(contentOrderpicDTO,contentOrderpic);
        contentOrderpicMapper.insert(contentOrderpic);
        BeanUtils.copyProperties(contentOrderpic,contentOrderpicDTO);
        return contentOrderpicDTO;
    }

    public int deleteContentOrderpic(ContentOrderpicDTO contentOrderpicDTO){
        Map<String,Object> contentOrderpicMap = new HashMap<String,Object>();
        contentOrderpicMap.put("CONTENTID",contentOrderpicDTO.getContentid());
        return contentOrderpicMapper.deleteByMap(contentOrderpicMap);
    }

}
