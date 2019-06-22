package com.iwhalecloud.retail.goods.service.impl;

import com.iwhalecloud.retail.goods.GoodsServiceApplication;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.*;
import com.iwhalecloud.retail.goods.dto.resp.ComplexGoodsGetResp;
import com.iwhalecloud.retail.goods.entity.ProdGoodsCat;
import com.iwhalecloud.retail.goods.manager.ProdBrandManager;
import com.iwhalecloud.retail.goods.mapper.GoodsExtMapper;
import com.iwhalecloud.retail.goods.mapper.ProdGoodsCatMapper;
import com.iwhalecloud.retail.goods.service.dubbo.ProdBrandService;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsCatService;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GoodsServiceApplication.class)
public class ProdBrandServiceImplTest {

	@Autowired
	private ProdBrandService goodsBrandService;
	@Autowired
	private ProdGoodsCatService goodsCatsService;
	@Autowired
	private ProdGoodsCatMapper prodGoodsCatMapper;
	@Autowired
	private ProdGoodsService prodGoodsService;
	@Autowired
	private GoodsExtMapper goodsExtMapper;
	@Autowired
	private ProdBrandManager prodBrandManager;
	
	
    @Test
    public void addBrand(){
    	ProdBrandAddReq req = new ProdBrandAddReq();
    	req.setBrandCode("100000");
    	req.setBrief("魅族");
    	req.setName("魅族");
    	req.setUrl("www.meizu.com");
        goodsBrandService.addBrand(req);
    }

    @Test
    public void deleteBrand(){
        String brandId = "199999111111";
        ProdBrandDeleteReq req = new ProdBrandDeleteReq();
        req.setBrandId(brandId);
        goodsBrandService.deleteBrand(req);
        
    }

    @Test
    public void updateBrand(){
    	ProdBrandUpdateReq req = new ProdBrandUpdateReq();
    	req.setBrandId("199999111111");
    	req.setBrandCode("120000");
    	req.setBrief("国际品牌");
    	req.setName("乔丹2");
    	req.setUrl("www.jordenFALK.com");
    	goodsBrandService.updateBrand(req);
    }
    
    @Test
    public void listBrand(){
    	ResultVO listBrand = goodsBrandService.listBrand();
    	System.out.println(listBrand.toString());
    }
    @Test
    public void getBrand(){
    	ProdBrandGetReq req = new ProdBrandGetReq();
    	req.setBrandId("199999111111");
    	ResultVO brand = goodsBrandService.getBrand(req);
    	System.out.println(brand);
    }
    
    @Test
    public void listCats(){
    	ProdGoodsCatsListByIdReq req = new ProdGoodsCatsListByIdReq();
        req.setParentCatId("0");
    	ResultVO listCats = goodsCatsService.listCats(req);
    	System.out.println(listCats.toString());
    }
    
    @Test
    public void getComplexGoods(){
    	ResultVO<List<ComplexGoodsGetResp>> complexGoods = goodsCatsService.getComplexGoods("10000000");
    	System.out.println(complexGoods.toString());
    }
    
    @Test
    public void addCatComplex(){
    	List<ProdCatComplexAddReq> req = new ArrayList<ProdCatComplexAddReq>();
    	for (int i = 0; i < 1; i++) {
			ProdCatComplexAddReq t = new ProdCatComplexAddReq();
			t.setCatId("150922161500073797");
			t.setGoodsId("1068409016645865474");
			t.setGoodsName("这是一个锤子手机");
			req.add(t);
		}
    	ResultVO brand = goodsCatsService.addCatComplex(req);
    	System.out.println(brand);
    }
    
    @Test
    public void insertGoodsCat(){
    	ProdGoodsCat entity = new ProdGoodsCat();
    	entity.setName("海量产品");
    	entity.setParentId(0L);
    	entity.setCatPath("0|1");
    	int insert = prodGoodsCatMapper.insert(entity);
    	System.out.println(insert);
    }
    
    @Test
    public void insertGoodsbatch(){
    	/*Map<String, Object> data = POIUtils.getData("D:/file/zte_file/商品信息合稿.xlsx");
    	List<ProdGoodsAddReq> goodsList = (List<ProdGoodsAddReq>)data.get("goodsList");
    	List<GoodsExt> extList = (List<GoodsExt>)data.get("extList");
    	List<String> goodsIdList = new ArrayList<>(goodsList.size());
    	Integer snum = 0;
    	for (ProdGoodsAddReq prodGoodsAddReq : goodsList) {
    		String brandName = prodGoodsAddReq.getBrandId();
    		String brandId = null;
    		List<ProdBrand> list = prodBrandManager.selectSpecBySpecId(brandName);
//    		List<ProdBrand> list = prodBrandManager.selectSpecBySpecId(brandName);
    		if (null != brandName) {
    			if (null == list) {
    				ProdBrandAddReq req = new ProdBrandAddReq();
    				req.setBrandCode("100000");
    				req.setBrief(brandName);
    				req.setName(brandName);
    				ResultVO<String> addBrand = goodsBrandService.addBrand(req);
    				brandId = addBrand.getResultData();
    			}else {
//    				brandId = list.get(0).getBrandId();
    				brandId = list.get(0).getBrandId();
    			}
			}
    		prodGoodsAddReq.setBrandId(brandId);
    		prodGoodsAddReq.setRegionId("430100");
    		prodGoodsAddReq.setRegionName("长沙市");
    		ResultVO<ProdGoodsAddResp> addGoods = prodGoodsService.addGoods(prodGoodsAddReq);
    		ProdGoodsAddResp resultData = addGoods.getResultData();
    		if (null == resultData) {
				System.out.println("插入失败");
				return;
			}
    		goodsIdList.add(resultData.getGoodsId());
    		snum += 1;
		}
    	System.out.println("prodGoods插入成功数据 :::::::::::::::::"+snum);
    	Integer num = 0;
    	for (int i = 0; i < extList.size(); i++) {
    		GoodsExt goodsExt = extList.get(i);
    		String goodsId = goodsIdList.get(i);
    		goodsExt.setGoodsId(goodsId);
    		num += goodsExtMapper.insert(goodsExt);
		}
    	System.out.println("GoodsExt插入成功数据 :::::::::::::::::"+num);*/
    }
}
