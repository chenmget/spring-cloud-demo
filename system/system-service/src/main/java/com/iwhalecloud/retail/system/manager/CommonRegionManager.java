package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.dto.request.CommonRegionPageReq;
import com.iwhalecloud.retail.system.entity.CommonRegion;
import com.iwhalecloud.retail.system.mapper.CommonRegionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class CommonRegionManager {
    @Resource
    private CommonRegionMapper commonRegionMapper;

    /**
     * 获取本地区域 列表
     * @param regionId
     * @return
     */
    @Cacheable(value = SystemConst.CACHE_NAME_SYS_COMMON_REGION)
    public CommonRegion getCommonRegionById(String regionId) {
        log.info("CommonRegionManager.getCommonRegionById(): get data from database");
        CommonRegion commonRegion = commonRegionMapper.selectById(regionId);
        return commonRegion;
    }

    /**
     * 获取本地区域 列表
     * @param req
     * @return
     */
    @Cacheable(value = SystemConst.CACHE_NAME_SYS_COMMON_REGION_LIST, key = "#req.parRegionId", condition = "#req.parRegionId != null")
    public List<CommonRegion> listCommonRegion(CommonRegionListReq req) {
        log.info("CommonRegionManager.listCommonRegion(): get data from database");
        QueryWrapper<CommonRegion> queryWrapper = new QueryWrapper<CommonRegion>();
        if (!CollectionUtils.isEmpty(req.getRegionIdList())) {
            queryWrapper.in(CommonRegion.FieldNames.regionId.getTableFieldName(), req.getRegionIdList());
        }
        if (!CollectionUtils.isEmpty(req.getParRegionIdList())) {
            queryWrapper.in(CommonRegion.FieldNames.parRegionId.getTableFieldName(), req.getParRegionIdList());
        }
        if (!StringUtils.isEmpty(req.getParRegionId())) {
            queryWrapper.eq(CommonRegion.FieldNames.parRegionId.getTableFieldName(), req.getParRegionId());
        }
        if (!StringUtils.isEmpty(req.getRegionName())) {
            queryWrapper.like(CommonRegion.FieldNames.regionName.getTableFieldName(), req.getRegionName());
        }
        List<CommonRegion> list = commonRegionMapper.selectList(queryWrapper);
        return list;
    }


    /**
     * 分页获取本地区域 列表
     * @param req
     * @return
     */
    public Page<CommonRegionDTO> pageCommonRegion(CommonRegionPageReq req) {
        IPage<CommonRegion> page =  new Page<CommonRegion>(req.getPageNo(), req.getPageSize());
        QueryWrapper<CommonRegion> queryWrapper = new QueryWrapper<CommonRegion>();
        if (!CollectionUtils.isEmpty(req.getRegionIdList())) {
            queryWrapper.in(CommonRegion.FieldNames.regionId.getTableFieldName(), req.getRegionIdList());
        }
        page = commonRegionMapper.selectPage(page, queryWrapper);
        Page<CommonRegionDTO> respPage = new Page<>();
        BeanUtils.copyProperties(page, respPage);
        List<CommonRegion> commonRegionList = page.getRecords();
        List<CommonRegionDTO> commonRegionDTOList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(commonRegionList)) {
            commonRegionList.forEach(commonRegion -> {
                CommonRegionDTO commonRegionDTO = new CommonRegionDTO();
                BeanUtils.copyProperties(commonRegion, commonRegionDTO);
                commonRegionDTOList.add(commonRegionDTO);
            });
        }
        respPage.setRecords(commonRegionDTOList);

        return respPage;
    }
}
