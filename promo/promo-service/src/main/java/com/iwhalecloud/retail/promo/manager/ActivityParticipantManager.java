package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.ActivityParticipantDTO;
import com.iwhalecloud.retail.promo.entity.ActivityParticipant;
import com.iwhalecloud.retail.promo.mapper.ActivityParticipantMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ActivityParticipantManager{
    @Resource
    private ActivityParticipantMapper activityParticipantMapper;

    /**
     * 获取主键
     * @return
     */
    public String getPrimaryKey(){
        return activityParticipantMapper.getPrimaryKey();
    }

    /**插入参与对象信息
     * @param activityParticipant 参与对象实体
     * @return
     */
    public int insertActivityParticipant(ActivityParticipant activityParticipant) {
        return activityParticipantMapper.insert(activityParticipant);
    }
    /**
     * 添加参与对象
     * @param activityParticipantList 参与对象
     * @return
     */
    public void addActivityParticipantBatch(List<ActivityParticipant> activityParticipantList) {
        if (!CollectionUtils.isEmpty(activityParticipantList)) {
            for (ActivityParticipant activityParticipant : activityParticipantList) {
                if (activityParticipant.getId()==null){
                    activityParticipant.setId(activityParticipantMapper.getPrimaryKey());
                }
                activityParticipantMapper.insert(activityParticipant);
            }

        }
    }
    /**
     * 删除参与对象
     * @param marketingActivityId 参与对象
     * @return
     */
    public void deleteActivityParticipantBatch(String marketingActivityId) {
        activityParticipantMapper.deleteActivityParticipantBatch(marketingActivityId);
    }
    /**
     * 根据活动id查询参与对象
     * @param marketingActivityId 根据活动id查询参与对象
     * @return
     */
    public List<ActivityParticipant> queryActivityParticipantByCondition(String marketingActivityId) {
        QueryWrapper<ActivityParticipant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityParticipant.FieldNames.isDeleted.getTableFieldName(),PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        queryWrapper.eq(ActivityParticipant.FieldNames.marketingActivityId.getTableFieldName(), marketingActivityId);
        return activityParticipantMapper.selectList(queryWrapper);
    }
    public List<ActivityParticipantDTO> queryActivityParticipantByMktId(String marketingActivityId) {
        List<ActivityParticipantDTO> activityParticipantDTOList= new ArrayList<>();
        if (StringUtils.isNotEmpty(marketingActivityId)){
            activityParticipantDTOList= activityParticipantMapper.queryActivityParticipantByMktId(marketingActivityId);
        }
        return  activityParticipantDTOList;
    }

    public int queryActivityParticipantCountByMktId(List<String> marketingActivityIds,String merchantCode){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ActivityParticipant.FieldNames.marketingActivityId.getTableFieldName(), marketingActivityIds);
        queryWrapper.eq(ActivityParticipant.FieldNames.merchantCode.getTableFieldName(), merchantCode);
        queryWrapper.eq(ActivityParticipant.FieldNames.isDeleted.getTableFieldName(), PromoConst.UNDELETED);
        return activityParticipantMapper.selectCount(queryWrapper);
    }

    /**
     * 根据活动编码和地市编码过滤参与对象
     * @param marketingActivityId
     * @param lanId
     * @return
     */
    public ActivityParticipant queryActivityParticipantByLandId(String marketingActivityId, String lanId){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityParticipant.FieldNames.marketingActivityId.getTableFieldName(), marketingActivityId);
        queryWrapper.eq(ActivityParticipant.FieldNames.lanId.getTableFieldName(), lanId);
        queryWrapper.eq(ActivityParticipant.FieldNames.isDeleted.getTableFieldName(), PromoConst.UNDELETED);
        return activityParticipantMapper.selectOne(queryWrapper);
    }

    /**
     * 根据活动编码和区县编码过滤参与对象
     * @param marketingActivityId
     * @param cityId
     * @return
     */
    public ActivityParticipant queryActivityParticipantByCityId(String marketingActivityId, String cityId){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityParticipant.FieldNames.marketingActivityId.getTableFieldName(), marketingActivityId);
        queryWrapper.eq(ActivityParticipant.FieldNames.city.getTableFieldName(), cityId);
        queryWrapper.eq(ActivityParticipant.FieldNames.isDeleted.getTableFieldName(), PromoConst.UNDELETED);
        return activityParticipantMapper.selectOne(queryWrapper);
    }

    /**
     * 根据活动编码和商家编码过滤参与对象
     * @param marketingActivityId
     * @param merchantCode
     * @return
     */
    public ActivityParticipant queryActivityParticipantByMerchantCode(String marketingActivityId, String merchantCode){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityParticipant.FieldNames.marketingActivityId.getTableFieldName(), marketingActivityId);
        queryWrapper.eq(ActivityParticipant.FieldNames.merchantCode.getTableFieldName(), merchantCode);
        queryWrapper.eq(ActivityParticipant.FieldNames.isDeleted.getTableFieldName(), PromoConst.UNDELETED);
        return activityParticipantMapper.selectOne(queryWrapper);
    }

    /**
     * 批量更新参与对象
     * @param activityParticipantList 参与对象
     * @return
     */
    public void updateActivityParticipantBatch(List<ActivityParticipant> activityParticipantList) {
        if (!CollectionUtils.isEmpty(activityParticipantList)) {
            for (ActivityParticipant activityParticipant : activityParticipantList) {
                if (StringUtils.isNotEmpty(activityParticipant.getId())) {
                    activityParticipant.setGmtModified(new Date());
                    activityParticipantMapper.updateById(activityParticipant);
                }
            }
        }
    }


    /**
     * 根据id查询参与对象
     * @param participantId 活动参与对象id
     * @return
     */
    public ActivityParticipant queryActivityParticipantById(String participantId) {
        return activityParticipantMapper.selectById(participantId);
    }

    public List<ActivityParticipantDTO> queryActivityParticipantByMktIdAndStatus(String activityId, String status) {
        log.info("ActivityScopeManager.queryActivityScopeByMktIdAndStatus activityId={},status={}", activityId);
        if (StringUtils.isNotEmpty(activityId)&&StringUtils.isNotEmpty(status)){
            List<ActivityParticipantDTO> activityParticipantDTOList= activityParticipantMapper.queryActivityParticipantByMktIdAndStatus(activityId,status);
            return  activityParticipantDTOList;
        }
        return null;
    }
}
