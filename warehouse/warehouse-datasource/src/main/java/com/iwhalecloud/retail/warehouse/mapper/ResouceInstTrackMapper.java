package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsTrackGetReq;
import com.iwhalecloud.retail.warehouse.entity.ResouceInstTrack;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    /**
     * 通过串码查询仓库id
     * @param nbr
     * @return
     */
    String getStoreIdByNbr(@Param("nbr") String nbr);

    /**
     * 查询串码轨迹列表
     * @param req
     * @return
     */
    List<ResouceInstTrackDTO> listResourceInstsTrack(ResourceInstsTrackGetReq req);
}