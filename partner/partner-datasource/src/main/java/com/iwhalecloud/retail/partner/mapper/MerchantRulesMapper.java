package com.iwhalecloud.retail.partner.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.MerchantRulesDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantRuleGetReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantRulesDetailPageReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantRulesListReq;
import com.iwhalecloud.retail.partner.dto.resp.MerchantRulesDetailPageResp;
import com.iwhalecloud.retail.partner.entity.MerchantRules;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author autoCreate
 * @Class: MerchantRulesMapper
 */
@Mapper
public interface MerchantRulesMapper extends BaseMapper<MerchantRules> {

    /**
     * 根据条件查询商家权限规则
     *
     * @param req 条件入参
     * @return List<MerchantRulesDTO>
     */
    public List<MerchantRulesDTO> queryMerchantRuleByCondition(@Param("req") MerchantRuleGetReq req);

    /**
     * 根据调价查询商家及商家权限
     * @param req
     * @return
     */
    public Page<MerchantRulesDetailPageResp> pageMerchantRules(Page<MerchantRulesDetailPageResp> page, @Param("req") MerchantRulesDetailPageReq req);

    public Page<MerchantRulesDTO> pageMerchantRulesDetail(Page<MerchantRulesDTO> page, @Param("req") MerchantRulesListReq req);
}