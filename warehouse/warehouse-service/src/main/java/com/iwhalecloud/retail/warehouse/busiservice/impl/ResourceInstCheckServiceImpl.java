package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.TypeConst;
import com.iwhalecloud.retail.goods2b.dto.MerchantTagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.MerchantTagRelListReq;
import com.iwhalecloud.retail.goods2b.dto.req.TagRelListReq;
import com.iwhalecloud.retail.goods2b.dto.req.TypeSelectByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.TagRelListResp;
import com.iwhalecloud.retail.goods2b.dto.resp.TypeDetailResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.MerchantTagRelService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.goods2b.service.dubbo.TagRelService;
import com.iwhalecloud.retail.goods2b.service.dubbo.TypeService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.service.PublicDictService;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstCheckService;
import com.iwhalecloud.retail.warehouse.common.MarketingResConst;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.SelectProcessResp;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstManager;
import com.iwhalecloud.retail.warehouse.util.MarketingZopClientUtil;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResourceInstCheckServiceImpl implements ResourceInstCheckService {

    @Autowired
    private ResourceInstManager resourceInstManager;
    @Reference
    private ProductService productService;
    @Value("${addNbrService.checkMaxNum}")
    private Integer checkMaxNum;
    @Autowired
    private ResouceInstTrackService resouceInstTrackService;
    @Autowired
    private MarketingZopClientUtil zopClientUtil;
    @Reference
    private TypeService typeService;
    @Reference
    private PublicDictService publicDictService;
    @Reference
    private MerchantRulesService merchantRulesService;
    @Reference
    private MerchantTagRelService merchantTagRelService;
    @Reference
    private TagRelService tagRelService;


    @Override
    @Transactional(isolation= Isolation.SERIALIZABLE,propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public List<String> vaildOwnStore(ResourceInstValidReq req, CopyOnWriteArrayList<String> nbrList){
        List<String> existNbrs = new ArrayList<>();
        // 一去重：串码存在不再导入
        ResourceInstsGetReq resourceInstsGetReq = new ResourceInstsGetReq();
        List<String> mktResInstNbrs = Lists.newArrayList(nbrList);
        resourceInstsGetReq.setMktResInstNbrs(mktResInstNbrs);
        List<String> merchantTypes = null;
        if (PartnerConst.MerchantTypeEnum.MANUFACTURER.getType().equals(req.getMerchantType())) {
            // 厂商增加：只校验厂商库
            merchantTypes = Lists.newArrayList(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType());
        }else{
            // 非厂商增加：只校验非厂商库
            merchantTypes = Lists.newArrayList(PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType(),
                    PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType(),
                    PartnerConst.MerchantTypeEnum.PARTNER.getType());
        }
        resourceInstsGetReq.setMerchantTypes(merchantTypes);
        resourceInstsGetReq.setTypeId(req.getTypeId());
        List<ResourceInstDTO> inst = resourceInstManager.validResourceInst(resourceInstsGetReq);
        log.info("ResourceInstCheckServiceImpl.vaildOwnStore resourceInstManager.getResourceInsts req={},resp={}", JSON.toJSONString(resourceInstsGetReq), JSON.toJSONString(inst));
        if (CollectionUtils.isNotEmpty(inst)) {
            // 删除的串码可再次导入
            String deleteStatus = ResourceConst.STATUSCD.DELETED.getCode();
            inst = inst.stream().filter(t -> !deleteStatus.equals(t.getStatusCd())).collect(Collectors.toList());
            List<String> instNbrs = inst.stream().map(ResourceInstDTO::getMktResInstNbr).collect(Collectors.toList());
            existNbrs = mktResInstNbrs.stream().filter(t -> instNbrs.contains(t)).collect(Collectors.toList());
        }
        return existNbrs;
    }


    @Override
    public List<ResouceInstTrackDTO> validMerchantStore(ResourceInstValidReq req){
        List<String> validNbrList = req.getMktResInstNbrs();
        ResourceInstsTrackGetReq resourceInstsTrackGetReq = new ResourceInstsTrackGetReq();
        CopyOnWriteArrayList<String> mktResInstNbrList = new CopyOnWriteArrayList<String>(validNbrList);
        resourceInstsTrackGetReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        resourceInstsTrackGetReq.setTypeId(req.getTypeId());
        resourceInstsTrackGetReq.setMktResInstNbrList(mktResInstNbrList);
        ResultVO<List<ResouceInstTrackDTO>> trackListVO = resouceInstTrackService.listResourceInstsTrack(resourceInstsTrackGetReq);
        log.info("ResourceInstCheckServiceImpl.validMerchantStore resourceInstManager.getResourceInsts req={},resp={}", JSON.toJSONString(resourceInstsTrackGetReq), JSON.toJSONString(trackListVO));
        if (trackListVO.isSuccess() && null != trackListVO.getResultData()) {
            List<ResouceInstTrackDTO> trackList = trackListVO.getResultData();
            // 串码来源是空的为厂商串码
            trackList = trackList.stream().filter(t -> StringUtils.isBlank(t.getSourceType())).collect(Collectors.toList());
            return trackList;
        }
        return  null;
    }

    @Override
    public List<ResourceRequestAddReq.ResourceRequestInst> getReqInst(ResourceInstAddReq req){
        List<String> mktResInstNbrs = req.getMktResInstNbrs();
        List<String> checkMktResInstNbrs = req.getCheckMktResInstNbrs();
        List<ResourceRequestAddReq.ResourceRequestInst> instDTOList = new ArrayList<>(mktResInstNbrs.size());
        log.info("ResourceInstCheckServiceImpl.getReqInst reqNbrSize={}",  mktResInstNbrs.size());
        if (!CollectionUtils.isEmpty(checkMktResInstNbrs)) {
            mktResInstNbrs.removeAll(checkMktResInstNbrs);
        }
        for (String nbr : mktResInstNbrs) {
            ResourceRequestAddReq.ResourceRequestInst instDTO = new ResourceRequestAddReq.ResourceRequestInst();
            instDTO.setMktResInstNbr(nbr);
            instDTO.setMktResId(req.getMktResId());
            if (null != req.getCtCodeMap()) {
                instDTO.setCtCode(req.getCtCodeMap().get(nbr));
            }
            if (null != req.getSnCodeMap()) {
                instDTO.setSnCode(req.getSnCodeMap().get(nbr));
            }
            if (null != req.getMacCodeMap()) {
                instDTO.setMacCode(req.getMacCodeMap().get(nbr));
            }
            instDTOList.add(instDTO);
        }
        log.info("ResourceInstCheckServiceImpl.getReqInst noCheckNbrSize={}",  instDTOList.size());

        if (!CollectionUtils.isEmpty(checkMktResInstNbrs)) {
            for (String nbr : checkMktResInstNbrs) {
                ResourceRequestAddReq.ResourceRequestInst instDTO = new ResourceRequestAddReq.ResourceRequestInst();
                instDTO.setMktResInstNbr(nbr);
                instDTO.setMktResId(req.getMktResId());
                instDTO.setIsInspection(ResourceConst.CONSTANT_YES);
                if (null != req.getCtCodeMap()) {
                    instDTO.setCtCode(req.getCtCodeMap().get(nbr));
                }
                if (null != req.getSnCodeMap()) {
                    instDTO.setSnCode(req.getSnCodeMap().get(nbr));
                }
                if (null != req.getMacCodeMap()) {
                    instDTO.setMacCode(req.getMacCodeMap().get(nbr));
                }
                instDTOList.add(instDTO);
            }
        }
        log.info("ResourceInstCheckServiceImpl.getReqInst req={},totalNbrSize={}", JSON.toJSONString(req), instDTOList.size());
        return instDTOList;
    }

    @Override
    public ResultVO selectProcess(ResourceInstAddReq req){
        String requestStatusCd = null;
        String processId = null;
        String taskSubType = null;
        // 固网串码审核流程
        if (ResourceConst.CONSTANT_YES.equals(req.getIsFixedLine())) {
            taskSubType = WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_3010.getTaskSubType();
            if (req.getMktResInstNbrs().size() < checkMaxNum || ResourceConst.MKTResInstType.NONTRANSACTION.getCode().equals(req.getMktResInstType())) {
                requestStatusCd = ResourceConst.MKTRESSTATE.WATI_REVIEW.getCode();
                processId = WorkFlowConst.PROCESS_ID.PROCESS_1015.getTypeCode();
            }else {
                TypeSelectByIdReq typeReq = new TypeSelectByIdReq();
                typeReq.setTypeId(req.getTypeId());
                ResultVO<TypeDetailResp> typeDetailRespResultVO = typeService.getDetailType(typeReq);
                if (!typeDetailRespResultVO.isSuccess() || null == typeDetailRespResultVO.getResultData()) {
                    return ResultVO.error("产品类型不存在");
                }
                String detailCode = typeDetailRespResultVO.getResultData().getDetailCode();
                if (TypeConst.TYPE_DETAIL.FUSION_TERMINAL.getCode().equals(detailCode) || TypeConst.TYPE_DETAIL.SET_TOP_BOX.getCode().equals(detailCode)) {
                    processId = WorkFlowConst.PROCESS_ID.PROCESS_1016.getTypeCode();
                    requestStatusCd = ResourceConst.MKTRESSTATE.WAIT_SPOTCHECK_CUSTSUP.getCode();
                } else{
                    processId = WorkFlowConst.PROCESS_ID.PROCESS_1014.getTypeCode();
                    requestStatusCd = ResourceConst.MKTRESSTATE.WAIT_SPOTCHECK.getCode();
                }
            }
        }else{
            // 移动串码审核流程
            taskSubType = WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_1020.getTaskSubType();
            requestStatusCd = ResourceConst.MKTRESSTATE.PROCESSING.getCode();
            processId = WorkFlowConst.PROCESS_ID.PROCESS_1013.getTypeCode();
        }
        SelectProcessResp resp = new SelectProcessResp();
        resp.setProcessId(processId);
        resp.setRequestStatusCd(requestStatusCd);
        resp.setTaskSubType(taskSubType);
        return ResultVO.success(resp);
    }

    @Async
    @Override
    public ResultVO noticeITMS(List<String> mktResInstNbrList, String userName, String storeId, String lanId) {
        //附加参数  city_code=731# warehouse=12#source=1#factory=厂家
        StringBuffer params = new StringBuffer();
        String addMethod = "ITMS_ADD";
        String dellMethod = "ITMS_DELL";
        params.append("city_code=").append(lanId).append("#warehouse=").append(storeId).append("#source=2").
                append("#factory=手机");
        List<String> successList = new ArrayList<>();
        ResultVO resultVO = null;
        for (String mktResInstNbr : mktResInstNbrList) {
            Map request = new HashMap<>();
            request.put("deviceId", mktResInstNbr);
            request.put("userName", userName);
            request.put("code", addMethod);
            request.put("params", params.toString());
            resultVO = zopClientUtil.callExcuteNoticeITMS(MarketingResConst.ServiceEnum.OrdInventoryChange.getCode(), MarketingResConst.ServiceEnum.OrdInventoryChange.getVersion(), request);
            log.info("ResourceInstCheckServiceImpl.noticeITMS zopClientUtil.callExcuteNoticeITMS resp={}", JSON.toJSONString(resultVO));
            if (resultVO.isSuccess()) {
                successList.add(mktResInstNbr);
            }else {
                break;
            }
        }
        mktResInstNbrList.removeAll(successList);
        if (CollectionUtils.isNotEmpty(mktResInstNbrList)) {
            for (String mktResInstNbr : mktResInstNbrList) {
                Map request = new HashMap<>();
                request.put("deviceId", mktResInstNbr);
                request.put("userName", userName);
                request.put("code", dellMethod);
                request.put("params", params.toString());
                ResultVO dellResultVO = zopClientUtil.callExcuteNoticeITMS(MarketingResConst.ServiceEnum.OrdInventoryChange.getCode(), MarketingResConst.ServiceEnum.OrdInventoryChange.getVersion(), request);
                log.info("ResourceInstCheckServiceImpl.noticeITMS zopClientUtil.callExcuteNoticeITMS dellResultVO={}", JSON.toJSONString(dellResultVO));
            }
            return resultVO;
        } else {
            return ResultVO.success();
        }
    }

    @Override
    public ResultVO<Boolean> greenChannelValid(String mktResId, String merchantId) {
        TagRelListReq tagRelListReq = new TagRelListReq();
        tagRelListReq.setProductId(mktResId);
        tagRelListReq.setRelTagIdList(Lists.newArrayList(SystemConst.GreenChannelType.SpecialType.getType()));
        ResultVO<List<TagRelListResp>> tagRelVO = tagRelService.listTagRel(tagRelListReq);
        log.info("ResourceInstCheckServiceImpl.greenChannelValid publicDictService.queryPublicDictListByType req={}, resp={}", JSON.toJSONString(tagRelListReq), JSON.toJSONString(tagRelVO));
        // 特殊权限机型
        if (tagRelVO.isSuccess() && CollectionUtils.isNotEmpty(tagRelVO.getResultData())) {
            return ResultVO.success(true);
        }
        MerchantTagRelListReq tagReq = new MerchantTagRelListReq();
        tagReq.setMerchantId(merchantId);
        tagReq.setRelTagIdList(Lists.newArrayList(SystemConst.GreenChannelType.ChainStore.getType(),
                SystemConst.GreenChannelType.Echannle.getType(),
                SystemConst.GreenChannelType.Honor.getType(),
                SystemConst.GreenChannelType.XiaoMi.getType(),
                SystemConst.GreenChannelType.HuaWei.getType()));
        ResultVO<List<MerchantTagRelDTO>> merchantTagVO = merchantTagRelService.listMerchantAndTag(tagReq);
        if (merchantTagVO.isSuccess() && CollectionUtils.isNotEmpty(merchantTagVO.getResultData())) {
            List<MerchantTagRelDTO> merchantTagRelList = merchantTagVO.getResultData();
            TagRelListReq tagRelReq = new TagRelListReq();
            for (MerchantTagRelDTO dto : merchantTagRelList) {
                tagRelReq.setRelTagIdList(Lists.newArrayList(dto.getRelTagId()));
                ResultVO<List<TagRelListResp>> tagRelTagVO = tagRelService.listTagRel(tagRelReq);
                // 既有商家权限，又有对应的机型权限，通过
                if (tagRelTagVO.isSuccess() && CollectionUtils.isNotEmpty(tagRelTagVO.getResultData())) {
                    return ResultVO.success(true);
                }
            }
        }
        ResultVO<List<String>> transferPermissionVO = merchantRulesService.getGreenChannelPermission(merchantId);
        log.info("ResourceInstCheckServiceImpl.greenChannelValid merchantRulesService.getGreenChannelPermission req={}, resp={}", merchantId, JSON.toJSONString(transferPermissionVO));
        if (null == transferPermissionVO || !transferPermissionVO.isSuccess() || CollectionUtils.isEmpty(transferPermissionVO.getResultData())) {
            // 没有权限，返回false
            return ResultVO.success(false);
        }

        List<String> productIdList = transferPermissionVO.getResultData();
        if (productIdList.contains(mktResId)) {
            return ResultVO.success(true);
        }
        return ResultVO.success(false);
    }
}