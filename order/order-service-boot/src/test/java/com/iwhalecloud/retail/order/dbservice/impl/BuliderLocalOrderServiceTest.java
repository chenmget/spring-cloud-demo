package com.iwhalecloud.retail.order.dbservice.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.order.OrderServiceApplication;
import com.iwhalecloud.retail.order.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order.dto.model.ContractPInfoModel;
import com.iwhalecloud.retail.order.dto.model.OrderGoodsItemModel;
import com.iwhalecloud.retail.order.dto.resquest.BuilderOrderRequest;
import com.iwhalecloud.retail.order.ropservice.BuilderOrderManagerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceApplication.class)
public class BuliderLocalOrderServiceTest {


    @Autowired
    BuilderOrderManagerService orderManagerService;

    @Test
    public void builderOrder() {
        for (int i = 0; i < 1; i++) {
            BuilderOrderRequest builderOrderRequestDTO = new BuilderOrderRequest();

            builderOrderRequestDTO.setUserSessionId("5e5503e17a484ecca603308b98e44c061");
            builderOrderRequestDTO.setMemberId("1069570693743599618");

            /**
             * 商品信息
             */
            List<OrderGoodsItemModel> goods = new ArrayList<>();
            OrderGoodsItemModel g1 = new OrderGoodsItemModel();
//            g1.setProductId("181116224500572196");
//            g1.setGoodsNum(1);
//            goods.add(g1);
//            OrderGoodsItemDTO g2 = new OrderGoodsItemDTO();
//            g2.setProductId("181116308800571893");
//            g2.setGoodsNum(1);
//            goods.add(g2);
            OrderGoodsItemModel g3 = new OrderGoodsItemModel();
            g3.setProductId("1068790565764931586");
            g3.setGoodsNum(2);
            goods.add(g3);


            builderOrderRequestDTO.setGoodsItem(goods);

         //备注
            builderOrderRequestDTO.setRemark("gs_20181008测试2018" + System.currentTimeMillis() + ":" + i);
            //地址
            builderOrderRequestDTO.setAddressId("1068743167017533441");
            //发货类型 1 2
            builderOrderRequestDTO.setShipType("2");
            //厅店id
            builderOrderRequestDTO.setUserId("2342432");
            //1:APP；2：微商城-普通单；3：微商城-商机单；4：其它
            builderOrderRequestDTO.setTypeCode(1);
            //1:在线支付  2货到付款 3线下支付
            builderOrderRequestDTO.setPayType("1");
            builderOrderRequestDTO.setOrderType("1");
            builderOrderRequestDTO.setBindType("0");
            builderOrderRequestDTO.setSourceType(OrderManagerConsts.ORDER_SOURCE_TYPE_GWC);
//            builderOrderRequestDTO.setCouponCode("1063338930068381697");
//            builderOrderRequestDTO.setCouponDesc("使用优惠券");
            /**
             * 发票信息
             */
            builderOrderRequestDTO.setInvoiceTitle(1); //发票抬头：1 个人 2单位
            builderOrderRequestDTO.setInvoiceType(1); //  //发票类型：1普通发票、2增值发票
            builderOrderRequestDTO.setInvoiceDetail("invoiceDetail");
            builderOrderRequestDTO.setInvoiceTitleDesc("invoiceTitleDesc");
            System.out.println(JSON.toJSONString(builderOrderRequestDTO));

            /**
             * 合约信息
             */
            ContractPInfoModel contractPInfoDTO=new ContractPInfoModel();
            contractPInfoDTO.setContractName("zzzz");
            contractPInfoDTO.setIcNum("360782000000000000");
            contractPInfoDTO.setAuthentication("aabbcccdddeee");
            contractPInfoDTO.setContractPhone("18988889999");
            contractPInfoDTO.setPhone("13250299515");
            builderOrderRequestDTO.setContractInfo(contractPInfoDTO);
            JSON.toJSONString(builderOrderRequestDTO);
            orderManagerService.builderOrderLocal(builderOrderRequestDTO);
        }

    }

    @Test
    public  void testBuilderOrder(){
        String params="{\"addressId\":\"1070955114116542466\",\"userId\":\"4301211024068\",\"sourceShopName\":\"佳来通讯@长沙佳来通讯器材有限公司（虚拟）\",\"payType\":1,\"remark\":\"\",\"shipType\":1,\"typeCode\":\"1\",\"orderType\":\"1\",\"invoiceTitle\":1,\"invoiceType\":1,\"invoiceDetail\":1,\"shippingAmount\":1,\"sourceType\":\"GWCGM\",\"bindType\":0,\"goodsItem\":[{\"goodsNum\":2,\"productId\":\"1070506882498260994\"},{\"goodsNum\":1,\"productId\":\"1070506882603118594\"}],\"contractInfo\":{\"contractName\":\"\",\"contractPhone\":\"\",\"icNum\":\"\",\"authentication\":\"\",\"phone\":\"\"}}";
        BuilderOrderRequest builderOrderRequestDTO = JSON.parseObject(params,BuilderOrderRequest.class);
        orderManagerService.builderOrderLocal(builderOrderRequestDTO);


    }


}