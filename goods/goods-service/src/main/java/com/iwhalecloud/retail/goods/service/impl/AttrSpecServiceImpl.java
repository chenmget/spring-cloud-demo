package com.iwhalecloud.retail.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.goods.dto.AttrSpecDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods.manager.AttrSpecManager;
import com.iwhalecloud.retail.goods.service.AttrSpecService;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
@Slf4j
public class AttrSpecServiceImpl implements AttrSpecService {

    @Autowired
    private AttrSpecManager attrSpecManager;


    /**
     * 根据类别获取属性规格配置
     * @param typeId 类别ID
     * @return 配置的属性规格集合
     *
     */
    @Override
    public List<AttrSpecDTO> queryAttrSpecList(String typeId) {

        log.info("AttrSpecServiceImpl.queryAttrSpecList typeId={}",typeId);
        if (StringUtils.isEmpty(typeId)) {
            return null;
        }

        List<AttrSpecDTO> attrSpecDTOs =  attrSpecManager.queryAttrSpecList(typeId);
        log.info("AttrSpecServiceImpl.queryAttrSpecList attrSpecDTOs={}", JSON.toJSON(attrSpecDTOs));
        return attrSpecDTOs;
    }

    /**
     * 查询类别属性与设置的实例值
     * @param typeId 类别ID
     * @param goodsId 商品ID
     * @return 类别的属性与配置的属性值
     */
    @Override
    public List<AttrSpecDTO> queryAttrSpecWithInstValue(String typeId,String goodsId) {

        log.info("AttrSpecServiceImpl.queryAttrSpecWithInstValue typeId={}",typeId);
        if (StringUtils.isEmpty(typeId) || StringUtils.isEmpty(goodsId)) {
            return null;
        }
        List<AttrSpecDTO> attrSpecDTOs = attrSpecManager.queryAttrSpecWithInstValue(typeId,goodsId);
        log.info("AttrSpecServiceImpl.queryAttrSpecWithInstValue attrSpecDTOs={}", JSON.toJSON(attrSpecDTOs));
        return attrSpecDTOs;
    }


}