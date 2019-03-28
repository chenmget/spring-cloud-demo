package com.iwhalecloud.retail.system;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.request.*;
import com.iwhalecloud.retail.system.dto.response.RandomLogGetResp;
import com.iwhalecloud.retail.system.dto.response.RegionsGetResp;
import com.iwhalecloud.retail.system.service.RandomLogService;
import com.iwhalecloud.retail.system.service.RegionsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
@Slf4j
public class RegionsServiceTest {

    @Autowired
    private RegionsService regionsService;
    
    @Autowired
    private RandomLogService randomLogService;

    @org.junit.Test
    public void getPregionId(){
    	ResultVO pregionId = regionsService.getPregionId("1234");
    	log.info(pregionId.toString());
    }

    @org.junit.Test
    public void getRegion(){
    	RegionsGetReq req = new RegionsGetReq();
    	req.setRegionCondType("0");
    	req.setRegionName("ctes");
    	ResultVO<RegionsGetResp> region = regionsService.getRegion(req);
    	log.info("result is:"+(region == null));
    }
    
    @org.junit.Test
    public void listRegions(){
    	RegionsListReq req = new RegionsListReq();
		List regionGrades = new ArrayList<String>();
		regionGrades.add("0");
    	req.setRegionGrades(regionGrades);
    	ResultVO listRegions = regionsService.listRegions(req);
    	log.info(listRegions.toString());
    }
    
    @org.junit.Test
    public void insertSelective(){
    	RandomLogAddReq req = new RandomLogAddReq();
    	req.setBusiType(10);
    	req.setCreateDate(new Date());
    	req.setRandomCode("0027012009");
    	ResultVO<Integer> insertSelective = randomLogService.insertSelective(req);
    	log.info("insert result："+insertSelective);
    }
    
    @org.junit.Test
    public void selectLogIdByRandomCode(){
    	RandomLogGetReq req = new RandomLogGetReq();
    	req.setBusiType(10);
    	ResultVO<RandomLogGetResp> selectLogIdByRandomCode = randomLogService.selectLogIdByRandomCode(req);
    	log.info("query result："+selectLogIdByRandomCode);
    }
    
    @org.junit.Test
    public void updateByPrimaryKey(){
    	RandomLogUpdateReq req = new RandomLogUpdateReq();
    	req.setBusiType(3);
    	req.setLogId("1068475557382619137");
    	ResultVO<Integer> updateResult = randomLogService.updateByPrimaryKey(req);
    	log.info("update result："+updateResult);
    }
    

}
