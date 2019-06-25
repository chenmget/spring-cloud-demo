package com.iwhalecloud.retail.promo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsQueryByProductIdsReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProdFileReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsProductRelService;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.entity.ActivityProduct;
import com.iwhalecloud.retail.promo.entity.MarketingActivity;
import com.iwhalecloud.retail.promo.manager.ActivityProductManager;
import com.iwhalecloud.retail.promo.manager.MarketingActivityManager;
import com.iwhalecloud.retail.promo.service.ProdFileBingToService;
import com.ztesoft.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Component("prodFileBingToService")
public class ProdFileBingToServiceImpl implements ProdFileBingToService  {

	@Autowired
    private MarketingActivityManager marketingActivityManager;
	
	@Autowired
    private ActivityProductManager activityProductManager;
	
	@Reference
    private GoodsProductRelService goodsProductRelService;
	
	@Override
	public void goodsBingProdFile() {
		log.info("**********************开始查询生效的预售活动******************start****");
		// 查询生效的预售活动
        List<MarketingActivity> advanceSaleActivityList = marketingActivityManager.queryActivityListByStatus(false, PromoConst.ACTIVITYTYPE.BOOKING.getCode());
        //生效的活动配的图插入到prod_file表
        for(int i=0;i<advanceSaleActivityList.size();i++) {
        	MarketingActivity marketingActivity = advanceSaleActivityList.get(i);
        	String marketingActivityId = marketingActivity.getId();//营销活动ID
        	log.info("**********************通过活动ID查act_activity_product(参与活动产品表)******************start****");
        	 List<ActivityProduct> activityProducts = activityProductManager.queryActivityProductByCondition(marketingActivityId); //通过活动ID查  fileUrl  ，  productPicUseType
        	 for(int j=0;j<activityProducts.size();j++) {
        		 ActivityProduct activityProduct = activityProducts.get(j);
        		 String fileUrl = activityProduct.getProductPic();//	附件路径
        		 if(fileUrl == null) {
        			 break ;
        		 }
        		 String productPicUseType = activityProduct.getProductPicUseType();
        		 String productId = activityProduct.getProductId();//取出产品ID，去查商品ID
        		 List<String> productIdList = new ArrayList<String>();
        		 productIdList.add(productId);
        		 GoodsQueryByProductIdsReq req = new GoodsQueryByProductIdsReq();
                 req.setProductIds(productIdList);
                 log.info("**********************获取这个产品下的所有goods_id******************start**** param={}",JSON.toJSONString(req));
        		 List<String> goodsIdsList = goodsProductRelService.queryGoodsIdsByProductIds(req).getResultData().getGoodsIds();//获取这个产品下的所有goods_id
        		 if(CollectionUtils.isEmpty(goodsIdsList)){
					 break ;
				 }
        		 for(int k=0;k<goodsIdsList.size();k++) {
            		 ProdFileReq prodFileReq = new ProdFileReq();
            		//插表prod_file
            		 String targetId = goodsIdsList.get(k);//商品ID
            		 //如果商品已经配了图片，就continue
            		 String isBinging = goodsProductRelService.isBindingToPricture(targetId);
            		 if(Integer.parseInt(isBinging)>0) {
            			 continue ;
            		 }
            		 String fileId = goodsProductRelService.selectProdFileId();
            		 if("1".equals(productPicUseType)) {//应用到商品详情轮拨图8
            			 prodFileReq.setFileId(fileId);
            			 prodFileReq.setFileType("1");//	1：图片 2：文件
            			 prodFileReq.setTargetType("1");//商品图片：1 	订单图片：2 	商品规格图片：3
            			 prodFileReq.setCreateDate(new Date());
            			 prodFileReq.setSubType("8");//当关联对象类型为商品时， 子类型为 1：默认图片 2：轮播图片 3：详情图片    8的时候说明是配置的活动图片
            			 prodFileReq.setTargetId(targetId);
            			 prodFileReq.setFileUrl(fileUrl);
            			 goodsProductRelService.insertProdFile(prodFileReq); 
            		 }else if("2".equals(productPicUseType)) {//应用到商品列表缩略图9
            			 prodFileReq.setFileId(fileId);
            			 prodFileReq.setFileType("1");//	1：图片 2：文件
            			 prodFileReq.setTargetType("1");//商品图片：1 订单图片：2 商品规格图片：3
            			 prodFileReq.setCreateDate(new Date());
            			 prodFileReq.setSubType("9");//当关联对象类型为商品时， 子类型为 1：默认图片 2：轮播图片 3：详情图片,6缩略图    8的时候说明是配置的活动图片
            			 prodFileReq.setTargetId(targetId);
            			 prodFileReq.setFileUrl(fileUrl);
            			 goodsProductRelService.insertProdFile(prodFileReq);
            		 }else if ("12".equals(productPicUseType)) {//两者都应用10
            			 prodFileReq.setFileId(fileId);
            			 prodFileReq.setFileType("1");//	1：图片 2：文件
            			 prodFileReq.setTargetType("1");//商品图片：1 订单图片：2 商品规格图片：3
            			 prodFileReq.setCreateDate(new Date());
            			 prodFileReq.setSubType("10");//当关联对象类型为商品时， 子类型为 1：默认图片 2：轮播图片 3：详情图片,6缩略图    8的时候说明是配置的活动图片
            			 prodFileReq.setTargetId(targetId);
            			 prodFileReq.setFileUrl(fileUrl);
            			 goodsProductRelService.insertProdFile(prodFileReq);
            		 }
        		 }
        	 }
        }
        
        log.info("**********************开始查询生效的前置补贴活动******************start****");
        // 查询生效的前置补贴活动
        List<MarketingActivity> subsidyActivityList = marketingActivityManager.queryActivityListByStatus(false, PromoConst.ACTIVITYTYPE.PRESUBSIDY.getCode());
        //生效的活动配的图插入到prod_file表
        for(int i=0;i<subsidyActivityList.size();i++) {
        	MarketingActivity marketingActivity = subsidyActivityList.get(i);
        	String marketingActivityId = marketingActivity.getId();//营销活动ID
        	
        	 List<ActivityProduct> activityProducts = activityProductManager.queryActivityProductByCondition(marketingActivityId); //通过活动ID查  fileUrl  ，  productPicUseType
        	 for(int j=0;j<activityProducts.size();j++) {
        		 ActivityProduct activityProduct = activityProducts.get(j);
            	 
        		 String fileUrl = activityProduct.getProductPic();//	附件路径
        		 if(fileUrl == null) {
        			 break ;
        		 }
        		 String productPicUseType = activityProduct.getProductPicUseType();
        		 String productId = activityProduct.getProductId();//取出产品ID，去查商品ID
        		 List<String> productIdList = new ArrayList<String>();
        		 productIdList.add(productId);
        		 GoodsQueryByProductIdsReq req = new GoodsQueryByProductIdsReq();
                 req.setProductIds(productIdList);
        		 List<String> goodsIdsList = goodsProductRelService.queryGoodsIdsByProductIds(req).getResultData().getGoodsIds();//获取这个产品下的所有goods_id
        		 if(CollectionUtils.isEmpty(goodsIdsList)){
					 break ;
				 }
				 for(int k=0;k<goodsIdsList.size();k++) {
            		 ProdFileReq prodFileReq = new ProdFileReq();
            		//插表prod_file
            		 String targetId = goodsIdsList.get(k);//商品ID
            		//如果商品已经配了图片，就continue
            		 String isBinging = goodsProductRelService.isBindingToPricture(targetId);
            		 if(Integer.parseInt(isBinging)>0) {
            			 continue ;
            		 }
            		 String fileId = goodsProductRelService.selectProdFileId();
            		 if("1".equals(productPicUseType)) {//应用到商品详情轮拨图8
            			 prodFileReq.setFileId(fileId);
            			 prodFileReq.setFileType("1");//	1：图片 2：文件
            			 prodFileReq.setTargetType("1");//商品图片：1 	订单图片：2 	商品规格图片：3
            			 prodFileReq.setCreateDate(new Date());
            			 prodFileReq.setSubType("8");//当关联对象类型为商品时， 子类型为 1：默认图片 2：轮播图片 3：详情图片    8的时候说明是配置的活动图片
            			 prodFileReq.setTargetId(targetId);
            			 prodFileReq.setFileUrl(fileUrl);
            			 goodsProductRelService.insertProdFile(prodFileReq); 
            		 }else if("2".equals(productPicUseType)) {//应用到商品列表缩略图9
            			 prodFileReq.setFileId(fileId);
            			 prodFileReq.setFileType("1");//	1：图片 2：文件
            			 prodFileReq.setTargetType("1");//商品图片：1 订单图片：2 商品规格图片：3
            			 prodFileReq.setCreateDate(new Date());
            			 prodFileReq.setSubType("9");//当关联对象类型为商品时， 子类型为 1：默认图片 2：轮播图片 3：详情图片,6缩略图    8的时候说明是配置的活动图片
            			 prodFileReq.setTargetId(targetId);
            			 prodFileReq.setFileUrl(fileUrl);
            			 goodsProductRelService.insertProdFile(prodFileReq);
            		 }else if ("12".equals(productPicUseType)) {//两者都应用10
            			 prodFileReq.setFileId(fileId);
            			 prodFileReq.setFileType("1");//	1：图片 2：文件
            			 prodFileReq.setTargetType("1");//商品图片：1 订单图片：2 商品规格图片：3
            			 prodFileReq.setCreateDate(new Date());
            			 prodFileReq.setSubType("10");//当关联对象类型为商品时， 子类型为 1：默认图片 2：轮播图片 3：详情图片,6缩略图    8的时候说明是配置的活动图片
            			 prodFileReq.setTargetId(targetId);
            			 prodFileReq.setFileUrl(fileUrl);
            			 goodsProductRelService.insertProdFile(prodFileReq);
            			 
            		 }
        			 
        		 }
        		 
        	 }
        	
        }
        
	}

	@Override
	public void goodsUnBundlingProdFile() {
		log.info("**********************开始查询失效的预售活动******************start****");
		// 查询失效的预售活动
        List<MarketingActivity> advanceSaleActivityList = marketingActivityManager.queryActivityListByStatus(true, PromoConst.ACTIVITYTYPE.BOOKING.getCode());
        for(int i=0;i<advanceSaleActivityList.size();i++) {
        	MarketingActivity marketingActivity = advanceSaleActivityList.get(i);
        	String marketingActivityId = marketingActivity.getId();//营销活动ID
        	
       	 	List<ActivityProduct> activityProducts = activityProductManager.queryActivityProductByCondition(marketingActivityId);
       	 	
       	 	for(int j=0;j<activityProducts.size();j++) {
       	 		ActivityProduct activityProduct = activityProducts.get(j);
       	 		String productId = activityProduct.getProductId();
       	 		List<String> productIdList = new ArrayList<String>();
       	 		productIdList.add(productId);
       	 		GoodsQueryByProductIdsReq req = new GoodsQueryByProductIdsReq();
       	 		req.setProductIds(productIdList);
       	 		List<String> goodsIdsList = goodsProductRelService.queryGoodsIdsByProductIds(req).getResultData().getGoodsIds();//获取这个产品下的所有goods_id
	       	 	if(CollectionUtils.isEmpty(goodsIdsList)){
					 break ;
				}
       	 		for(int k=0;k<goodsIdsList.size();k++) {
       	 			String goodsId = goodsIdsList.get(k);
       	 			goodsProductRelService.delProdFileByTargetId(goodsId);
       	 			
       	 		}
       	 	}
       	 	
        }
        log.info("**********************开始查询失效的前置补贴活动******************start****");
        // 查询失效的前置补贴活动
        List<MarketingActivity> subsidyActivityList = marketingActivityManager.queryActivityListByStatus(true, PromoConst.ACTIVITYTYPE.PRESUBSIDY.getCode());
        for(int i=0;i<subsidyActivityList.size();i++) {
        	MarketingActivity marketingActivity = subsidyActivityList.get(i);
        	String marketingActivityId = marketingActivity.getId();//营销活动ID
        	
       	 	List<ActivityProduct> activityProducts = activityProductManager.queryActivityProductByCondition(marketingActivityId);
       	 	
       	 	for(int j=0;j<activityProducts.size();j++) {
       	 		ActivityProduct activityProduct = activityProducts.get(j);
       	 		String productId = activityProduct.getProductId();
       	 		List<String> productIdList = new ArrayList<String>();
       	 		productIdList.add(productId);
       	 		GoodsQueryByProductIdsReq req = new GoodsQueryByProductIdsReq();
       	 		req.setProductIds(productIdList);
       	 		List<String> goodsIdsList = goodsProductRelService.queryGoodsIdsByProductIds(req).getResultData().getGoodsIds();//获取这个产品下的所有goods_id
	       	 	if(CollectionUtils.isEmpty(goodsIdsList)){
					 break ;
				}
       	 		for(int k=0;k<goodsIdsList.size();k++) {
       	 			String goodsId = goodsIdsList.get(k);
       	 			goodsProductRelService.delProdFileByTargetId(goodsId);
       	 			
       	 		}
       	 	}
       	 	
        }
	}

}
