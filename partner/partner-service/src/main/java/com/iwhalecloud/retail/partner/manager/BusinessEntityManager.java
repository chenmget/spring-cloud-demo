package com.iwhalecloud.retail.partner.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.BusinessEntityDTO;
import com.iwhalecloud.retail.partner.dto.req.BusinessEntityGetReq;
import com.iwhalecloud.retail.partner.dto.req.BusinessEntityListReq;
import com.iwhalecloud.retail.partner.dto.req.BusinessEntityPageByRightsReq;
import com.iwhalecloud.retail.partner.dto.req.BusinessEntityPageReq;
import com.iwhalecloud.retail.partner.entity.BusinessEntity;
import com.iwhalecloud.retail.partner.mapper.BusinessEntityMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class BusinessEntityManager{
    @Resource
    private BusinessEntityMapper businessEntityMapper;

    /**
     * 添加一个经营主体
     * @param businessEntity
     * @return
     */
    public BusinessEntityDTO insert(BusinessEntity businessEntity){
        int resultInt = businessEntityMapper.insert(businessEntity);
        if(resultInt > 0){
            BusinessEntityDTO businessEntityDTO = new BusinessEntityDTO();
            BeanUtils.copyProperties(businessEntity, businessEntityDTO);
            return businessEntityDTO;
        }
        return null;
    }

    /**
     * 添加经营主体（返回新增的行数）
     * @param businessEntity
     * @return
     */
    public int insertBusinessEntity(BusinessEntity businessEntity){
        return businessEntityMapper.insert(businessEntity);
    }

    /**
     * 根据条件（精确）查找单个经营主体
     * @param req
     * @return
     */
    @Cacheable(value = PartnerConst.CACHE_NAME_PAR_BUSINESS_ENTITY, key = "#req.businessEntityId", condition = "#req.businessEntityId != null")
    public BusinessEntity getBusinessEntity(BusinessEntityGetReq req){
        QueryWrapper<BusinessEntity> queryWrapper = new QueryWrapper<BusinessEntity>();
        Boolean hasParam = false;
        if(!StringUtils.isEmpty(req.getBusinessEntityId())){
            hasParam = true;
            queryWrapper.eq(BusinessEntity.FieldNames.businessEntityId.getTableFieldName(), req.getBusinessEntityId());
        }
        if(!StringUtils.isEmpty(req.getBusinessEntityCode())){
            hasParam = true;
            queryWrapper.eq(BusinessEntity.FieldNames.businessEntityCode.getTableFieldName(), req.getBusinessEntityCode());
        }
        if (!hasParam) {
            return null;
        }
        queryWrapper.last(" limit 1 "); // 限定查询条数(避免没参数的查出整表）

        List<BusinessEntity> businessEntityList = businessEntityMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(businessEntityList)) {
            return null;
        }
        return businessEntityList.get(0);
    }

    /**
     * 更新
     * 直接清楚掉缓存中对应的数据
     * @param businessEntity
     * @return
     */
    @CacheEvict(value = PartnerConst.CACHE_NAME_PAR_BUSINESS_ENTITY, key = "#businessEntity.businessEntityId")
    public int updateBusinessEntity(BusinessEntity businessEntity){
        return businessEntityMapper.updateById(businessEntity);
    }

    /**
     * 根据经营主体编码更新经营主体
     * 调用这个更新要清除全部的缓存，因为没有根据code 做缓存
     * @param businessEntityDTO
     * @return
     */
    @CacheEvict(value = PartnerConst.CACHE_NAME_PAR_BUSINESS_ENTITY, allEntries = true)
    public int updateBusinessEntityByCode(BusinessEntityDTO businessEntityDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(BusinessEntity.FieldNames.businessEntityCode.getTableFieldName(), businessEntityDTO.getBusinessEntityCode());
        BusinessEntity businessEntity = new BusinessEntity();
        BeanUtils.copyProperties(businessEntityDTO, businessEntity);
        return businessEntityMapper.update(businessEntity, queryWrapper);
    }

    /**
     * 删除
     * @param businessEntityId
     * @return
     */
    @CacheEvict(value = PartnerConst.CACHE_NAME_PAR_BUSINESS_ENTITY, key = "#businessEntityId")
    public int deleteBusinessEntityById(String businessEntityId){
        return businessEntityMapper.deleteById(businessEntityId);
    }

    /**
     * 分页查询
     * @param req
     * @return
     */
    public Page<BusinessEntity> pageBusinessEntity(BusinessEntityPageReq req) {
        // 条件过滤
        QueryWrapper<BusinessEntity> queryWrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(req.getStatus())) {
            queryWrapper.eq(BusinessEntity.FieldNames.status.getTableFieldName(), req.getStatus());
        }
        if(!StringUtils.isEmpty(req.getLanId())) {
            queryWrapper.eq(BusinessEntity.FieldNames.lanId.getTableFieldName(), req.getLanId());
        }
        if(!StringUtils.isEmpty(req.getRegionId())) {
            queryWrapper.eq(BusinessEntity.FieldNames.regionId.getTableFieldName(), req.getRegionId());
        }
        if(!StringUtils.isEmpty(req.getBusinessEntityCode())){
            queryWrapper.like(BusinessEntity.FieldNames.businessEntityCode.getTableFieldName(), req.getBusinessEntityCode());
        }
        if(!StringUtils.isEmpty(req.getBusinessEntityName())){
            queryWrapper.like(BusinessEntity.FieldNames.businessEntityName.getTableFieldName(), req.getBusinessEntityName());
        }

        Page<BusinessEntity> resultPage =  new Page<BusinessEntity>(req.getPageNo(), req.getPageSize());
        resultPage =  (Page)businessEntityMapper.selectPage(resultPage, queryWrapper);
        return resultPage;

//        Page<BusinessEntityDTO> page = new Page<>(req.getPageNo(), req.getPageSize());
//        Page<BusinessEntityDTO> businessEntityDTOPage = businessEntityMapper.pageBusinessEntity(page, req);
//        return businessEntityDTOPage;
    }

    /**
     * 根据条件（模糊）查找经营主体列表
     * @param req
     * @return
     */
    public List<BusinessEntityDTO> listBusinessEntity(BusinessEntityListReq req){
        QueryWrapper<BusinessEntity> queryWrapper = new QueryWrapper<BusinessEntity>();
        if(!StringUtils.isEmpty(req.getBusinessEntityName())){
            queryWrapper.like(BusinessEntity.FieldNames.businessEntityName.getTableFieldName(), req.getBusinessEntityName());
        }
        if(!StringUtils.isEmpty(req.getBusinessEntityCode())){
            queryWrapper.like(BusinessEntity.FieldNames.businessEntityCode.getTableFieldName(), req.getBusinessEntityCode());
        }
        if(!StringUtils.isEmpty(req.getStatus())){
            queryWrapper.eq(BusinessEntity.FieldNames.status.getTableFieldName(), req.getStatus());
        }
        List<BusinessEntity> businessEntityList = businessEntityMapper.selectList(queryWrapper);
        List<BusinessEntityDTO> businessEntityDTOList = new ArrayList<>();
        for (BusinessEntity businessEntity : businessEntityList) {
            BusinessEntityDTO businessEntityDTO = new BusinessEntityDTO();
            BeanUtils.copyProperties(businessEntity, businessEntityDTO);
            businessEntityDTOList.add(businessEntityDTO);
        }
        return businessEntityDTOList;
    }

    /**
     * 查询有权限的经营主体
     * @param req
     * @return
     */
    public Page<BusinessEntityDTO> pageBusinessEntityByRight(BusinessEntityPageByRightsReq req){
        Page<BusinessEntityDTO> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<BusinessEntityDTO> businessEntityDTOPage = businessEntityMapper.pageBusinessEntityByRight(page, req);
        return businessEntityDTOPage;
    }
}
