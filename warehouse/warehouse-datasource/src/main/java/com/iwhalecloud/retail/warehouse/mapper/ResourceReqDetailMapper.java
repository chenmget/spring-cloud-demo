package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailPageReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailQueryReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;
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

    /**
     * 申请单详情分页
     * @param req
     * @return
     */
    Page<ResourceReqDetailPageResp> resourceRequestPage(Page<ResourceReqDetailPageResp> page,@Param("req") ResourceReqDetailPageReq req);

    /**
     * 申请单详情总数
     * @param req
     * @return
     */
    Integer resourceRequestCount(@Param("req") ResourceReqDetailReq req);

    /**
     * 申请单明细处理中的串码
     * @param nbrList
     * @return
     */
    List<String> getProcessingNbrList(@Param("nbrList") List<String> nbrList);
}