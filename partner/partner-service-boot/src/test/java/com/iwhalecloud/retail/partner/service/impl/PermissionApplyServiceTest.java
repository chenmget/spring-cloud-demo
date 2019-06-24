//package com.iwhalecloud.retail.partner.service.impl;
//
//import com.iwhalecloud.retail.dto.ResultVO;
//import com.iwhalecloud.retail.partner.PartnerServiceApplication;
//import com.iwhalecloud.retail.partner.dto.req.PermissionApplyListReq;
//import com.iwhalecloud.retail.partner.dto.req.PermissionApplySaveReq;
//import com.iwhalecloud.retail.partner.dto.req.PermissionApplyUpdateReq;
//import com.iwhalecloud.retail.partner.service.PermissionApplyService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * @author wenlong.zhong
// * @date 2019/3/30
// */
//@SpringBootTest(classes = PartnerServiceApplication.class)
//@RunWith(SpringRunner.class)
//public class PermissionApplyServiceTest {
//
//    @Autowired
//    private PermissionApplyService permissionApplyService;
//
//    @Test
//    public void save() {
//        PermissionApplySaveReq req = new PermissionApplySaveReq();
//        req.setApplyCode("12345");
//        req.setApplyName("测试申请单");
//        req.setApplyType("10");
//        req.setMerchantId("12345");
//        req.setLanId("731");
//        req.setRegionId("73101");
//        ResultVO resultVO = permissionApplyService.savePermissionApply(req);
//        System.out.print("结果：" + resultVO.toString());
//    }
//
//    @Test
//    public void update() {
//        PermissionApplyUpdateReq req = new PermissionApplyUpdateReq();
//        req.setApplyId("10120517");
//        req.setApplyCode("1234566");
//        req.setApplyName("测试申请单uu");
//        req.setApplyType("10");
//        req.setMerchantId("1234566");
//        ResultVO resultVO = permissionApplyService.updatePermissionApply(req);
//        System.out.print("结果：" + resultVO.toString());
//    }
//
//    @Test
//    public void list() {
//        PermissionApplyListReq req = new PermissionApplyListReq();
////        req.setApplyName("测试申请单u");
////        req.setApplyType("10");
//        req.setMerchantId("1234566");
//        ResultVO resultVO = permissionApplyService.listPermissionApply(req);
//        System.out.print("结果：" + resultVO.toString());
//    }
//
//}
