package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.ContentTextDTO;
import com.iwhalecloud.retail.oms.entity.ContentText;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.oms.mapper.ContentTextMapper;

import java.util.List;


@Component
public class ContentTextManager{
    @Resource
    private ContentTextMapper contentTextMapper;

    public List<ContentTextDTO> queryContentText(Long contentid){
        return contentTextMapper.queryContentText(contentid);
    }


    public ContentTextDTO insertContentText(ContentTextDTO contentTextDTO){
        ContentText contentText = new ContentText();
        BeanUtils.copyProperties(contentTextDTO,contentText);
        contentTextMapper.insert(contentText);
        BeanUtils.copyProperties(contentText,contentTextDTO);
        return contentTextDTO;
    }

    public ContentTextDTO updateContentText(ContentTextDTO contentTextDTO){
        ContentText contentText = new ContentText();
        BeanUtils.copyProperties(contentTextDTO,contentText);
        contentTextMapper.updateById(contentText);
        BeanUtils.copyProperties(contentText,contentTextDTO);
        return contentTextDTO;
    }

}
