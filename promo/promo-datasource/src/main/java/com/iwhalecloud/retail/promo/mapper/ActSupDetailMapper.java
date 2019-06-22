package com.iwhalecloud.retail.promo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.promo.dto.req.ActSupDetailReq;
import com.iwhalecloud.retail.promo.dto.resp.ActSupDetailResp;
import com.iwhalecloud.retail.promo.entity.ActSupDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: ActSupDetailMapper
 * @author autoCreate
 */
@Mapper
public interface ActSupDetailMapper extends BaseMapper<ActSupDetail>{

    /**
     * 获取补录活动记录的明细记录
     * @param req
     * @return
     */

    Page<ActSupDetailResp> listActSupDetailByRecordId(Page<ActSupDetailResp> page, @Param("req")ActSupDetailReq req);

    /**
     * 校验订单和串码是否已经补录过了
     * @param actSupDetailReq
     * @return
     */
    Integer orderResSupCheck(ActSupDetailReq actSupDetailReq);
}