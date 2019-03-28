package com.iwhalecloud.retail.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.dto.request.MemberPageReq;
import com.iwhalecloud.retail.member.dto.response.MemberLoginResp;
import lombok.extern.slf4j.Slf4j;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.request.MemberIsExistsReq;
import com.iwhalecloud.retail.member.dto.request.MemberLoginReq;
import com.iwhalecloud.retail.member.dto.request.MemberAddReq;
import com.iwhalecloud.retail.member.dto.response.MemberIsExistsResp;
import com.iwhalecloud.retail.member.dto.response.MemberResp;
import com.iwhalecloud.retail.member.service.MemberService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MemberServiceApplication.class)
@Slf4j
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;


    @org.junit.Test
    public void getPregionId(){
    	MemberResp member = memberService.getMember("150714973800161343");
    	log.info("member======:"+member.toString());
    }
    
    @org.junit.Test
    public void isExists(){
    	MemberIsExistsReq req = new MemberIsExistsReq();
    	req.setUname("panjinmu");
    	MemberIsExistsResp exists = memberService.isExists(req);
    	log.info("exists======:"+exists.toString());
    }
    
    @org.junit.Test
    public void login(){
    	MemberLoginReq req = new MemberLoginReq();
    	req.setUserName("mumu");
    	req.setPassword("df831c971c6c9dc3aebbbf46164c43");
    	MemberLoginResp login = memberService.login(req);
    	log.info(login.toString());
    }
    
    @org.junit.Test
    public void register(){
    	MemberAddReq req = new MemberAddReq();
    	req.setUname("panjinmu");
    	ResultVO login = memberService.register(req);
    	log.info(login.toString());
    }

	@org.junit.Test
	public void getMemberPage(){
		MemberPageReq req = new MemberPageReq();
//		req.setUName("panjinmu");
		Page result = memberService.pageMember(req);
		log.info("222");
	}
}
