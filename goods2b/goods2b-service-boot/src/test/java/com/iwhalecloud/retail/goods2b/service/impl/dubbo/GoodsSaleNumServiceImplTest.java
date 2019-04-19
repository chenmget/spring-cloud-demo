package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.GoodsSaleNumDTO;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsSaleNumService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2019/4/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes =Goods2BServiceApplication.class)
public class GoodsSaleNumServiceImplTest {
    @Resource
    private GoodsSaleNumService goodsSaleNumService;

    @Test
    public void test(){

        goodsSaleNumService.cleanCacheGoodSaleNum(GoodsConst.CACHE_KEY_GOODS_SALE_ORDER_30);
        goodsSaleNumService.cleanCacheGoodSaleNum(GoodsConst.CACHE_KEY_GOODS_SALE_ORDER_7);

        ResultVO<List<GoodsSaleNumDTO>> resultVO = goodsSaleNumService.getGoodsSaleOrder(GoodsConst.CACHE_KEY_GOODS_SALE_ORDER_30);
        ResultVO<List<GoodsSaleNumDTO>> resultVO1 = goodsSaleNumService.getGoodsSaleOrder(GoodsConst.CACHE_KEY_GOODS_SALE_ORDER_7);

        System.out.println(resultVO.getResultData());
    }
}
