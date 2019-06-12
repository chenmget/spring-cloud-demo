package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.request.CommonOrgListReq;
import com.iwhalecloud.retail.system.entity.CommonOrg;
import com.iwhalecloud.retail.system.mapper.CommonOrgMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
/**
 * 通用组织信息数据管理类
 * @author lipeng
 */
@Slf4j
@Component
public class CommonOrgManager{
    @Resource
    private CommonOrgMapper commonOrgMapper;

    /**
     * 获取通用组织信息列表
     * @param orgId
     * @return
     */
    @Cacheable(value = SystemConst.CACHE_NAME_SYS_COMMON_ORG)
    public CommonOrg getCommonOrgById(String orgId) {
        log.info("CommonOrgManager.getCommonOrgById(): get data from database");
        CommonOrg commonOrg = commonOrgMapper.selectById(orgId);
        return commonOrg;
    }

    /**
     * 获取通用组织信息列表
     * @param req
     * @return
     */
    @Cacheable(value = SystemConst.CACHE_NAME_SYS_COMMON_ORG_LIST, key = "#req.parentOrgId", condition = "#req.parentOrgId != null")
    public List<CommonOrg> listCommonOrg(CommonOrgListReq req) {
        log.info("CommonOrgManager.listCommonOrg(): get data from database");
        QueryWrapper<CommonOrg> queryWrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(req.getOrgIdList())) {
            queryWrapper.in(CommonOrg.FieldNames.orgId.getTableFieldName(), req.getOrgIdList());
        }
        if (!StringUtils.isEmpty(req.getParentOrgId())) {
            queryWrapper.eq(CommonOrg.FieldNames.parentOrgId.getTableFieldName(), req.getParentOrgId());
        }
        if (!StringUtils.isEmpty(req.getOrgName())) {
            queryWrapper.like(CommonOrg.FieldNames.orgName.getTableFieldName(), req.getOrgName());
        }
        List<CommonOrg> list = commonOrgMapper.selectList(queryWrapper);
        return list;
    }
    
}
