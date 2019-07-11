package com.iwhalecloud.retail.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailPageReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqDetailQueryReq;
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

    /**
     * 管理平台串码申请单详情分页（条件查询）
     * @param req
     * @return
     */
    ResultVO<Page<ResourceReqDetailPageResp>> listResourceRequestDetailPage(ResourceReqDetailQueryReq req);

    /**
     * 交易平台串码申请单详情分页（条件查询）
     * @param req
     * @return
     */
    ResultVO<Page<ResourceReqDetailPageResp>> listMerchantResourceRequestDetailPage(ResourceReqDetailQueryReq req);

    /**
     * 获取用户工作流中待办的串码审核流程申请单id
     * @param userId
     * @return
     */
    List<String> getUserHandleFormId(String userId);

    /**
     * 申请单明细处理中的串码
     * @param nbrList
     * @return
     */
    ResultVO<List<String>> getProcessingNbrList(List<String> nbrList);
}
