package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailPageDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
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

    /**
     * 申请单详情分页
     * @param req
     * @return
     */
    public List<ResourceReqDetailPageResp> executorResourceRequestPage(ResourceReqDetailPageReq req){
        return resourceReqDetailMapper.executorResourceRequestPage(req);
    }

    /**
     * 根据申请单项主键修改申请单详情状态
     * @param req
     * @return
     */
    public Integer updateResourceReqDetailStatusCd(ResourceReqDetailUpdateReq req) {
        return resourceReqDetailMapper.updateResourceReqDetailStatusCd(req);
    }

    public Page<ResourceReqDetailPageDTO> listResourceRequestPage(ResourceReqDetailQueryReq req) {
        Page<ResourceReqDetailPageDTO> page = new Page<ResourceReqDetailPageDTO>(req.getPageNo(), req.getPageSize());
        page.setSearchCount(req.isSearchCount());
        return resourceReqDetailMapper.listResourceRequestPage(page,req);
    }

    /**
     * 批量修改申请单明细
     * @param req
     */
    public boolean updateDetailByNbrs(ResourceReqDetailUpdateReq req) {
        ResourceReqDetail detail = new ResourceReqDetail();
        detail.setStatusCd(req.getStatusCd());
        detail.setRemark(req.getRemark());
        detail.setUpdateStaff(req.getUpdateStaff());
        detail.setUpdateDate(req.getUpdateDate());
        detail.setRemark(req.getRemark());
        detail.setStatusDate(req.getStatusDate());
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.in(ResourceReqDetail.FieldNames.mktResReqDetailId.getTableFieldName(), req.getMktResReqDetailIds());
        //updateWrapper.eq(ResourceReqDetail.FieldNames.createDate.getTableFieldName(), req.getCreateDate());
        return resourceReqDetailMapper.update(detail, updateWrapper) > 0;
    }

    /**
     * 根据明细ID集合批量修改申请单明细
     * @param req
     */
    public boolean updateDetailByDetailIds(ResourceReqDetailUpdateReq req) {

        List<ResourceReqDetail> details = Lists.newArrayList();
        ResourceReqDetail detail = new ResourceReqDetail();
        detail.setStatusCd(req.getStatusCd());
        detail.setRemark(req.getRemark());
        detail.setUpdateStaff(req.getUpdateStaff());
        detail.setUpdateDate(req.getUpdateDate());
        detail.setRemark(req.getRemark());
        detail.setStatusDate(req.getStatusDate());

        for (String detailId : req.getMktResReqDetailIds()) {
            detail.setMktResReqDetailId(detailId);
            details.add(detail);
        }

       return super.updateBatchById(details);
    }

    /**
     * 获取用户处理的串码申请
     * @param req
     * @return
     */
    public List<ResourceReqDetailPageDTO> listDistinctResourceRequestByUser(ResourceReqDetailQueryReq req) {
        return resourceReqDetailMapper.listDistinctResourceRequestByUser(req);
    }
}
