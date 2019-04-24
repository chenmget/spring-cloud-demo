package com.iwhalecloud.retail.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;

/**
 * 厂家串码操作
 * @author chengxu
 */
public interface MerchantResourceInstService {

    /**
     * 商家获取串码列表
     *
     * @param req
     * @return
     */
    ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(ResourceInstListPageReq req);

    /**
     * 商家删除串码
     *
     * @param req
     * @return
     */
    ResultVO delResourceInst(ResourceInstUpdateReq req);

    /**
     * 商家新增串码
     *
     * @param req
     * @return
     */
    ResultVO addResourceInst(ResourceInstAddReq req);

    /**
     * 商家选择产品
     *
     * @param req
     * @return
     */
    ResultVO selectProduct(PageProductReq req);

    /**
     * 查询校验串码
     * @param req
     * @return
     */
    ResultVO<Page<ResourceUploadTempListResp>> listResourceUploadTemp(ResourceUploadTempListPageReq req);

}
