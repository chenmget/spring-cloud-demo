package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.promo.entity.ActivityChangeDetail;
import com.iwhalecloud.retail.promo.mapper.ActivityChangeDetailMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


@Component
public class ActivityChangeDetailManager{
    @Resource
    private ActivityChangeDetailMapper activityChangeDetailMapper;

    /**
     * 批量添加变更明细
     * @param activityChangeDetails 变更明细
     * @return
     */
    public void addActivityChangeDetailBatch(List<ActivityChangeDetail> activityChangeDetails) {
        if (!CollectionUtils.isEmpty(activityChangeDetails)) {
            for (ActivityChangeDetail activityChangeDetail : activityChangeDetails) {
                activityChangeDetailMapper.insert(activityChangeDetail);
            }
        }
    }

    /**
     * 根据营销活动id查询变更详细信息列表
     * @param changeId 营销活动变更ID
     * @return
     */
    public List<ActivityChangeDetail> queryActivityChangeDetail(String changeId) {
        QueryWrapper<ActivityChangeDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityChangeDetail.FieldNames.changeId.getTableFieldName(),changeId);
        List<ActivityChangeDetail> changeDetails = activityChangeDetailMapper.selectList(queryWrapper);
        return changeDetails;
    }

}
