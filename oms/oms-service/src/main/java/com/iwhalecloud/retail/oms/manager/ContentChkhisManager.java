package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.entity.ContentChkhis;
import com.iwhalecloud.retail.oms.mapper.ContentChkhisMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class ContentChkhisManager{
    @Resource
    private ContentChkhisMapper contentChkhisMapper;
    
    
    public  int insertContentChkhis(ContentChkhis contentChkhis){
        return  contentChkhisMapper.insert(contentChkhis);
    }

    public  int updateContentChkhis(ContentChkhis contentChkhis){
        return  contentChkhisMapper.updateContentChkhis(contentChkhis);
    }


}
