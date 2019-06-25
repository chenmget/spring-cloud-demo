package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.CommonOrgDTO;
import com.iwhalecloud.retail.system.dto.SysCommonOrgRequest;
import com.iwhalecloud.retail.system.dto.SysCommonOrgResp;
import com.iwhalecloud.retail.system.dto.request.CommonOrgListReq;
import com.iwhalecloud.retail.system.dto.request.CommonOrgPageReq;
import com.iwhalecloud.retail.system.entity.CommonOrg;
import com.iwhalecloud.retail.system.mapper.CommonOrgMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 通用组织信息数据管理类
 *
 * @author lipeng
 */
@Slf4j
@Component
public class CommonOrgManager {
    @Resource
    private CommonOrgMapper commonOrgMapper;

    /**
     * 获取通用组织信息列表
     *
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
     *
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
        if (!StringUtils.isEmpty(req.getLanId())) {
            queryWrapper.eq(CommonOrg.FieldNames.lanId.getTableFieldName(), req.getLanId());
        }
        if (!StringUtils.isEmpty(req.getOrgName())) {
            queryWrapper.like(CommonOrg.FieldNames.orgName.getTableFieldName(), req.getOrgName());
        }
        List<CommonOrg> list = commonOrgMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 分页获取本地区域 列表
     *
     * @param req
     * @return
     */
    public Page<CommonOrgDTO> pageCommonOrg(CommonOrgPageReq req) {
        IPage<CommonOrg> page = new Page<CommonOrg>(req.getPageNo(), req.getPageSize());
        QueryWrapper<CommonOrg> queryWrapper = new QueryWrapper<CommonOrg>();
        if (!CollectionUtils.isEmpty(req.getOrgIdList())) {
            queryWrapper.in(CommonOrg.FieldNames.orgId.getTableFieldName(), req.getOrgIdList());
        }
        page = commonOrgMapper.selectPage(page, queryWrapper);
        Page<CommonOrgDTO> respPage = new Page<>();
        BeanUtils.copyProperties(page, respPage);
        List<CommonOrg> entityList = page.getRecords();
        List<CommonOrgDTO> dtoList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(entityList)) {
            entityList.forEach(entity -> {
                CommonOrgDTO dto = new CommonOrgDTO();
                BeanUtils.copyProperties(entity, dto);
                dtoList.add(dto);
            });
        }
        respPage.setRecords(dtoList);

        return respPage;
    }
    
    public List<SysCommonOrgResp> getSysCommonOrg(SysCommonOrgRequest req) {
    	return commonOrgMapper.getSysCommonOrg(req);
    }
    

}
