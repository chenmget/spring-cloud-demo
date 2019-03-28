package com.iwhalecloud.retail.goods2b.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.AttrSpecGroupDTO;

import java.util.List;

public interface AttrSpecGroupService {

    /**
     * 根据条件查询，条件为空时查询全部
     * @param condition
     * @return
     */
    ResultVO<List<AttrSpecGroupDTO>> listAttrSpecGroupByCondition(AttrSpecGroupDTO condition);


    /**
     * 新增
     * @param entity
     * @return
     */
    ResultVO addAttrSpecGroup(AttrSpecGroupDTO entity);

    /**
     * 删除
     * @param id
     * @return
     */
    ResultVO deleteAttrSpecGroup(String id);

    /**
     * 更改
     * @param entity
     * @return
     */
    ResultVO updateAttrSpecGroup(AttrSpecGroupDTO entity);
}