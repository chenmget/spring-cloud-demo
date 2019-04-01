package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.entity.ResouceInstTrack;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: ResouceInstTrackMapper
 * @author autoCreate
 */
@Mapper
public interface ResouceInstTrackMapper extends BaseMapper<ResouceInstTrack>{

    String qryOrderIdByNbr(@Param("nbr") String nbr, @Param("storageType") String storageType);


    /**
     * 通过串码和商家找到串码的轨迹
     * @param nbr
     * @param merchantId
     * @return
     */
    ResouceInstTrackDTO getResourceInstTrackByNbrAndMerchantId(@Param("nbr") String nbr, @Param("merchantId") String merchantId);
}