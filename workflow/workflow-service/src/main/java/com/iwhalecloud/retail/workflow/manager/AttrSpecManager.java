package com.iwhalecloud.retail.workflow.manager;

import com.iwhalecloud.retail.workflow.entity.AttrSpec;
import com.iwhalecloud.retail.workflow.mapper.AttrSpecMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class AttrSpecManager{
    @Resource
    private AttrSpecMapper attrSpecMapper;

    /**
     * 根据属性ID查询流程属性规格表信息
     * @param attrIds
     * @return
     */
    public List<AttrSpec> selectAttrSpecByAttrIds(List<String> attrIds) {
        return attrSpecMapper.selectAttrSpecByAttrIds(attrIds);
    }
}
