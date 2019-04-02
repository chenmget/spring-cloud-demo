package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.common.DateUtils;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.warehouse.common.MarketingResConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.MktResStoreTempDTO;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreItemReq;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreReq;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreToFormalReq;
import com.iwhalecloud.retail.warehouse.dto.response.markresswap.SynMarkResStoreResp;
import com.iwhalecloud.retail.warehouse.entity.MktResStoreTemp;
import com.iwhalecloud.retail.warehouse.entity.ResouceStore;
import com.iwhalecloud.retail.warehouse.entity.ResouceStoreObjRel;
import com.iwhalecloud.retail.warehouse.manager.MktResStoreTempManager;
import com.iwhalecloud.retail.warehouse.manager.ResouceStoreManager;
import com.iwhalecloud.retail.warehouse.manager.ResouceStoreObjRelManager;
import com.iwhalecloud.retail.warehouse.service.MktResStoreTempService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Slf4j
public class MktResStoreTempServiceImpl implements MktResStoreTempService {

    @Autowired
    private MktResStoreTempManager mktResStoreTempManager;

    @Autowired
    private ResouceStoreObjRelManager resouceStoreObjRelManager;

    @Reference
    private MerchantService merchantService;

    @Autowired
    private ResouceStoreManager resouceStoreManager;

    @Reference
    private CommonRegionService commonRegionService;


    @Autowired
    private Constant constant;
    @Override
    public ResultVO<SynMarkResStoreResp> synMarkResStore(String reqStr){
        SynMarkResStoreReq req = JSON.parseObject(reqStr,SynMarkResStoreReq.class);
        ResultVO<SynMarkResStoreResp>  resultVO = this.synMarkResStoreForObj(req);

        return resultVO;
    }
    @Override
    public ResultVO<SynMarkResStoreResp> synMarkResStoreForObj(SynMarkResStoreReq req) {
        log.info("MktResStoreTempServiceImpl.synMarkResStore req={}", req == null ? "" : JSON.toJSON(req));
        String actType = req.getActType();
        ResultVO<SynMarkResStoreResp> resultVO = ResultVO.success();
        if (MarketingResConst.ACT_TYPE_ADD.equals(actType)) {
            resultVO = addMarkResStore(req);
        } else if (MarketingResConst.ACT_TYPE_UPDATE.equals(actType)) {
            resultVO = updateMarkResStore(req);
        } else {
            return ResultVO.error(constant.getCommonParamError());
        }
        SynMarkResStoreResp resp = new SynMarkResStoreResp();
        if (resultVO.isSuccess()) {
            resp.setCode(MarketingResConst.TO_MK_SUC);
            resp.setMessage(constant.getStoreDealSuc());
        } else {
            resp.setCode(MarketingResConst.TO_MK_ERROR);
            resp.setMessage(StringUtils.isEmpty(resultVO.getResultMsg()) ? constant.getStoreDealError(): resultVO.getResultMsg());
        }
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultMsg(constant.getStoreDealSuc());
        resultVO.setResultData(resp);
        log.info("MktResStoreTempServiceImpl.synMarkResStore resp={}", JSON.toJSON(resultVO));
        return resultVO;


    }

    @Override
    public ResultVO addMarkResStore(SynMarkResStoreReq req) {
        SynMarkResStoreItemReq storeItemReq = req.getRequest();
        if (storeItemReq == null) {
            return ResultVO.error(constant.getCommonParamError());
        }
        String mktResStoreId = storeItemReq.getMktResStoreId();
        if (StringUtils.isEmpty(mktResStoreId)) {
            return ResultVO.error(constant.getCommonParamError());
        }
        MktResStoreTempDTO mktResStoreTempDTO = this.getMktResStoreTempDTO(mktResStoreId);
        if (mktResStoreTempDTO != null) {
            return ResultVO.error(constant.getStoreTitle()+ req.getRequest().getMktResStoreId() + constant.getCommonExit());
        }
        MktResStoreTemp addData = new MktResStoreTemp();
        reqToMktResStoreTemp(addData, storeItemReq);

        return mktResStoreTempManager.addMktResStoreTemp(addData);
    }

    @Override
    public List<MktResStoreTempDTO> listSynMktResStoreTempDTOList(SynMarkResStoreToFormalReq req) {
        Page<MktResStoreTempDTO> page = mktResStoreTempManager.listSynMktResStoreTempDTOList(req);
        if(page!=null&&page.getRecords()!=null){
            return page.getRecords();
        }
        return null;
    }

    private  int getSynMktResStoreTempDTOCount() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(MktResStoreTemp.FieldNames.synStatus.getTableFieldName(),MarketingResConst.COMMON_NO);
        return this.mktResStoreTempManager.count(queryWrapper);

    }


    @Override
    public ResultVO updateMarkResStore(SynMarkResStoreReq req) {
        SynMarkResStoreItemReq storeItemReq = req.getRequest();
        if (storeItemReq == null) {
            return ResultVO.error(constant.getCommonParamError());
        }
        String mktResStoreId = storeItemReq.getMktResStoreId();
        if (StringUtils.isEmpty(mktResStoreId)) {
            return ResultVO.error(constant.getCommonParamError());
        }
        MktResStoreTempDTO mktResStoreTempDTO = this.getMktResStoreTempDTO(mktResStoreId);
        if (mktResStoreTempDTO == null) {
            ResultVO.error(constant.getStoreTitle() + req.getRequest().getMktResStoreId() + constant.getCommonNoExit());
        }
        MktResStoreTemp updateData = new MktResStoreTemp();
        reqToMktResStoreTemp(updateData, storeItemReq);
        return this.mktResStoreTempManager.updateMktResStoreTemp(updateData);
    }


    @Override
    public MktResStoreTempDTO getMktResStoreTempDTO(String mktResStoreId) {
        MktResStoreTemp temp = this.mktResStoreTempManager.getMktResStoreTempDTO(mktResStoreId);
        MktResStoreTempDTO rs = null;
        if (temp != null) {
            rs = new MktResStoreTempDTO();
            BeanUtils.copyProperties(temp, rs);
        }

        return rs;
    }

    @Override
    public ResultVO synTempToMktResStore() {
        log.info(MktResStoreTempServiceImpl.class.getName() + ".synTempToMktResStore start");
        long startTimes = System.currentTimeMillis();

        int length = this.getSynMktResStoreTempDTOCount();
        log.info(MktResStoreTempServiceImpl.class.getName() + ".synTempToMktResStore count={}" , length);

        if(length>0){
            int pageCount = (length + MarketingResConst.SYN_BATCH - 1) / MarketingResConst.SYN_BATCH;
            for (int page = 1; page <= pageCount; page++) {
                int start = 1 + (MarketingResConst.SYN_BATCH * (page - 1));
                int end = MarketingResConst.SYN_BATCH * page > length ? length : MarketingResConst.SYN_BATCH * page;
                try {
                    //获取需要同步的数据
                    SynMarkResStoreToFormalReq req = new SynMarkResStoreToFormalReq();
                    req.setSynStatus(MarketingResConst.COMMON_NO);
                    req.setPageNo(page);
                    req.setPageSize(MarketingResConst.SYN_BATCH);
                    List<MktResStoreTempDTO> dealList = this.listSynMktResStoreTempDTOList(req);
                    if(dealList!=null&&!dealList.isEmpty()){
                        log.info(MktResStoreTempServiceImpl.class.getName() + ".synTempToMktResStore start syn:{}-{}",start,end);
                        this.synTempToMktResStoreBatch(dealList);
                        log.info(MktResStoreTempServiceImpl.class.getName() + ".synTempToMktResStore suc syn:{}-{}",start,end);
                    }

                } catch (RuntimeException e) {
                    log.info(MktResStoreTempServiceImpl.class.getName() + ".synTempToMktResStore error syn:{}-{}",start,end);
                    log.error(MktResStoreTempServiceImpl.class.getName() + ".synTempToMktResStore error:", e);
                } catch (Exception e) {
                    log.info(MktResStoreTempServiceImpl.class.getName() + ".synTempToMktResStore error syn:{}-{}",start,end);
                    log.error(MktResStoreTempServiceImpl.class.getName() + ".synTempToMktResStore error:", e);

                }
            }
        }


        long endTimes = System.currentTimeMillis();
        log.info(MktResStoreTempServiceImpl.class.getName() + ".synTempToMktResStore end:"+(endTimes-startTimes/1000));

        return ResultVO.success();
    }

    private void reqToMktResStoreTemp(MktResStoreTemp dbData, SynMarkResStoreItemReq req) {

        dbData.setMktResStoreId(req.getMktResStoreId());
        dbData.setMktResStoreName(req.getMktResStoreName());
        if (StringUtils.isNotEmpty(req.getCheckDate())) {
            dbData.setCheckDate(DateUtils.strToUtilDate(req.getCheckDate()));
        }
        if (StringUtils.isNotEmpty(req.getCreateDate())) {
            dbData.setCreateDate(DateUtils.strToUtilDate(req.getCreateDate()));
        }
        if (StringUtils.isNotEmpty(req.getParStoreId())) {
            dbData.setParStoreId(Long.valueOf(req.getParStoreId()));
        }
        if (StringUtils.isNotEmpty(req.getOrgId())) {
            dbData.setOrgId(Long.valueOf(req.getOrgId()));
        }
        if (StringUtils.isNotEmpty(req.getStaffId())) {
            dbData.setStaffId(Long.valueOf(req.getStaffId()));
        }
        if (StringUtils.isNotEmpty(req.getRegionId())) {
            dbData.setRegionId(Long.valueOf(req.getRegionId()));
        }
        if (StringUtils.isNotEmpty(req.getChannelId())) {
            dbData.setChannelId(Long.valueOf(req.getChannelId()));
        }
        dbData.setStoreType(req.getStoreType());
        dbData.setStatusCd(req.getStatusCd());
        if (StringUtils.isNotEmpty(req.getStatusDate())) {
            dbData.setStatusDate(DateUtils.strToUtilDate(req.getStatusDate()));
        }

        dbData.setRemark(req.getRemark());
        dbData.setMktResStoreNbr(req.getMktResStoreNbr());
        dbData.setStorageCode1(req.getRStorageCode1());
        dbData.setStorageCode2(req.getRStorageCode2());
        if (StringUtils.isNotEmpty(req.getLanId())) {
            dbData.setLanId(Long.valueOf(req.getLanId()));
        }
        if (StringUtils.isNotEmpty(req.getCOperId())) {
            dbData.setOperId(Long.valueOf(req.getCOperId()));
        }

        dbData.setAddress(req.getAddress());
        dbData.setVbatchcode(req.getVbatchcode());
        if (StringUtils.isNotEmpty(req.getRcType())) {
            dbData.setRcType(Long.valueOf(req.getRcType()));
        }
        if (StringUtils.isNotEmpty(req.getFamilyId())) {
            dbData.setFamilyId(Long.valueOf(req.getFamilyId()));
        }
        if (StringUtils.isNotEmpty(req.getCreateStaff())) {
            dbData.setCreateStaff(Long.valueOf(req.getCreateStaff()));
        }

        dbData.setDirectSupply(req.getDirectSupply());
        dbData.setProvider(req.getProvider());
        dbData.setProviderName(req.getProviderName());
        if (StringUtils.isNotEmpty(req.getRecStoreId())) {
            dbData.setRecStoreId(Long.valueOf(req.getRecStoreId()));
        }

        dbData.setRecType(req.getRecType());
        if (StringUtils.isNotEmpty(req.getRecDay())) {
            dbData.setRecDay(Long.valueOf(req.getRecDay()));
        }

        dbData.setStoreSubType(req.getStoreSubType());
        dbData.setStoreGrade(req.getStoreGrade());
        if (StringUtils.isNotEmpty(req.getEffDate())) {
            dbData.setEffDate(DateUtils.strToUtilDate(req.getEffDate()));
        }
        if (StringUtils.isNotEmpty(req.getExpDate())) {
            dbData.setExpDate(DateUtils.strToUtilDate(req.getExpDate()));
        }
        if (StringUtils.isNotEmpty(req.getUpdateStaff())) {
            dbData.setUpdateStaff(Long.valueOf(req.getUpdateStaff()));
        }
        if (StringUtils.isNotEmpty(req.getUpdateDate())) {
            dbData.setUpdateDate(DateUtils.strToUtilDate(req.getUpdateDate()));
        }
        //将状态改成未同步
        dbData.setSynStatus(MarketingResConst.COMMON_NO);


    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO synTempToMktResStoreBatch(List<MktResStoreTempDTO> dataList) {
        //获取商家信息
        List<String> parCrmOrgIdList = new ArrayList<String>();
        List<String> storeList = new ArrayList<String>();

        for (MktResStoreTempDTO tempDTO : dataList) {
            Long orgId = tempDTO.getOrgId();
            if (null != orgId) {
                parCrmOrgIdList.add(String.valueOf(orgId));
            }
            storeList.add(tempDTO.getMktResStoreId());
        }
        //一次性获取所有数据
        List<String> regionIdList = new ArrayList<String>();
        Map<String, MerchantDTO> marchantMap = this.getMerchantDTOMap(parCrmOrgIdList);
        for (String key : marchantMap.keySet()) {
            MerchantDTO merchantDTO =marchantMap.get(key);
            if(!org.springframework.util.StringUtils.isEmpty(merchantDTO.getLanId())){
                regionIdList.add(merchantDTO.getLanId());
            }
            if(!org.springframework.util.StringUtils.isEmpty(merchantDTO.getCity())){
                regionIdList.add(merchantDTO.getCity());
            }
        }
        //区域数据
        Map<String, String> regionMap = this.getRegionMap(regionIdList);
        //原对象使用数据
        Map<String, ResouceStoreObjRel> storeObjRelMap = this.getResouceStoreObjRelMap(storeList);
        //原数据
        Map<String, ResouceStore> storeMap = this.getResouceStoreMap(storeList);
        //批量操作的数据
        List<ResouceStore> addList = new ArrayList<ResouceStore>();
        List<ResouceStore> updateList = new ArrayList<ResouceStore>();
        List<ResouceStoreObjRel> addRelList = new ArrayList<ResouceStoreObjRel>();
        List<ResouceStoreObjRel> updateRelList = new ArrayList<ResouceStoreObjRel>();
        List<String> updateTempList = new ArrayList<String>();

        for (MktResStoreTempDTO storeTempDTO : dataList) {
            MerchantDTO merchantDTO = marchantMap.get(String.valueOf(storeTempDTO.getOrgId()));
            //没有商家不进行处理
            if (null == merchantDTO||StringUtils.isEmpty(merchantDTO.getMerchantType())) {
                log.info("库存ID={},orgID={}对应的商家不存在",storeTempDTO.getMktResStoreId(),storeTempDTO.getOrgId());
                continue;
            }
            String mktResStoreId = storeTempDTO.getMktResStoreId();

            ResouceStoreDTO dto = new ResouceStoreDTO();
            BeanUtils.copyProperties(storeTempDTO, dto);
            // 这两个值从merchantDTO获取
            dto.setRegionId(merchantDTO.getCity());
            dto.setLanId(merchantDTO.getLanId());
            // 参数没有，正式表有 指向公共管理区域名称
            dto.setRegionName(regionMap.get(merchantDTO.getCity()));
            // 商家ID
            dto.setMerchantId(merchantDTO.getMerchantId());
            // 商家编码
            dto.setMerchantCode(merchantDTO.getMerchantCode());
            // 商家名称
            dto.setMerchantName(merchantDTO.getMerchantName());
            // 商家类型
            dto.setMerchantType(merchantDTO.getMerchantType());
            // 商家所属经营主体
            dto.setBusinessEntityName(merchantDTO.getBusinessEntityName());
            // 销售点编码
            dto.setShopCode(merchantDTO.getShopCode());
            // 销售点名称
            dto.setShopName(merchantDTO.getShopName());
            // 本地网名称
            dto.setLanName(regionMap.get(merchantDTO.getLanId()));

            //参数有，正式表没有,不进行处理,包括字段:orgId,rStorageCode1,rStorageCode2,channelId,cOperId,address,vbatchcode,rcType,familyId
            ResouceStore resouceStore = new ResouceStore();
            BeanUtils.copyProperties(dto, resouceStore);
            //对象使用的处理

            ResouceStore exitStore = storeMap.get(mktResStoreId);
            ResouceStoreObjRel exitStoreObjRel = storeObjRelMap.get(mktResStoreId);
            //不存在，新增
            if (null==exitStore) {
                resouceStore.setCreateDate(new Date());
                resouceStore.setCreateStaff(MarketingResConst.DEF_CREATER);
                addList.add(resouceStore);
            } else {
                resouceStore.setUpdateDate(new Date());
                resouceStore.setUpdateStaff(MarketingResConst.DEF_CREATER);
                updateList.add(resouceStore);
            }
            ResouceStoreObjRel storeObjRel = new ResouceStoreObjRel();

            storeObjRel.setMktResStoreId(mktResStoreId);
            storeObjRel.setIsDefault(MarketingResConst.COMMON_YES);
            storeObjRel.setObjId(merchantDTO.getMerchantId());
            storeObjRel.setObjType(merchantDTO.getMerchantType());
            storeObjRel.setStatusCd(dto.getStatusCd());
            storeObjRel.setRegionId(dto.getRegionId());
            storeObjRel.setRemark("营销资源同步");

            if (null == exitStoreObjRel) {
                storeObjRel.setCreateDate(new Date());
                storeObjRel.setCreateStaff(MarketingResConst.DEF_CREATER);
                addRelList.add(storeObjRel);
            } else {
                storeObjRel.setMktResStoreObjRelId(exitStoreObjRel.getMktResStoreObjRelId());
                storeObjRel.setUpdateDate(new Date());
                storeObjRel.setUpdateStaff(MarketingResConst.DEF_CREATER);
                updateRelList.add(storeObjRel);
            }
            updateTempList.add(mktResStoreId);
        }
        if (!addList.isEmpty()) {
            resouceStoreManager.saveBatch(addList);
        }
        if (!updateList.isEmpty()) {
            resouceStoreManager.updateBatchById(updateList);
        }
        if (!addRelList.isEmpty()) {
            resouceStoreObjRelManager.saveBatch(addRelList);
        }
        if (!updateRelList.isEmpty()) {
            resouceStoreObjRelManager.updateBatchById(updateRelList);
        }
        if (!updateTempList.isEmpty()) {
            mktResStoreTempManager.updateMktResStoreTempSyn(updateTempList);
        }
        return ResultVO.success();
    }

    private Map<String, ResouceStoreObjRel> getResouceStoreObjRelMap(List<String> storeList) {
        Map<String, ResouceStoreObjRel> map = new HashMap<String, ResouceStoreObjRel>();
        if (null == storeList || storeList.isEmpty()) {
            return map;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in(ResouceStoreObjRel.FieldNames.mktResStoreId.getTableFieldName(), storeList);

        List<ResouceStoreObjRel> resouceStoreObjRelList = resouceStoreObjRelManager.list(queryWrapper);
        if (null == resouceStoreObjRelList || resouceStoreObjRelList.isEmpty()) {
            return map;
        }
        for (ResouceStoreObjRel storeObjRel : resouceStoreObjRelList) {
            map.put(storeObjRel.getMktResStoreId(), storeObjRel);
        }

        return map;
    }

    private Map<String, ResouceStore> getResouceStoreMap(List<String> storeList) {
        Map<String, ResouceStore> map = new HashMap<String, ResouceStore>();
        if (null == storeList || storeList.isEmpty()) {
            return map;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in(ResouceStore.FieldNames.mktResStoreId.getTableFieldName(), storeList);

        List<ResouceStore> resouceStoreObjRelList = resouceStoreManager.list(queryWrapper);
        if (null == resouceStoreObjRelList || resouceStoreObjRelList.isEmpty()) {
            return map;
        }
        for (ResouceStore resouceStore : resouceStoreObjRelList) {
            map.put(resouceStore.getMktResStoreId(), resouceStore);
        }


        return map;
    }
    private Map<String,String> getRegionMap(List<String> regionList){
        Map<String,String> regionMap = new HashMap<String,String>();
        CommonRegionListReq req =  new CommonRegionListReq();
        req.setRegionIdList(regionList);
        ResultVO<List<CommonRegionDTO>> resultVO = this.commonRegionService.listCommonRegion(req);
        if(resultVO!=null&&resultVO.isSuccess()&&resultVO.getResultData()!=null){
            for (CommonRegionDTO commonRegionDTO : resultVO.getResultData()) {
                regionMap.put(commonRegionDTO.getRegionId(),commonRegionDTO.getRegionName());
            }
        }
        return regionMap;
    }


    private Map<String, MerchantDTO> getMerchantDTOMap(List<String> parCrmOrgIdList) {
        Map<String, MerchantDTO> marchantMap = new HashMap<String, MerchantDTO>();
        if (null == parCrmOrgIdList || parCrmOrgIdList.isEmpty()) {
            return marchantMap;
        }
        MerchantListReq req = new MerchantListReq();
        req.setParCrmOrgIdList(parCrmOrgIdList);
//        req.setNeedOtherTableFields(true);
        ResultVO<List<MerchantDTO>> listResultVO = merchantService.listMerchant(req);
        if (null != listResultVO && listResultVO.isSuccess() && null != listResultVO.getResultData()) {
            List<MerchantDTO> merchantDTOList = listResultVO.getResultData();
            for (MerchantDTO merchantDTO : merchantDTOList) {
                String parCrmOrgId = merchantDTO.getParCrmOrgId();
                if (!org.springframework.util.StringUtils.isEmpty(parCrmOrgId)) {
                    marchantMap.put(parCrmOrgId, merchantDTO);
                }
            }
        }
        return marchantMap;

    }
}