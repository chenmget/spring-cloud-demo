package com.iwhalecloud.retail.goods2b.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.AttrSpecDTO;
import com.iwhalecloud.retail.goods2b.dto.req.AttrSpecAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.AttrSpecUpdateReq;
import com.iwhalecloud.retail.goods2b.manager.AttrSpecManager;
import com.iwhalecloud.retail.goods2b.service.AttrSpecService;
import com.iwhalecloud.retail.goods2b.utils.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;
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
     */
    @Override
    public ResultVO<List<AttrSpecDTO>> queryAttrSpecList(String typeId) {

        log.info("AttrSpecServiceImpl.queryAttrSpecList typeId={}",typeId);
        if (StringUtils.isEmpty(typeId)) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }

        List<AttrSpecDTO> attrSpecDTOs =  attrSpecManager.queryAttrSpecList(typeId);
        log.info("AttrSpecServiceImpl.queryAttrSpecList attrSpecDTOs={}", JSON.toJSON(attrSpecDTOs));
        return ResultVOUtils.genQueryResultVO(attrSpecDTOs);
    }

    /**
     * 查询类别属性与设置的实例值
     * @param typeId 类别ID
     * @param goodsId 商品ID
     * @return 类别的属性与配置的属性值
     */
    @Override
    public ResultVO<List<AttrSpecDTO>> queryAttrSpecWithInstValue(String typeId, String goodsId) {

        log.info("AttrSpecServiceImpl.queryAttrSpecWithInstValue typeId={}",typeId);
        if (StringUtils.isEmpty(typeId) || StringUtils.isEmpty(goodsId)) {
            return null;
        }
        List<AttrSpecDTO> attrSpecDTOs = attrSpecManager.queryAttrSpecWithInstValue(typeId,goodsId);
        log.info("AttrSpecServiceImpl.queryAttrSpecWithInstValue attrSpecDTOs={}", JSON.toJSON(attrSpecDTOs));
        return ResultVOUtils.genQueryResultVO(attrSpecDTOs);
    }

    @Override
    public ResultVO addAttrSpec(AttrSpecAddReq req, String userId) {
        log.info("AttrSpecServiceImpl.addAttrSpec req={}",req);
        AttrSpecDTO dto = new AttrSpecDTO();
        BeanUtils.copyProperties(req,dto);
        dto.setCreateDate(new Date());
        dto.setCreateStaff(userId);
        return ResultVOUtils.genInsertResultVO(attrSpecManager.addAttrSpec(dto));
    }

    @Override
    public ResultVO deleteAttrSpec(String id) {
        log.info("AttrSpecServiceImpl.deleteAttrSpec id={}",id);
        return ResultVOUtils.genAduResultVO(attrSpecManager.deleteAttrSpec(id));
    }

    @Override
    public ResultVO updateAttrSpec(AttrSpecUpdateReq req, String userId) {
        log.info("AttrSpecServiceImpl.updateAttrSpec req={}",req);
        AttrSpecDTO dto = new AttrSpecDTO();
        BeanUtils.copyProperties(req,dto);
        dto.setUpdateDate(new Date());
        dto.setUpdateStaff(userId);
        return ResultVOUtils.genAduResultVO(attrSpecManager.updateAttrSpec(dto));
    }


}