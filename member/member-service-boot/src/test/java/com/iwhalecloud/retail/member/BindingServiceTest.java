package com.iwhalecloud.retail.member;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.request.BindingAddReq;
import com.iwhalecloud.retail.member.dto.request.BindingDeleteReq;
import com.iwhalecloud.retail.member.dto.request.BindingQueryReq;
import com.iwhalecloud.retail.member.dto.request.BindingUpdateReq;
import com.iwhalecloud.retail.member.dto.response.BindingQueryResp;
import com.iwhalecloud.retail.member.service.BindingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MemberServiceApplication.class)
@Slf4j
public class BindingServiceTest {

    @Autowired
    private BindingService bindingService;


    @org.junit.Test
    public void insertBinding(){
    	BindingAddReq req = new BindingAddReq();
    	req.setMemberId("100000");
    	req.setTargetId("110000");
    	req.setTargetType(1);
    	req.setUname("test");
    	bindingService.insertBinding(req);
    }
    
    @org.junit.Test
    public void queryeBindingCodition(){
    	BindingQueryReq req = new BindingQueryReq();
//    	req.setId("1068431439893794818");
    	req.setTargetId("110000");
//    	req.setTargetType(1);
//    	req.setUname("test");
    	ResultVO<List<BindingQueryResp>> binding = bindingService.queryeBindingCodition(req);
    	log.info("query result:"+(binding == null));
    	log.info("query result:"+binding.toString());
    }
    
    @org.junit.Test
    public void updateBindingCodition(){
    	BindingUpdateReq req = new BindingUpdateReq();
    	req.setId("1068428361975091201");
    	req.setMemberId("110000");
    	req.setTargetId("120000");
    	req.setTargetType(1);
    	req.setUname("kobe");
    	bindingService.updateBindingCodition(req);
    }
    
    @org.junit.Test
    public void deleteBindingCondition(){
    	BindingDeleteReq req = new BindingDeleteReq();
    	req.setMemberId("110000");
    	req.setTargetType(1);
    	bindingService.deleteBindingCondition(req);
    }
    
    
}
