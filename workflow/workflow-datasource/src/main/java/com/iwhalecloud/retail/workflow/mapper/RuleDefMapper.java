package com.iwhalecloud.retail.workflow.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.workflow.entity.RuleDef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: RuleDefMapper
 * @author autoCreate
 */
@Mapper
public interface RuleDefMapper extends BaseMapper<RuleDef>{

    /**
     * 根据属性ID查询流程属性规格表信息
     * @param attrId
     * @param attrValue
     * @return
     */
    List<RuleDef> queryRuleDefByParams(@Param("attrId") String attrId, @Param("attrValue") String attrValue);
}