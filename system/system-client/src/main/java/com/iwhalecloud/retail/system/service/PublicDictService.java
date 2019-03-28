package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.system.dto.PublicDictDTO;

import java.util.List;

public interface PublicDictService{

    /**
     * 根据类型查询 字典列表
     * @param type
     * @return
     */
    List<PublicDictDTO> queryPublicDictListByType(String type);
}