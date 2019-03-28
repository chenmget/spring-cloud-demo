package com.iwhalecloud.retail.order.reference;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.member.dto.response.MemberAddressRespDTO;
import com.iwhalecloud.retail.member.dto.response.MemberResp;
import com.iwhalecloud.retail.member.service.MemberAddressService;
import com.iwhalecloud.retail.member.service.MemberService;
import com.iwhalecloud.retail.order.model.MemberAddrDTO;
import com.iwhalecloud.retail.partner.dto.PartnerShopDTO;
import com.iwhalecloud.retail.partner.service.PartnerShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MemberInfoReference {

    @Reference
    private MemberService memberService;

    @Reference
    private MemberAddressService memberAddressService;

    @Reference
    private PartnerShopService partnerShopService;

    /**
     * 会员查询
     */
    public MemberResp selectMember(String memberId) {
        MemberResp resp= memberService.getMember(memberId);
        return resp;
    }

    public MemberAddrDTO selectAddrByID(String addr){
        MemberAddressRespDTO respDTO= memberAddressService.queryAddress(addr);
        if(respDTO==null){
            return  new MemberAddrDTO();
        }
        MemberAddrDTO addInfo=new MemberAddrDTO();
        BeanUtils.copyProperties(respDTO,addInfo);
        addInfo.setShipAddr(respDTO.getAddr());
//        addInfo.setShipMobile(respDTO.getMobile());
//        addInfo.setShipTel(respDTO.getTel());
        addInfo.setShipMobile(respDTO.getConsigeeMobile());
        addInfo.setShipTel(respDTO.getConsigeeMobile());
        addInfo.setShipZip(respDTO.getZip());
       return addInfo;
    }

    public MemberAddrDTO selectAddrByShipId(String addr){
        PartnerShopDTO respDTO= partnerShopService.getPartnerShop(addr);
        if(respDTO==null){
            return  null;
        }
        MemberAddrDTO addInfo=new MemberAddrDTO();
        BeanUtils.copyProperties(respDTO,addInfo);
        addInfo.setShipAddr(respDTO.getAddress());
        return addInfo;
    }
}
