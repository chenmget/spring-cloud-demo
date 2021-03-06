package com.iwhalecloud.retail.partner.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.partner.dto.MerchantRulesDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantRuleGetReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantRulesDeleteReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantRulesDetailPageReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantRulesListReq;
import com.iwhalecloud.retail.partner.dto.resp.MerchantRulesDetailPageResp;
import com.iwhalecloud.retail.partner.entity.MerchantRules;
import com.iwhalecloud.retail.partner.mapper.MerchantRulesMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MerchantRulesManager extends ServiceImpl<MerchantRulesMapper, MerchantRules> {
    @Resource
    private MerchantRulesMapper merchantRulesMapper;

    /**
     * 添加一个 商家 权限规则
     *
     * @param merchantRules
     * @return
     */
    public int insert(MerchantRules merchantRules) {
        int resultInt = merchantRulesMapper.insert(merchantRules);
        return resultInt;
    }

    /**
     * 删除 商家 权限规则 信息
     *
     * @param req
     * @return
     */
    public int deleteMerchantRules(MerchantRulesDeleteReq req) {
        QueryWrapper<MerchantRules> queryWrapper = new QueryWrapper<MerchantRules>();
        Boolean hasParams = false; // 是否有参数
        if (!StringUtils.isEmpty(req.getMerchantRuleId())) {
            hasParams = true;
            queryWrapper.eq(MerchantRules.FieldNames.merchantRuleId.getTableFieldName(), req.getMerchantRuleId());
        }
        if (!StringUtils.isEmpty(req.getMerchantId())) {
            hasParams = true;
            queryWrapper.eq(MerchantRules.FieldNames.merchantId.getTableFieldName(), req.getMerchantId());
        }
        if (!StringUtils.isEmpty(req.getRuleType())) {
            hasParams = true;
            queryWrapper.eq(MerchantRules.FieldNames.ruleType.getTableFieldName(), req.getRuleType());
        }
        if (!StringUtils.isEmpty(req.getTargetType())) {
            hasParams = true;
            queryWrapper.eq(MerchantRules.FieldNames.targetType.getTableFieldName(), req.getTargetType());
        }

        if (CollectionUtils.isNotEmpty(req.getMerchantRuleIdList())) {
            hasParams = true;
            queryWrapper.in(MerchantRules.FieldNames.merchantRuleId.getTableFieldName(), req.getMerchantRuleIdList());
        }

        // 没有参数 直接返回  不然会删整个表
        if (!hasParams) {
            return 0;
        }

        return merchantRulesMapper.delete(queryWrapper);
    }

    /**
     * 根据条件 获取 商家 权限规则信息列表
     *
     * @param req
     * @return
     */
    public List<MerchantRulesDTO> listMerchantRules(MerchantRulesListReq req) {
        log.info("MerchantRulesManager.listMerchantRules(), input: MerchantRulesListReq={} ", JSON.toJSONString(req));

        QueryWrapper<MerchantRules> queryWrapper = new QueryWrapper<MerchantRules>();
        if (!StringUtils.isEmpty(req.getMerchantId())) {
            queryWrapper.eq(MerchantRules.FieldNames.merchantId.getTableFieldName(), req.getMerchantId());
        }
        if (!StringUtils.isEmpty(req.getRuleType())) {
            queryWrapper.eq(MerchantRules.FieldNames.ruleType.getTableFieldName(), req.getRuleType());
        }
        if (!StringUtils.isEmpty(req.getTargetType())) {
            queryWrapper.eq(MerchantRules.FieldNames.targetType.getTableFieldName(), req.getTargetType());
        }
        if (!StringUtils.isEmpty(req.getTargetId())) {
            queryWrapper.eq(MerchantRules.FieldNames.targetId.getTableFieldName(), req.getTargetId());
        }
        List<MerchantRules> merchantRulesList = merchantRulesMapper.selectList(queryWrapper);
        List<MerchantRulesDTO> merchantRulesDTOList = new ArrayList<>();
        for (MerchantRules merchantRules : merchantRulesList) {
            MerchantRulesDTO merchantRulesDTO = new MerchantRulesDTO();
            BeanUtils.copyProperties(merchantRules, merchantRulesDTO);
            merchantRulesDTOList.add(merchantRulesDTO);
        }
        log.info("MerchantRulesManager.listMerchantRules(), output: merchantRulesDTOList.size={} ", JSON.toJSONString(merchantRulesDTOList.size()));
        return merchantRulesDTOList;
    }

    public Page<MerchantRulesDTO> pageMerchantRulesDetail(MerchantRulesListReq req){
        Page<MerchantRulesDTO> page = new Page<>(req.getPageNo(), req.getPageSize());
        return merchantRulesMapper.pageMerchantRulesDetail(page,req);
    }

    public Page<MerchantRulesDetailPageResp> pageMerchantRules(MerchantRulesDetailPageReq req) {
        Page<MerchantRulesDetailPageResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return merchantRulesMapper.pageMerchantRules(page, req);
    }

    /**
     * 根据条件查询商家权限规则
     *
     * @param req
     * @return
     */
    public List<MerchantRulesDTO> queryMerchantRuleByCondition(MerchantRuleGetReq req) {
        return merchantRulesMapper.queryMerchantRuleByCondition(req);
    }


}
