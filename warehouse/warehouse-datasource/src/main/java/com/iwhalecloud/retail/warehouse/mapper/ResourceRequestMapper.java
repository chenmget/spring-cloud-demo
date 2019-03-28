package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.warehouse.dto.ResourceRequestDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestQueryReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestQueryResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestResp;
import com.iwhalecloud.retail.warehouse.entity.ResourceRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ResourceRequest  Mapper
 * @author autoCreate
 */
@Mapper
public interface ResourceRequestMapper extends BaseMapper<ResourceRequest> {
    /**
     * 查询营销资源申请单分页
     * @return
     */
    Page<ResourceRequestQueryResp> listResourceRequest(Page<ResourceRequestQueryResp> page, @Param("req") ResourceRequestQueryReq req);
}