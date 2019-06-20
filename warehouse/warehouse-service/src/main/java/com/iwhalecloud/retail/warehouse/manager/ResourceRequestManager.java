package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestItemQueryReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestQueryReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestUpdateReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestQueryResp;
import com.iwhalecloud.retail.warehouse.entity.ResourceRequest;
import com.iwhalecloud.retail.warehouse.mapper.ResourceRequestMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


@Component
public class ResourceRequestManager{
    @Resource
    private ResourceRequestMapper resourceRequestMapper;

    /**
     * 插入营销资源申请单
     * @param req
     * @return
     */
    public String insertResourceRequest(ResourceRequestAddReq req ){
        ResourceRequest request = new ResourceRequest();
        BeanUtils.copyProperties(req,request);
        Date now = new Date();
        request.setUpdateDate(now);
        request.setStatusDate(now);
        request.setCreateDate(now);
        resourceRequestMapper.insert(request);
        return request.getMktResReqId();
    }

    /**
     * 查询营销资源申请单分页
     * @param req
     * @return
     */
    public Page<ResourceRequestQueryResp> pageResourceRequest(ResourceRequestQueryReq req){
        Page<ResourceRequestQueryResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return resourceRequestMapper.listResourceRequest(page,req);
    }

    /**
     * 修改申请单状态
     * @param req
     * @return
     */
    public int updateResourceRequestState(ResourceRequestUpdateReq req){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ResourceRequest.FieldNames.mktResReqId.getTableFieldName(),req.getMktResReqId());
        queryWrapper.eq(ResourceRequest.FieldNames.createDate.getTableFieldName(),req.getCreateDate());
        ResourceRequest request = new ResourceRequest();
        request.setStatusCd(req.getStatusCd());
        return resourceRequestMapper.update(request,queryWrapper);
    }

    /**
     * 查询单笔申请单
     * @param req
     * @return
     */
    public ResourceRequest queryResourceRequest(ResourceRequestItemQueryReq req){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ResourceRequest.FieldNames.mktResReqId.getTableFieldName(),req.getMktResReqId());
        return  resourceRequestMapper.selectOne(queryWrapper);
    }

}
