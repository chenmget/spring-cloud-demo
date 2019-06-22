package com.iwhalecloud.retail.workflow.manager;

import com.iwhalecloud.retail.workflow.entity.RuleDef;
import com.iwhalecloud.retail.workflow.mapper.RuleDefMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class RuleDefManager{
    @Resource
    private RuleDefMapper ruleDefMapper;

    /**
     * 根据属性ID查询流程属性规格表信息
     * @param attrId
     * @param attrValue
     * @return
     */
    public List<RuleDef> queryRuleDefByParams(String attrId, String attrValue) {
        return ruleDefMapper.queryRuleDefByParams(attrId, attrValue);
    }
}
