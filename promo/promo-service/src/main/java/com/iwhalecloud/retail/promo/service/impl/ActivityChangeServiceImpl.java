package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.ActivityChangeDetailDTO;
import com.iwhalecloud.retail.promo.dto.ActivityParticipantDTO;
import com.iwhalecloud.retail.promo.dto.ActivityScopeDTO;
import com.iwhalecloud.retail.promo.dto.resp.ActivityChangeResp;
import com.iwhalecloud.retail.promo.entity.*;
import com.iwhalecloud.retail.promo.manager.*;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.mapper.CommonRegionMapper;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.promo.service.ActivityChangeService;

import java.util.ArrayList;
import java.util.List;


@Service
public class ActivityChangeServiceImpl implements ActivityChangeService {

    @Autowired
    private MarketingActivityManager marketingActivityManager;
    @Autowired
    private ActivityChangeManager activityChangeManager;
    @Autowired
    private ActivityChangeDetailManager activityChangeDetailManager;
    @Autowired
    private ActivityScopeManager activityScopeManager;
    @Autowired
    private ActivityParticipantManager activityParticipantManager;
    @Reference
    private CommonRegionService commonRegionService;

    /**
     * 查找营销活动变更信息
     * @param activityId
     * @return
     */
    @Override
    public ResultVO<ActivityChangeResp> queryMarketingActivityChangeInfo(String activityId) {
        MarketingActivity activity = marketingActivityManager.getMarketingActivityById(activityId);
        if(activity==null|| PromoConst.ActivityIsModifying.NO.getCode().equals(activity.getIsModifiying())){
            return ResultVO.error();
        }
        ActivityChange activityChange = activityChangeManager.queryLatestActivityChangeByActivityId(activityId);
        if (activityChange==null){
            return ResultVO.error();
        }
        ActivityChangeResp resp = new ActivityChangeResp();
        BeanUtils.copyProperties(activityChange,resp);
        List<ActivityChangeDetail> changeDetails = activityChangeDetailManager.queryActivityChangeDetail(activityChange.getChangeId());
        if (changeDetails == null || changeDetails.size() == 0){
            return ResultVO.error();
        }
        //营销活动变更详情列表信息
        List<ActivityChangeDetailDTO> activityChangeDetailDTOList = new ArrayList<>();
        for (int i = 0; i < changeDetails.size(); i++) {
            ActivityChangeDetail detail = changeDetails.get(i);
            ActivityChangeDetailDTO activityChangeDetailDTO = new ActivityChangeDetailDTO();
            BeanUtils.copyProperties(detail, activityChangeDetailDTO);
            if(ActivityScope.TNAME.equals(detail.getTableName())){
                //根据变更详情获取活动范围信息
                ActivityScopeDTO activityScopeDTO = getActivityScopeDTO(detail);
                activityChangeDetailDTO.setActivityScopeDTO(activityScopeDTO);
            }
            if(ActivityParticipant.TNAME.equals(detail.getTableName())){
                //根据变更详情获取参与对象信息
                ActivityParticipantDTO activityParticipantDTO = getActivityParticipantDTO(detail);
                activityChangeDetailDTO.setActivityParticipantDTO(activityParticipantDTO);
            }
            activityChangeDetailDTOList.add(activityChangeDetailDTO);
        }
        resp.setActivityChangeDetailDTOList(activityChangeDetailDTOList);
        return ResultVO.success(resp);
    }

    /**
     * 根据变更详情获取参与对象信息
     * @param detail
     * @return
     */
    private ActivityParticipantDTO getActivityParticipantDTO(ActivityChangeDetail detail) {
        ActivityParticipantDTO activityParticipantDTO = new ActivityParticipantDTO();
        ActivityParticipant participant = activityParticipantManager.queryActivityParticipantById(detail.getKeyValue());
        if(participant==null){
            return null;
        }
        BeanUtils.copyProperties(participant, activityParticipantDTO);
        //activityParticipantDTO
        if(PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_20.getCode().equals(detail.getFieldType())){
            activityParticipantDTO.setMerchantName(activityParticipantDTO.getShopName());
            activityParticipantDTO.setMerchantCode(activityParticipantDTO.getShopCode());
            activityParticipantDTO.setMerchantId(activityParticipantDTO.getShopCode());
        }else if(PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_10.getCode().equals(detail.getFieldType())){
            if (activityParticipantDTO.getLanId()!=null){
                ResultVO<CommonRegionDTO> resultVO = commonRegionService.getCommonRegionById(activityParticipantDTO.getLanId());
                CommonRegionDTO commonRegionDTO = resultVO==null?null:resultVO.getResultData();
                if (commonRegionDTO!=null){
                    activityParticipantDTO.setLanName(commonRegionDTO.getRegionName());
                    activityParticipantDTO.setKey(commonRegionDTO.getRegionId());
                    activityParticipantDTO.setTitle(commonRegionDTO.getRegionName());
                    activityParticipantDTO.setRegionId(commonRegionDTO.getRegionId());
                }
            }
            if (activityParticipantDTO.getCity()!=null){
                ResultVO<CommonRegionDTO> resultVO = commonRegionService.getCommonRegionById(activityParticipantDTO.getCity());
                CommonRegionDTO commonRegionDTO = resultVO==null?null:resultVO.getResultData();
                if (commonRegionDTO!=null){
                    activityParticipantDTO.setCityName(commonRegionDTO.getRegionName());
                    activityParticipantDTO.setKey(commonRegionDTO.getRegionId());
                    activityParticipantDTO.setTitle(commonRegionDTO.getRegionName());
                    activityParticipantDTO.setRegionId(commonRegionDTO.getRegionId());
                }
            }
        }
        return activityParticipantDTO;
    }

    /**
     * 根据变更详情获取活动范围信息
     * @param detail
     * @return
     */
    private ActivityScopeDTO getActivityScopeDTO(ActivityChangeDetail detail) {
        ActivityScopeDTO activityScopeDTO = new ActivityScopeDTO();
        ActivityScope scope = activityScopeManager.queryActivityScopeById(detail.getKeyValue());
        if(scope==null){
            return null;
        }
        BeanUtils.copyProperties(scope, activityScopeDTO);
        //补充activityScopeDTO其他信息
        if(PromoConst.ActivityScopeType.ACTIVITY_SCOPE_TYPE_20.getCode().equals(detail.getFieldType())){
            activityScopeDTO.setMerchantName(activityScopeDTO.getSupplierName());
            activityScopeDTO.setMerchantCode(activityScopeDTO.getSupplierCode());
            activityScopeDTO.setMerchantId(activityScopeDTO.getSupplierCode());
        }else if(PromoConst.ActivityScopeType.ACTIVITY_SCOPE_TYPE_10.getCode().equals(detail.getFieldType())){
            if (activityScopeDTO.getLanId()!=null){
                ResultVO<CommonRegionDTO> resultVO = commonRegionService.getCommonRegionById(activityScopeDTO.getLanId());
                CommonRegionDTO commonRegionDTO = resultVO==null?null:resultVO.getResultData();
                if (commonRegionDTO!=null){
                    activityScopeDTO.setLanName(commonRegionDTO.getRegionName());
                    activityScopeDTO.setKey(commonRegionDTO.getRegionId());
                    activityScopeDTO.setTitle(commonRegionDTO.getRegionName());
                    activityScopeDTO.setRegionId(commonRegionDTO.getRegionId());
                }
            }
            if (activityScopeDTO.getCity()!=null){
                ResultVO<CommonRegionDTO> resultVO = commonRegionService.getCommonRegionById(activityScopeDTO.getCity());
                CommonRegionDTO commonRegionDTO = resultVO==null?null:resultVO.getResultData();
                if (commonRegionDTO!=null){
                    activityScopeDTO.setCityName(commonRegionDTO.getRegionName());
                    activityScopeDTO.setKey(commonRegionDTO.getRegionId());
                    activityScopeDTO.setTitle(commonRegionDTO.getRegionName());
                    activityScopeDTO.setRegionId(commonRegionDTO.getRegionId());
                }
            }
        }
        return activityScopeDTO;
    }

}