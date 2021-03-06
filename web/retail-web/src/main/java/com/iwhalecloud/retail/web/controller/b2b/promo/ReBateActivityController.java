package com.iwhalecloud.retail.web.controller.b2b.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.req.ActReBateProductReq;
import com.iwhalecloud.retail.promo.dto.req.ReBateActivityListReq;
import com.iwhalecloud.retail.promo.dto.resp.ReBateActivityListResp;
import com.iwhalecloud.retail.promo.service.ActActivityProductRuleService;
import com.iwhalecloud.retail.promo.service.ActivityProductService;
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

/**
 * @author lhr 2019-03-25 16:06:30
 */
@RestController
@RequestMapping("/api/b2b/promo/reBate")
@Slf4j
public class ReBateActivityController {
    @Reference
    private ActivityProductService activityProductService;

    @Reference
    private ActActivityProductRuleService actActivityProductRuleService;
    @ApiOperation(value = "添加返利活动产品",notes = "添加返利活动产品")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/addReBateProductActivity")
    @UserLoginToken
    public ResultVO addReBateProductActivity(@RequestBody ActReBateProductReq actReBateProductReq){
        log.info("ReBateActivityController addReBateProductActivity actReBateProductReq={}", JSON.toJSON(actReBateProductReq));
        if(StringUtils.isEmpty(UserContext.getUserId())){
            return ResultVO.error("用户不能为空");
        }else{
            UserDTO userDTO = UserContext.getUser();
            actReBateProductReq.setUserId(userDTO.getUserId());
            actReBateProductReq.setUserName(userDTO.getUserName());
            actReBateProductReq.setSysPostName(userDTO.getUserName());
            actReBateProductReq.setOrgId(userDTO.getOrgId());
        }
        return actActivityProductRuleService.addReBateProduct(actReBateProductReq);
    }

    @ApiOperation(value = "返利活动列表",notes = "返利活动列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/listMarketingActivity")
    @UserLoginToken
    public ResultVO<Page<ReBateActivityListResp>> listMarketingActivity(@RequestBody ReBateActivityListReq reBateActivityListReq){
        log.info("ReBateActivityController listMarketingActivity reBateActivityListReq={}", JSON.toJSON(reBateActivityListReq));
        if(StringUtils.isEmpty(UserContext.getUserId())){
            return ResultVO.error("用户不能为空");
        }
        if("Invalid date".equals(reBateActivityListReq.getStartTimeS())) {
            reBateActivityListReq.setStartTimeS("");
        }
        if("Invalid date".equals(reBateActivityListReq.getStartTimeE())) {
            reBateActivityListReq.setStartTimeE("");
        }
        if("Invalid date".equals(reBateActivityListReq.getEndTimeS())) {
            reBateActivityListReq.setEndTimeS("");
        }
        if("Invalid date".equals(reBateActivityListReq.getEndTimeE())) {
            reBateActivityListReq.setEndTimeE("");
        }
        return actActivityProductRuleService.listReBateActivity(reBateActivityListReq);
    }
}
