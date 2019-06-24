package com.iwhalecloud.retail.workflow.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.workflow.entity.AttrSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: AttrSpecMapper
 * @author autoCreate
 */
@Mapper
public interface AttrSpecMapper extends BaseMapper<AttrSpec>{

    /**
     * 根据属性ID查询流程属性规格表信息
     * @param attrId
     * @return
     */
    List<AttrSpec> selectAttrSpecByAttrIds(List<String> attrId);
}