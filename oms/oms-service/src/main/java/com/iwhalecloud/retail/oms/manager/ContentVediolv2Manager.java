package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.ContentVediolv2DTO;
import com.iwhalecloud.retail.oms.entity.ContentVediolv2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.oms.mapper.ContentVediolv2Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ContentVediolv2Manager{
    @Resource
    private ContentVediolv2Mapper contentVediolv2Mapper;


    public List<ContentVediolv2DTO> queryContentVediolTwoList(List<Long> matids){
        return contentVediolv2Mapper.queryContentVediolTwoList(matids);
    }

    public List<ContentVediolv2DTO> queryContentVediolByUpmatid(List<Long> matids){
        return contentVediolv2Mapper.queryContentVediolByUpmatid(matids);
    }

    public ContentVediolv2DTO insertContentVediolv2(ContentVediolv2DTO contentVediolv2DTO){
        ContentVediolv2 contentVediolv2 = new ContentVediolv2();
        BeanUtils.copyProperties(contentVediolv2DTO,contentVediolv2);
        contentVediolv2Mapper.insert(contentVediolv2);
        BeanUtils.copyProperties(contentVediolv2,contentVediolv2DTO);
        return contentVediolv2DTO;
    }

    public int deleteContentVediolv2(ContentVediolv2DTO contentVediolv2DTO){
        Map<String,Object> contentVediolv2Map = new HashMap<String,Object>();
        contentVediolv2Map.put("MATID",contentVediolv2DTO.getMatid());
        return contentVediolv2Mapper.deleteByMap(contentVediolv2Map);
    }

    public List<Long> queryVideolv2ContentIdsByProductIds(List<String> productIds){
        return contentVediolv2Mapper.queryVideolv2ContentIdsByProductIds(productIds);
    }

}
