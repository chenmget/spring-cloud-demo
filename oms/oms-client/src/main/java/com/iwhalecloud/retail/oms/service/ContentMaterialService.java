package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.oms.dto.ContentMaterialDTO;

import java.util.List;

public interface ContentMaterialService{

    /**
     * 查询内容素材信息
     * @param contentId
     * @return
     */
    public List<ContentMaterialDTO> queryContentMaterialList(Long contentId);

}