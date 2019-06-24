package com.iwhalecloud.retail.goods.service.impl.dubbo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods.GoodsServiceApplication;
import com.iwhalecloud.retail.goods.dto.ProdGoodsDTO;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.*;
import com.iwhalecloud.retail.goods.dto.resp.*;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoodsServiceApplication.class)
public class ProdGoodsServiceImplTest {

    @Autowired
    private ProdGoodsService prodGoodsService;

    @Test
    public void addGoods() {
        ProdGoodsAddReq req = new ProdGoodsAddReq();
        req.setName("这是一个苹果手机18代");
        req.setSellingPoint("系统流畅");
        req.setSord(1L);
        req.setSearchKey("终端");
        req.setCatId("600002");
        req.setRegionId("430100,430300");
        req.setRegionName("长沙市,湘潭市");
        req.setSupperId("1");
        req.setBrandId("181109996900045649");
        req.setTagId(new String[]{"15","16","17"});
        req.setSpecPrices(new String[]{"100","100"});
        req.setSpecValues(new String[]{"黑,32G,100M","黑,32G,1000M"});
        req.setStores(new String[]{"20","50"});
        req.setTagId(new String[]{"1"});
        req.setDetailImageFile("https://gy.ztesoft.com/group1/M00/00/04/Ci0vWlv7k3iAdFUwAAIcAP2vKJ019.jpeg");
        req.setRollImageFile("https://gy.ztesoft.com/group1/M00/00/04/Ci0vWVv7hNmAOX6DAAH73Wpe7QM25.jpeg");
        req.setPrice(100D);
        req.setMktprice(110D);
        req.setRecommendList(new String[]{"181123245100592944","181123586500592622"});
        req.setContractPeriod(36L);
        req.setOfferList(new String[]{"1068059849173889026"});
        req.setTerminalList(new String[]{"1067698200642428929"});
        ResultVO<ProdGoodsAddResp> respResultVO = prodGoodsService.addGoods(req);
        System.out.println(respResultVO.isSuccess());
    }

    @Test
    public void editGoods (){
        ProdGoodsEditReq req = new ProdGoodsEditReq();
        req.setGoodsId("1069908556747907073");
        req.setName("这是一个苹果手机18代");
        req.setSellingPoint("系统流畅");
        req.setSord(1L);
        req.setSearchKey("终端");
        req.setCatId("600002");
        req.setRegionId("430100,430300");
        req.setRegionName("长沙市,湘潭市");
        req.setSupperId("1");
        req.setBrandId("181109996900045649");
        req.setTagId(new String[]{"15","16","17"});
        req.setSpecPrices(new String[]{"100","100"});
        req.setSpecValues(new String[]{"黑,32G,100M","黑,32G,1000M"});
        req.setStores(new String[]{"20","50"});
        req.setTagId(new String[]{"0","0"});
        req.setDetailImageFile("https://gy.ztesoft.com/group1/M00/00/04/Ci0vWlv7k3iAdFUwAAIcAP2vKJ019.jpeg");
        req.setRollImageFile("https://gy.ztesoft.com/group1/M00/00/04/Ci0vWVv7hNmAOX6DAAH73Wpe7QM25.jpeg");
        req.setPrice(100D);
        req.setMktprice(110D);
        req.setRecommendList(new String[]{"181123245100592944","181123586500592622"});
        req.setContractPeriod(36L);
        req.setOfferList(new String[]{"1068059849173889026"});
        req.setTerminalList(new String[]{"1067698200642428929"});
        ResultVO<ProdGoodsEditResp> resultVO = prodGoodsService.editGoods(req);
        System.out.println(resultVO.isSuccess());
    }

    @Test
    public void queryGoodsForPage() {
        ProdGoodsQueryReq prodGoodsQueryReq = new ProdGoodsQueryReq();
        prodGoodsQueryReq.setPageNo(0);
        prodGoodsQueryReq.setPageSize(2);
        List<String> list = Lists.newArrayList();
        list.add("1068064486270455810");
        list.add("1068409016645865474");
        prodGoodsQueryReq.setIds(list);
        prodGoodsQueryReq.setCatId("600002");
        prodGoodsQueryReq.setSearchKey("这是一个手机");
        ResultVO<Page<ProdGoodsDTO>> resultVO = prodGoodsService.queryGoodsForPage(prodGoodsQueryReq);
        System.out.println(resultVO.toString());
    }

    @Test
    public void listGoods() {
        ProdGoodsListReq prodGoodsListReq = new ProdGoodsListReq();
        List<String> list = Lists.newArrayList();
        list.add("1068064486270455810");
        list.add("1068409016645865474");
        prodGoodsListReq.setIds(list);
        prodGoodsListReq.setCatId("600002");
        prodGoodsListReq.setSearchKey("这是一个手机");
        ResultVO<List<ProdGoodsDTO>> resultVO = prodGoodsService.listGoods(prodGoodsListReq);
        System.out.println(resultVO.toString());
    }

    @Test
    public void deleteProdGoods() {
        ProdGoodsDeleteReq req = new ProdGoodsDeleteReq();
        req.setGoodsId("1067741555057270786");
        ResultVO<ProdGoodsDeleteResp> resultVO = prodGoodsService.deleteGoods(req);
        System.out.println(resultVO.isSuccess());
    }

    @Test
    public void queryGoodsDetail() {
        String goodsId = "1069915357984333825";
        ResultVO<ProdGoodsDetailResp> respResultVO = prodGoodsService.queryGoodsDetail(goodsId);
        System.out.println(respResultVO.isSuccess());
    }

    @Test
    public void qryGoodsByProductId() {
        String productId = "1069915358273740802";
        ResultVO<QryGoodsByProductIdResp> respResultVO = prodGoodsService.qryGoodsByProductId(productId);
        System.out.println(respResultVO.isSuccess());
    }

    @Test
    public void updateMarketEnableByPrimaryKey() {
        String goodsId = "1069481235930959874";
        Integer marketEnable = 1;
        ResultVO<Boolean> respResultVO = prodGoodsService.updateMarketEnableByPrimaryKey(goodsId, marketEnable);
        System.out.println(respResultVO.isSuccess());
    }

    @Test
    public void updateBuyCountById() {
        List<UpdateBuyCountByIdReq> req = Lists.newArrayList();
        UpdateBuyCountByIdReq item = new UpdateBuyCountByIdReq();
        item.setGoodsId("1073117186112552962");
        item.setBuyCount(2);
        req.add(item);
        ResultVO<Boolean> respResultVO = prodGoodsService.updateBuyCountById(req);
        System.out.println(respResultVO.isSuccess());
    }
}