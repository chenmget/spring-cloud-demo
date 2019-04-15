package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.entity.ResouceInstTrack;
import com.iwhalecloud.retail.warehouse.mapper.ResouceInstTrackMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ResouceInstTrackManager{

    @Resource
    private ResouceInstTrackMapper resouceInstTrackMapper;
    
    public int saveResouceInstTrack(ResouceInstTrackDTO resouceInstTrackDTO){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ResouceInstTrack.FieldNames.mktResInstNbr.getTableFieldName(),resouceInstTrackDTO.getMktResInstNbr());
        ResouceInstTrack qryResouceInstTrack = resouceInstTrackMapper.selectOne(queryWrapper);
        ResouceInstTrack resouceInstTrack = new ResouceInstTrack();
        BeanUtils.copyProperties(resouceInstTrackDTO, resouceInstTrack);
        if ( null == qryResouceInstTrack){
            return resouceInstTrackMapper.insert(resouceInstTrack);
        } else {
            // 2019-04-10 轨迹表只有一条数据，不断更新，所以这里应该是有则更新没有则添加
            BeanUtils.copyProperties(resouceInstTrackDTO, resouceInstTrack);
            return resouceInstTrackMapper.updateById(resouceInstTrack);
        }
    }

    public String qryOrderIdByNbr(String nbr, String storageType){
        return resouceInstTrackMapper.qryOrderIdByNbr(nbr, storageType);
    }


    public ResouceInstTrackDTO getResourceInstTrackByNbrAndMerchantId(String nbr, String merchantId){
        return resouceInstTrackMapper.getResourceInstTrackByNbrAndMerchantId(nbr, merchantId);
    }
}
