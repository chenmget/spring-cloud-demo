package com.iwhalecloud.retail.member;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.request.MemberAddressAddReq;
import com.iwhalecloud.retail.member.dto.request.MemberAddressListReq;
import com.iwhalecloud.retail.member.dto.request.MemberAddressUpdateReq;
import com.iwhalecloud.retail.member.service.MemberAddressService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author My
 * @Date 2018/12/1
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MemberServiceApplication.class)
@Slf4j
public class MemberAddressServiceTest {
    @Autowired
    public MemberAddressService memberAddressService;
    @Test
    public void addMemberAddress(){
        MemberAddressAddReq reqDTO = new MemberAddressAddReq();
        reqDTO.setAddr("11111");
        reqDTO.setConsigeeName("张三");
        reqDTO.setCity("长沙");
        reqDTO.setMemberId("11111");
        memberAddressService.addAddress(reqDTO);
        System.out.println(JSON.toJSONString(reqDTO));

    }

    @Test
    public void queryMemberAddress(){
        MemberAddressListReq req = new MemberAddressListReq();
        req.setMemberId("1212");
        ResultVO resultVO = memberAddressService.listMemberAddress(req);
    }

    @Test
    public void queryMemberAddressById(){
        MemberAddressRespDTO dto = memberAddressService.queryAddress("10002251");
        System.out.print(JSON.toJSONString(dto));
    }

    @Test
    public void deleteMemberAddress(){
        String addrId = "1068743167017533441";
        memberAddressService.deleteAddressById(addrId);
    }

    @Test
    public void updateMemberAddress(){
        MemberAddressUpdateReq req = new MemberAddressUpdateReq();
        req.setAddr("aa");
        req.setMemberId("1212");
        req.setAddrId("1068743167017533441");
        memberAddressService.updateAddress(req);
    }

}
