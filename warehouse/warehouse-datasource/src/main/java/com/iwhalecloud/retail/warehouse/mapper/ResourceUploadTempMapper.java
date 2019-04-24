package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceUploadTempListPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.entity.ResouceUploadTemp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: ResourceRequest  Mapper
 * @author he.sw
 */
@Mapper
public interface ResourceUploadTempMapper extends BaseMapper<ResouceUploadTemp> {
    /**
     * 查询校验串码分页
     * @return
     */
    Page<ResourceUploadTempListResp> listResourceUploadTemp(Page<ResourceUploadTempListResp> page, @Param("req") ResourceUploadTempListPageReq req);
}