package com.iwhalecloud.retail.web.controller.b2b.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.request.MemberAddressAddReq;
import com.iwhalecloud.retail.member.dto.request.MemberAddressListReq;
import com.iwhalecloud.retail.member.dto.request.MemberAddressUpdateReq;
import com.iwhalecloud.retail.member.dto.response.MemberAddressAddResp;
import com.iwhalecloud.retail.member.dto.response.MemberAddressRespDTO;
import com.iwhalecloud.retail.member.service.MemberAddressService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author zhongwenlong
 * @Date 2018/12/27
 *
 * 分销商 和会员的地址  用的是同一个  表
 * 从MemberAddressController类里面 copy 过来的
 *
 * 所有方法里面的 memberId  都换成  userId
 **/
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/b2b/partnerAddress")
public class PartnerAddressB2BController {

    @Reference
    private MemberAddressService memberAddressService;

    @ApiOperation(value = "查询分销商地址列表", notes = "查询分销商地址列表。")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/list")
    @UserLoginToken
    public ResultVO<List<MemberAddressRespDTO>> listMemberAddress(){

        String memberId = UserContext.getUserId();
        if(StringUtils.isEmpty(memberId)){
            return ResultVO.error("memberId is must not be null");
        }
        MemberAddressListReq req = new MemberAddressListReq();
        req.setMemberId(memberId);
        ResultVO resultVO = memberAddressService.listMemberAddress(req);
        log.info("PartnerAddressB2BController listMemberAddress resultVO={} ", JSON.toJSONString(resultVO));
        return resultVO;
    }

    @ApiOperation(value = "添加分销商地址", notes = "添加分销商地址。")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value="/add",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<MemberAddressAddResp> addMemberAddress(@RequestBody @ApiParam(value = "MemberAddressAddReq", required = true) MemberAddressAddReq memberAddress){
        log.info("PartnerAddressB2BController addMemberAddress memberAddress={} ",memberAddress);
        if(null == memberAddress){
            return ResultVO.error();
        }

        String memberId = UserContext.getUserId();
        if(StringUtils.isEmpty(memberId)){
            return ResultVO.error();
        }
        memberAddress.setMemberId(memberId);
        ResultVO resp = memberAddressService.addAddress(memberAddress);
        log.info("PartnerAddressB2BController.addMemberAddress() resp={} ", JSON.toJSONString(resp));
        return resp;
    }

    @ApiOperation(value = "编辑分销商地址", notes = "编辑分销商地址。")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value="/edit",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Integer> editMemberAddress(@RequestBody @ApiParam(value = "MemberAddressUpdateReq", required = true) MemberAddressUpdateReq req){
        log.info("PartnerAddressB2BController.editMemberAddress() input: MemberAddressUpdateReq={} ",req);

        String memberId = UserContext.getUserId();
        req.setMemberId(memberId);
//        req.setLastUpdate(DateUtils.currentSysTimeForDate());
        ResultVO resp = memberAddressService.updateAddress(req);
        log.info("PartnerAddressB2BController.editMemberAddress() output: resp={} ",resp);
        return resp;
    }

    @ApiOperation(value = "删除分销商地址", notes = "删除分销商地址。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addressId", value = "地址ID", paramType = "query", required = false, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/delete")
    @UserLoginToken
    public ResultVO deleteMemberAddress(@RequestParam(value = "addressId") String addressId){
        log.info("PartnerAddressB2BController deleteMemberAddress addressId={} ",addressId);
        if(StringUtils.isEmpty(addressId)){
            return ResultVO.error();
        }
        ResultVO resp = memberAddressService.deleteAddressById(addressId);
        log.info("PartnerAddressB2BController deleteMemberAddress resp={} ",resp);
        return resp;
    }

}
