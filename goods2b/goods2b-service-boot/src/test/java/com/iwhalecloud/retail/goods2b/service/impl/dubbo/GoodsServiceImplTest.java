package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.Goods2BServiceApplication;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsPageResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Goods2BServiceApplication.class)
public class GoodsServiceImplTest {

    @Autowired
    private GoodsService goodsService;

    @Test
    public void addGoods() throws Exception {
        GoodsAddReq req = new GoodsAddReq();
        req.setSellingPoint("卖点");
        req.setSourceFrom("111");
        req.setMarketEnable(GoodsConst.MARKET_ENABLE);
        req.setSn("商品编码");
        List<RegionReq> regionList = Lists.newArrayList();
        RegionReq regionReq = new RegionReq();
        regionReq.setRegionId("73101");
        regionReq.setRegionName("芙蓉区");
        regionReq.setLanId("731");
        regionList.add(regionReq);
        req.setRegionList(regionList);
        List<GoodsTargetReq> goodsTargetReqList = new ArrayList<>();
        GoodsTargetReq goodsTargetReq = new GoodsTargetReq();
        goodsTargetReq.setTargetId("11");
        goodsTargetReqList.add(goodsTargetReq);
        req.setTargetList(goodsTargetReqList);
        List<String> tagList = Lists.newArrayList();
        tagList.add("1");
        tagList.add("1070147537508532226");
        req.setTagList(tagList);
        req.setChannelType("渠道类型");
        req.setEffDate(new Date());
        req.setExpDate(new Date());
        req.setGoodsCatId("1077490510579699713");
        req.setBrandId("1070614496378155009");
        req.setSupplierId("1");
        req.setGoodsName("这是一个商品测试");
        req.setTypeId("1");
        req.setMktprice(100.0);
        req.setTagList(tagList);
        req.setTargetType("2");
        List<FileAddReq> fileAddReqList = Lists.newArrayList();
        FileAddReq fileAddReq = new FileAddReq();
        fileAddReq.setSubType("7");
        fileAddReq.setFileUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vWlxAJguALu5tACZN0FL_pyw101.mp4");
        fileAddReq.setThumbnailUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vV1xAILmAItIKAAiNFO5xTAU799.png");
        fileAddReqList.add(fileAddReq);
        fileAddReq.setSubType("2");
        fileAddReq.setFileUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vWlxAJfyAGOObAAFsYkfsuEY330.jpg");
        fileAddReq.setThumbnailUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vWlxAJfyAGOObAAFsYkfsuEY330.jpg");
        fileAddReqList.add(fileAddReq);
        fileAddReq.setSubType("6");
        fileAddReq.setFileUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vWVxAH_uAUJvrAAG6Rod2V_k463.jpg");
        fileAddReq.setThumbnailUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vWVxAH_uAUJvrAAG6Rod2V_k463.jpg");
        fileAddReqList.add(fileAddReq);
        fileAddReq.setSubType("3");
        fileAddReq.setFileUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vWlxAJgKAAvZsAAG6Rod2V_k475.jpg");
        fileAddReq.setThumbnailUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vWlxAJgKAAvZsAAG6Rod2V_k475.jpg");
        fileAddReqList.add(fileAddReq);
        req.setFileAddReqList(fileAddReqList);
        String str = JSON.toJSONString(req);
        System.out.println(str);
        ResultVO resultVO = goodsService.addGoods(req);
        System.out.println(resultVO.getResultData());
    }

    @Test
    public void editGoods() throws Exception {
        GoodsEditReq req = new GoodsEditReq();
        req.setSourceFrom("hnyhj_b2b");
        req.setGoodsId("1105079964503736322");
        req.setSellingPoint("卖点更新");
        req.setMarketEnable(GoodsConst.MARKET_ENABLE);
        req.setSn("商品编码更新");
        req.setTargetType("1");
        List<RegionReq> regionList = Lists.newArrayList();
        RegionReq regionReq = new RegionReq();
        regionReq.setRegionId("73102");
        regionReq.setRegionName("岳麓区");
        regionReq.setLanId("731");
        regionList.add(regionReq);
        req.setRegionList(regionList);
        List<String> tagList = Lists.newArrayList();
        tagList.add("1");
        tagList.add("1070147537508532226");
        req.setTagList(tagList);
        req.setChannelType("渠道类型");
        req.setEffDate(new Date());
        req.setExpDate(new Date());
        req.setGoodsCatId("1077490510579699713");
        req.setBrandId("1070614496378155009");
        req.setSupplierId("1");
        req.setGoodsName("这是一个商品测试");
        req.setTypeId("1");
        req.setMktprice(100.0);
        req.setTagList(tagList);
        List<FileAddReq> fileAddReqList = Lists.newArrayList();
        FileAddReq fileAddReq = new FileAddReq();
        fileAddReq.setSubType("7");
        fileAddReq.setFileUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vWlxAJguALu5tACZN0FL_pyw101.mp4");
        fileAddReq.setThumbnailUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vV1xAILmAItIKAAiNFO5xTAU799.png");
        fileAddReqList.add(fileAddReq);
        fileAddReq.setSubType("2");
        fileAddReq.setFileUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vWlxAJfyAGOObAAFsYkfsuEY330.jpg");
        fileAddReq.setThumbnailUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vWlxAJfyAGOObAAFsYkfsuEY330.jpg");
        fileAddReqList.add(fileAddReq);
        fileAddReq.setSubType("6");
        fileAddReq.setFileUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vWVxAH_uAUJvrAAG6Rod2V_k463.jpg");
        fileAddReq.setThumbnailUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vWVxAH_uAUJvrAAG6Rod2V_k463.jpg");
        fileAddReqList.add(fileAddReq);
        fileAddReq.setSubType("3");
        fileAddReq.setFileUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vWlxAJgKAAvZsAAG6Rod2V_k475.jpg");
        fileAddReq.setThumbnailUrl("https://gy.ztesoft.com/group1/M00/00/16/Ci0vWlxAJgKAAvZsAAG6Rod2V_k475.jpg");
        fileAddReqList.add(fileAddReq);
        req.setFileAddReqList(fileAddReqList);
        String str = JSON.toJSONString(req);
        System.out.println(str);
        ResultVO resultVO = goodsService.editGoods(req);
        System.out.println(resultVO.getResultData());
    }

    @Test
    public void deleteGoods() {
        String goodsId = "1077375563869319169";
        GoodsDeleteReq req = new GoodsDeleteReq();
        req.setGoodsId(goodsId);
        ResultVO resultVO = goodsService.deleteGoods(req);
        System.out.println(resultVO.isSuccess());
    }

    @Test
    public void queryGoodsForPage() {
        GoodsForPageQueryReq req = new GoodsForPageQueryReq();
        req.setPageNo(1);
        req.setPageSize(20);
//        req.setSourceFrom("hnyhj_b2b");
        req.setIsLogin(true);
        List<String> supplierIdList = new ArrayList<>();
        supplierIdList.add("4301811025392");
        req.setSupplierIdList(supplierIdList);
        List<String> productIds = new ArrayList<>();
        productIds.add("100000861");
        req.setProductIds(productIds);
        /*List<String> list = Lists.newArrayList();
        list.add("1077375563869319169");
        req.setIds(list);
        List<AttrSpecValueReq> attrSpecValueReqs = Lists.newArrayList();
        AttrSpecValueReq attrSpecValueReq = new AttrSpecValueReq();
        attrSpecValueReq.setAttrId("11");
        List<String> attrSpecValueList = Lists.newArrayList();
        attrSpecValueList.add("全网通");
        attrSpecValueList.add("电信");
        attrSpecValueList.add("移动");
        attrSpecValueReq.setAttrSpecValuesList(attrSpecValueList);
        attrSpecValueReqs.add(attrSpecValueReq);

        AttrSpecValueReq attrSpecValueReq1 = new AttrSpecValueReq();
        attrSpecValueReq1.setAttrId("8");
        List<String> attrSpecValueList1 = Lists.newArrayList();
        attrSpecValueList1.add("金色");
        attrSpecValueList1.add("黑色");
        attrSpecValueList1.add("银色");
        attrSpecValueReq1.setAttrSpecValuesList(attrSpecValueList1);
        attrSpecValueReqs.add(attrSpecValueReq1);

        req.setAttrSpecValueList(attrSpecValueReqs);*/

        /*try {
            String jsonStr = "[{\"attrId\":\"11\",\"attrSpecValuesList\":[\"全网通\",\"电信\",\"移动\"]},{\"attrId\":\"8\"," +
                    "\"attrSpecValuesList\":[\"金色\",\"黑色\",\"银色\"]}]";
            List<AttrSpecValueReq> attrSpecValueList = (List<AttrSpecValueReq>) JSON.parse(jsonStr);
            System.out.println(attrSpecValueList.size());
        } catch (Exception ex) {
            ex.toString();
        }*/
        ResultVO resultVO = goodsService.queryGoodsForPage(req);
        System.out.println(resultVO.isSuccess());
    }

    @Test
    public void updateMarketEnableByPrimaryKey() {
        GoodsMarketEnableReq goodsMarketEnableReq = new GoodsMarketEnableReq();
        String goodsId = "1086074948152938498";
        Integer marketEnable = 1;
        goodsMarketEnableReq.setGoodsId(goodsId);
        goodsMarketEnableReq.setMarketEnable(marketEnable);
        ResultVO resultVO = goodsService.updateMarketEnable(goodsMarketEnableReq);
        System.out.println(resultVO.isSuccess());
    }

    @Test
    public void updateBuyCountById() {
        GoodsBuyCountByIdReq goodsBuyCountByIdReq = new GoodsBuyCountByIdReq();
        List<UpdateBuyCountByIdReq> list = Lists.newArrayList();
        UpdateBuyCountByIdReq req = new UpdateBuyCountByIdReq();
        req.setGoodsId("1098756635756294145");
        req.setProductId("1098427109759229953");
        req.setBuyCount(-1);
        list.add(req);
        goodsBuyCountByIdReq.setUpdateBuyCountByIdReqList(list);
        ResultVO resultVO = goodsService.updateBuyCountById(goodsBuyCountByIdReq);
        System.out.println(resultVO.isSuccess());
    }

    @Test
    public void testQuery(){
        GoodsPageReq req = new GoodsPageReq();
        req.setSn("001101");
        req.setProductBaseId("1078633867515666433");
        req.setUnitType("single");
        List<String> brandList = new ArrayList<>();
        brandList.add("1068723084526751745");
        req.setBrandIdList(brandList);
        ResultVO<Page<GoodsPageResp>> pageResultVO = goodsService.queryPageByConditionAdmin(req);
        System.out.println(pageResultVO.getResultData());
    }
}