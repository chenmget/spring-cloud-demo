package com.iwhalecloud.retail.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceReqDetailPageResp;

import java.util.List;

/**
 * @Author My
 * @Date 2019/04/18
 **/
public interface ResourceReqDetailService {

    /**
     * 申请单详情分页
     * @param req
     * @return
     */
    ResultVO<Page<ResourceReqDetailPageResp>> resourceRequestPage(ResourceReqDetailPageReq req);

    /**
     * 申请单详情不分页
     * @param req
     * @return
     */
    ResultVO<List<ResourceReqDetailPageResp>> resourceRequestList(ResourceReqDetailPageReq req);


}
