package com.iwhalecloud.retail.promo.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.ActivityScopeDTO;
import com.iwhalecloud.retail.promo.entity.ActivityScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.promo.mapper.ActivityScopeMapper;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ActivityScopeManager{
    @Resource
    private ActivityScopeMapper activityScopeMapper;

    /**
     * 添加参与活动范围
     * @param activityScopeList 参与活动范围
     * @return
     */
    public void addActivityScopeBatch(List<ActivityScope> activityScopeList) {
        if (!CollectionUtils.isEmpty(activityScopeList)) {
            for (ActivityScope activityScope : activityScopeList) {
                if (StringUtils.isEmpty(activityScope.getId())) {

                    Date date = new Date();
                    activityScope.setGmtCreate(date);
                    activityScope.setGmtModified(date);
                    activityScope.setIsDeleted("0");
                    activityScopeMapper.insert(activityScope);
                }
            }
        }
    }
    /**
     * 删除参与活动范围
     * @param marketingActivityId 删除活动范围
     * @return
     */
    public void deleteActivityScopeBatch(String marketingActivityId) {

        activityScopeMapper.deleteActivityScopeBatch(marketingActivityId);

    }
    /**
     * 根据活动id查询参与活动范围
     * @param marketingActivityId 根据活动id查询参与活动范围
     * @return
     */
    public List<ActivityScope> queryActivityScopeByCondition(String marketingActivityId) {
        QueryWrapper<ActivityScope> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityScope.FieldNames.isDeleted.getTableFieldName(),PromoConst.IsDelete.IS_DELETE_CD_0);
        queryWrapper.eq(ActivityScope.FieldNames.marketingActivityId.getTableFieldName(), marketingActivityId);
        return activityScopeMapper.selectList(queryWrapper);
    }
    public List<ActivityScopeDTO> queryActivityScopeByMktId(String marketingActivityId) {
        log.info("ActivityScopeManager.queryActivityScopeByMktId id={}", marketingActivityId);
        if (StringUtils.isNotEmpty(marketingActivityId)){
            List<ActivityScopeDTO> activityScopeDTOList= activityScopeMapper.queryActivityScopeByMktId(marketingActivityId);
            return  activityScopeDTOList;
        }
        return null;
    }

    /**
     * 根据供应商编码查询参与活动范围
     * @param supplierCode 供应商编码
     * @return
     */
    public ActivityScope queryActivityScopeBySupplierCode(String marketingActivityId, String supplierCode) {
        QueryWrapper<ActivityScope> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityScope.FieldNames.marketingActivityId.getTableFieldName(),marketingActivityId);
        queryWrapper.eq(ActivityScope.FieldNames.isDeleted.getTableFieldName(),PromoConst.UNDELETED);
        queryWrapper.eq(ActivityScope.FieldNames.supplierCode.getTableFieldName(), supplierCode);
        return activityScopeMapper.selectOne(queryWrapper);
    }

    /**
     * 根据供应商编码查询参与活动范围
     * @param supplierId 供应商ID
     * @return
     */
    public ActivityScope getActivityScopeBySupplierCode(String marketingActivityId, String supplierId) {
        QueryWrapper<ActivityScope> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityScope.FieldNames.marketingActivityId.getTableFieldName(), marketingActivityId);
        queryWrapper.eq(ActivityScope.FieldNames.isDeleted.getTableFieldName(),PromoConst.UNDELETED);
        queryWrapper.eq(ActivityScope.FieldNames.supplierCode.getTableFieldName(), supplierId);
        return activityScopeMapper.selectOne(queryWrapper);
    }

    /**
     * 根据供应商地市查询参与活动范围
     * @param lanId 地市
     * @return
     */
    public ActivityScope queryActivityScopeByLandId(String marketingActivityId, String lanId) {
        QueryWrapper<ActivityScope> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityScope.FieldNames.marketingActivityId.getTableFieldName(),marketingActivityId);
        queryWrapper.eq(ActivityScope.FieldNames.isDeleted.getTableFieldName(),PromoConst.UNDELETED);
        queryWrapper.eq(ActivityScope.FieldNames.lanId.getTableFieldName(), lanId);
        return activityScopeMapper.selectOne(queryWrapper);
    }

    /**
     * 根据供应商地市查询参与活动范围
     * @param cityId 地市
     * @return
     */
    public ActivityScope queryActivityScopeByCityId(String marketingActivityId, String cityId) {
        QueryWrapper<ActivityScope> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ActivityScope.FieldNames.marketingActivityId.getTableFieldName(),marketingActivityId);
        queryWrapper.eq(ActivityScope.FieldNames.isDeleted.getTableFieldName(),PromoConst.UNDELETED);
        queryWrapper.eq(ActivityScope.FieldNames.city.getTableFieldName(), cityId);
        return activityScopeMapper.selectOne(queryWrapper);
    }
}
