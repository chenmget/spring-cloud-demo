package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.ActivityParticipantDTO;
import com.iwhalecloud.retail.promo.entity.*;
import com.iwhalecloud.retail.promo.mapper.ActivityChangeMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class ActivityChangeManager{
    @Resource
    private ActivityChangeMapper activityChangeMapper;
    @Resource
    private ActivityChangeDetailManager activityChangeDetailManager;
    @Resource
    private MarketingActivityManager marketingActivityManager;
    @Resource
    private ActivityScopeManager activityScopeManager;
    @Resource
    private ActivityParticipantManager activityParticipantManager;

    /**
     * 根据变更信息id查询变更信息
     * @param id 变更信息ID
     * @return
     */
    public ActivityChange queryActivityChangeById(String id) {
        return id==null?null:activityChangeMapper.selectById(id);
    }

    /**
     * 根据营销活动id查询变更信息列表
     * @param activityId 营销活动ID
     * @return
     */
    public List<ActivityChange> queryActivityChangeByActivityId(String activityId) {
        QueryWrapper<ActivityChange> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityChange.FieldNames.marketingActivityId.getTableFieldName(),activityId);
        List<ActivityChange> changes = activityChangeMapper.selectList(queryWrapper);
        return changes;
    }


    /**
     * 根据营销活动查询最新变更信息
     * @param activityId 营销活动ID
     * @return
     */

    public ActivityChange queryLatestActivityChangeByActivityId(String activityId) {
        ActivityChange change = activityChangeMapper.queryLatestActivityChangeByActivityId(activityId);
        return change;

    }

    /**插入活动变更信息
     * @param activityChange 活动变更实体
     * @return
     */
    public int insertActivityChange(ActivityChange activityChange) {
        return activityChangeMapper.insert(activityChange);
    }

    /**
     * 获取主键
     * @return
     */
    public String getPrimaryKey(){
        return activityChangeMapper.getPrimaryKey();
    }

    /**
     * 根据营销活动变更id将变更信息置为审核不通过
     * @param change  营销活动
     * @return
     */
    public boolean updateActivityChange(ActivityChange change) {
        return change==null?false:activityChangeMapper.updateById(change)==1;
    }



    /**
     * 根据营销活动变更id将变更信息置为审核通过,并进行营销活动变更操作
     * @param id  营销活动
     * @return
     */
    public boolean updateActivityChangePassById(String id) {
        ActivityChange change = activityChangeMapper.selectById(id);
        if (change==null){
           return false;
        }
        //1.准备变更相关数据
        //获取最新对应的变更明细
        List<ActivityChangeDetail> details = activityChangeDetailManager.queryActivityChangeDetail(change.getChangeId());
        //获取营销活动详情
        MarketingActivity marketingActivity = marketingActivityManager.getMarketingActivityById(change.getMarketingActivityId());
        //活动范围信息
        List<ActivityScope> activityScopeList = Lists.newArrayList();
        //参与对象信息
        List<ActivityParticipant> activityParticipantList = Lists.newArrayList();
        //2.组装新的营销活动、活动范围信息、活动对象信息
        for (ActivityChangeDetail detail:details) {
            //活动名称
            if(MarketingActivity.TNAME.equals(detail.getTableName())&&MarketingActivity.FieldNames.name.getTableFieldName().equals(detail.getChangeField())){
                marketingActivity.setName(detail.getNewValue());
                continue;
            }
            //活动开始时间
            if(MarketingActivity.TNAME.equals(detail.getTableName())&&MarketingActivity.FieldNames.startTime.getTableFieldName().equals(detail.getChangeField())){
                marketingActivity.setStartTime(new Date(Long.valueOf(detail.getNewValue())));
                continue;
            }
            //活动结束时间
            if(MarketingActivity.TNAME.equals(detail.getTableName())&&MarketingActivity.FieldNames.endTime.getTableFieldName().equals(detail.getChangeField())){
                marketingActivity.setEndTime(new Date(Long.valueOf(detail.getNewValue())));
                continue;
            }
            //活动发货开始时间
            if(MarketingActivity.TNAME.equals(detail.getTableName())&&MarketingActivity.FieldNames.deliverStartTime.getTableFieldName().equals(detail.getChangeField())){
                marketingActivity.setDeliverStartTime(new Date(Long.valueOf(detail.getNewValue())));
                continue;
            }
            //活动发货结束时间
            if(MarketingActivity.TNAME.equals(detail.getTableName())&&MarketingActivity.FieldNames.deliverEndTime.getTableFieldName().equals(detail.getChangeField())){
                marketingActivity.setDeliverEndTime(new Date(Long.valueOf(detail.getNewValue())));
                continue;
            }
            //活动支付定金开始时间
            if(MarketingActivity.TNAME.equals(detail.getTableName())&&MarketingActivity.FieldNames.preStartTime.getTableFieldName().equals(detail.getChangeField())){
                marketingActivity.setPreStartTime(new Date(Long.valueOf(detail.getNewValue())));
                continue;
            }
            //活动支付定金结束时间
            if(MarketingActivity.TNAME.equals(detail.getTableName())&&MarketingActivity.FieldNames.preEndTime.getTableFieldName().equals(detail.getChangeField())){
                marketingActivity.setPreEndTime(new Date(Long.valueOf(detail.getNewValue())));
                continue;
            }
            //活动支付尾款开始时间
            if(MarketingActivity.TNAME.equals(detail.getTableName())&&MarketingActivity.FieldNames.tailPayStartTime.getTableFieldName().equals(detail.getChangeField())){
                marketingActivity.setTailPayStartTime(new Date(Long.valueOf(detail.getNewValue())));
                continue;
            }
            //活动支付尾款结束时间
            if(MarketingActivity.TNAME.equals(detail.getTableName())&&MarketingActivity.FieldNames.tailPayEndTime.getTableFieldName().equals(detail.getChangeField())){
                marketingActivity.setTailPayEndTime(new Date(Long.valueOf(detail.getNewValue())));
                continue;
            }
            //活动概述
            if(MarketingActivity.TNAME.equals(detail.getTableName())&&MarketingActivity.FieldNames.brief.getTableFieldName().equals(detail.getChangeField())){
                marketingActivity.setBrief(detail.getNewValue());
                continue;
            }
            //活动描述
            if(MarketingActivity.TNAME.equals(detail.getTableName())&&MarketingActivity.FieldNames.description.getTableFieldName().equals(detail.getChangeField())){
                marketingActivity.setDescription(detail.getNewValue());
                continue;
            }
            //活动顶部图片
            if(MarketingActivity.TNAME.equals(detail.getTableName())&&MarketingActivity.FieldNames.topImgUrl.getTableFieldName().equals(detail.getChangeField())){
                marketingActivity.setTopImgUrl(detail.getNewValue());
                continue;
            }
            //活动页面图片
            if(MarketingActivity.TNAME.equals(detail.getTableName())&&MarketingActivity.FieldNames.pageImgUrl.getTableFieldName().equals(detail.getChangeField())){
                marketingActivity.setPageImgUrl(detail.getNewValue());
                continue;
            }
            //活动范围信息（卖家范围）
            if(ActivityScope.TNAME.equals(detail.getTableName())){
                ActivityScope activityScope = new ActivityScope();
                activityScope.setId(detail.getKeyValue());
                activityScope.setStatus(PromoConst.Status.Audited.getCode());
                activityScopeList.add(activityScope);
                continue;
            }
            //活动对象信息（买家范围）
            if(ActivityParticipant.TNAME.equals(detail.getTableName())){
                if(PromoConst.ActivityParticipantType.ACTIVITY_PARTICIPANT_TYPE_30.getCode().equals(marketingActivity.getActivityParticipantType())){
                    List<ActivityParticipantDTO> activityParticipantDTOS = activityParticipantManager.queryActivityParticipantByMktIdAndStatus(marketingActivity.getId(), PromoConst.Status.Audited.getCode());
                    for (ActivityParticipantDTO activityParticipantDTO : activityParticipantDTOS) {
                        ActivityParticipant activityParticipant = new ActivityParticipant();
                        activityParticipant.setId(activityParticipantDTO.getId());
                        activityParticipant.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_1.getCode());
                        activityParticipantList.add(activityParticipant);
                    }
                }
                ActivityParticipant activityParticipant = new ActivityParticipant();
                activityParticipant.setId(detail.getKeyValue());
                activityParticipant.setStatus(PromoConst.Status.Audited.getCode());
                activityParticipantList.add(activityParticipant);
                continue;
            }
        }
        // 3.执行活动变更
        // 将营销活动“修改标识”改为0(不在审核修改中)
        marketingActivity.setIsModifying(PromoConst.ActivityIsModifying.NO.getCode());
        marketingActivityManager.updateMarketingActivity(marketingActivity);
        activityScopeManager.updateActivityScopeBatch(activityScopeList);
        activityParticipantManager.updateActivityParticipantBatch(activityParticipantList);
        //4.将变更信息置为审核通过
        change.setAuditState(PromoConst.AuditState.AuditState_5.getCode());
        return activityChangeMapper.updateById(change)==1;
    }


    /**
     * 根据营销活动变更id将变更信息置为不审核通过,并将变更信息的参与范围和参与对象置为删除状态
     * @param id  营销活动
     * @return
     */
    public boolean updateActivityChangeNoPassById(String id,String status) {
        ActivityChange change = activityChangeMapper.selectById(id);
        if (change==null){
            return false;
        }
        //1.准备变更相关数据
        //获取最新对应的变更明细
        List<ActivityChangeDetail> details = activityChangeDetailManager.queryActivityChangeDetail(change.getChangeId());
        //获取营销活动详情
        MarketingActivity marketingActivity = marketingActivityManager.getMarketingActivityById(change.getMarketingActivityId());
        //活动范围信息
        List<ActivityScope> activityScopeList = Lists.newArrayList();
        //参与对象信息
        List<ActivityParticipant> activityParticipantList = Lists.newArrayList();
        //2.组装活动范围信息、活动对象信息
        for (ActivityChangeDetail detail:details) {
            //活动范围信息（卖家范围）
            if(ActivityScope.TNAME.equals(detail.getTableName())){
                ActivityScope activityScope = new ActivityScope();
                activityScope.setId(detail.getKeyValue());
                activityScope.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_1.getCode());
                activityScopeList.add(activityScope);
                continue;
            }
            //活动对象信息（买家范围）
            if(ActivityParticipant.TNAME.equals(detail.getTableName())){
                ActivityParticipant activityParticipant = new ActivityParticipant();
                activityParticipant.setId(detail.getKeyValue());
                activityParticipant.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_1.getCode());
                activityParticipantList.add(activityParticipant);
                continue;
            }
        }
        // 3.执行活动变更
        // 将营销活动“修改标识”改为0(不在审核修改中)
        marketingActivity.setIsModifying(PromoConst.ActivityIsModifying.NO.getCode());
        marketingActivityManager.updateMarketingActivity(marketingActivity);
        activityScopeManager.updateActivityScopeBatch(activityScopeList);
        activityParticipantManager.updateActivityParticipantBatch(activityParticipantList);
        //4.将变更信息置为传入的审核不通过状态
        change.setAuditState(status);
        return activityChangeMapper.updateById(change)==1;
    }
}
