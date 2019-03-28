package com.iwhalecloud.retail.rights;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import com.iwhalecloud.retail.rights.dto.SelectedCouponDTO;
import com.iwhalecloud.retail.rights.dto.request.*;
import com.iwhalecloud.retail.rights.entity.CouponApplyObject;
import com.iwhalecloud.retail.rights.entity.MktResCoupon;
import com.iwhalecloud.retail.rights.manager.CouponApplyObjectManager;
import com.iwhalecloud.retail.rights.manager.CouponInstManager;
import com.iwhalecloud.retail.rights.manager.CouponSupplyRuleManager;
import com.iwhalecloud.retail.rights.manager.MktResCouponManager;
import com.iwhalecloud.retail.rights.service.CouponInstService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RightsServiceApplication.class)
public class CouponInstTest {

    @Reference
    private CouponInstService couponInstService;

    @Autowired
    private CouponApplyObjectManager couponApplyObjectManager;

    @Autowired
    private CouponInstManager couponInstManager;

    @Autowired
    private MktResCouponManager mktResCouponManager;

    @Autowired
    private CouponSupplyRuleManager couponSupplyRuleManager;


    @Test
    public void inputRights() throws ParseException {
        InputRightsRequestDTO req = new InputRightsRequestDTO();
        req.setGmtCreate("1");
        req.setInventoryNum(10);
        req.setRightsId("1103229902445555713"); // ('1103230346290999298','1103229902445555713')
        ResultVO respResultVO = couponInstService.inputRights(req);
        log.info(JSON.toJSONString(respResultVO));
    }

    @Test
    public void saveRights() {
        SaveRightsRequestDTO req = new SaveRightsRequestDTO();
        req.setCustNum("1077840960118870018"); // login_name = supplier_ground
        req.setMktResId("1103229902445555713"); //('1103230346290999298','1103229902445555713')
        req.setSupplyNum(1L);
        ResultVO respResultVO = couponInstService.saveRights(req);
        log.info(JSON.toJSONString(respResultVO));
    }

    @Test
    public void orderCouponList() {
        OrderCouponListReq req = new OrderCouponListReq();

//{"activityId":"100000336",
// "selectedCouponList":[{"couponInstId":"100008309","orderItemId":"1"}],
// "couponInsList":[{"couponCode":"100008309","couponDesc":"三否","couponMKId":"1107536226419392513","couponType":"1","productId":"100000861"}],
// "goodsItem":[{"priceDiscount":0,"discount":"","goodsId":"100008147","num":1,"orderItemId":1,"price":120000,"productId":"100000861"}],
// "merchantId":"4301811025392","sourceType":"LJGM"}

        req.setUserId("4301811022885");
        req.setUserMerchantId("4301811022885");
        req.setMerchantId("");
        CouponOrderItemDTO orderItem = new CouponOrderItemDTO();
        orderItem.setGoodsId("100008147");
        orderItem.setProductId("100000861");
        orderItem.setNum(1);
        orderItem.setOrderItemId("1");
        orderItem.setPrice(120000D);
        orderItem.setPriceDiscount(0D);
        req.setOrderItemList(Lists.newArrayList(orderItem));
        SelectedCouponDTO selectedCouponDTO = new SelectedCouponDTO();
        selectedCouponDTO.setCouponInstId("100008872");
        selectedCouponDTO.setOrderItemId("1");
        req.setSelectedCouponList(Lists.newArrayList(selectedCouponDTO));
        ResultVO resultVO = couponInstService.orderCouponList(req);
        log.info(JSON.toJSONString(resultVO));
    }

    @Test
    public void listCouponInstDetail() {
        QueryCouponInstReqDTO req = new QueryCouponInstReqDTO();
        req.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNUSED);
        req.setCustNum("151012609600174677");
        List list = couponInstManager.listCouponInstDetail(req);
        log.info(JSON.toJSONString(list));
    }

    @Test
    public void listCouponApplyObject() {
        CouponApplyObjectListReq couponApplyObjectListReq = new CouponApplyObjectListReq();
        couponApplyObjectListReq.setMktResId("2001");
        List<CouponApplyObject> couponApplyObjectList = couponApplyObjectManager.listCouponApplyObject(couponApplyObjectListReq);
        log.info(JSON.toJSONString(couponApplyObjectList));
    }

    @Test
    public void inputRightsTest() throws ParseException {
        List<MktResCoupon> mktResCoupons = mktResCouponManager.queryCouponByActId("1103985864034988033");
        for (MktResCoupon mktResCoupon : mktResCoupons) {
            InputRightsRequestDTO req = new InputRightsRequestDTO();
            req.setGmtCreate("1");
            req.setInventoryNum(10);
            req.setRightsId(mktResCoupon.getMktResId());
            ResultVO respResultVO = couponInstService.inputRights(req);
            log.info(JSON.toJSONString(respResultVO));
        }
    }


    public static void main(String[] args) {

        Double d1 = new Double(2);
        Double d2 = new Double(1);
//        System.out.print(Double.compare(d1, d2));
        int num = (new Double(0.999999)).intValue();
//        System.out.print(Double.max(couponOrderItemDTO.getDiscount(), d2));
//        System.out.print(Objects.isNull(couponOrderItemDTO.getDiscount()));
        System.out.print(num);
    }

}
