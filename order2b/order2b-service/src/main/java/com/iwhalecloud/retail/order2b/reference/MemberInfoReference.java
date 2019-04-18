package com.iwhalecloud.retail.order2b.reference;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.response.MemberAddressRespDTO;
import com.iwhalecloud.retail.member.dto.response.MemberResp;
import com.iwhalecloud.retail.member.service.MemberAddressService;
import com.iwhalecloud.retail.member.service.MemberService;
import com.iwhalecloud.retail.order2b.model.MemberAddrModel;
import com.iwhalecloud.retail.order2b.model.MemberModel;
import com.iwhalecloud.retail.order2b.model.SupplierModel;
import com.iwhalecloud.retail.order2b.model.UserInfoModel;
import com.iwhalecloud.retail.partner.dto.MerchantAccountDTO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.PartnerShopDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountListReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantLigthReq;
import com.iwhalecloud.retail.partner.dto.resp.MerchantLigthResp;
import com.iwhalecloud.retail.partner.service.MerchantAccountService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.partner.service.PartnerShopService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.UserListReq;
import com.iwhalecloud.retail.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MemberInfoReference {

    @Reference
    private MemberService memberService;

    @Reference
    private MemberAddressService memberAddressService;

    @Reference
    private PartnerShopService partnerShopService;

    @Reference
    private MerchantService merchantService;

    @Reference
    private UserService userService;

    @Reference
    private MerchantAccountService merchantAccountService;

    /**
     * 会员查询
     */
    public MemberModel selectMemberInfo(String memberId) {
        MemberResp resp = memberService.getMember(memberId);
        if (resp == null) {
            return null;
        }
        MemberModel model = new MemberModel();
        BeanUtils.copyProperties(resp, model);
        return model;
    }

    /**
     * 用户查询
     */
    public UserInfoModel selectUserInfo(String memberId) {
        UserDTO resp = userService.getUserByUserId(memberId);
        if (resp == null) {
            return null;
        }
        UserInfoModel model = new UserInfoModel();
        BeanUtils.copyProperties(resp, model);
        return model;
    }


    /**
     * 用户查询
     */
    public List<UserInfoModel> selectUserInfoByUserCode(String relCode) {
        UserListReq userGetReq=new UserListReq();
        userGetReq.setRelCode(relCode);
        List<UserDTO> resp = userService.getUserList(userGetReq);
        if (resp == null) {
            return new ArrayList<>();
        }
        List<UserInfoModel> list= JSON.parseArray(JSON.toJSONString(resp),UserInfoModel.class);
        return list;
    }


    public MemberAddrModel selectReceiveAddrById(String addr) {
        MemberAddressRespDTO respDTO = memberAddressService.queryAddress(addr);
        if (respDTO == null) {
            return null;
        }
        MemberAddrModel addInfo = new MemberAddrModel();
        BeanUtils.copyProperties(respDTO, addInfo);
        addInfo.setReceiveAddr(respDTO.getAddr());
//        addInfo.setReceiveMobile(respDTO.getMobile());
//        addInfo.setReceiveMobile(respDTO.getTel());
        addInfo.setReceiveMobile(respDTO.getConsigeeMobile());
        addInfo.setReceiveMobile(respDTO.getConsigeeMobile());
        addInfo.setReceiveZip(respDTO.getZip());
        return addInfo;
    }

    public MemberAddrModel selectAddrByShipId(String shipId) {
        PartnerShopDTO respDTO = partnerShopService.getPartnerShop(shipId);
        if (respDTO == null) {
            return null;
        }
        MemberAddrModel addInfo = new MemberAddrModel();
        BeanUtils.copyProperties(respDTO, addInfo);
        addInfo.setReceiveAddr(respDTO.getAddress());
        return addInfo;
    }

    public SupplierModel selectSuperById(String supperId) {
        ResultVO<MerchantDTO> supplierDTO = merchantService.getMerchantById(supperId);
        if (supplierDTO.getResultData() == null) {
            return null;
        }
        SupplierModel supplierModel = new SupplierModel();
        BeanUtils.copyProperties(supplierDTO.getResultData(),supplierModel);
        supplierModel.setSupplierId(supperId);
        return supplierModel;
    }

    public ResultVO<List<MerchantAccountDTO>> listMerchantAccount(MerchantAccountListReq merchantAccountListReq) {
        return merchantAccountService.listMerchantAccount(merchantAccountListReq);
    }


    public List<String> listMerchantIdList(MerchantLigthReq req) {
        ResultVO<List<MerchantLigthResp>> merchantRespVO = merchantService.listMerchantForOrder(req);
        log.info("MemberInfoReference.listMerchantIdList merchantService.listMerchantForOrder req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantRespVO));
        if (!merchantRespVO.isSuccess() || CollectionUtils.isEmpty(merchantRespVO.getResultData())) {
            String nullValue = "-1";
            return Lists.newArrayList(nullValue);
        }

        List<MerchantLigthResp> merchantResp = merchantRespVO.getResultData();
        List<String> merchantIdList = merchantResp.stream().map(MerchantLigthResp::getMerchantId).collect(Collectors.toList());
        return merchantIdList;
    }

    public MerchantLigthResp getMerchantForOrder(MerchantGetReq req) {
        ResultVO<MerchantLigthResp> merchantRespVO = merchantService.getMerchantForOrder(req);
        log.info("MemberInfoReference.getMerchantForOrder merchantService.getMerchantForOrder req={},resp={}", JSON.toJSONString(req), JSON.toJSONString(merchantRespVO));
        if (!merchantRespVO.isSuccess() || null != merchantRespVO.getResultData()) {
            return merchantRespVO.getResultData();
        }
        return null;
    }
}
