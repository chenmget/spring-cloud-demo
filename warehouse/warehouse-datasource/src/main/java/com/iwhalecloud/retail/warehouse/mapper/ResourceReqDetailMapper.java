package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailQueryReq;
import com.iwhalecloud.retail.warehouse.entity.ResourceReqDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ResourceReqDetailMapper
 * @author autoCreate
 */
@Mapper
public interface ResourceReqDetailMapper extends BaseMapper<ResourceReqDetail>{
    /**
     *查询营销资源申请单明细列表
     * @param req
     * @return
     */
    List<ResourceReqDetailDTO> listDetail(@Param("req") ResourceReqDetailQueryReq req);
}