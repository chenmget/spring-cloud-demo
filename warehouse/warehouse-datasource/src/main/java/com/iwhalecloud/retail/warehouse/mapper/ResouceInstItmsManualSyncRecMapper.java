package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.warehouse.dto.request.ResouceInstItmsManualSyncRecPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResouceInstItmsManualSyncRecListResp;
import com.iwhalecloud.retail.warehouse.entity.ResouceInstItmsManualSyncRec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: ResouceInstItmsSyncRecMapper
 * @author autoCreate
 */
@Mapper
public interface ResouceInstItmsManualSyncRecMapper extends BaseMapper<ResouceInstItmsManualSyncRec>{


    /**
     * 条件分页查询
     * @param page
     * @param req
     * @return
     */
    Page<ResouceInstItmsManualSyncRecListResp> listResourceItemsManualSyncRec(Page<ResouceInstItmsManualSyncRecListResp> page, @Param("req")ResouceInstItmsManualSyncRecPageReq req);

    /**
     * 根据串码查询最新的一条推送记录
     * @param mktResInstNbr
     * @return
     */
    ResouceInstItmsManualSyncRecListResp getDestLanIdByNbr(@Param("mktResInstNbr")String mktResInstNbr);
}