package com.iwhalecloud.retail.warehouse.busiservice.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstCheckService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstValidReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsGetReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestAddReq;
import com.iwhalecloud.retail.warehouse.dto.response.SelectProcessResp;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstManager;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResourceInstCheckServiceImpl implements ResourceInstCheckService {

    @Autowired
    private ResourceInstManager resourceInstManager;

    @Reference
    private ProductService productService;

    @Value("${addNbrService.typeId}")
    private String typeId;

    @Value("${addNbrService.checkMaxNum}")
    private Integer checkMaxNum;


    @Override
    @Transactional(isolation= Isolation.SERIALIZABLE,propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public List<String> vaildOwnStore(ResourceInstValidReq req, CopyOnWriteArrayList<String> nbrList){
        List<String> existNbrs = new ArrayList<>();
        ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
        productGetByIdReq.setProductId(req.getMktResId());
        ResultVO<ProductResp> producttVO = productService.getProduct(productGetByIdReq);
        log.info("ResourceInstServiceImpl.vaildOwnStore productService.getProduct mktResId={} resp={}", req.getMktResId(), JSON.toJSONString(producttVO));
        String typeId = "";
        if (producttVO.isSuccess() && null != producttVO.getResultData()) {
            typeId = producttVO.getResultData().getTypeId();
            req.setTypeId(typeId);
        }
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
        resourceInstsGetReq.setMktResStoreId(req.getMktResStoreId());
        resourceInstsGetReq.setTypeId(typeId);
        List<ResourceInstDTO> inst = resourceInstManager.getResourceInsts(resourceInstsGetReq);
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
    public List<ResourceInstDTO> validMerchantStore(ResourceInstValidReq req){
        List<String> validNbrList = req.getMktResInstNbrs();
        ResourceInstsGetReq manufacturerResourceInstsGetReq = new ResourceInstsGetReq();
        manufacturerResourceInstsGetReq.setMktResInstNbrs(validNbrList);
        manufacturerResourceInstsGetReq.setMktResId(req.getMktResId());
        List<String> manufacturerTypes = new ArrayList<>();
        manufacturerTypes.add(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType());
        manufacturerResourceInstsGetReq.setMerchantTypes(manufacturerTypes);
        manufacturerResourceInstsGetReq.setMktResStoreId(req.getMktResStoreId());
        manufacturerResourceInstsGetReq.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        List<ResourceInstDTO> merchantInst = resourceInstManager.getResourceInsts(manufacturerResourceInstsGetReq);
        log.info("ResourceInstCheckServiceImpl.validMerchantStore resourceInstManager.getResourceInsts req={},resp={}", JSON.toJSONString(manufacturerResourceInstsGetReq), JSON.toJSONString(merchantInst));
        return  merchantInst;
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
            if (null != req.getCtCode()) {
                instDTO.setCtCode(req.getCtCode().get(nbr));
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
                if (null != req.getCtCode()) {
                    instDTO.setCtCode(req.getCtCode().get(nbr));
                }
                instDTOList.add(instDTO);
            }
        }
        log.info("ResourceInstCheckServiceImpl.getReqInst req={},totalNbrSize={}", JSON.toJSONString(req), instDTOList.size());
        return instDTOList;
    }

    @Override
    public SelectProcessResp selectProcess(ResourceInstAddReq req){
        String requestStatusCd = null;
        String processId = null;
        String taskSubType = null;
        // 固网串码审核流程
        if (ResourceConst.CONSTANT_YES.equals(req.getIsFixedLine())) {
            taskSubType = WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_3010.getTaskSubType();
            // 固网串码审核流程 1 集采或入库串码数量小于checkMaxNum（配置值）流程；2 两步抽检流程；3 一步抽检流程
            if (req.getMktResInstNbrs().size() < checkMaxNum || ResourceConst.MKTResInstType.NONTRANSACTION.getCode().equals(req.getMktResInstType())) {
                requestStatusCd = ResourceConst.MKTRESSTATE.WATI_REVIEW.getCode();
                processId = ResourceConst.FIXED_NBR_WORK_FLOW_INST;
            }else {
                String inTypeId = req.getTypeId();
                // 机顶盒，三合一产品类型对应的typeId
                List<String> typeIdList = Arrays.asList(typeId.split(","));
                if (typeIdList.contains(inTypeId)) {
                    processId = ResourceConst.TWO_STEP_WORK_FLOW_INST;
                    requestStatusCd = ResourceConst.MKTRESSTATE.WAIT_SPOTCHECK_MOBINT.getCode();
                } else{
                    processId = ResourceConst.ONE_STEP_WORK_FLOW_INST;
                    requestStatusCd = ResourceConst.MKTRESSTATE.WAIT_SPOTCHECK.getCode();
                }
            }
        }else{
            // 移动串码审核流程
            taskSubType = WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_1020.getTaskSubType();
            requestStatusCd = ResourceConst.MKTRESSTATE.PROCESSING.getCode();
            processId = ResourceConst.MOVE_NBR_WORK_FLOW_INST;
        }
        SelectProcessResp resp = new SelectProcessResp();
        resp.setProcessId(processId);
        resp.setRequestStatusCd(requestStatusCd);
        resp.setTaskSubType(taskSubType);
        return resp;
    }
}