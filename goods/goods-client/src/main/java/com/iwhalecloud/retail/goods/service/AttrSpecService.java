package com.iwhalecloud.retail.goods.service;


import com.iwhalecloud.retail.goods.dto.AttrSpecDTO;

import java.util.List;

public interface AttrSpecService{

    /**
     * 根据类别获取属性规格配置
     * @param typeId 类别ID
     * @return 配置的属性规格集合
     */
    public List<AttrSpecDTO> queryAttrSpecList(String typeId);

    /**
     * 查询类别属性与设置的实例值
     * @param typeId 类别ID
     * @param goodsId 商品ID
     * @return 类别的属性与配置的属性值
     */
    public List<AttrSpecDTO> queryAttrSpecWithInstValue(String typeId,String goodsId);
}