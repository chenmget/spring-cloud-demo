package com.iwhalecloud.retail.warehouse.manager;

import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResourceReqDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailQueryReq;
import com.iwhalecloud.retail.warehouse.entity.ResourceReqDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.warehouse.mapper.ResourceReqDetailMapper;

import java.util.Calendar;
import java.util.List;


@Component
public class ResourceReqDetailManager{
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
    
}
