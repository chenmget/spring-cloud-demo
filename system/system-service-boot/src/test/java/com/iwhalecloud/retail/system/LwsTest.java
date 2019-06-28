package com.iwhalecloud.retail.system;

import java.util.ArrayList;
import java.util.List;

import com.iwhalecloud.retail.system.dto.LanDTO;
import com.iwhalecloud.retail.system.service.LanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.SysCommonOrgRequest;
import com.iwhalecloud.retail.system.dto.SysCommonOrgResp;
import com.iwhalecloud.retail.system.service.CommonOrgService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
public class LwsTest {
	
	@Autowired
	private CommonOrgService commonOrgService;
	@Autowired
	private LanService lanService;

	@Test
	public void test() {
		SysCommonOrgRequest req = new SysCommonOrgRequest();
		List<String> list = new ArrayList<String>();
		list.add("731");
		req.setLanIdList(list);
		ResultVO<List<SysCommonOrgResp>> listSys = commonOrgService.getSysCommonOrg(req);
		System.out.println("111111111111111111");
		
		
	}
	@Test
	public void testLan() {
		LanDTO lanDTO=lanService.getLanInfoById("731");
		System.out.println("111111111111111111"+lanDTO.getLanName());


	}

}
