package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.ContentMaterialDTO;
import com.iwhalecloud.retail.oms.dto.ContentPicDTO;
import com.iwhalecloud.retail.oms.entity.ContentPic;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.oms.mapper.ContentPicMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ContentPicManager{
    @Resource
    private ContentPicMapper contentPicMapper;

    /**
     * 查询单图片内容
     * @param contentId
     * @return
     */
    public  List<ContentPicDTO> queryContentPicList(Long contentId){
        return contentPicMapper.queryContentPicList(contentId);
    }

    public ContentPicDTO insertContentPic(ContentPicDTO contentPicDTO){
        ContentPic contentPic = new ContentPic();
        BeanUtils.copyProperties(contentPicDTO,contentPic);
        contentPicMapper.insert(contentPic);
        BeanUtils.copyProperties(contentPic,contentPicDTO);
        return contentPicDTO;
    }

    public ContentPicDTO updateContentPic(ContentPicDTO contentPicDTO){
        ContentPic contentPic = new ContentPic();
        BeanUtils.copyProperties(contentPicDTO,contentPic);
        contentPicMapper.updateById(contentPic);
        BeanUtils.copyProperties(contentPic,contentPicDTO);
        return contentPicDTO;
    }

    public int deleteContentPic(ContentPicDTO contentPicDTO){
        Map<String,Object> contentContentPicMap = new HashMap<String,Object>();
        contentContentPicMap.put("CONTENTID",contentPicDTO.getContentid());
        return contentPicMapper.deleteByMap(contentContentPicMap);
    }

    /**
     * 通过objId查询单图片内容
     * @param objIds
     * @return
     */
    public List<ContentPicDTO> queryContentPicListByObjId(List<String> objIds){
        return contentPicMapper.queryContentPicListByObjId(objIds);
    }

    public List<Long> queryContentIdListByProductId(String productId){
        return contentPicMapper.queryContentIdListByProductId(productId);
    }
}
