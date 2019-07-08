package com.iwhalecloud.retail.web.controller.b2b.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.request.MemberAddressAddReq;
import com.iwhalecloud.retail.member.dto.request.MemberAddressListReq;
import com.iwhalecloud.retail.member.dto.request.MemberAddressUpdateReq;
import com.iwhalecloud.retail.member.dto.response.MemberAddressAddResp;
import com.iwhalecloud.retail.member.dto.response.MemberAddressRespDTO;
import com.iwhalecloud.retail.member.service.MemberAddressService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.interceptor.MemberContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author pjq
 * @date 2018/10/8
 */
@RestController
@RequestMapping("/api/address")
@Slf4j
public class MemberAddressController extends BaseController {
    @Reference
    private MemberAddressService memberAddressService;

    @GetMapping(value="listMemberAddress")
    @UserLoginToken
    public ResultVO<List<MemberAddressRespDTO>> listMemberAddress(){
        String memberId = MemberContext.getMemberId();
        if(StringUtils.isEmpty(memberId)){
            return ResultVO.error("memberId is must not be null");
        }
        MemberAddressListReq req = new MemberAddressListReq();
        req.setMemberId(memberId);
        ResultVO resultVO = memberAddressService.listMemberAddress(req);
        log.info("MemberAddressController listMemberAddress resultVO={} ",resultVO);
        return resultVO;
    }

    @RequestMapping(value="addMemberAddress",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<MemberAddressAddResp> addMemberAddress(@RequestBody MemberAddressAddReq memberAddress){
        log.info("MemberAddressController addMemberAddress memberAddress={} ",memberAddress);
        if(null == memberAddress){
            return ResultVO.error();
        }
        String memberId = MemberContext.getMemberId();
        if(StringUtils.isEmpty(memberId)){
            return ResultVO.error();
        }
        memberAddress.setMemberId(memberId);
        ResultVO resp = memberAddressService.addAddress(memberAddress);
        log.info("MemberAddressController addMemberAddress resp={} ", resp);
        return resp;
    }

    @RequestMapping(value="editMemberAddress",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Integer> editMemberAddress(@RequestBody MemberAddressUpdateReq memberAddress){
        log.info("MemberAddressController editMemberAddress memberAddress={} ",memberAddress);
        if(null == memberAddress){
            return ResultVO.error();
        }
        String memberId = MemberContext.getMemberId();
        memberAddress.setMemberId(memberId);
//        memberAddress.setLastUpdate(DateUtils .currentSysTimeForDate());
        ResultVO resp = memberAddressService.updateAddress(memberAddress);
        log.info("MemberAddressController editMemberAddress resp={} ",resp);
        return resp;
    }

    @GetMapping(value="deleteMemberAddress")
    @UserLoginToken
    public ResultVO deleteMemberAddress(@RequestParam(value = "addressId", required=false) String addressId){
        log.info("MemberAddressController deleteMemberAddress addressId={} ",addressId);
        if(StringUtils.isEmpty(addressId)){
            return ResultVO.error();
        }
        ResultVO resp = memberAddressService.deleteAddressById(addressId);
        log.info("MemberAddressController deleteMemberAddress resp={} ",resp);
        return resp;
    }
}
