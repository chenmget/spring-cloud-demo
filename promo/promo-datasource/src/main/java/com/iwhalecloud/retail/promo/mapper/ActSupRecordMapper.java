package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.promo.dto.req.QueryActSupRecordReq;
import com.iwhalecloud.retail.promo.dto.resp.ActSupRecodeListResp;
import com.iwhalecloud.retail.promo.entity.ActSupRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: ActSupRecordMapper
 * @author autoCreate
 */
@Mapper
public interface ActSupRecordMapper extends BaseMapper<ActSupRecord> {

    /**
     * 查询前置补录信息
     * @param page
     * @param queryActSupRecordReq
     * @return
     */
    Page<ActSupRecodeListResp> queryActSupRecord(Page<ActSupRecodeListResp> page, @Param("req") QueryActSupRecordReq queryActSupRecordReq);
}