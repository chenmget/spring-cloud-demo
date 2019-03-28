package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.oms.dto.ContentOrderpicDTO;
import java.util.List;

public interface ContentOrderpicService{

    /**
     * 查询轮播图内容信息
     * @param contentId
     * @return
     */
    public List<ContentOrderpicDTO> queryContentOrderPicList(Long contentId);

}