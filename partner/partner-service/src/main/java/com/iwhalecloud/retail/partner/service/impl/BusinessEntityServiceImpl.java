package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.BusinessEntityDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.entity.BusinessEntity;
import com.iwhalecloud.retail.partner.manager.BusinessEntityManager;
import com.iwhalecloud.retail.partner.service.BusinessEntityService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BusinessEntityServiceImpl implements BusinessEntityService {

    @Autowired
    private BusinessEntityManager businessEntityManager;

    @Reference
    private CommonRegionService commonRegionService;

    @Override
    public ResultVO<BusinessEntityDTO> saveBusinessEntity(BusinessEntitySaveReq req) {
        log.info("BusinessEntityServiceImpl.saveBusinessEntity(), 入参BusinessEntitySaveReq={} ", req);
        BusinessEntity businessEntity = new BusinessEntity();
        BeanUtils.copyProperties(req, businessEntity);
        BusinessEntityDTO businessEntityDTO = businessEntityManager.insert(businessEntity);
        log.info("BusinessEntityServiceImpl.saveBusinessEntity(), 出参businessEntityDTO={} ", businessEntityDTO);
        if (businessEntityDTO == null){
            return ResultVO.error("新增经营实体信息失败");
        }
        return ResultVO.success(businessEntityDTO);
    }

    @Override
    public ResultVO<BusinessEntityDTO> getBusinessEntity(BusinessEntityGetReq req){
        log.info("BusinessEntityServiceImpl.getBusinessEntity(), 入参BusinessEntityGetReq={} ", req);
        BusinessEntity businessEntity = businessEntityManager.getBusinessEntity(req);
        BusinessEntityDTO businessEntityDTO = new BusinessEntityDTO();
        if(Objects.nonNull(businessEntity)){
            BeanUtils.copyProperties(businessEntity, businessEntityDTO);
        } else {
            businessEntityDTO = null;
        }
        log.info("BusinessEntityServiceImpl.getBusinessEntity(), 出参对象businessEntityDTO={} ", businessEntityDTO);
        return ResultVO.success(businessEntityDTO);
    }

    @Override
    public ResultVO<Integer>  updateBusinessEntity(BusinessEntityUpdateReq req) {
        log.info("BusinessEntityServiceImpl.updateBusinessEntity(), 入参BusinessEntityUpdateReq={} ", req);
        BusinessEntity businessEntity = new BusinessEntity();
        BeanUtils.copyProperties(req, businessEntity);
        int result = businessEntityManager.updateBusinessEntity(businessEntity);
        log.info("BusinessEntityServiceImpl.updateBusinessEntity(), 出参对象(更新影响数据条数）={} ", result);
        if (result <= 0){
            return ResultVO.error("编辑厂商信息失败");
        }
        return ResultVO.success(result);
    }

    @Override
    public ResultVO<Integer> deleteBusinessEntityById(String id) {
        log.info("BusinessEntityServiceImpl.deleteBusinessEntityById(), 入参businessEntityId={} ", id);
        int result = businessEntityManager.deleteBusinessEntityById(id);
        log.info("BusinessEntityServiceImpl.deleteBusinessEntityById(), 出参对象(删除影响数据条数）={} ", result);
        if (result <= 0){
            return ResultVO.error("删除厂商信息失败");
        }
        return ResultVO.success(result);
    }

    @Override
    public ResultVO<Page<BusinessEntityDTO>> pageBusinessEntity(BusinessEntityPageReq pageReq) {
        log.info("BusinessEntityServiceImpl.pageBusinessEntity(), 入参BusinessEntityPageReq={} ", pageReq);
        Page<BusinessEntityDTO> page = businessEntityManager.pageBusinessEntity(pageReq);

//        Page<BusinessEntity> businessEntityPage = businessEntityManager.pageBusinessEntity(pageReq);
        // 转换成 对应的 dto
        Page<BusinessEntityDTO> targetPage = new Page<>();
        BeanUtils.copyProperties(page, targetPage);
        List<BusinessEntityDTO> targetList = Lists.newArrayList();
        for (BusinessEntityDTO businessEntity : page.getRecords()){
            BusinessEntityDTO targetDTO = new BusinessEntityDTO();
            BeanUtils.copyProperties(businessEntity, targetDTO);

            // 取本地网名称  市县名称
            targetDTO.setLanName(getRegionNameByRegionId(targetDTO.getLanId()));
            targetDTO.setRegionName(getRegionNameByRegionId(targetDTO.getRegionId()));

            targetList.add(targetDTO);
        }
        targetPage.setRecords(targetList);

        log.info("BusinessEntityServiceImpl.pageBusinessEntity(), output: list={} ", JSON.toJSONString(targetPage.getRecords()));
        return ResultVO.success(targetPage);
    }


    /**
     * 根据regionId获取 regionName
     * @param regionId
     * @return
     */
    private String getRegionNameByRegionId(String regionId) {
        if (StringUtils.isEmpty(regionId)) {
            return "";
        }
        CommonRegionDTO commonRegionDTO = commonRegionService.getCommonRegionById(regionId).getResultData();
        if (commonRegionDTO != null) {
            return commonRegionDTO.getRegionName();
        }
        return "";
    }

    /**
     * 经营主体信息列表
     * @param req
     * @return
     */
    @Override
    public ResultVO<List<BusinessEntityDTO>> listBusinessEntity(BusinessEntityListReq req) {
        log.info("BusinessEntityServiceImpl.listBusinessEntity(), 入参BusinessEntityListReq={} ", req);
        List<BusinessEntityDTO> list = businessEntityManager.listBusinessEntity(req);
        log.info("BusinessEntityServiceImpl.listBusinessEntity(), 出参list={} ", list);
        return ResultVO.success(list);
    }

    @Override
    public ResultVO<Page<BusinessEntityDTO>> pageBusinessEntityByRight(BusinessEntityPageByRightsReq pageReq) {
        log.info("BusinessEntityServiceImpl.pageBusinessEntity(), 入参BusinessEntityPageReq={} ", pageReq);
        Page<BusinessEntityDTO> page = businessEntityManager.pageBusinessEntityByRight(pageReq);
        log.info("BusinessEntityServiceImpl.pageBusinessEntity(), 出参page={} ", page);
        return ResultVO.success(page);
    }
}