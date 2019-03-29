package com.iwhalecloud.retail.web.controller.b2b.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.dto.request.AddPreSubsidyCouponReqDTO;
import com.iwhalecloud.retail.rights.dto.request.AddPromotionProductReqDTO;
import com.iwhalecloud.retail.rights.dto.request.QueryPreSubsidyReqDTO;
import com.iwhalecloud.retail.rights.dto.request.UpdatePreSubsidyCouponReqDTO;
import com.iwhalecloud.retail.rights.dto.response.MktResCouponRespDTO;
import com.iwhalecloud.retail.rights.dto.response.PreSubsidyProductPromResqDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryPreSubsidyCouponResqDTO;
import com.iwhalecloud.retail.rights.service.PreSubsidyCouponService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhou.zc
 * @date 2019年02月19日
 * @Description:前置补贴活动配置
 */
@RestController
@RequestMapping("/api/b2b/promo/preSubsidy")
@Slf4j
public class PreSubsidyActivityB2BController {


    @Reference
    private PreSubsidyCouponService preSubsidyCouponService;

    @ApiOperation(value = "前置补贴活动配置优惠劵",notes = "前置补贴活动配置优惠劵")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/addPreSubsidyCoupon")
    @UserLoginToken
    public ResultVO addPreSubsidyCoupon(@RequestBody AddPreSubsidyCouponReqDTO req) {
        log.info("PreSubsidyActivityService addPreSubsidyCoupon req={}", JSON.toJSON(req));
        if (UserContext.isMerchant()) {
            req.setPartnerId(UserContext.getUser().getRelCode());
        } else {
            req.setPartnerId("-1");
        }
        req.setCreateStaff(UserContext.getUserId());
        return preSubsidyCouponService.addPreSubsidyCoupon(req);
    }

    @ApiOperation(value = "前置补贴活动更新优惠劵",notes = "前置补贴活动更新优惠劵")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/updatePreSubsidyCoupon")
    @UserLoginToken
    public ResultVO updatePreSubsidyCoupon(@RequestBody UpdatePreSubsidyCouponReqDTO req) {
        log.info("PreSubsidyActivityService updatePreSubsidyCoupon req={}", JSON.toJSON(req));
        if(UserContext.isMerchant()){
            req.setPartnerId(UserContext.getUser().getRelCode());
        }else{
            req.setPartnerId("-1");
        }
        req.setUpdateStaff(UserContext.getUserId());
        return  preSubsidyCouponService.updatePreSubsidyCoupon(req);
    }

    @ApiOperation(value = "根据活动id查询前置补贴活动优惠劵",notes = "根据活动id查询前置补贴活动优惠劵")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/queryPreSubsidyCoupon")
    public ResultVO<List<QueryPreSubsidyCouponResqDTO>> queryPreSubsidyCoupon(@RequestBody QueryPreSubsidyReqDTO queryPreSubsidyReqDTO) {
        log.info("PreSubsidyActivityService queryPreSubsidyCoupon queryPreSubsidyReqDTO={}",JSON.toJSON(queryPreSubsidyReqDTO));
        return  preSubsidyCouponService.queryPreSubsidyCoupon(queryPreSubsidyReqDTO);
    }

    @ApiOperation(value = "删除前置补贴活动优惠劵",notes = "删除查询前置补贴活动优惠劵")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/deletePreSubsidyCoupon")
    public ResultVO deletePreSubsidyCoupon(@RequestBody QueryPreSubsidyReqDTO queryPreSubsidyReqDTO) {
        log.info("PreSubsidyActivityService deletePreSubsidyCoupon queryPreSubsidyReqDTO={}",queryPreSubsidyReqDTO);
        return  preSubsidyCouponService.deletePreSubsidyCoupon(queryPreSubsidyReqDTO);
    }


    @ApiOperation(value = "添加前置补贴活动产品",notes = "添加前置补贴活动产品")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/addPreSubsidyProduct",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO addPreSubsidyProduct(@RequestBody AddPromotionProductReqDTO addPromotionProductReqDTO) {
        log.info("PreSubsidyActivityService addPreSubsidyProduct addPromotionProductReqDTO={}",addPromotionProductReqDTO);
        if(StringUtils.isEmpty(UserContext.getUserId())){
            return ResultVO.error("用户不能为空");
        }else {
            addPromotionProductReqDTO.setUserId(UserContext.getUserId());
        }
        return  preSubsidyCouponService.addPreSubsidyProduct(addPromotionProductReqDTO);
    }

    @ApiOperation(value = "查询前置补贴活动产品",notes = "查询前置补贴活动产品")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/queryPreSubsidyProduct")
    public ResultVO<List<PreSubsidyProductPromResqDTO>> queryPreSubsidyProduct(@RequestBody QueryPreSubsidyReqDTO queryPreSubsidyReqDTO) {
        log.info("PreSubsidyActivityService queryPreSubsidyProduct ,queryPreSubsidyReqDTO={}",JSON.toJSON(queryPreSubsidyReqDTO));
        return  preSubsidyCouponService.queryPreSubsidyProduct(queryPreSubsidyReqDTO);
    }

    @ApiOperation(value = "查询前置补贴活动可混用的优惠券",notes = "配置可混用券使用")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/queryMixUseCoupon")
    public ResultVO<List<MktResCouponRespDTO>> queryMixUseCoupon(@RequestBody QueryPreSubsidyReqDTO queryPreSubsidyReqDTO) {
        log.info("PreSubsidyActivityService queryPreSubsidyProduct ,queryPreSubsidyReqDTO={}",JSON.toJSON(queryPreSubsidyReqDTO));
        return  preSubsidyCouponService.queryMixUseCoupon(queryPreSubsidyReqDTO);
    }

    @ApiOperation(value = "更新前置补贴活动优惠券类型",notes = "一种活动只有一种类型的券")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/updateActCouponType")
    public ResultVO updateActCouponType(@RequestBody QueryPreSubsidyReqDTO queryPreSubsidyReqDTO) {
        log.info("PreSubsidyActivityService updateActCouponType ,queryPreSubsidyReqDTO={}",JSON.toJSON(queryPreSubsidyReqDTO));
        return  preSubsidyCouponService.updateActCouponType(queryPreSubsidyReqDTO);
    }
}