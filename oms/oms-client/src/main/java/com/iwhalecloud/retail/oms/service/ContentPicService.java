package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.oms.dto.ContentPicDTO;

import java.util.List;

public interface ContentPicService{

    /**
     * 查询单图片内容
     * @param contentId
     * @return
     */
    public List<ContentPicDTO> queryContentPicList(Long contentId);

}