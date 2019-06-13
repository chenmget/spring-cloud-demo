package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.warehouse.dto.ResourceChngEvtDetailDTO;
import com.iwhalecloud.retail.warehouse.entity.ResouceEvent;
import com.iwhalecloud.retail.warehouse.entity.ResourceChngEvtDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.warehouse.mapper.ResourceChngEvtDetailMapper;

import java.util.Calendar;
import java.util.List;


@Component
public class ResourceChngEvtDetailManager{
    @Resource
    private ResourceChngEvtDetailMapper resourceChngEvtDetailMapper;

    /**
     *新增变动事件详情
     * @param reqDTO
     * @return
     */
    public int insertChngEvtDetail(ResourceChngEvtDetailDTO reqDTO){
        ResourceChngEvtDetail resDetail = new ResourceChngEvtDetail();
        BeanUtils.copyProperties(reqDTO,resDetail);
        resDetail.setCreateDate(Calendar.getInstance().getTime());
        return resourceChngEvtDetailMapper.insert(resDetail);
    }

    /**
     * 批量新增
     * @param detailDTOList
     * @return
     */
    public int batchInsertChngEvtDetail(List<ResourceChngEvtDetailDTO> detailDTOList){
        int num = 0;
        int sum =0;
       for(ResourceChngEvtDetailDTO detailDTO:detailDTOList){
           ResourceChngEvtDetail resDetail = new ResourceChngEvtDetail();
           BeanUtils.copyProperties(detailDTO,resDetail);
           resDetail.setCreateDate(Calendar.getInstance().getTime());
           num = resourceChngEvtDetailMapper.insert(resDetail);
           sum += num;
       }
       return sum == detailDTOList.size() ? 1:0;
    }

    /**
     * 根据事件ID查询detail表数据
     * @param resouceEvent
     * @return
     */
    public List<ResourceChngEvtDetail> resourceChngEvtDetailList(ResouceEvent resouceEvent){
        QueryWrapper<ResourceChngEvtDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ResourceChngEvtDetail.FieldNames.mktResEventId.getTableFieldName(),resouceEvent.getMktResEventId());
        return resourceChngEvtDetailMapper.selectList(queryWrapper);
    }
    
    
}
