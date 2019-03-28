package com.iwhalecloud.retail.warehouse.manager;

import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDetailDTO;
import com.iwhalecloud.retail.warehouse.entity.ResouceInstTrackDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.warehouse.mapper.ResouceInstTrackDetailMapper;


@Component
public class ResouceInstTrackDetailManager {

    @Resource
    private ResouceInstTrackDetailMapper resouceInstTrackDetailMapper;

    public int saveResouceInstTrackDetail(ResouceInstTrackDetailDTO resouceInstTrackDTO){
        ResouceInstTrackDetail t = new ResouceInstTrackDetail();
        BeanUtils.copyProperties(resouceInstTrackDTO, t);
        return resouceInstTrackDetailMapper.insert(t);
    }

}
