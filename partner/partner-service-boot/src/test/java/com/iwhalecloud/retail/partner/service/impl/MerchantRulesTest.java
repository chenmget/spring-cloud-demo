package com.iwhalecloud.retail.partner.service.impl;

import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.dto.MerchantRulesDetailDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
public class MerchantRulesTest {

    @Autowired
    private MerchantRulesService merchantRulesService;

    @Test
    public void save(){
        MerchantRulesSaveReq req = new MerchantRulesSaveReq();
        req.setMerchantId("777");
        req.setRuleType("1");
        req.setTargetType("1");
        req.setTargetIdList(Lists.newArrayList("2", "3"));
        ResultVO resultVO = merchantRulesService.saveMerchantRules(req);
        System.out.print("结果：" + resultVO.toString());
    }


    @Test
    public void delete(){
        MerchantRulesDeleteReq req = new MerchantRulesDeleteReq();
//        req.setSupplierRuleId("1080355929825738753");
        req.setMerchantId("666");
        req.setRuleType("1");
        req.setTargetType("1");
        ResultVO resultVO = merchantRulesService.deleteMerchantRules(req);
        System.out.print("结果：" + resultVO.toString());
    }


    @Test
    public void listDetail(){
        MerchantRulesDetailListReq req = new MerchantRulesDetailListReq();
        req.setMerchantId("4331301049127");
        req.setRuleType("1");
        req.setTargetType("3");
        ResultVO resultVO = merchantRulesService.listMerchantRulesDetail(req);
        System.out.print("结果：" + resultVO.toString());
    }


    @Test
    public void getTransferPermission(){
        ResultVO resultVO = merchantRulesService.getTransferPermission("111");
        System.out.print("结果：" + resultVO.toString());
    }


    public static void main(String[] args) {
        MerchantRulesDetailDTO dto = new MerchantRulesDetailDTO();
        dto.setTargetName("12345");
        MerchantRulesDetailDTO dto1 = new MerchantRulesDetailDTO();
        dto1.setTargetName("123456");
        MerchantRulesDetailDTO dto2 = new MerchantRulesDetailDTO();

        List<MerchantRulesDetailDTO> list = new ArrayList<>();
        list.add(dto);
        list.add(dto1);
        list.add(dto2);

        System.out.print(list.toString());
        System.out.print("\n");

        list = list.stream().filter(i -> i.getTargetName() != null).collect(Collectors.toList());
        System.out.print(list.toString());

    }

    @Test
    public void getGreenChannelPermission(){
        ResultVO resultVO = merchantRulesService.getGreenChannelPermission("4331301049127");
        System.out.print("结果：" + resultVO.toString());
    }

    @Test
    public void merchant_rule_check_test(){
        MerchantRulesCheckReq req = new MerchantRulesCheckReq();
        req.setSourceMerchantId("4331301049127");
        List<String> targetMerchantList = new ArrayList<>();
        targetMerchantList.add("4331301049127");
        targetMerchantList.add("4300001063521");
        req.setTargetMerchantIds(targetMerchantList);
        ResultVO resultVO = merchantRulesService.checkMerchantRules(req);
        System.out.print("结果：" + resultVO.toString());
    }
}
