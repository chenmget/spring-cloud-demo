package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailPageReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailQueryReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;
import com.iwhalecloud.retail.warehouse.entity.ResourceReqDetail;
import com.iwhalecloud.retail.warehouse.mapper.ResourceReqDetailMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;


@Component
public class ResourceReqDetailManager extends ServiceImpl<ResourceReqDetailMapper, ResourceReqDetail> {
    @Resource
    private ResourceReqDetailMapper resourceReqDetailMapper;

    /**
     * 新增营销资源申请单明细
     * @param req
     * @return
     */
    public int insertResourceReqDetail(ResourceReqDetailAddReq req){
        ResourceReqDetail reqDetail = new ResourceReqDetail();
        BeanUtils.copyProperties(req,reqDetail);
        reqDetail.setCreateDate(Calendar.getInstance().getTime());
        reqDetail.setStatusDate(Calendar.getInstance().getTime());
        reqDetail.setStatusCd(ResourceConst.PUT_IN_STOAGE);
        return resourceReqDetailMapper.insert(reqDetail);
    }

    /**
     * 查询营销资源申请单明细列表
     * @param req
     * @return
     */
    public List<ResourceReqDetailDTO> listDetail(ResourceReqDetailQueryReq req){
        return resourceReqDetailMapper.listDetail(req);
    }

    /**
     * 申请单详情分页
     * @param req
     * @return
     */
    public Page<ResourceReqDetailPageResp> resourceRequestPage(ResourceReqDetailPageReq req){
        Page<ResourceReqDetailPageResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return resourceReqDetailMapper.resourceRequestPage(page, req);
    }

    /**
     * 申请单明细处理中的串码
     * @param nbrList
     * @return
     */
    public List<String> getProcessingNbrList(List<String> nbrList){
        return resourceReqDetailMapper.getProcessingNbrList(nbrList);
    }

    /**
     * 申请单明细总数
     * @param req
     * @return
     */
    public Integer resourceRequestCount(ResourceReqDetailReq req){
        return resourceReqDetailMapper.resourceRequestCount(req);
    }
}
