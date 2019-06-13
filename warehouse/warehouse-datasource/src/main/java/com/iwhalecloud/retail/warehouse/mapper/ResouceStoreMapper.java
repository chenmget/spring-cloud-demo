package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.AllocateStorePageReq;
import com.iwhalecloud.retail.warehouse.dto.request.StoreGetStoreIdReq;
import com.iwhalecloud.retail.warehouse.dto.request.StorePageReq;
import com.iwhalecloud.retail.warehouse.entity.ResouceStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ResouceStoreMapper
 * @author autoCreate
 */
@Mapper
public interface ResouceStoreMapper extends BaseMapper<ResouceStore>{

    ResouceStoreDTO getStore(@Param("merchantId") String merchantId, @Param("storeSubType") String storeSubType);

    Page<ResouceStoreDTO> pageStore(Page<ResouceStoreDTO> page, @Param("req") StorePageReq req);

    Page<ResouceStoreDTO> pageAllocateStore(Page<ResouceStoreDTO> page, @Param("req") AllocateStorePageReq req);

    int updateStore(ResouceStore resouceStore);

    /**
     * 仓库ID查询
     *
     * @param req
     * @return
     */
    String getStoreId(StoreGetStoreIdReq req);

    /**
     * 根据lanID查询仓库ID
     *
     * @param lanId
     * @return
     */
    String getStoreIdByLanId(String lanId);


    /**
     * 查询仓库ID列表
     * @param objIdList
     * @return
     */
    List<ResouceStoreDTO> listGivenStore(@Param("objIdList") List<String> objIdList);
}