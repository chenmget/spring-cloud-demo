package com.iwhalecloud.retail.web.controller.b2b.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.MarketingActivityDTO;
import com.iwhalecloud.retail.promo.dto.req.ActivityGoodsByMerchantReq;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityByMerchantListReq;
import com.iwhalecloud.retail.promo.dto.req.VerifyProductPurchasesLimitReq;
import com.iwhalecloud.retail.promo.dto.resp.ActivityGoodsByMerchantResp;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityByMerchantResp;
import com.iwhalecloud.retail.promo.dto.resp.VerifyProductPurchasesLimitResp;
import com.iwhalecloud.retail.promo.service.ActivityGoodService;
import com.iwhalecloud.retail.promo.service.ActivityProductService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.xinhang
 * @date 2019/3/5
 */
@RestController
@RequestMapping("/api/b2b/activityGoods")
@Slf4j

@Api(value="活动专区:", tags={"ActivityGoodsB2BController"})
public class ActivityGoodsB2BController {

    @Reference
    private ActivityGoodService activityGoodService;
    @Reference
    private ActivityProductService activityProductService;

    @ApiOperation(value = "根据登录用户查询活动列表", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/listMarketingActivityByMerchant")
    @UserLoginToken
    public ResultVO<Page<MarketingActivityDTO>> listMarketingActivityByMerchant(@RequestBody MarketingActivityByMerchantListReq req){
        log.info("ActivityGoodsB2BController listMarketingActivityByMerchant MarketingActivityByMerchantListReq={} ", req);
        if(UserContext.isUserLogin()) {
            UserDTO userDTO = UserContext.getUser();
            if(userDTO!=null){
                req.setLanId(userDTO.getLanId());
                req.setRegionId(userDTO.getRegionId());
                req.setMerchantId(userDTO.getRelCode());
            }
        }
        return activityGoodService.listMarketingActivityByMerchant(req);
    }

    @ApiOperation(value = "根据活动查询商品列表", notes = "条件查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/listActivityGoodsByActivityId")
//    @UserLoginToken
    public ResultVO<ActivityGoodsByMerchantResp> listActivityGoodsByActivityId(@RequestBody ActivityGoodsByMerchantReq req){
        log.info("ActivityGoodsB2BController listActivityGoodsByActivityId ActivityGoodsByMerchantReq={} ", req);
        if(UserContext.isUserLogin()) {
            req.setMerchantId(UserContext.getMerchantId());
            req.setLanId(UserContext.getUser().getLanId());
            req.setRegionId(UserContext.getUser().getRegionId());
        }
        return activityGoodService.listActivityGoodsByActivityId(req);
    }
    @ApiOperation(value = "校验产品购买量是否达到购买上限服务", notes = "校验产品购买量是否达到购买上限服务")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/verifyProductPurchasesLimit")
    ResultVO<VerifyProductPurchasesLimitResp> verifyProductPurchasesLimit(@RequestBody VerifyProductPurchasesLimitReq req){
        log.info("GoodsController verifyProductPurchasesLimit goodsId={}", JSON.toJSON(req));
        return activityProductService.verifyProductPurchasesLimit(req);
    }
}
