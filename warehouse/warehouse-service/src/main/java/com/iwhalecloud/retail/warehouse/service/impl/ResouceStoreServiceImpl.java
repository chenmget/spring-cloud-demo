package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.MerchantDetailDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.dto.resp.MerchantPageResp;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.common.DateUtils;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.dto.response.OrganizationRegionResp;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.system.service.OrganizationService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreObjRelDTO;
import com.iwhalecloud.retail.warehouse.dto.request.AllocateStorePageReq;
import com.iwhalecloud.retail.warehouse.dto.request.StoreGetStoreIdReq;
import com.iwhalecloud.retail.warehouse.dto.request.StorePageReq;
import com.iwhalecloud.retail.warehouse.entity.ResouceStore;
import com.iwhalecloud.retail.warehouse.manager.ResouceStoreManager;
import com.iwhalecloud.retail.warehouse.manager.ResouceStoreObjRelManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstManager;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class ResouceStoreServiceImpl implements ResouceStoreService {

    @Autowired
    private ResouceStoreManager resouceStoreManager;

    @Autowired
    private ResouceStoreObjRelManager resouceStoreObjRelManager;

    @Reference
    private MerchantService merchantService;

    @Reference
    private MerchantRulesService merchantRulesService;

    @Reference
    private CommonRegionService commonRegionService;

    @Autowired
    private ResourceInstManager resourceInstManager;

    @Reference
    private OrganizationService organizationService;

    @Autowired
    private Constant constant;

    @Override
    public Page<ResouceStoreDTO> pageStore(StorePageReq req) {
        List<String> merchantIds = req.getMerchantIds();

        if (!StringUtils.isEmpty(req.getMerchantCode()) || !StringUtils.isEmpty(req.getMerchantName()) || !StringUtils.isEmpty(req.getMerchantType())) {
            MerchantListReq merchantListReq = new MerchantListReq();
            merchantListReq.setMerchantCode(req.getMerchantCode());
            merchantListReq.setMerchantName(req.getMerchantName());
            merchantListReq.setMerchantType(req.getMerchantType());
            ResultVO<List<MerchantDTO>> merchantDTOVO = merchantService.listMerchant(merchantListReq);
            log.info("ResouceStoreServiceImpl.pageStore merchantService.pageMerchant req={},resp={}", JSON.toJSONString(merchantListReq), JSON.toJSONString(merchantDTOVO));
            if (!merchantDTOVO.isSuccess() || CollectionUtils.isEmpty(merchantDTOVO.getResultData())) {
                return new Page<ResouceStoreDTO>();
            }
            List<MerchantDTO> list = merchantDTOVO.getResultData();
            if (CollectionUtils.isEmpty(merchantIds)) {
                merchantIds = list.stream().map(MerchantDTO::getMerchantId).collect(toList());
            } else {
                merchantIds.addAll(list.stream().map(MerchantDTO::getMerchantId).collect(toList()));
            }
        }
        req.setMerchantIds(merchantIds);
        req.setLanIdList(getLanIdList(req.getLanIdName()));
        log.info("ResouceStoreServiceImpl.pageStore resouceStoreManager.pageStore req={}", JSON.toJSONString(req));
        Page<ResouceStoreDTO> resouceStoreDTOPage = resouceStoreManager.pageStore(req);
        for (ResouceStoreDTO dto : resouceStoreDTOPage.getRecords()) {
            MerchantGetReq merchantGetReq = new MerchantGetReq();
            merchantGetReq.setMerchantId(dto.getMerchantId());
            ResultVO<MerchantDetailDTO> resultVO = this.merchantService.getMerchantDetail(merchantGetReq);
            ResultVO<CommonRegionDTO> regionResultVO = commonRegionService.getCommonRegionById(dto.getRegionId());
            if (regionResultVO != null && regionResultVO.isSuccess() && regionResultVO.getResultData() != null) {
                dto.setRegionName(regionResultVO.getResultData().getRegionName());
            } else {
                log.warn("ResouceStoreServiceImpl.pageStore commonRegionService.getCommonRegionById regionId={} regionResultVO is null", dto.getRegionId());
            }
            ResultVO<CommonRegionDTO> landResultVO = commonRegionService.getCommonRegionById(dto.getLanId());
            if (landResultVO != null && landResultVO.isSuccess() && landResultVO.getResultData() != null) {
                dto.setLanName(landResultVO.getResultData().getRegionName());
            } else {
                log.warn("ResouceStoreServiceImpl.pageStore commonRegionService.getCommonRegionById landId={} landResultVO is null", dto.getLanId());
            }
            if (resultVO.isSuccess() && resultVO.getResultData() != null) {
                MerchantDetailDTO merchantDetailDTO = resultVO.getResultData();
                BeanUtils.copyProperties(merchantDetailDTO, dto);
            } else {
                log.warn("ResouceStoreServiceImpl.pageStore merchantService.getMerchantById merchantId={} merchant is null", dto.getMerchantId());
            }
        }
        return resouceStoreDTOPage;
    }


    @Override
    public ResultVO getMerchantByStore(String storeId) {
        ResouceStoreObjRelDTO resouceStoreObjRelDTO = resouceStoreObjRelManager.getMerchantByStore(storeId);
        log.info("ResouceStoreServiceImpl.getMerchantByStore resouceStoreObjRelManager.getMerchantByStore storeId={},resp={}", storeId, JSON.toJSON(resouceStoreObjRelDTO));
        if (null != resouceStoreObjRelDTO) {
            return merchantService.getMerchantById(resouceStoreObjRelDTO.getObjId());
        } else {
            return ResultVO.error(constant.getCannotGetStoreMsg());
        }
    }

    @Override
    public ResultVO<Page<ResouceStoreDTO>> pageMerchantAllocateStore(AllocateStorePageReq req) {
        String merchantId = req.getMerchantId();
        MerchantRulesCommonReq commonReq = new MerchantRulesCommonReq();
        commonReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.TRANSFER.getType());
        commonReq.setMerchantId(req.getMerchantId());
        ResultVO<List<String>> permissionMerchantIdListVO = merchantRulesService.getTransferRegionAndMerchantPermission(commonReq);
        log.info("ResouceStoreServiceImpl.pageMerchantAllocateStore merchantService.getRegionAndMerchantPermission req={},resp={}", JSON.toJSONString(commonReq), JSON.toJSONString(permissionMerchantIdListVO));
        if (permissionMerchantIdListVO != null && permissionMerchantIdListVO.isSuccess() && !CollectionUtils.isEmpty(permissionMerchantIdListVO.getResultData())) {
            List<String> permissionMerchantIdList = permissionMerchantIdListVO.getResultData();
            // 权限过滤接口已整合区域和商家对象，返回商家id集合
            req.setMerchantIdList(permissionMerchantIdList);
        }
        // 根据商家编码和名称查询商家，获取商家ID后作为查询添加去查询仓库
        if (!StringUtils.isEmpty(req.getMerchantCode()) || !StringUtils.isEmpty(req.getMerchantName()) || !StringUtils.isEmpty(req.getMerchantType())) {
            MerchantListReq merchantReq = new MerchantListReq();
            merchantReq.setMerchantCode(req.getMerchantCode());
            merchantReq.setMerchantName(req.getMerchantName());
            merchantReq.setMerchantType(req.getMerchantType());
            ResultVO<List<MerchantDTO>> merchantResultVO = merchantService.listMerchant(merchantReq);
            log.info("ResouceStoreServiceImpl.pageMerchantAllocateStore merchantService.listMerchant merchantReq={},resp={}", JSON.toJSONString(merchantReq), JSON.toJSONString(merchantResultVO));
            if (merchantResultVO != null && merchantResultVO.isSuccess() && merchantResultVO.getResultData() != null) {
                List<MerchantDTO> merchantList = merchantResultVO.getResultData();
                List<String> merchantIds = merchantList.stream().map(MerchantDTO::getMerchantId).collect(Collectors.toList());

                List<String> allList = req.getMerchantIdList();
                if (null == merchantIds || merchantIds.isEmpty()) {
                    return ResultVO.success(new Page<ResouceStoreDTO>());
                }
                // 根据商家编码和名称查询商家的ID不在权限范围内
                merchantIds = merchantIds.stream().filter(s -> allList.contains(s)).collect(Collectors.toList());
                // 根据商家编码和名称查询商家，没找到商家直接返回
                if (null == merchantIds || merchantIds.isEmpty()) {
                    return ResultVO.success(new Page<ResouceStoreDTO>());
                }
                req.setMerchantIdList(merchantIds);
            }
        }
        req.setLanIdList(getLanIdList(req.getLanIdName()));
        log.info("ResouceStoreServiceImpl.pageMerchantAllocateStore merchantService.pageAllocateStore req={}", JSON.toJSONString(req));
        Page<ResouceStoreDTO> resouceStoreDTOPage = resouceStoreManager.pageAllocateStore(req);
        for (ResouceStoreDTO dto : resouceStoreDTOPage.getRecords()) {
            MerchantGetReq merchantGetReq = new MerchantGetReq();
            merchantGetReq.setMerchantId(dto.getMerchantId());
            ResultVO<MerchantDetailDTO> resultVO = this.merchantService.getMerchantDetail(merchantGetReq);
            if (resultVO.isSuccess() && resultVO.getResultData() != null) {
                MerchantDetailDTO merchantDetailDTO = resultVO.getResultData();
                BeanUtils.copyProperties(merchantDetailDTO, dto);
                dto.setRegionName(merchantDetailDTO.getCityName());
            } else {
                log.warn("ResouceStoreServiceImpl.pageMerchantAllocateStore merchantService.getMerchantById merchantId={} merchant is null", merchantId);
            }
        }
        return ResultVO.success(resouceStoreDTOPage);
    }

    @Override
    public ResultVO<ResouceStoreDTO> getResouceStore(String storeId) {
        log.info("ResouceStoreServiceImpl.getResouceStore req={}", storeId);
        ResouceStore resouceStore = resouceStoreManager.getResouceStore(storeId);
        ResouceStoreDTO dto = null;
        if (null != resouceStore) {
            dto = new ResouceStoreDTO();
            BeanUtils.copyProperties(resouceStore, dto);
        }
        return ResultVO.success(dto);
    }


    @Override
    public String getStoreId(StoreGetStoreIdReq req){
        log.info("ResouceStoreServiceImpl.getStoreId req={}", JSON.toJSONString(req));
        return resouceStoreManager.getStoreId(req);
    }
    @Override
    public String getStoreIdByLanId(String lanId){
        log.info("ResouceStoreServiceImpl.getStoreIdByLanId req={}", JSON.toJSONString(lanId));
        return resouceStoreManager.getStoreIdByLanId(lanId);
    }

    @Override
    public void initStoredata() {
        log.info("ResouceStoreServiceImpl.initStoredata");
        List<String> merchantTypeList = Lists.newArrayList(Lists.newArrayList(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType(),PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType(),PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType()));
        MerchantCountReq merchantCountReq = new MerchantCountReq();
        merchantCountReq.setMerchantTypeList(merchantTypeList);
        ResultVO<Integer> resultVO = merchantService.countMerchant(merchantCountReq);
        log.info("ResouceStoreServiceImpl.initStoredata merchantService.countMerchant req={} resp={}", JSON.toJSONString(merchantCountReq), JSON.toJSONString(resultVO));
        if (!resultVO.isSuccess() || resultVO.getResultData() < 1) {
            return;
        }
        int count = resultVO.getResultData();
        for (int i= 0; i < count; i = i+100) {
            MerchantPageReq pageReq = new MerchantPageReq();
            pageReq.setPageNo(i/100);
            pageReq.setPageSize(100);
            pageReq.setMerchantTypeList(merchantTypeList);
            ResultVO<Page<MerchantPageResp>> pageResultVO = merchantService.pageMerchant(pageReq);
            log.info("ResouceStoreServiceImpl.initStoredata merchantService.pageMerchant req={} resp={}", JSON.toJSONString(pageReq), JSON.toJSONString(pageResultVO.getResultData().getSize()));
            for (MerchantPageResp merchantDTO : pageResultVO.getResultData().getRecords()) {
                if (PartnerConst.MerchantTypeEnum.PARTNER.getType().equals(merchantDTO.getMerchantType())) {
                    continue;
                }
                StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
                storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
                storeGetStoreIdReq.setMerchantId(merchantDTO.getMerchantId());
                Integer countStore = resouceStoreObjRelManager.countStoreRelByMerchantId(merchantDTO.getMerchantId());
                log.info("ResouceStoreServiceImpl.interceptMerchant resouceStoreObjRelManager.countStoreRelByMerchantId req={} resp={}", JSON.toJSONString(merchantDTO.getMerchantId()), JSON.toJSONString(countStore));
                if (null == countStore || countStore < 1) {
                    saveStoreInfo(merchantDTO);
                } else {
                    updateStoreInfo(merchantDTO, merchantDTO.getMerchantId());
                }
            }
        }

    }

    private int updateStoreInfo(MerchantPageResp merchantDTO, String merchantId) {
        int i = 0;
        Date now = new Date();
        // 商家下面有多个仓库，查对应的仓库，有进行update仓库操作，没有直接删除仓库使用对象
        List<String> storeIds = resouceStoreObjRelManager.selectStoreRelByMerchantId(merchantId);
        if (!CollectionUtils.isEmpty(storeIds)) {
            for (String storeId : storeIds) {
                ResouceStoreDTO resouceStoreDTO = new ResouceStoreDTO();
                resouceStoreDTO.setMktResStoreName(merchantDTO.getMerchantName() + "-" + ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getName());
                resouceStoreDTO.setMerchantCode(merchantDTO.getMerchantCode());
                resouceStoreDTO.setMerchantName(merchantDTO.getMerchantName());
                resouceStoreDTO.setMktResStoreNbr("CK" + storeId);
                resouceStoreDTO.setMerchantType(merchantDTO.getMerchantType());
                resouceStoreDTO.setStatusCd(merchantDTO.getStatus());
                resouceStoreDTO.setCreateDate(now);
                resouceStoreDTO.setUpdateDate(now);
                resouceStoreDTO.setStatusDate(now);
                resouceStoreDTO.setEffDate(now);
                resouceStoreDTO.setExpDate(now);
                resouceStoreDTO.setCheckDate(now);
                resouceStoreDTO.setCreateStaff(merchantDTO.getMerchantId());
                resouceStoreDTO.setMktResStoreId(storeId);
                int updateCount = resouceStoreManager.updateStoreById(resouceStoreDTO);
                if (updateCount < 1){
                    resouceStoreObjRelManager.deleteStoreRel(merchantId, storeId);
                }
                i = i + updateCount;
            }
        }

        // 使用对象与商家一致
        ResouceStoreObjRelDTO updateResouceStoreObjRelDTO = new ResouceStoreObjRelDTO();
        updateResouceStoreObjRelDTO.setStatusCd(merchantDTO.getStatus());
        updateResouceStoreObjRelDTO.setUpdateDate(now);
        updateResouceStoreObjRelDTO.setObjId(merchantId);
        i = i + resouceStoreObjRelManager.updateStoreRelByObjId(updateResouceStoreObjRelDTO);

        return i;
    }

    private String saveStoreInfo(MerchantPageResp dto) {
        String remark = "fun_sysn_data_"+ DateUtils.currentSysTimeForStr();
        String mktResStoreId = resourceInstManager.getPrimaryKey();
        ResouceStoreDTO resouceStoreDTO = new ResouceStoreDTO();
        resouceStoreDTO.setMktResStoreId(mktResStoreId);
        resouceStoreDTO.setMerchantCode(dto.getMerchantId());
        resouceStoreDTO.setMerchantName(dto.getMerchantName());
        resouceStoreDTO.setMerchantId(dto.getMerchantId());
        resouceStoreDTO.setMerchantType(dto.getMerchantType());
        resouceStoreDTO.setMktResStoreName(dto.getMerchantName() + "-" + ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getName());
        resouceStoreDTO.setMktResStoreNbr("CK" + mktResStoreId);
        resouceStoreDTO.setBusinessEntityName(dto.getBusinessEntityName());
        // PUB-C-0001 1000	有效 1100	无效 1200	未生效
        resouceStoreDTO.setStatusCd("1000");
        // RES-0001 2101  终端库 2102  集采库 2103  备机库
        resouceStoreDTO.setStoreType("1299");
        resouceStoreDTO.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        // 记录号码回收期限：必须输入（天数，默认90天）
        resouceStoreDTO.setRecDay(90L);
        // RES-C-0015 1000	本地网回收 1001	管理机构回收 1002	回收池回收 1003	回收池回收并回放
        resouceStoreDTO.setRecType("1000");
        // RES-C-0016 1000	省级库	全省仓库 1100	本地网库	本地网仓库 1200	本地网下级库	本地网以下级仓库
        String storeGrade = "";
        if (PartnerConst.MerchantTypeEnum.MANUFACTURER.getType().equals(dto.getMerchantType())) {
            storeGrade = "1000";
        } else if (PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType().equals(dto.getMerchantType())) {
            storeGrade = "1100";
        } else {
            storeGrade = "1200";
        }
        resouceStoreDTO.setStoreGrade(storeGrade);
        resouceStoreDTO.setParStoreId("-1");
        resouceStoreDTO.setRecStoreId("-1");
        resouceStoreDTO.setRegionId(dto.getCity());
        resouceStoreDTO.setLanId(dto.getLanId());
        resouceStoreDTO.setRemark(remark);
        Date now = new Date();
        resouceStoreDTO.setEffDate(now);
        resouceStoreDTO.setExpDate(now);
        resouceStoreDTO.setCreateStaff("10101");
        resouceStoreDTO.setCreateDate(now);
        resouceStoreDTO.setUpdateDate(now);
        resouceStoreDTO.setStatusDate(now);
        resouceStoreDTO.setCheckDate(now);
        resouceStoreManager.saveStore(resouceStoreDTO);

        ResouceStoreObjRelDTO resouceStoreObjRelDTO = new ResouceStoreObjRelDTO();
        resouceStoreObjRelDTO.setObjId(dto.getMerchantId());
        resouceStoreObjRelDTO.setObjType(dto.getMerchantType());
        resouceStoreObjRelDTO.setCreateDate(now);
        resouceStoreObjRelDTO.setMktResStoreId(mktResStoreId);
        // PUB-C-0001 1000	有效 1100	无效 1200	未生效
        resouceStoreObjRelDTO.setStatusCd("1000");
        resouceStoreObjRelDTO.setRegionId(dto.getCity());
        resouceStoreObjRelDTO.setRemark(remark);
        resouceStoreObjRelDTO.setCreateDate(now);
        resouceStoreObjRelDTO.setCreateStaff("10101");
        // PUB-C-0006 1	是 0	否
        resouceStoreObjRelDTO.setIsDefault("1");
        resouceStoreObjRelManager.saveStoreRel(resouceStoreObjRelDTO);
        return mktResStoreId;
    }

    /**
     * 本地网名查询
     * @param lanName
     * @return
     */
    private List<String> getLanIdList(String lanName){
        List<String> list = null;
        if (StringUtils.isBlank(lanName)) {
            return list;
        }
        CommonRegionListReq commonRegionListReq = new CommonRegionListReq();
        commonRegionListReq.setRegionName(lanName);
        ResultVO<List<CommonRegionDTO>> commonRegionDTOVO = commonRegionService.listCommonRegion(commonRegionListReq);
        if (commonRegionDTOVO.isSuccess() && !CollectionUtils.isEmpty(commonRegionDTOVO.getResultData())) {
            List<CommonRegionDTO> regionDTOList = commonRegionDTOVO.getResultData();
            list = regionDTOList.stream().map(CommonRegionDTO::getRegionId).collect(Collectors.toList());
        } else{
            String nullValue = "null";
            list = Lists.newArrayList(nullValue);
        }
        return list;
    }

    @Override
    public ResultVO<List<ResouceStoreDTO>> listGivenStore(){
        ResultVO<List<OrganizationRegionResp>> organizationRegionRespVO = organizationService.queryRegionOrganizationId();
        if (!organizationRegionRespVO.isSuccess() || CollectionUtils.isEmpty(organizationRegionRespVO.getResultData())) {
            return ResultVO.error("获取地市级对象失败");
        }
        List<OrganizationRegionResp> organizationRegionResp = organizationRegionRespVO.getResultData();
        List<String> objIdList = organizationRegionResp.stream().map(OrganizationRegionResp::getOrgId).collect(Collectors.toList());
        return ResultVO.success(resouceStoreManager.listGivenStore(objIdList));
    }

}