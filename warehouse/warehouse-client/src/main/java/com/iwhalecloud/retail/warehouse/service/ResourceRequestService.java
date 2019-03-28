package com.iwhalecloud.retail.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestItemQueryReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestQueryReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceRequestUpdateReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestQueryResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceRequestResp;

/**
 * @Author My
 * @Date 2019/1/10
 **/
public interface ResourceRequestService {
    /**
     * 新增申请单
     *
     * @param req
     * @return
     */
    ResultVO<String> insertResourceRequest(ResourceRequestAddReq req);

    /**
     * 查询申请单
     *
     * @param req
     * @return
     */
    ResultVO<ResourceRequestResp> queryResourceRequest(ResourceRequestItemQueryReq req);

    /**
     * 查询分页申请单
     *
     * @param req
     * @return
     */
    ResultVO<Page<ResourceRequestQueryResp>> listResourceRequest(ResourceRequestQueryReq req);

    /**
     * 修改申请单状态
     *
     * @param req
     * @return
     */
    ResultVO<Boolean> updateResourceRequestState(ResourceRequestUpdateReq req);

    /**
     * 查询申请单详情
     *
     * @param req
     * @return
     */
    ResultVO<ResourceRequestResp> queryResourceRequestDetail(ResourceRequestItemQueryReq req);

    /**
     * 确认收货
     *
     * @param req
     * @return
     */
    ResultVO<Boolean> hadDelivery(ResourceRequestUpdateReq req);

}
