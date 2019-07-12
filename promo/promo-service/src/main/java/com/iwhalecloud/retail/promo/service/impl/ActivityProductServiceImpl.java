package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.QueryProductInfoReqDTO;
import com.iwhalecloud.retail.goods2b.dto.resp.QueryProductInfoResqDTO;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.common.ResultCodeEnum;
import com.iwhalecloud.retail.promo.dto.ActivityProductDTO;
import com.iwhalecloud.retail.promo.dto.MarketingActivityDTO;
import com.iwhalecloud.retail.promo.dto.req.*;
import com.iwhalecloud.retail.promo.dto.resp.ActivityProductResp;
import com.iwhalecloud.retail.promo.dto.resp.ActivityProductRespDTO;
import com.iwhalecloud.retail.promo.dto.resp.PreSubsidyProductRespDTO;
import com.iwhalecloud.retail.promo.dto.resp.VerifyProductPurchasesLimitResp;
import com.iwhalecloud.retail.promo.entity.ActivityProduct;
import com.iwhalecloud.retail.promo.entity.MarketingActivity;
import com.iwhalecloud.retail.promo.manager.ActivityProductManager;
import com.iwhalecloud.retail.promo.manager.HistoryPurchaseManager;
import com.iwhalecloud.retail.promo.manager.MarketingActivityManager;
import com.iwhalecloud.retail.promo.service.ActivityProductService;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import com.iwhalecloud.retail.promo.utils.CloneUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xuqinyuan
 */
@Slf4j
@Component("activityProductService")
@Service
public class ActivityProductServiceImpl implements ActivityProductService {

    @Autowired
    private ActivityProductManager activityProductManager;

    @Reference
    private ProductService productService;

    @Autowired
    private MarketingActivityManager marketingActivityManager;

    @Autowired
    private HistoryPurchaseManager historyPurchaseManager;

    @Autowired
    private MarketingActivityService marketingActivityService;

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;
    
    @Override
    public ResultVO<ActivityProductResp> addActivityProduct(ActivityProductBatchReq req) {
        log.info("ActivityProductServiceImpl.addActivityProduct req={}", JSON.toJSONString(req));
        List<ActivityProduct> activityProductList = new ArrayList<>();
        List<ActivityProductReq> activityProductReqList = req.getActivityProductReqList();
        for (ActivityProductReq activityProductReq : activityProductReqList) {
            ActivityProduct activityProduct = new ActivityProduct();
            BeanUtils.copyProperties(activityProductReq, activityProduct);
            activityProductList.add(activityProduct);
        }
        // 添加参与活动产品
        activityProductManager.saveBatch(activityProductList);
        ActivityProductResp activityProductResp = new ActivityProductResp();
        if (!CollectionUtils.isEmpty(activityProductList)) {
            String marketingActivityCode = activityProductList.get(0).getMarketingActivityId();
            activityProductResp.setId(marketingActivityCode);
        }
        return ResultVO.success(activityProductResp);
    }

    @Transactional
    @Override
    public ResultVO<ActivityProductResp> editActivityProduct(ActivityProductBatchReq req) {
        log.info("ActivityProductServiceImpl.editActivityProduct req={}", JSON.toJSONString(req));
        if (ObjectUtils.isEmpty(req)) {
            return ResultVO.error("参数错误");
        }
        List<ActivityProductReq> productReqList = req.getActivityProductReqList();
        if (CollectionUtils.isEmpty(productReqList)) {
            return ResultVO.error("产品信息为空");
        }
        String marketingActivityId = productReqList.get(0).getMarketingActivityId();
        // 删除旧参与活动产品

        activityProductManager.deleteActivityProduct(marketingActivityId);
        // 添加新参与活动产品
        List<ActivityProduct> activityProductList = new ArrayList<>();
        List<ActivityProductReq> activityProductReqList = req.getActivityProductReqList();
        for (ActivityProductReq activityProductReq : activityProductReqList) {
            ActivityProduct activityProduct = new ActivityProduct();
            BeanUtils.copyProperties(activityProductReq, activityProduct);
            activityProductList.add(activityProduct);
        }
        boolean flag = activityProductManager.saveBatch(activityProductList);
        ActivityProductResp activityProductResp = new ActivityProductResp();
        if (flag) {
            activityProductResp.setId(marketingActivityId);
        }
        return ResultVO.success(activityProductResp);
    }

    @Override
    public Integer deletePreSubsidyProduct(String marketingActivityId) {
        return activityProductManager.deleteActivityProduct(marketingActivityId);
    }

    /**
     * 根据活动id查询前置补贴或预售的产品配置信息
     *
     * @param marketingActivityId
     * @return
     */
    @Override
    public ResultVO<List<PreSubsidyProductRespDTO>> queryPreSubsidyProduct(String marketingActivityId) {
        log.info("ActivityProductServiceImpl.queryPreSubsidyProduct marketingActivityId={}", marketingActivityId);
        List<PreSubsidyProductRespDTO> preSubsidyProductResqDTOS = new ArrayList<>();
        //根据活动id查找活动有效产品
        List<ActivityProduct> activityProducts = activityProductManager.queryActivityProductByCondition(marketingActivityId);
        log.info("ActivityProductServiceImpl.queryPreSubsidyProduct activityProductManager.queryActivityProductByCondition activityProducts={}", JSON.toJSON(activityProducts));
        if (activityProducts.size() <= 0) {
            return ResultVO.success(preSubsidyProductResqDTOS);
        }
        for (ActivityProduct activityProduct : activityProducts) {
            PreSubsidyProductRespDTO preSubsidyProductResqDTO = new PreSubsidyProductRespDTO();
            String productId = activityProduct.getProductId();
            QueryProductInfoReqDTO queryProductInfoReqDTO = new QueryProductInfoReqDTO();
            queryProductInfoReqDTO.setProductId(productId);
            ResultVO<QueryProductInfoResqDTO> productInfoResqDTOResultVO = productService.getProductInfor(queryProductInfoReqDTO);
            log.info("ActivityProductServiceImpl.queryPreSubsidyProduct productService.getProductInfo productInfoResqDTOResultVO ={}", JSON.toJSON(productInfoResqDTOResultVO));
            if (productInfoResqDTOResultVO.getResultData() == null) {
                continue;
            }
            BeanUtils.copyProperties(productInfoResqDTOResultVO.getResultData(), preSubsidyProductResqDTO);
            ActivityProductRespDTO activityProductResqDTO = new ActivityProductRespDTO();
            BeanUtils.copyProperties(activityProduct, activityProductResqDTO);
            preSubsidyProductResqDTO.setActivityProductResqDTO(activityProductResqDTO);

            //把全路径拼接出来
            if(activityProduct.getProductPic() != null) {
                String productPic = fullImageUrl(activityProduct.getProductPic(), dfsShowIp, true);
                activityProduct.setProductPic(productPic);
            }
            //获取活动产品图片
            ActivityProductRespDTO activityProductRespDTO = preSubsidyProductResqDTO.getActivityProductResqDTO();
            if(activityProductRespDTO != null) {
                preSubsidyProductResqDTO.setProductPic(activityProductRespDTO.getProductPic());
                preSubsidyProductResqDTO.setProductPicUseType(activityProductRespDTO.getProductPicUseType());
            }
            //如果是预售活动,则把支付定金和尾款的时间加上
            ResultVO<MarketingActivityDTO> marketingActivityDTOResultVO = marketingActivityService.queryMarketingActivityById(activityProduct.getMarketingActivityId());
            MarketingActivityDTO marketingActivityDTO = marketingActivityDTOResultVO.getResultData();
            if(marketingActivityDTO!=null&&PromoConst.ACTIVITYTYPE.BOOKING.getCode().equals(marketingActivityDTO.getActivityType())){
                activityProductResqDTO.setPreStartTime(marketingActivityDTO.getPreStartTime());
                activityProductResqDTO.setPreEndTime(marketingActivityDTO.getPreEndTime());
                activityProductResqDTO.setTailPayStartTime(marketingActivityDTO.getTailPayStartTime());
                activityProductResqDTO.setTailPayEndTime(marketingActivityDTO.getTailPayEndTime());
            }

            preSubsidyProductResqDTOS.add(preSubsidyProductResqDTO);
        }
        return ResultVO.success(preSubsidyProductResqDTOS);
    }

    /**
     * 拼接完整的地址
     * @param imagePath
     * @param showUrl
     * @param flag 为true时，拼接完整地址，为false时，是截取地址
     * @return
     */
    private static String fullImageUrl(String imagePath, String showUrl, boolean flag) {
        String aftPath = "";
        if (flag) {
            String[] pathArr = imagePath.split(",");
            for (String befPath : pathArr) {
                if (org.springframework.util.StringUtils.isEmpty(aftPath)) {
                    if (!befPath.startsWith("http")) {
                        aftPath += showUrl + befPath;
                    } else {
                        aftPath += befPath;
                    }
                } else {
                    if (!befPath.startsWith("http")) {
                        aftPath += "," + showUrl + befPath;
                    } else {
                        aftPath += "," + befPath;
                    }
                }
            }
        } else {
            if (!org.springframework.util.StringUtils.isEmpty(imagePath)) {
                aftPath = imagePath.replaceAll(showUrl, "");
            }
        }
        return aftPath;
    }

    @Override
    public ResultVO addPreSubsidyProduct(ActivityProductReq activityProductReq) {
        ActivityProduct activityProduct = new ActivityProduct();
        BeanUtils.copyProperties(activityProductReq, activityProduct);
        if (PromoConst.ProductNumFlg.ProductNumFlg_0.getCode().equals(activityProductReq.getNumLimitFlg())) {
            activityProduct.setNum(Long.valueOf("-1"));
        }
        activityProduct.setGmtCreate(new Date());
        activityProduct.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
        activityProduct.setStatus(PromoConst.Status.Audited.getCode());
        activityProductManager.insertProductActivity(activityProduct);
        return ResultVO.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addPreSaleProduct(AddPreSaleProductReqDTO addPreSaleProductReqDTO) {
        log.info("ActivityProductServiceImpl.addPreSaleProduct addPreSaleProductReqDTO={}", JSON.toJSON(addPreSaleProductReqDTO));
        if (addPreSaleProductReqDTO.getActivityProductReqs().size() <= 0) {
            return ResultVO.error("异常产品配置信息为空");
        }
        String marketingActivityId = addPreSaleProductReqDTO.getMarketingActivityId();
        MarketingActivity marketingActivity = marketingActivityManager.queryMarketingActivity(marketingActivityId);
        log.info("ActivityProductServiceImpl.addPreSaleProduct marketingActivityManager.queryMarketingActivity marketingActivity={}", JSON.toJSON(marketingActivity));
        if (marketingActivity == null) {
            return ResultVO.error("活动数据异常");
        }
        List<ActivityProductReq> activityProductReqs = addPreSaleProductReqDTO.getActivityProductReqs();
        activityProductManager.deleteActivityProduct(marketingActivityId);
        List<ActivityProduct> activityProducts = Lists.newArrayList();
        for (ActivityProductReq activityProductReq : activityProductReqs) {
            ActivityProduct activityProduct = new ActivityProduct();
            BeanUtils.copyProperties(activityProductReq, activityProduct);
            activityProduct.setIsDeleted(PromoConst.IsDelete.IS_DELETE_CD_0.getCode());
            activityProduct.setStatus(PromoConst.Status.Audited.getCode());
            activityProduct.setMarketingActivityId(addPreSaleProductReqDTO.getMarketingActivityId());
            activityProduct.setCreator(addPreSaleProductReqDTO.getUserId());
            activityProducts.add(activityProduct);
        }
        activityProductManager.saveBatch(activityProducts);
        AuitMarketingActivityReq auitMarketingActivityReq = new AuitMarketingActivityReq();
        BeanUtils.copyProperties(addPreSaleProductReqDTO, auitMarketingActivityReq);
        auitMarketingActivityReq.setId(addPreSaleProductReqDTO.getMarketingActivityId());
        auitMarketingActivityReq.setName(marketingActivity.getName());
        marketingActivityService.auitMarketingActivity(auitMarketingActivityReq);
        return ResultVO.success();
    }


    @Override
    public ResultVO checkProductDiscountAmount(String productId, Long discountAmount) {
        QueryProductInfoReqDTO queryProductInfoReqDTO = new QueryProductInfoReqDTO();
        queryProductInfoReqDTO.setProductId(productId);
        ResultVO<QueryProductInfoResqDTO> productInfoResqDTOResultVO = productService.getProductInfo(queryProductInfoReqDTO);
        if (productInfoResqDTOResultVO.getResultData() == null) {
            return ResultVO.error("产品不存在");
        }
        if (productInfoResqDTOResultVO.getResultData().getCost() <= discountAmount) {
            return ResultVO.error(productInfoResqDTOResultVO.getResultData().getProductName() + "产品的减免金额应小于产品销售价");
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO<List<ActivityProductDTO>> queryActivityProducts(ActivityProductListReq req) {
        log.info("ActivityProductServiceImpl.queryActivityProducts req={}", JSON.toJSONString(req));
        List<ActivityProduct> activityProducts = activityProductManager.queryActivityProductByCondition(req);
        List<ActivityProductDTO> activityProductDTOs = CloneUtils.batchClone(activityProducts, ActivityProductDTO.class);
        log.info("ActivityProductServiceImpl.queryActivityProducts output={}", JSON.toJSONString(activityProductDTOs));
        return ResultVO.success(activityProductDTOs);
    }

    @Override
    public ResultVO<VerifyProductPurchasesLimitResp> verifyProductPurchasesLimit(VerifyProductPurchasesLimitReq req) {
        VerifyProductPurchasesLimitResp resp = new VerifyProductPurchasesLimitResp();
        resp.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resp.setResultMsg("校验通过");
        //根据产品ID和活动ID获取最大购买数
        Long limit = activityProductManager.queryActProductSumByProduct(req.getActivityId(), req.getProductId());
        limit = limit == null ? 0 : limit;
        //根据产品ID和活动ID获取已购买数量
        Long purchasedSum = historyPurchaseManager.queryActProductPurchasedSum(req);
        purchasedSum = purchasedSum == null ? 0 : purchasedSum;
        int purchaseCount = req.getPurchaseCount().intValue();
        if ((purchasedSum.longValue() + purchaseCount) > limit.longValue()) {
            resp.setResultCode(ResultCodeEnum.ERROR.getCode());
            resp.setResultMsg("校验失败,超过允许购买的最大数量:" + limit + "(已购买数量:" + purchasedSum + ")!");
        }

        return ResultVO.success(resp);
    }
}