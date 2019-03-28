package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.oms.dto.ContentTagDTO;
import java.util.List;

public interface ContentTagService{

    /**
     * 查询内容标签详情
     * @param contentTagDTO
     * @return
     */
    public List<ContentTagDTO> queryContentTag(ContentTagDTO contentTagDTO);

}