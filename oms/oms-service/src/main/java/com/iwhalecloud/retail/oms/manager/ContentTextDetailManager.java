package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.ContentTextDetailDTO;
import com.iwhalecloud.retail.oms.entity.ContentTextDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.oms.mapper.ContentTextDetailMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ContentTextDetailManager{
    @Resource
    private ContentTextDetailMapper contentTextDetailMapper;

    public  int insertContentTextDetail(ContentTextDetailDTO contentTextDetailDTO){
        ContentTextDetail contentTextDetail = new ContentTextDetail();
        BeanUtils.copyProperties(contentTextDetailDTO,contentTextDetail);
        return contentTextDetailMapper.insert(contentTextDetail);
    }

    public  int updateContentTextDetail(ContentTextDetailDTO contentTextDetailDTO){
        ContentTextDetail contentTextDetail = new ContentTextDetail();
        BeanUtils.copyProperties(contentTextDetailDTO,contentTextDetail);
        return contentTextDetailMapper.updateById(contentTextDetail);
    }

    public List<ContentTextDetailDTO> queryContentTextDetail(Long textid){
        return contentTextDetailMapper.queryContentTextDetail(textid);
    }

    public  int deleteContentTextDetailByContentId(ContentTextDetailDTO contentTextDetailDTO){
        Map<String,Object> contentTextDetailMap = new HashMap<String,Object>();
        contentTextDetailMap.put("TXT_CONTENT_ID",contentTextDetailDTO.getTxtContentId());
        return contentTextDetailMapper.deleteByMap(contentTextDetailMap);
    }
}
