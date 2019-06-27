package com.iwhalecloud.retail.partner.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.dto.resp.MerchantPageResp;
import com.iwhalecloud.retail.partner.manager.MerchantManager;
import com.iwhalecloud.retail.partner.service.MerchantService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
public class MerchantTest {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantManager merchantManager;

    @Test
    public void count() {
        MerchantCountReq req = new MerchantCountReq();
        req.setMerchantTypeList(Lists.newArrayList("1","2","3"));
//        req.setStatus("100");
        ResultVO resultVO = merchantService.countMerchant(req);
        System.out.print("结果：" + resultVO.toString());
    }

    @Test
    public void get() {
        MerchantGetReq req = new MerchantGetReq();
//        req.setMerchantCode("12345");
        ResultVO resultVO = merchantService.getMerchant(req);
        System.out.print("结果：" + resultVO.toString());
    }

    @Test
    public void bindUser() {
        MerchantBindUserReq req = new MerchantBindUserReq();
        req.setMerchantId("4300001063521");
        req.setMerchantType("4");
        req.setUserId("1077841362017062914");
        ResultVO resultVO = new ResultVO();
        try {
            resultVO = merchantService.bindUser(req);

        }catch (Exception e){

        }
        System.out.print("结果：" + resultVO.toString());
    }

    @Test
    public void getDetail() {
        MerchantGetReq req = new MerchantGetReq();
        req.setMerchantCode("12345");
        ResultVO resultVO = merchantService.getMerchantDetail(req);
        System.out.print("结果：" + resultVO.toString());
    }

    @Test
    public void list() {
        MerchantListReq req = new MerchantListReq();
        req.setNeedOtherTableFields(true);
        req.setMerchantType("4");
        ResultVO<List<MerchantDTO>> resultVO = merchantService.listMerchant(req);
        List<MerchantDTO> list = resultVO.getResultData();
        for (MerchantDTO dto : list) {
            if ("4300002063684".equals(dto.getMerchantId())) {
                System.out.print("merchantId = "+dto.getMerchantId());
            }
        }
//        System.out.print("结果：" + JSON.toJSONString(resultVO.toString()));
    }

    @Test
    public void page() {
        MerchantPageReq req = new MerchantPageReq();
//        req.setMerchantTypeList(Lists.newArrayList( "2"));
//        req.setLanIdList(Lists.newArrayList("730", "731"));
//        req.setCityList(Lists.newArrayList("73001", "73101"));
//        req.setLanIdList(Lists.newArrayList());
//        req.setCityList(Lists.newArrayList());
//        req.setTagId("11222");
//        req.setMerchantCode("12345");
        req.setPageSize(1000);
        req.setMerchantName("郑小文");
        ResultVO<Page<MerchantPageResp>> resultVO = merchantService.pageMerchant(req);
        MerchantPageResp dto = resultVO.getResultData().getRecords().get(0);
        System.out.print("结果：" + dto.getMerchantId());
    }

    @Test
    public void pageRetailMerchant() {
        RetailMerchantPageReq req = new RetailMerchantPageReq();
//        req.setTagId("11222");
//        req.setMerchantCode("12345");
        ResultVO resultVO = merchantService.pageRetailMerchant(req);
        System.out.print("结果：" + resultVO.toString());
    }

    @Test
    public void pageSupplyMerchant() {
        SupplyMerchantPageReq req = new SupplyMerchantPageReq();
//        req.setTagId("11222");
//        req.setMerchantCode("12345");
        ResultVO resultVO = merchantService.pageSupplyMerchant(req);
        System.out.print("结果：" + resultVO.toString());
    }

    @Test
    public void pageFactoryMerchant() {
        FactoryMerchantPageReq req = new FactoryMerchantPageReq();
//        req.setTagId("11222");
//        req.setMerchantCode("12345");
        ResultVO resultVO = merchantService.pageFactoryMerchant(req);
        System.out.print("结果：" + resultVO.toString());
    }

    public static void main(String[] args) {
        List list1 = new ArrayList();
        list1.add("1111");
        list1.add("2222");
        list1.add("3333");

        List list2 = new ArrayList();
        list2.add("3333");
        list2.add("4444");
        list2.add("5555");


        //并集
        //list1.addAll(list2);
        //交集
//        list1.retainAll(list2);
        list1.retainAll(Lists.newArrayList("1111"));
        //差集
        //list1.removeAll(list2);
        //无重复并集
//        list2.removeAll(list1);
//        list1.addAll(list2);

        System.out.print(list1.toString());
    }

    @Test
    public void getMerchantForOrder() {
        MerchantGetReq req = new MerchantGetReq();
        req.setMerchantId("4300001063072");
        ResultVO resultVO = merchantService.getMerchantForOrder(req);
        System.out.print("结果：" + resultVO.toString());
    }

    @Test
    public void listMerchantForOrder() {
        MerchantLigthReq req = new MerchantLigthReq();
        req.setMerchantName("廖");
        ResultVO resultVO = merchantService.listMerchantForOrder(req);
        System.out.print("结果：" + resultVO.toString());
    }
    @Test
    public void getMerchantIdList() {
        List<String> l =merchantService.getMerchantIdList("中绿");
        System.out.print("结果：" + l.toString());
    }
}
