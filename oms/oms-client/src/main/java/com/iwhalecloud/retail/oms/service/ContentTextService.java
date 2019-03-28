package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.oms.dto.ContentTextDTO;

import java.util.List;

public interface ContentTextService{

    /**
     * 查询软文信息
     * @param contentid
     * @return
     */
    public List<ContentTextDTO> queryContentText(Long contentid);

}