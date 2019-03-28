package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.ContentMaterialDTO;
import com.iwhalecloud.retail.oms.entity.ContentMaterial;
import com.iwhalecloud.retail.oms.mapper.ContentMaterialMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ContentMaterialManager{
    @Resource
    private ContentMaterialMapper contentMaterialMapper;
    @Value("${fdfs.showUrl}")
    private String showUrl;


    public List<ContentMaterialDTO> queryContentMaterialList(Long contentId) {
        List<ContentMaterialDTO> contentMaterialList = contentMaterialMapper.queryContentMaterialList(contentId);
        for (ContentMaterialDTO contentMaterialDTO: contentMaterialList){
            //对于素材中存储的图片URL，统一增加fdfs.showUrl的配置
            String path = contentMaterialDTO.getPath();
            if(!StringUtils.isEmpty(path) && !StringUtils.isEmpty(showUrl) && !path.toLowerCase().contains("http")){
                contentMaterialDTO.setPath(showUrl + path);
            }
            String thumbpath = contentMaterialDTO.getThumbpath();
            if(!StringUtils.isEmpty(thumbpath) && !StringUtils.isEmpty(showUrl) && !thumbpath.toLowerCase().contains("http")){
                contentMaterialDTO.setThumbpath(showUrl + thumbpath);
            }
        }
        return contentMaterialList;
    }


    public ContentMaterialDTO insertContentMaterial(ContentMaterialDTO contentMaterialDTO){
        ContentMaterial contentMaterial = new ContentMaterial();
        BeanUtils.copyProperties(contentMaterialDTO,contentMaterial);
        contentMaterialMapper.insert(contentMaterial);
        BeanUtils.copyProperties(contentMaterial,contentMaterialDTO);
        return contentMaterialDTO;
    }

    public ContentMaterialDTO updateContentMaterial(ContentMaterialDTO contentMaterialDTO){
        ContentMaterial contentMaterial = new ContentMaterial();
        BeanUtils.copyProperties(contentMaterialDTO,contentMaterial);
        contentMaterialMapper.updateById(contentMaterial);
        BeanUtils.copyProperties(contentMaterial,contentMaterialDTO);
        return contentMaterialDTO;
    }

    public int deleteContentMaterial(ContentMaterialDTO contentMaterialDTO){
        Map<String,Object> contentMaterialMap = new HashMap<String,Object>();
        contentMaterialMap.put("CONTENTID",contentMaterialDTO.getContentid());
        return contentMaterialMapper.deleteByMap(contentMaterialMap);
    }
}
