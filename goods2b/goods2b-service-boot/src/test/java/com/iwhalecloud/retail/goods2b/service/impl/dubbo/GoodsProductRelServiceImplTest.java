package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.dto.ActivityGoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.BuyCountCheckDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsProductRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsProductRelEditReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsProductRelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class GoodsProductRelServiceImplTest {

    @Autowired
    private GoodsProductRelService goodsProductRelService;

    @Test
    public void updateIsHaveStock() {
        String supplierId = "4301811025392";
        String productId = "1082241745046700034";
        GoodsProductRelEditReq goodsProductRelEditReq = new GoodsProductRelEditReq();
        goodsProductRelEditReq.setMerchantId(supplierId);
        goodsProductRelEditReq.setProductId(productId);
        goodsProductRelEditReq.setIsHaveStock(true);
        ResultVO<Boolean> resultVO = goodsProductRelService.updateIsHaveStock(goodsProductRelEditReq);
        System.out.println(resultVO.isSuccess());
    }

    @Test
    public void qryMinAndMaxNum() {
        String goodsId = "1083659059575103489";
        String productId = "1082241745046700034";
        GoodsProductRelEditReq goodsProductRelEditReq = new GoodsProductRelEditReq();
        goodsProductRelEditReq.setGoodsId(goodsId);
        goodsProductRelEditReq.setProductId(productId);
        ResultVO<GoodsProductRelDTO> resultVO = goodsProductRelService.qryMinAndMaxNum(goodsProductRelEditReq);
        System.out.println(resultVO.isSuccess());
    }

    @Test
    public void checkBuyCount() {
        String goodsId = "10412641";
        String productId = "10412537";
        String merchantId = "4301811025392";
        String userId = "1077840960118870018";
        List<BuyCountCheckDTO> buyCountCheckDTOList = Lists.newArrayList();
        BuyCountCheckDTO buyCountCheckDTO = new BuyCountCheckDTO();
        buyCountCheckDTO.setGoodsId(goodsId);
        buyCountCheckDTO.setProductId(productId);
        buyCountCheckDTO.setBuyCount(1L);
        buyCountCheckDTOList.add(buyCountCheckDTO);
        GoodsProductRelEditReq goodsProductRelEditReq = new GoodsProductRelEditReq();
        goodsProductRelEditReq.setBuyCountCheckDTOList(buyCountCheckDTOList);
        goodsProductRelEditReq.setMerchantId(merchantId);
        goodsProductRelEditReq.setUserId(userId);
        ResultVO<Boolean> resultVO = goodsProductRelService.checkBuyCount(goodsProductRelEditReq);
        System.out.println(resultVO.isSuccess());
    }

    @Test
    public void test() {
        List<String> productIdList = new ArrayList<>();
        productIdList.add("1077895352720969729");
        productIdList.add("1082241745046700034");
        String regionId = "430100";
        String lanId = "731";
        String merchantId="11";
        String pathCode = "1000000020.843000000000000.843073100000000.843073105040000.843073105041041.110025773777.110025774594";
        ResultVO<List<ActivityGoodsDTO>> listResultVO = goodsProductRelService.qryActivityGoodsId(productIdList,merchantId,pathCode);

        System.out.println(listResultVO.getResultData());
    }
}