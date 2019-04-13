package com.iwhalecloud.retail.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.PageProductReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstListPageReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstUpdateReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;

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
    ResultVO<ResourceInstAddResp> addResourceInst(ResourceInstAddReq req);

    /**
     * 商家选择产品
     *
     * @param req
     * @return
     */
    ResultVO selectProduct(PageProductReq req);
}
