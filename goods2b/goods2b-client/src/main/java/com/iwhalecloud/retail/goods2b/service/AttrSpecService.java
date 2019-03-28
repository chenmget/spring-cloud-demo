package com.iwhalecloud.retail.goods2b.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.AttrSpecDTO;
import com.iwhalecloud.retail.goods2b.dto.req.AttrSpecAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.AttrSpecUpdateReq;

import java.util.List;

public interface AttrSpecService{

    /**
     * 根据类别获取属性规格配置
     * @param typeId 类别ID
     * @return 配置的属性规格集合
     */
    ResultVO<List<AttrSpecDTO>> queryAttrSpecList(String typeId);

    /**
     * 查询类别属性与设置的实例值
     * @param typeId 类别ID
     * @param goodsId 商品ID
     * @return 类别的属性与配置的属性值
     */
    ResultVO<List<AttrSpecDTO>> queryAttrSpecWithInstValue(String typeId,String goodsId);

    /**
     * 新增
     * @param req
     * @return
     */
    ResultVO addAttrSpec(AttrSpecAddReq req, String userId);

    /**
     * 删除
     * @param id
     * @return
     */
    ResultVO deleteAttrSpec(String id);

    /**
     * 更改
     * @param req
     * @return
     */
    ResultVO updateAttrSpec(AttrSpecUpdateReq req, String userId);

}