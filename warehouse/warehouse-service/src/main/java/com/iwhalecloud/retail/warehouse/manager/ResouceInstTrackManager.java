package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.entity.ResouceInstTrack;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.warehouse.mapper.ResouceInstTrackMapper;

@Component
public class ResouceInstTrackManager{

    @Resource
    private ResouceInstTrackMapper resouceInstTrackMapper;
    
    public int saveResouceInstTrack(ResouceInstTrackDTO resouceInstTrackDTO){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ResouceInstTrack.FieldNames.mktResInstNbr.getTableFieldName(),resouceInstTrackDTO.getMktResInstNbr());
        ResouceInstTrack resouceInstTrack = resouceInstTrackMapper.selectOne(queryWrapper);
        if ( null == resouceInstTrack){
            ResouceInstTrack t = new ResouceInstTrack();
            BeanUtils.copyProperties(resouceInstTrackDTO, t);
            return resouceInstTrackMapper.insert(t);
        } else {
            return 0;
        }

    }

    public String qryOrderIdByNbr(String nbr, String storageType){
        return resouceInstTrackMapper.qryOrderIdByNbr(nbr, storageType);
    }
    
}
