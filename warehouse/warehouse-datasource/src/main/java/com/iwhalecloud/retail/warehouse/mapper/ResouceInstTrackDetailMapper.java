package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsTrackDetailGetReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstTrackDetailListResp;
import com.iwhalecloud.retail.warehouse.entity.ResouceInstTrackDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: ResouceInstTrackDetailMapper
 * @author autoCreate
 */
@Mapper
public interface ResouceInstTrackDetailMapper extends BaseMapper<ResouceInstTrackDetail>{

    /**
     * 通过串码查轨迹明细
     * @param req
     * @return
     */
    List<ResourceInstTrackDetailListResp> getResourceInstTrackDetailByNbr(ResourceInstsTrackDetailGetReq req);
}