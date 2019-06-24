package com.iwhalecloud.retail.warehouse.manager;

import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsTrackDetailGetReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstTrackDetailListResp;
import com.iwhalecloud.retail.warehouse.entity.ResouceInstTrackDetail;
import com.iwhalecloud.retail.warehouse.mapper.ResouceInstTrackDetailMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class ResouceInstTrackDetailManager {

    @Resource
    private ResouceInstTrackDetailMapper resouceInstTrackDetailMapper;

    public int saveResouceInstTrackDetail(ResouceInstTrackDetailDTO resouceInstTrackDTO){
        ResouceInstTrackDetail resouceInstTrackDetail = new ResouceInstTrackDetail();
        BeanUtils.copyProperties(resouceInstTrackDTO, resouceInstTrackDetail);
        return resouceInstTrackDetailMapper.insert(resouceInstTrackDetail);
    }

    /**
     * 通过串码查轨迹明细
     * @param req
     * @return
     */
    public List<ResourceInstTrackDetailListResp> getResourceInstTrackDetailByNbr(ResourceInstsTrackDetailGetReq req){
        return resouceInstTrackDetailMapper.getResourceInstTrackDetailByNbr(req);
    }
}
