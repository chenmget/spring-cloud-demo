package com.iwhalecloud.retail.web.controller.b2b.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.MarketingActivityDTO;
import com.iwhalecloud.retail.promo.dto.req.AddPreSaleProductReqDTO;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityAddReq;
import com.iwhalecloud.retail.promo.dto.req.QueryMarketingActivityReq;
import com.iwhalecloud.retail.promo.dto.resp.PreSubsidyProductRespDTO;
import com.iwhalecloud.retail.promo.service.ActivityProductService;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author zhou.zc
 * @date 2019年02月26日
 * @Description:预售活动配置
 */
@RestController
@RequestMapping("/api/b2b/promo/preSale")
@Slf4j
public class PreSaleActivityB2BController {

    @Reference
    private ActivityProductService activityProductService;

    @Reference
    private MarketingActivityService marketingActivityService;

    @ApiOperation(value = "添加预售活动产品",notes = "添加预售活动产品")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping (value = "/addPreSaleActivity")
    @UserLoginToken
    public ResultVO addPreSaleActivity(@RequestBody AddPreSaleProductReqDTO addPreSaleProductReqDTO){
        log.info("PreSaleActivityB2BController addPreSaleActivity addPreSaleProductReqDTO={}", JSON.toJSON(addPreSaleProductReqDTO));
        UserDTO userDTO = UserContext.getUser();
        addPreSaleProductReqDTO.setUserId(userDTO.getUserId());
        addPreSaleProductReqDTO.setUserName(userDTO.getUserName());
        addPreSaleProductReqDTO.setSysPostName(userDTO.getUserName());
        addPreSaleProductReqDTO.setOrgId(userDTO.getOrgId());
        return activityProductService.addPreSaleProduct(addPreSaleProductReqDTO);
    }

    @ApiOperation(value = "查询预售活动产品",notes = "查询预售活动产品")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/queryPreSaleProduct")
    public ResultVO<List<PreSubsidyProductRespDTO>> queryPreSaleProduct(@RequestBody QueryMarketingActivityReq queryMarketingActivityReq){
        log.info("PreSaleActivityB2BController queryPreSaleProduct queryMarketingActivityReq={}", JSON.toJSON(queryMarketingActivityReq));
        return activityProductService.queryPreSaleProduct(queryMarketingActivityReq);
    }

    @ApiOperation(value = "更新预售活动规则",notes = "更新预售活动规则")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping (value = "/updatePreSaleActivityRule")
    @UserLoginToken
    public ResultVO updatePreSaleActivityRule(@RequestBody MarketingActivityAddReq marketingActivityAddReq){
        log.info("PreSaleActivityB2BController updatePreSaleActivityRule updatePreSaleActivityRule={}", JSON.toJSON(marketingActivityAddReq));
        if(StringUtils.isEmpty(UserContext.getUserId())){
            return ResultVO.error("用户不能为空");
        }else{
            marketingActivityAddReq.setModifier(UserContext.getUserId());
        }
        return marketingActivityService.updatePreSaleActivityRule(marketingActivityAddReq);
    }

    @ApiOperation(value = "查询预售活动规则",notes = "查询预售活动规则")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/queryPreSaleActivityRule")
    public ResultVO<MarketingActivityDTO> queryPreSaleActivityRule(@RequestBody QueryMarketingActivityReq queryMarketingActivityReq){
        log.info("PreSaleActivityB2BController queryPreSaleActivityRule queryMarketingActivityReq={}", JSON.toJSON(queryMarketingActivityReq));
        return marketingActivityService.queryMarketingActivityById(queryMarketingActivityReq);
    }

}
