package com.iwhalecloud.retail.warehouse.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstListPageReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsTrackGetReq;
import com.iwhalecloud.retail.warehouse.entity.ResouceInstTrack;
import com.iwhalecloud.retail.warehouse.mapper.ResouceInstTrackMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class ResouceInstTrackManager{

    @Resource
    private ResouceInstTrackMapper resouceInstTrackMapper;
    
    public int saveResouceInstTrack(ResouceInstTrackDTO resouceInstTrackDTO){
        log.info("ResouceInstTrackManager.saveResouceInstTrack req={}", JSON.toJSONString(resouceInstTrackDTO));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ResouceInstTrack.FieldNames.mktResInstNbr.getTableFieldName(),resouceInstTrackDTO.getMktResInstNbr());
        if (StringUtils.isEmpty(resouceInstTrackDTO.getMktResId()) && StringUtils.isEmpty(resouceInstTrackDTO.getTypeId())) {
            return -1;
        }
        if (StringUtils.isNotEmpty(resouceInstTrackDTO.getMktResId())) {
            queryWrapper.eq(ResouceInstTrack.FieldNames.mktResId.getTableFieldName(),resouceInstTrackDTO.getMktResId());
        }
        if (StringUtils.isNotEmpty(resouceInstTrackDTO.getTypeId())) {
            queryWrapper.eq(ResouceInstTrack.FieldNames.typeId.getTableFieldName(),resouceInstTrackDTO.getTypeId());
        }
        ResouceInstTrack qryResouceInstTrack = resouceInstTrackMapper.selectOne(queryWrapper);
        log.info("ResouceInstTrackManager.saveResouceInstTrack resouceInstTrackMapper.selectOne resp={}", JSON.toJSONString(qryResouceInstTrack));
        ResouceInstTrack resouceInstTrack = new ResouceInstTrack();
        BeanUtils.copyProperties(resouceInstTrackDTO, resouceInstTrack);
        resouceInstTrack.setStatusDate(new Date());
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

    /**
     * 通过串码查仓库id
     * @param req
     * @return
     */
    public List<String> getStoreIdByNbr(ResourceInstListPageReq req){
        return resouceInstTrackMapper.getStoreIdByNbrs(req);
    }

    /**
     * 查询串码轨迹列表
     * @param req
     * @return
     */
    public List<ResouceInstTrackDTO> listResourceInstsTrack(ResourceInstsTrackGetReq req){
        return resouceInstTrackMapper.listResourceInstsTrack(req);
    }

    public int updateById(ResouceInstTrackDTO resouceInstTrackDTO){
        ResouceInstTrack resouceInstTrack = new ResouceInstTrack();
        BeanUtils.copyProperties(resouceInstTrackDTO, resouceInstTrack);
        return resouceInstTrackMapper.updateById(resouceInstTrack);
    }
}
