package com.iwhalecloud.retail.promo.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.ActivityProductDTO;
import com.iwhalecloud.retail.promo.dto.req.*;
import com.iwhalecloud.retail.promo.dto.resp.ActivityProductResp;
import com.iwhalecloud.retail.promo.dto.resp.PreSubsidyProductRespDTO;
import com.iwhalecloud.retail.promo.dto.resp.VerifyProductPurchasesLimitResp;

import java.util.List;

/**
 * @author xuqinyuan
 */
public interface ActivityProductService {

    /**
     * 添加参与活动产品
     *
     * @param req
     * @return
     */
    ResultVO<ActivityProductResp> addActivityProduct(ActivityProductBatchReq req);

    /**
     * 编辑参与活动产品
     *
     * @param req
     * @return
     */
    ResultVO<ActivityProductResp> editActivityProduct(ActivityProductBatchReq req);

    /**
     * 删除前置补贴配置产品
     *
     * @param marketingActivityId
     * @return
     */
    Integer deletePreSubsidyProduct(String marketingActivityId);

    /**
     * 根据活动id查询前置补贴或预售的产品配置信息
     *
     * @param marketingActivityId
     * @return
     */
    ResultVO<List<PreSubsidyProductRespDTO>> queryPreSubsidyProduct(String marketingActivityId);

    /**
     * 添加前置补贴活动的产品
     *
     * @param activityProductReq
     * @return
     */
    ResultVO addPreSubsidyProduct(ActivityProductReq activityProductReq);

    /**
     * 添加预售活动产品
     *
     * @param addPreSaleProductReqDTO
     * @return
     */
    ResultVO addPreSaleProduct(AddPreSaleProductReqDTO addPreSaleProductReqDTO);

    /**
     * 校验减免金额是否小于销售价
     *
     * @param productId
     * @param discountAmount
     * @return
     */
    ResultVO checkProductDiscountAmount(String productId, Long discountAmount);

    /**
     * 查询活动产品信息
     *
     * @param req
     * @return
     */
    ResultVO<List<ActivityProductDTO>> queryActivityProducts(ActivityProductListReq req);

    /**
     * 根据活动ID、产品ID，校验产品购买量是否达到购买上限服务
     *
     * @param req
     * @return
     */
    ResultVO<VerifyProductPurchasesLimitResp> verifyProductPurchasesLimit(VerifyProductPurchasesLimitReq req);


}